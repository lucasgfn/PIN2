import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import { useBookData } from "../../../hook/useBookData";
import type { IBookData } from "../../../interface/IBookData";
import Book from "../../../components/book/Book";

import Autor from "../../../components/autor/Autor";

import propaganda from "../../../assets/propaganda.png";

export function Container() {
  const { data, isLoading, isError } = useBookData();

  
  if (isLoading) {
    return (
      <Box sx={{ width: "90%", margin: "0 auto", backgroundColor: "gray", padding: 2 }}>
        <Typography variant="body1" color="white">
          Carregando livros...
        </Typography>
      </Box>
    );
  }

  if (isError) {
    return (
      <Box sx={{ width: "90%", margin: "0 auto", backgroundColor: "gray", padding: 2 }}>
        <Typography variant="body1" color="white">
          Erro ao carregar livros.
        </Typography>
      </Box>
    );
  }

  const top4 = data?.slice(0, 4) ?? [];

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

      <Typography variant="h5" sx={{ mt: 2, color: "white" }}>
        Principais Autores
      </Typography>
      <Box display="flex" flexWrap="wrap" gap={2} mt={1}>
       
      </Box>

    {/* PARTE DO LIVROS  */}
      <Typography variant="h5" sx={{ mt: 4, color: "white" }}>
        Livros
      </Typography>

      <Box display="flex" flexWrap="wrap" gap={2} mt={1}
      sx={{backgroundColor: "black"}}>

        {top4.map((book: IBookData) => (

          <Box key={book.id} flexBasis={{ xs: "100%", sm: "50%", md: "25%" }}>
            <Book image={book.img} title={book.nomeAutor} price={book.precoUnit} />
          </Box>

        ))}
      </Box>
    </Box>
  );
}
