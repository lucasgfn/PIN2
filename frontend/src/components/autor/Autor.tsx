import { Avatar, Box, Typography } from "@mui/material";


interface AutorProps {
  id? : number;
  nomeAutor?: string;
  image?: string;
  onClick?: (id?: number) => void; 
}

const Autor : React.FC<AutorProps> = ({id, nomeAutor, image, onClick}) => {

  const handleClick = () => {
    if (onClick) {
      onClick(id);
    }
  }

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
      onClick={handleClick}
    >
      <Avatar
        alt={nomeAutor || "Usuário"}
        src={image || ""}
        sx={{ width: 200, height: 200 }}
      />
      <Typography className="text-[#623333] text-center mt-4">
        <p className="font-[bitter] font-bold text-lg mt-2.5">
          {nomeAutor} 
        </p>
      </Typography>
    </Box>
  );
};

export default Autor;
