import { Box } from "@mui/material"
import type { IBookData } from "../../interface/IBookData"
import Book from "../book/Book"
import { useBookData } from "../../hook/useBookData";


interface BooksContainerProps {
    idAutor: number| undefined;
  }

const BooksContainer : React.FC<BooksContainerProps> = ({idAutor}) => {
    const { 
        data: dataBook,
      } = useBookData();

    const books = (dataBook ?? []).filter((book) => book.idAutor === idAutor);

    return (
        <>

        <Box sx={{
                width: "80%",
                margin: "0% 10%",  
                padding: "5%",    
                border: "1px solid #623333",
                borderRadius: "20px",
                display: "flex",
                flexWrap: "wrap",
                justifyContent: "space-around",
                gap: 3
            }}>

            {books.map((book: IBookData) => (
            <Box key={book.id} flexBasis={{ xs: "100%", sm: "48%", md: "23%" }}>
                <Book idLivro={book.id} image={book.img} title={book.nomeLivro} price={book.precoUnit} /> 
            </Box>
            ))}

        </Box>
        </>
    )
}

export default BooksContainer;
