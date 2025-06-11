import { Box, Typography } from "@mui/material";
import Footer from "../../components/footer/Footer";
import Header from "../../components/header/Header";

import { useAutorData } from "../../hook/useAutorData";
import { useBookData } from "../../hook/useBookData";

import book_gif from "../../assets/book-gif.gif"

import AutorListContainer from "../../components/autor/AutorListContainer";
import BookListContainer from "../../components/book/BookListCotainer";


const MainPage : React.FC = () => {


   const { 
      data: dataBook,
    } = useBookData();

  const {
       data: dataAutor,
   } = useAutorData();

    

 
  
   const books = (dataBook ?? []);
   const autor = (dataAutor ?? []);


    return (
      <>
         <Header/>
            <Box sx={{
               width: "80%",
               margin: "10% 10%",  
               padding: "5%",    
               border: "1px solid #623333",
               borderRadius: "20px",
               display: "flex",
               flexWrap: "wrap",
               gap: 10
            }}>
               <Box sx={{
                  width: "100%",
                  alignItems: "center",
                  justifyContent: "space-between",
                  margin: "0% 12%",
                 
            
                  
               }}>
                  <Typography variant="h3" sx={{
                     color: "#C26383",
                     fontWeight: "bold",
                     fontFamily: "bitter", 
                     background: "linear-gradient(90deg, #FF4081, #8E2DE2)",
                     WebkitBackgroundClip: "text",
                     WebkitTextFillColor: "transparent", 
                    
                     }}>
                        Começe sua jornada na Trilha do Leitor <img src={book_gif} alt={book_gif} style={{margin: "-6% 85%", width: "160px", transform: "rotate(-20deg)",}}/>
                  </Typography>
               </Box>
               
                <Typography variant="h5" sx={{ color: "#623333", fontWeight: "bold", fontFamily: "bitter", fontSize: "30px"}}>
                   Autores Destaques
                </Typography>

               <AutorListContainer dataAutor={autor} />  
             
                <Typography variant="h5" sx={{ color: "#623333", fontWeight: "bold", fontFamily: "bitter", fontSize: "30px"}}>
                  Parecidos com suas leituras
               </Typography> 
               <BookListContainer dataBook={books}/>
            </Box>
            
         <Footer/>

     

      </>
  );
}
  
  export default MainPage;  
  
