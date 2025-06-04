import {  Paper, Box} from "@mui/material";
import Autor from "../autor/Autor";

const PerfilComprador: React.FC = () => {
  return (
    <Box sx={{display: "flex",marginLeft:30}}>
      <Paper
        elevation={3}
        sx={{
          mt: 8,
          p: 5,
          borderRadius: 10,
          border: "2px solid",
          borderColor: "#623333",
          backgroundColor: "#F5F5F5",
          alignItems: "center",
        }}
      >
        <Autor/>
      </Paper>
    </Box>
  );
};

export default PerfilComprador;
