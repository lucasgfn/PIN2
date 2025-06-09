import { Box, Button, Container, Paper } from "@mui/material";
import { useState } from "react";
import CustomTextField from "./Fields/CustomTextField";
import ImageUpload from "./Fields/ImageUpload";
import logo from "../../assets/logo/logo_short.jpg";
import { useSendData } from "../../hook/useUserData";
import type { IUserData } from "../../interface/IUserData";
import { useNavigate } from "react-router-dom";

const RegisterForm: React.FC = () => {
  const [username, setUsername] = useState("");
  const [password, setPasssword] = useState("");
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

  const mutation = useSendData();
  const navigate = useNavigate();
  const [error, setError] = useState<string | null>(null);

  //Para converter img
  const fileToBase64 = (file: File): Promise<string> =>
    new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => resolve(reader.result as string);
      reader.onerror = reject;
    });

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try{
      let imagemBase64: string | undefined;
      if (imagem) {
        imagemBase64 = await fileToBase64(imagem);
      }
  
      const newUser: IUserData = {
        tipo_usuario: "comprador", 
        cpf,
        email,
        password,
        nome,
        username,
        tipo_admin: false, 
        bairro,
        cep,
        cidade,
        estado,
        img: imagemBase64,
        nivel: 1, 
        desconto: false, 
        rua: endereco,
        telefone
      };
  
      
      mutation.mutate(newUser);
      navigate("/login");
    }catch{
      setError("Erro ao cadastrar Usuario");
    }
    
   
  };

  return (
    <Box sx={{ backgroundColor: "#F5F5F5", minHeight: "100vh", py: 4 }}>
      <img
        src={logo}
        alt={"logo"}
        style={{
          marginTop: "20px",
          marginLeft: "80px",
          width: "300px",
          height: "auto",
        }}
      />
      <Container maxWidth="lg" >
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
          <Box
            component="form"
            sx={{ backgroundColor: "#F5F5F5" }}
            onSubmit={handleSubmit}
          >
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
              onChange={(e) => setPasssword(e.target.value)}
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
                if (/^\d{0,11}$/.test(val)) setCpf(val);
              }}
              sx={{ maxLength: 11 }}
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
                if (/^\d{0,8}$/.test(val)) setCEP(val);
              }}
              sx={{ maxLength: 8 }}
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
                if (/^\d{0,11}$/.test(val)) setTelefone(val);
              }}
              sx={{ maxLength: 11 }}
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
          </Box>
        </Paper>
      </Container>
     
    </Box>
  );
};

export default RegisterForm;
