import { Avatar, Box, Typography } from "@mui/material";
import { useNavigate } from "react-router-dom";


interface AutorProps {
  id? : number;
  nome: string;
  image: string;
}

const Autor : React.FC<AutorProps> = ({id, nome, image}) => {
  const navigate = useNavigate();

  const hookAutores = () => {
    console.log("id do autor", id);
    navigate(`/autores/${id}`);
    
  };

  return (
    <Box
      sx={{
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        justifyContent: "center",
        p: 4,
        cursor: "pointer",
        width: 250,
        height: 300,
        mx: "auto",
      }}
      onClick={hookAutores}
    >
      <Avatar
        alt={nome || "Usuário"}
        src={image || ""}
        sx={{ width: 200, height: 200 }}
      />
      <Typography className="text-[#623333] text-center mt-4">
        <p className="font-[bitter] font-bold text-lg">
          {nome} 
        </p>
      </Typography>
    </Box>
  );
};

export default Autor;
