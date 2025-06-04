import { Box, Button, Container, Paper, TextField } from "@mui/material";
import { useState } from "react";
import { useNavigate } from 'react-router-dom';

import logo from "../../assets/logoSemFundo.png";


const LoginForm : React.FC = () => {
    const [username, setUsername] = useState('');
    const [senha, setSenha] = useState('');
    const navigate = useNavigate();

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        // REALIZAR LOGICA AUTENTICACAO AQUI
        console.log("Login Sucesso!", {username, senha});
    }

    const handleCadastrar = (e: React.FormEvent) => {
        navigate('/cadastro');
    }

    return (
      <>
        <Box sx={{ backgroundColor: "#F5F5F5", minHeight: "100vh", py: 4 }}>
          <Container maxWidth="sm">
            <Paper
              elevation={3}
              sx={{
                p: 6,
                mt: 8,
                borderRadius: 20,
                border: "2px solid",
                borderColor: "purple",
                backgroundColor: "#F5F5F5",
              }}
            >
              <img
                src={logo}
                alt={"logo"}
                style={{ marginLeft: "80px", width: "300px", height: "auto" }}
              />
              <Box
                component="form"
                onSubmit={handleSubmit}
                sx={{
                  "& .MuiOutlinedInput-root": {
                    borderRadius: 3,
                    "& fieldset": {
                      borderColor: "deeppink",
                    },
                  },
                  "& label.Mui-focused": {
                    color: "purple",
                    "& .MuiOutlinedInput-root.Mui-focused .MuiOutlinedInput-notchedOutline":
                      {
                        borderColor: "pink",
                      },
                  },
                  backgroundColor: "#F5F5F5",
                }}
              >
                <TextField
                  label="Username"
                  type="text"
                  fullWidth
                  margin="normal"
                  required
                  value={username}
                  onChange={(e) => setUsername(e.target.value)}
                />
                <TextField
                  className=""
                  label="Senha"
                  type="password"
                  fullWidth
                  margin="normal"
                  required
                  value={senha}
                  onChange={(e) => setSenha(e.target.value)}
                />

                <Box
                  mt={3}
                  display="flex"
                  gap={2}
                  justifyContent={"space-around"}
                >
                  <Button
                    variant="contained"
                    color="secondary"
                    type="submit"
                    sx={{
                      fontSize: "1.1rem",
                      px: 7,
                      borderRadius: 3,
                      backgroundColor: "#007BFF",
                      "&:hover": {
                        backgroundColor: "deeppink",
                      },
                    }}
                  >
                    {" "}
                    Entrar
                  </Button>
                  <Button
                    variant="outlined"
                    onClick={handleCadastrar}
                    sx={{
                      fontSize: "0.9rem",
                      px: 7,
                      borderRadius: 3,
                      color: "#000",
                      borderColor: "deeppink",
                    }}
                  >
                    {" "}
                    Cadastra-se
                  </Button>
                </Box>
              </Box>
            </Paper>
          </Container>
        </Box>
      </>
    );


}

export default LoginForm;