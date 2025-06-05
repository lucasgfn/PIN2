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
      <Box sx={{ width: "90%", margin: "0 auto", backgroundColor: "gray", padding: 10 }}>
        <Typography variant="body1" color="white">
          Carregando dados...
        </Typography>
      </Box>
    );
  }

  if (isBookError || isAutoresError) {
    return (
      <Box sx={{ width: "90%", margin: "0 auto", backgroundColor: "gray", padding: 10 }}>
        <Typography variant="body1" color="white">
          Erro ao carregar!
        </Typography>
      </Box>
    );
  }

  const top4Book = dataBook?.slice(0, 4) ?? [];
  const top4Autor = dataAutor?.slice(0, 4) ?? [];

  return (
    <Box
      sx={{
        width: "90%",
        margin: "0 auto",
        backgroundColor: "gray",
        padding: 2,
      }}
    >
        
    <img src={propaganda} alt="propaganda" className="w-full" />

    {/* PARTE DO AUTORES  */}
      <Typography variant="h5" sx={{ mt: 2, color: "white" }}>
        Principais Autores
      </Typography>
      <Box display="flex" flexWrap="wrap" gap={2} mt={1}>

        {top4Autor.map((autor: IAutorData) => (
          <Box key={autor.id} flexBasis={{ xs: "100%", sm: "50%", md: "25%" }}>
           <Autor id={autor.id} nome={autor.nome} image={autor.img} /> 
           </Box>
        ))}
      </Box>

    {/* PARTE DO LIVROS  */}
      <Typography variant="h5" sx={{ mt: 4, color: "white" }}>
        Livros
      </Typography>

      <Box display="flex" flexWrap="wrap" gap={2} mt={1}
      sx={{backgroundColor: "black"}}>

        {top4Book.map((book: IBookData) => (

          <Box key={book.id} flexBasis={{ xs: "100%", sm: "50%", md: "25%" }}>
            <Book id={book.id} image={book.img} title={book.nomeAutor} price={book.precoUnit} />
          </Box>

        ))}
      </Box>
    </Box>
  );
}
