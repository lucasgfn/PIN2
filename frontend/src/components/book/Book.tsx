import { Box, Typography } from "@mui/material"

interface CardProps{
    image : string,
    title : string,
    price : number
}


const Book : React.FC<CardProps> = ({image, title, price}) => { 

    return (
        <>
        <Box
            sx={{
                padding: 2,
                background: "red",
                width: 250, height: 350
            }}
            >
            <img src={image} alt={image} style={{ borderRadius: "10px", background: "blue", width: "100%", height: "250px" }} />
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
