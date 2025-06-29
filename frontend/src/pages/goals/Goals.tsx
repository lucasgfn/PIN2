import React, { useState, useEffect } from "react";
import { Box, Button, Container, Paper, Typography } from "@mui/material";
import Header from "../../components/header/Header";
import ReadGoals from "./ReadGoals";
import PageGoals from "./PageGoals";
import PerfilComprador from "../../components/comprador/PerfilComprador";
import {
  usePutDiasLidos,
  usePostQuantidadePaginas,
  useFetchGoal,
  useFetchDiasLidos,
} from "../../hook/useGoalData";
import { useAuth } from "../../contexts/AuthContext";
import { Navigate } from "react-router-dom";
import MonthGoals from "./MonthGoals";

const Goals: React.FC = () => {
  const { userData, isLogged, logout } = useAuth();

  // Redireciona se não logado ou sem dados do usuário
  if (!isLogged || !userData) {
    logout();
    return <Navigate to="/login" />;
  }

  const compradorId = userData.id;

  const { data: goalData } = useFetchGoal(compradorId);
  const { data: diasLidosData } = useFetchDiasLidos(compradorId);

  const [diasLidos, setDiasLidos] = useState<string[]>([]);
  const [quantidadePaginas, setQuantidadePaginas] = useState<number | "">("");

  useEffect(() => {
    if (goalData?.quantidadePaginas !== undefined) {
      setQuantidadePaginas(goalData.quantidadePaginas);
    }
  }, [goalData]);

  useEffect(() => {
    if (diasLidosData) {
      setDiasLidos(diasLidosData);
    }
  }, [diasLidosData]);

  const { mutate: salvarDiasLidos } = usePutDiasLidos();
  const { mutate: salvarGoal } = usePostQuantidadePaginas(compradorId);

  const handleSalvar = () => {
    if (quantidadePaginas !== "") {
      salvarGoal({
        quantidadePaginas: Number(quantidadePaginas),
        diasLidos,
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
            width: "100%",
          }}
        >
          <Box sx={{ width: "25%", overflow: "auto" }}>
            <PerfilComprador />
          </Box>
          <Box sx={{ width: "70%" }}>
            <Container maxWidth="lg">
              <Paper
                elevation={3}
                sx={{
                  p: 6,
                  mt: 8,
                  borderRadius: 10,
                  border: "2px solid",
                  borderColor: "#FF4081",
                  backgroundColor: "#F5F5F5",
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
                    <Box sx={{ display: "flex", gap: 4 }}>
                      <Box sx={{ flex: 1 }}>
                        <ReadGoals
                          diasSelecionados={diasLidos}
                          setDiasSelecionados={setDiasLidos}
                        />
                      </Box>
                      <Box
                        sx={{
                          flex: 1,
                          backgroundColor: "#F5F5F5",
                          borderRadius: 2,
                        }}
                      >
                        <PageGoals
                          quantidadePaginas={quantidadePaginas}
                          setQuantidadePaginas={setQuantidadePaginas}
                        />
                      </Box>
                    </Box>
                  </Paper>
                </Box>
              </Paper>
            </Container>
            <Paper
              elevation={3}
              sx={{
                p: 6,
                mt: 8,
                borderRadius: 10,
                border: "2px solid",
                borderColor: "#8A2BE2",
                backgroundColor: "#F5F5F5",
              }}
            >
              <Box sx={{ width: "70%" }}>
                <MonthGoals />
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
