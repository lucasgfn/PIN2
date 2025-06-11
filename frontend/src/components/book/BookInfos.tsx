import { useNavigate, useParams } from "react-router-dom";
import AutorInfo from "../autor/AutorInfo";
import { useBookById } from "../../hook/useBookData";
import { Box, Button, Typography } from "@mui/material";
import { useState } from "react";

//Icones
import CalendarMonthIcon from '@mui/icons-material/CalendarMonth';
import FormatListNumberedIcon from '@mui/icons-material/FormatListNumbered';
import AddShoppingCartIcon from '@mui/icons-material/AddShoppingCart';
import FileDownloadDoneIcon from '@mui/icons-material/FileDownloadDone';
import PriceChangeIcon from '@mui/icons-material/PriceChange';

//Gif
import gatinho_dormindo from "../../assets/cat/bongo-cat-bongo.gif"


const BookInfos: React.FC = () => {
  const { idLivro } = useParams<{ idLivro: string }>();
  const { data: livro } = useBookById(Number(idLivro));

  const navigate = useNavigate();
  const [hover, setHover] = useState(false); 


  const goToCarrinho = () => {
    navigate(`/carrinho`);
}

  return (
    <>
     
      <Box
        sx={{
          width: "80%",
          margin: "10% auto",
          borderRadius: "20px",
          display: "flex",
          flexDirection: { xs: "column", md: "row" },
          gap: 4,
         
        }}
      >

        <Box sx={{ flex: 1, display: "flex", justifyContent: "center", borderRadius: "20px", border: "1px solid #623333", padding: "4%"}}>
          
          <img
            src={livro?.img}
            alt={livro?.nomeLivro}
            style={{ width: "100%", maxWidth: "400px", borderRadius: "8px" }}
          />
        </Box>

  
        <Box sx={{ flex: 2, padding: "20px"}}>
          <Typography
            variant="h4"
            sx={{ color: "#623333", fontWeight: "bold" }}
          >
            {livro?.nomeLivro}
          </Typography>
          <Typography
            sx={{ color: "#623333", fontWeight: "semibold" }}
          >
            {livro?.isbn}
          </Typography>

          <Typography
            variant="h5"
            sx={{ padding: "2%", color: "#623333", fontWeight: "bold" }}
          >

            <img src={gatinho_dormindo} alt={gatinho_dormindo}   style={{width:"40px", margin: "-0.9% 30%"}}/>
            <Button onClick={goToCarrinho} 
              onMouseEnter={() => setHover(true)}
              onMouseLeave={() => setHover(false)}
            sx={{
              color: "#623333",  fontWeight: "bold", fontSize:"15px", padding: "0px 20px", margin: "0% 5%", border: "1px solid #623333", borderRadius: "10px",
            }}> 
             {hover ? <FileDownloadDoneIcon sx={{color:"green"}}/> : <AddShoppingCartIcon />}
              Adicionar Carrinho
            </Button>
            <PriceChangeIcon fontSize="large"/> R$ {livro?.precoUnit}
          </Typography>

          <Typography sx={{ padding: "3% 0%", color: "#623333", fontSize: "18px" }}>
             {livro?.sinopse}
          </Typography>

          <Box 
          sx={{
            padding: 4,
            display: "flex",
            flexDirection: { xs: "column", md: "row" },
            justifyContent: "center",
            gap: 20,
          }}
          
          >
            <Typography sx={{ padding: "3% 0%", color: "#623333", fontSize: "16px", fontWeight:"bold"}}>
              <CalendarMonthIcon/> {livro?.anoPublicado}
            </Typography>
            
            <Typography sx={{ padding: "3% 0%", color: "#623333", fontSize: "16px", fontWeight:"bold"}}>
              <FormatListNumberedIcon/> {livro?.pages} páginas 
            </Typography> 

          </Box>
        </Box>
      </Box>
   
      <AutorInfo idAutor={livro?.idAutor} />
    </>
  );
};

export default BookInfos;
