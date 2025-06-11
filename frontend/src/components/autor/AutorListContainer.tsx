import { Box } from "@mui/material";
import { useNavigate } from "react-router-dom";

//Icon
import Autor from "../../components/autor/Autor";
import type { IAutorData } from "../../interface/IAutorData";

const AutorListContainer: React.FC<{ dataAutor: IAutorData[] }> = ({ dataAutor }) => {
 const navigate = useNavigate();

  const handleAutorClick = (id?: number) => {
   if (id !== undefined) {
     console.log("id do autor", id);
     navigate(`/autores/${id}`);
   }
 };

    return (
      <>      
                     <Box display="flex" flexWrap="wrap" gap={2} mt={1}>

                     {dataAutor.map((autor: IAutorData) => (
                        <Box key={autor.id} flexBasis={{ xs: "100%", sm: "50%", md: "23%" }}>
                           <Autor
                           id={autor.id}
                           nomeAutor={autor.nome}
                           image={autor.img}
                           onClick={handleAutorClick}
                           />
                        </Box>
                           ))}   
            </Box>
    

      </>
  );
}
  
  export default AutorListContainer;  
  
