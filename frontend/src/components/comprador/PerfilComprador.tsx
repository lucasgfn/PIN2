import { Paper, Box, Typography} from "@mui/material";
import BookListContainer from "../book/BookListCotainer";

import { useAuth } from "../../contexts/AuthContext";
import { useBookData } from "../../hook/useBookData";

import PhotoComprador from "./PhotoComprador";
import { Navigate } from "react-router-dom";



const PerfilComprador: React.FC = () => {

  const {isLogged, logout, userData} = useAuth();  
  const { data: allBooks } = useBookData();


  const livrosComprador = userData?.lista && allBooks
  ? allBooks.filter(book =>
      book.id !== undefined && userData.lista.includes(book.id.toString())
    )
  : [];


  //const livrosCompradorTeste = allBooks ?? [] --> Para teste
  
  if (!isLogged) {
    logout();
    return <Navigate to="/login" />;
  }

  return (
    <>

        <Box sx={{display: "flex",  margin: "10%"}}>

        <Paper
          elevation={3}
          sx={{
            borderRadius: 10,
            border: "1px solid",
            borderColor: "#623333",
            backgroundColor: "#F5F5F5",
            alignItems: "center",
            padding: "10% 2%",
            width: "100%",
          
          }}
        >
          {userData && <PhotoComprador user_id={userData} />}
          <Box sx={{ display: "flex", flexDirection: "column", gap: 2, mt: 2}}>
              <Typography sx={{ fontSize: "1.2rem" }}>
                {userData?.nome ?? "Nome não disponível"}
              </Typography>
              <Typography sx={{ fontSize: "1.2rem" }}>
                Endereço: {userData?.rua ?? "NULO"}
              </Typography>
              <Typography sx={{ fontSize: "1.2rem" }}>
                Bairro: {userData?.bairro ?? ""}
              </Typography>
              <Typography sx={{ fontSize: "1.2rem" }}>
                CEP: {userData?.cep ?? "NULO"}
              </Typography>
              <Typography sx={{ fontSize: "1.2rem" }}>
                Estado: {userData?.estado ?? "NULO"}
              </Typography>
              <Typography sx={{ fontSize: "1.2rem" }}>
                Telefone: {userData?.telefone ?? "NULO"}
              </Typography>
          </Box>
                  
       
        </Paper>
         <BookListContainer dataBook={livrosComprador}/>
        </Box>
      
    </>
    
  );
};

export default PerfilComprador;
