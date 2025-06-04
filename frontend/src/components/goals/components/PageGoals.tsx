import { Typography, TextField, Box } from "@mui/material";

const PageGoals: React.FC = () => {
  return (
    <>
      <Typography className="align-center text-[#1F1F25]">
        <p className="mb-2 text-4xl font-[bitter] font-bold">
          Quantidade de Páginas:
        </p>
      </Typography>

      <Box
        sx={{
          display: "flex",
          flexDirection: "column",
          alignItems: "flex-start",
          gap: 2,
        }}
      >
        <TextField
          sx={{
            marginTop:2,
            width: 50,
            height: 10,
            "& .MuiOutlinedInput-root": {
              borderRadius: 20,
              "& fieldset": {
                borderColor: "#007BFF",
              },
            },
          }}
        />
      </Box>
    </>
  );
};

export default PageGoals;
