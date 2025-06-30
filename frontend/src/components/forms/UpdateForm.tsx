import { Box, Button, Container, Paper, Typography } from "@mui/material";
import { useState, useEffect } from "react";
import CustomTextField from "./Fields/CustomTextField";
import ImageUpload from "./Fields/ImageUpload";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../../contexts/AuthContext";
import { useUpdateData } from "../../hook/useUserData";  
import type { IUserData } from "../../interface/IUserData";

const UpdateForm: React.FC = () => {
  const { userData } = useAuth();
  const navigate = useNavigate();

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [nome, setNome] = useState("");
  const [endereco, setEndereco] = useState("");
  const [bairro, setBairro] = useState("");
  const [cep, setCEP] = useState("");
  const [cidade, setCidade] = useState("");
  const [estado, setEstado] = useState("");
  const [telefone, setTelefone] = useState("");
  const [email, setEmail] = useState("");
  const [cpf, setCpf] = useState("");
  const [imagem, setImagem] = useState<File | null>(null);
  const [error, setError] = useState<string | null>(null);

  const mutationUpdate = useUpdateData(); // hook para PUT

  useEffect(() => {
    if (userData) {
      setUsername(userData.username || "");
      setPassword(userData.password || "");
      setNome(userData.nome || "");
      setEndereco(userData.rua || "");
      setBairro(userData.bairro || "");
      setCEP(userData.cep || "");
      setCidade(userData.cidade || "");
      setEstado(userData.estado || "");
      setTelefone(userData.telefone || "");
      setEmail(userData.email || "");
      setCpf(userData.cpf || "");
    }
  }, [userData]);

  const fileToBase64 = (file: File): Promise<string> =>
    new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => resolve(reader.result as string);
      reader.onerror = reject;
    });

  const handleUpdate = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);

    try {
      let imagemBase64: string | undefined;
      if (imagem) {
        imagemBase64 = await fileToBase64(imagem);
      }

      const updatedUser: IUserData = {
        ...userData!,
        username,
        password,
        nome,
        rua: endereco,
        bairro,
        cep,
        cidade,
        estado,
        telefone,
        email,
        cpf,
        img: imagemBase64 || userData?.img,
      };

      mutationUpdate.mutate(updatedUser);
      navigate("/perfil");
    } catch (err) {
      console.error(err);
      setError("Erro ao atualizar usuário.");
    }
  };

  return (
    <Box sx={{ backgroundColor: "#F5F5F5", minHeight: "100vh", py: 4 }}>
      <Container maxWidth="lg">
        <Paper
          elevation={3}
          sx={{
            p: 6,
            borderRadius: 20,
            border: "2px solid",
            borderColor: "#623333",
            backgroundColor: "#F5F5F5",
          }}
        >
          <Box component="form" onSubmit={handleUpdate}>
            <CustomTextField
              label="Username"
              type="text"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
            />
            <CustomTextField
              label="Senha"
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
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
              inputProps={{ maxLength: 11 }}
              value={cpf}
              onChange={(e) => {
                const val = e.target.value;
                if (/^\d{0,11}$/.test(val)) setCpf(val);
              }}
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
              inputProps={{ maxLength: 8 }}
              value={cep}
              onChange={(e) => {
                const val = e.target.value;
                if (/^\d{0,8}$/.test(val)) setCEP(val);
              }}
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
              inputProps={{ maxLength: 11 }}
              value={telefone}
              onChange={(e) => {
                const val = e.target.value;
                if (/^\d{0,11}$/.test(val)) setTelefone(val);
              }}
            />
            <CustomTextField
              label="Email"
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />

            <Box mt={3}>
              <ImageUpload onImageSelect={(file) => setImagem(file)} />
            </Box>

            {error && (
              <Typography color="error" mt={2}>
                {error}
              </Typography>
            )}

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
                Atualizar
              </Button>
            </Box>
          </Box>
        </Paper>
      </Container>
    </Box>
  );
};

export default UpdateForm;
