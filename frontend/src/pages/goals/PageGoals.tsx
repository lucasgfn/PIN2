import { Typography, TextField, Box } from "@mui/material";
import React from "react";

type PageGoalsProps = {
  quantidadePaginas: number | "";
  setQuantidadePaginas: React.Dispatch<React.SetStateAction<number | "">>;
};

const PageGoals: React.FC<PageGoalsProps> = ({
  quantidadePaginas,
  setQuantidadePaginas,
}) => {
  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const valor = event.target.value;
    setQuantidadePaginas(valor === "" ? "" : Number(valor));
  };

  return (
    <>
      <Typography
        className="align-center text-[#1F1F25]"
        variant="h4"
        fontFamily="bitter"
        fontWeight="bold"
        sx={{ mb: 2 }}
      >
        Quantidade de Páginas:
      </Typography>

      <Box
        sx={{
          display: "flex",
          flexDirection: "row",
          alignItems: "center",
          gap: 2,
        }}
      >
        <TextField
          type="number"
          value={quantidadePaginas}
          onChange={handleChange}
          sx={{
            marginTop: 0,
            width: 100,
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
