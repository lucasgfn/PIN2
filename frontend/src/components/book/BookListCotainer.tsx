import { Box } from "@mui/material";
import type { IBookData } from "../../interface/IBookData"
import Book from "./Book";

    
    
const BookListContainer: React.FC<{dataBook: IBookData[]}> = ({dataBook}) => { 
    

    return (
        <>
            <Box display="flex" justifyContent="space-around" padding="10px" flexWrap="wrap" gap={2} mt={1}>

                {dataBook.map((book: IBookData) => (

                <Box key={book.id} flexBasis={{ xs: "100%", md: "2%"}}>
                    <Book idLivro={book.id} image={book.img} title={book.nomeLivro} price={book.precoUnit} />
                </Box>

                ))}
            </Box>
            </>
    );

}
export default BookListContainer;