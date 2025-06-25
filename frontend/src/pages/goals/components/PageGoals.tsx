import { Typography, TextField, Box } from "@mui/material";
import React, { useEffect, useState } from "react";
import axios from "axios";

const PageGoals: React.FC = () => {
  const [quantidadePaginas, setQuantidadePaginas] = useState<number | "">("");
  
  //fazer o useEffect

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const valor = event.target.value;
    setQuantidadePaginas(valor === "" ? "" : Number(valor));
  };

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
          type="number"
          value={quantidadePaginas}
          onChange={handleChange}
          sx={{
            marginTop: 2,
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
