import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";

//Book
import { useBookData } from "../../../hook/useBookData";
import type { IBookData } from "../../../interface/IBookData";
import Book from "../../../components/cards/Book";

//Autor
import Autor from "../../../components/cards/Autor";
import { useAutorData } from "../../../hook/useAutorData";
import type { IAutorData } from "../../../interface/IAutorData";

import propaganda from "../../../assets/propaganda.png";
import cryingCar from "../../../assets/cat/crying-cat.gif";
import catLoading from "../../../assets/cat/cat-loading.gif";


//Adionado como container em PAGES diretamente porque não será reutilizado 
export function Container() {
  const { 
    data: dataBook,
    isLoading: isBookLoading, 
    isError: isBookError,
  } = useBookData();

  const {
    data: dataAutor,
    isLoading: isAutoresLoading,
    isError: isAutoresError,
  } = useAutorData();

  
  if (isBookLoading || isAutoresLoading) {
    return (
      <Box sx={{ width: "80%", margin: "10%",  display: "flex", flexDirection: "column", alignItems:"center", justifyContent: "center" }}>
        <Typography variant="body1" color="#7c4a4a">
          <img src={catLoading} style={{width: "150px", marginBottom: "16px" }}/>
          Carregando dados...
        </Typography>
      </Box>
    );
  }else if(isAutoresError || isBookError){
    return (
      <Box sx={{ width: "80%", margin: "10%", display: "flex", flexDirection: "column", alignItems:"center", justifyContent: "center" }}>
        <Typography variant="body1" color="#7c4a4a">
        <img src={cryingCar} style={{ width: "150px", marginBottom: "16px"}}/>
          Dados Indisponíveis!
        </Typography>
      </Box>
    );
  }
//
  const top4Book = dataBook?.slice(0, 4) ?? [];
  const top4Autor = dataAutor?.slice(0, 4) ?? [];

  return (
    <Box
      sx={{
        width: "80%",
        margin: "10%",
        paddingBottom: 10,
        border: '1px solid #623333',  
        borderRadius: '20px',  
        }}
    >
        
    <img src={propaganda} alt="propaganda" className="w-full rounded-2xl" />

    {/* PARTE DO AUTORES  */}
      <Typography variant="h5" sx={{ mt: 5, ml: 7,  color: "623333", fontWeight: "bold", fontFamily: "bitter", fontSize: "30px"}}>
        Principais Autores
      </Typography>
      <Box display="flex" flexWrap="wrap" gap={2} mt={1}>

        {top4Autor.map((autor: IAutorData) => (
          <Box key={autor.id} flexBasis={{ xs: "100%", sm: "50%", md: "23%" }}>
           <Autor id={autor.id} nomeAutor={autor.nome} image={autor.img} /> 
           </Box>
        ))}
      </Box>

    {/* PARTE DO LIVROS  */}
      <Typography variant="h5" sx={{ mt: 5, ml: 7,  color: "623333", fontWeight: "bold", fontFamily: "bitter", fontSize: "30px"}}>
        Mais Vendidos
      </Typography>

      <Box display="flex" justifyContent="space-around" padding="10px" flexWrap="wrap" gap={2} mt={1}>

        {top4Book.map((book: IBookData) => (

          <Box key={book.id} flexBasis={{ xs: "100%", md: "2%"}}>
            <Book id={book.id} image={book.img} title={book.nomeLivro} price={book.precoUnit} />
          </Box>

        ))}
      </Box>
    </Box>
  );
}
