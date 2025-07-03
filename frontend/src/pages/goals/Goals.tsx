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

  const { data: goalData } = useFetchGoal(compradorId);
  const { data: diasLidosData } = useFetchDiasLidos(compradorId);

  const [diasLidos, setDiasLidos] = useState<string[]>([]);
  const [quantidadePaginas, setQuantidadePaginas] = useState<number | "">("");
  const [metasMensais, setMetasMensais] = useState<string[]>([]);

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

  const handleSetMetasMensais = (novasMetas: string[]) => {
    setMetasMensais(novasMetas);
    if (goalData?.id) {
      updateMetasMensaisMutate(novasMetas);
    }
  };

  const handleSalvar = () => {
    if (quantidadePaginas !== "") {
      salvarGoal({
        quantidadePaginas: Number(quantidadePaginas),
        diasLidos,
        metasMensais,
      });
    }

    if (goalData?.id) {
      salvarDiasLidos({ id: goalData.id, dias: diasLidos });
    } else {
      alert("Meta ainda não carregada, aguarde...");
    }
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
          <Box sx={{ width: "30%", overflow: "auto" }}>
            <PerfilComprador />
          </Box>
          <Box sx={{ width: "80%" }}>
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
                {/* Paper azul envolvendo só ReadGoals */}
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

                {/* PageGoals fora do Paper azul */}
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
