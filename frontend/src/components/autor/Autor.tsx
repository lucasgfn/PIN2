import { Avatar, Box, Typography } from "@mui/material";
import { useState } from "react";

interface AutorProps {
  nome: string;
  image: string;
}

const Autor: React.FC = () => {
  const [autor, setAutor] = useState<AutorProps | null>(null);

  const handleUserData = () => {
    console.log("dados do usuário");
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
      onClick={handleUserData}
    >
      <Avatar
        alt={autor?.nome || "Usuário"}
        src={autor?.image || ""}
        sx={{ width: 200, height: 200 }}
      />
      <Typography className="text-[#623333] text-center mt-4">
        <p className="font-[bitter] font-bold text-lg">
          {autor?.nome || "NOME"}
        </p>
      </Typography>
    </Box>
  );
};

export default Autor;
