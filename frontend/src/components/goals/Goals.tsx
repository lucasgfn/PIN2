import Header from "../header/Header";
import { Box, Button, Container, Paper, TextField } from "@mui/material";
import ReadGoals from "./components/ReadGoals";
import PageGoals from "./components/PageGoals";
import MonthGoals from "./components/MonthGoals";
const Goals: React.FC = () => {
  return (
    <>
      <Header />
      <Box sx={{ backgroundColor: "#F5F5F5", minHeight: "100vh", py: 4 }}>
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
                    <ReadGoals />
                  </Box>
                  <Box
                    sx={{
                      flex: 1,
                      backgroundColor: "#F5F5F5",
                      borderRadius: 2,
                    }}
                  >
                    <PageGoals />
                  </Box>
                </Box>
              </Paper>
            </Box>

            <Paper
              elevation={3}
              sx={{
                p: 6,
                mt: 6,
                borderRadius: 10,
                border: "2px solid",
                borderColor: "#8A2BE2",
                backgroundColor: "#F5F5F5",
              }}
            >
            <Box>
                <MonthGoals/>
            </Box>
            </Paper>
          </Paper>
        </Container>
        <Box display="flex" justifyContent="center" mt={4}>
          <Button
            variant="outlined"
            type="submit"
            sx={{
              fontSize: "1.3rem",
              px: 7,
              borderRadius: 20,
              borderColor: "#335D62",
              color: "#335D62",
              "&:hover": {
                backgroundColor: "transparent",
                borderColor: "#335D62",
                marginBottom: 40,
              },
            }}
          >
          Salvar
          </Button>
        </Box>
      </Box>
    </>
  );
};

export default Goals;
