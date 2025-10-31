import React, { useState, useEffect } from "react";
import { Box, Button, Paper } from "@mui/material";
import Header from "../../components/header/Header";
import ReadGoals from "./ReadGoals";
import PageGoals from "./PageGoals";
import PerfilComprador from "../../components/comprador/PerfilComprador";
import {
  usePutDiasLidos,
  usePostQuantidadePaginas,
  useFetchGoal,
  useFetchDiasLidos,
  useUpdateMonthGoals,
} from "../../hook/useGoalData";
import { useAuth } from "../../contexts/AuthContext";
import { Navigate } from "react-router-dom";
import MonthGoals from "./MonthGoals";

const Goals: React.FC = () => {
  const { userData, isLogged, logout } = useAuth();

  if (!isLogged || !userData) {
    logout();
    return <Navigate to="/login" />;
  }

  const compradorId = userData.id;

  // Hooks de dados
  const { data: goalData } = useFetchGoal(compradorId);
  const { data: diasLidosData } = useFetchDiasLidos(compradorId);

  // Estados locais
  const [diasLidos, setDiasLidos] = useState<string[]>([]);
  const [quantidadePaginas, setQuantidadePaginas] = useState<number | "">("");
  const [metasMensais, setMetasMensais] = useState<string[]>([]);

  // Atualiza estados quando os dados são carregados
  useEffect(() => {
    if (goalData) {
      if (goalData.quantidadePaginas !== undefined) {
        setQuantidadePaginas(goalData.quantidadePaginas);
      }
      if (goalData.metasMensais) {
        setMetasMensais(goalData.metasMensais);
      }
    }
  }, [goalData]);

  useEffect(() => {
    if (diasLidosData) {
      setDiasLidos(diasLidosData);
    }
  }, [diasLidosData]);

  // Mutations
  const { mutate: salvarDiasLidos } = usePutDiasLidos();
  const { mutate: salvarGoal } = usePostQuantidadePaginas(compradorId);
  const { mutate: updateMetasMensaisMutate } = useUpdateMonthGoals(
    goalData?.id ?? 0,
    {
      onSuccess: (data) => {
        setMetasMensais(data.metasMensais || []);
      },
      onError: (error) => {
        console.error("Erro ao atualizar metas mensais:", error);
      },
    }
  );

  // Atualiza metas mensais
  const handleSetMetasMensais = (novasMetas: string[]) => {
    setMetasMensais(novasMetas);
    if (goalData?.id) {
      updateMetasMensaisMutate(novasMetas);
    }
  };

  // Botão de salvar (com criação automática se não existir meta)
  const handleSalvar = () => {
    if (quantidadePaginas === "") {
      alert("Informe a quantidade de páginas antes de salvar.");
      return;
    }

    // Caso ainda não exista meta, cria uma nova
    if (!goalData?.id) {
      salvarGoal({
        quantidadePaginas: Number(quantidadePaginas),
        diasLidos,
        metasMensais,
      });
      alert("Meta criada com sucesso!");
      return;
    }

    // Caso já exista, atualiza apenas os dias lidos
    salvarDiasLidos({ id: goalData.id, dias: diasLidos });
    alert("Metas atualizadas com sucesso!");
  };

  return (
    <>
      <Header />
      <Box sx={{ backgroundColor: "#F5F5F5", minHeight: "100vh", py: 4 }}>
        <Box
          sx={{
            display: "flex",
            flexDirection: "row",
            justifyContent: "center",
            gap: 4,
          }}
        >
          {/* Perfil do comprador */}
          <Box sx={{ width: "30%", overflow: "auto" }}>
            <PerfilComprador />
          </Box>

          {/* Seção principal */}
          <Box sx={{ width: "80%" }}>
            {/* Metas de leitura diárias e quantidade de páginas */}
            <Paper
              elevation={3}
              sx={{
                p: 6,
                mt: 8,
                borderRadius: 10,
                border: "2px solid",
                borderColor: "#FF4081",
                backgroundColor: "#F5F5F5",
                width: "90%",
              }}
            >
              <Box
                sx={{
                  backgroundColor: "#F5F5F5",
                  display: "flex",
                  gap: 4,
                  justifyContent: "left",
                }}
              >
                {/* Dias lidos */}
                <Paper
                  elevation={3}
                  sx={{
                    p: 6,
                    borderRadius: 10,
                    border: "2px solid",
                    borderColor: "#007BFF",
                    backgroundColor: "#F5F5F5",
                    flex: 1,
                  }}
                >
                  <ReadGoals
                    diasSelecionados={diasLidos}
                    setDiasSelecionados={setDiasLidos}
                  />
                </Paper>

                {/* Quantidade de páginas */}
                <Box
                  sx={{
                    flex: 1,
                    backgroundColor: "#F5F5F5",
                    borderRadius: 2,
                    p: 2,
                  }}
                >
                  <PageGoals
                    quantidadePaginas={quantidadePaginas}
                    setQuantidadePaginas={setQuantidadePaginas}
                  />
                </Box>
              </Box>
            </Paper>

            {/* Metas mensais */}
            <Paper
              elevation={3}
              sx={{
                p: 6,
                mt: 8,
                borderRadius: 10,
                border: "2px solid",
                borderColor: "#8A2BE2",
                backgroundColor: "#F5F5F5",
                width: "90%",
              }}
            >
              <Box sx={{ width: "90%" }}>
                <MonthGoals
                  metasMensais={metasMensais}
                  setMetasMensais={handleSetMetasMensais}
                />
              </Box>
            </Paper>
          </Box>
        </Box>

        {/* Botão de salvar */}
        <Box display="flex" justifyContent="center" mt={6}>
          <Button
            variant="outlined"
            sx={{
              fontSize: "1.3rem",
              px: 7,
              borderRadius: 20,
              borderColor: "#335D62",
              color: "#335D62",
              "&:hover": {
                backgroundColor: "transparent",
                borderColor: "#335D62",
              },
            }}
            onClick={handleSalvar}
          >
            Salvar
          </Button>
        </Box>
      </Box>
    </>
  );
};

export default Goals;
