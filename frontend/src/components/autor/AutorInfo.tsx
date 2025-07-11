import { Box, Typography } from "@mui/material";

import { useParams } from "react-router-dom";
import { useAutorById } from "../../hook/useAutorData";
import Autor from "./Autor";

interface AutorInfoProps {
    idAutor?: number; 
}

const AutorInfo : React.FC<AutorInfoProps>  = ({idAutor}) => {
    const params = useParams<{ id: string }>();

    const autorId = idAutor ?? Number(params.id);
    const { data: autor } = useAutorById(autorId);
        

    return (
        <>
            <Box sx={{
                width: "80%",
                margin: "10% 10% 3%",           
                border: "1px solid #623333",
                borderRadius: "20px",
            }}>
                <Autor nomeAutor={autor?.nome} image={autor?.img}/>

                <Typography variant="body1" sx={{backgroundColor: "", color: "#623333", fontWeight: "bold", width: "80%", margin:"0 10% 5%"}}> 
                    SOBRE: {autor?.sobre}
                </Typography>
             </Box>
        

        </>
    )

}

export default AutorInfo;