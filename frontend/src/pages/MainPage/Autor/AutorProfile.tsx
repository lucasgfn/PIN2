import { useParams } from "react-router-dom";
import { useAutorById } from "../../../hook/useAutorData";
import BooksContainer from "../../../components/autor/BooksContainer";
import { Box, Typography } from "@mui/material";

//Gif
import cryingCar from "../../../assets/cat/crying-cat.gif";
import catLoading from "../../../assets/cat/cat-loading.gif";


import Header from "../../../components/header/Header";
import AutorInfo from "../../../components/autor/AutorInfo";
import Footer from "../../../components/footer/Footer";

const AutorProfile : React.FC = () => {

  const { id } = useParams<{ id: string }>();
  const { data: autor, isLoading, isError } = useAutorById(Number(id));

  if (isLoading) {
    return (
    <Box sx={{ width: "80%", margin: "10%",  display: "flex", flexDirection: "column", alignItems:"center", justifyContent: "center" }}>
        <Typography variant="body1" color="#7c4a4a">
        <img src={catLoading} style={{width: "150px", marginBottom: "16px" }}/>
        Carregando dados...
        </Typography>
    </Box>
    );
}else if(isError){
    return (
    <Box sx={{ width: "80%", margin: "10%", display: "flex", flexDirection: "column", alignItems:"center", justifyContent: "center" }}>
        <Typography variant="body1" color="#7c4a4a">
        <img src={cryingCar} style={{ width: "150px", marginBottom: "16px"}}/>
        Dados Indisponíveis!
        </Typography>
    </Box>
    );
}

  return (
    <>
      <Header/>
        <Box sx={{margin: "0% 0% 10%"}}>
          <AutorInfo />
          <BooksContainer idAutor={autor?.id} />
        </Box> 
 
      <Footer/>
    </>
  );
}

export default AutorProfile;  
