import { Avatar, Box, Typography } from "@mui/material"
import { useState } from "react";

interface AutorProps{
    nome: string;
    image: string;
}

const Autor : React.FC = () => {
    const [autor, setAutor] = useState<AutorProps | null>(null)

    const hangleAutorData = () => {
        console.log("dados do autor")
    };


    return(
        <>
      
             <Box
                sx={{
                    display: 'flex',
                    padding: "20%",
                    flexDirection: 'column',
                    alignItems: 'center',
                    cursor: 'pointer',
                    //background: red[500], --> para saber aonde esta
                    width: 250, height: 300
                }}
                onClick={hangleAutorData}
            > 
                <Avatar
                    alt="User"
                    src="U" 
                    sx={{
                        width: 200, height: 200
                    }}
                />
                 <Typography ml={2} className="p-3 align-center text-[#623333]">
                    {autor?.nome} 
                    <p className="font-[bitter] font-bold">NOME AUTOR</p>
                </Typography>
                
            </Box>
         
        </>
       
    )
}

export default  Autor