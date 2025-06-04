import { Box, Button, Container, Paper } from "@mui/material";
import { useState } from "react";
import CustomTextField from "./Fields/CustomTextField";

const RegisterForm: React.FC = () => {
  const [username, setUsername] = useState("");
  const [senha, setSenha] = useState("");
  const [nome, setNome] = useState("");
  const [endereco, setEndereco] = useState("");
  const [bairro, setBairro] = useState("");
  const [cep, setCEP] = useState("");
  const [cidade, setCidade] = useState("");
  const [estado, setEstado] = useState("");
  const [telefone, setTelefone] = useState("");
  const [email, setEmail] = useState("");
  const [cpf, setCpf] = useState("");


  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    console.log("Login Sucesso!", {
      username,
      senha,
      nome,
      endereco,
      bairro,
      cep,
      cidade,
      estado,
    });
  };

  return (
    <>
      <Container maxWidth="lg">
        <Paper
          elevation={3}
          sx={{
            p: 6,
            mt: 8,
            borderRadius: 20,
            border: "2px solid",
            borderColor: "#623333",
          }}
        >
          <Box component="form" onSubmit={handleSubmit}>
            <CustomTextField
              label="Username"
              type="text"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
            />
            <CustomTextField
              label="Senha"
              type="password"
              value={senha}
              onChange={(e) => setSenha(e.target.value)}
            />
            <CustomTextField
              label="Nome"
              type="text"
              value={nome}
              onChange={(e) => setNome(e.target.value)}
            />
            <CustomTextField
              label="CPF"
              type="text"
              value={cpf}
              onChange={(e) => {
                const val = e.target.value;
                // só números, até 11 dígitos
                if (/^\d{0,11}$/.test(val)) {
                  setCpf(val);
                }
              }}
              inputProps={{ maxLength: 11 }}
            />
            <CustomTextField
              label="Endereço"
              type="text"
              value={endereco}
              onChange={(e) => setEndereco(e.target.value)}
            />
            <CustomTextField
              label="Bairro"
              type="text"
              value={bairro}
              onChange={(e) => setBairro(e.target.value)}
            />
            <CustomTextField
              label="Cidade"
              type="text"
              value={cidade}
              onChange={(e) => setCidade(e.target.value)}
            />
            <CustomTextField
              label="CEP"
              type="text"
              value={cep}
              onChange={(e) => {
                const val = e.target.value;
                if (/^\d{0,8}$/.test(val)) {
                  setCEP(val);
                }
              }}
              inputProps={{ maxLength: 8 }}
            />
            <CustomTextField
              label="Estado"
              type="text"
              value={estado}
              onChange={(e) => setEstado(e.target.value)}
            />
            <CustomTextField
              label="Telefone"
              type="text"
              value={telefone}
              onChange={(e) => {
                const val = e.target.value;
                // só números, até 11 dígitos (DDD + número)
                if (/^\d{0,11}$/.test(val)) {
                  setTelefone(val);
                }
              }}
              inputProps={{ maxLength: 11 }}
            />
            <CustomTextField
              label="Email"
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </Box>
        </Paper>
      </Container>

      <Box display="flex" justifyContent="center" mt={4}>
        <Button
          variant="outlined"
          type="submit"
          sx={{
            fontSize: "1.1rem",
            px: 7,
            borderRadius: 3,
            borderColor: "#335D62",
            color: "#335D62",
            "&:hover": {
              backgroundColor: "transparent",
              borderColor: "#335D62",
            },
          }}
        >
          Salvar
        </Button>
      </Box>
    </>
  );
};

export default RegisterForm;
