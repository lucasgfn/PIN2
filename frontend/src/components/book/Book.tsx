import { Box, Typography } from "@mui/material"
import { useNavigate } from "react-router-dom";

interface CardProps{
    id? : number,
    image : string,
    title : string,
    price : number
}


const Book : React.FC<CardProps> = ({id, image, title, price}) => { 
    const navigate = useNavigate();


    const hookBook = () => {
        navigate(`/livro/${id}`);
    }


    return (
        <>
        <Box
            sx={{
                padding: 2,
                width: 250, height: 350
            }}
            onClick={hookBook}
            >
            <img src={image} alt={image} style={{ objectFit: "cover", borderRadius: "10px" ,width: "80%", height: "250px" }} />
            <Typography variant="h6" sx={{ 
                mt: 1,
                color: "#623333",
                fontWeight: "bold",
            }}>
                {title}
            </Typography>
            <Typography ml={0} sx={{ 
                color: "#623333",
                fontWeight: "bold",
                fontSize: "20px",

            }}>
                R${price.toFixed(2)}
            </Typography>
        </Box>
        </>
    )

}

export default Book
