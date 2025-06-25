import { Typography, Box } from "@mui/material";
import React, { useEffect, useState } from "react";
import axios from "axios";
import CheckBox from "./CheckBox";

const diasDaSemana = [
  "Segunda-feira",
  "Terça-feira",
  "Quarta-feira",
  "Quinta-feira",
  "Sexta-feira",
  "Sábado",
  "Domingo",
];
const [diasSelecionados, setDiasSelecionados] = useState<string[]>([]);

const ReadGoals: React.FC = () => {
  //fazer useEffect
  
  const toggleDia = (dia: string) => {
    setDiasSelecionados((prev) =>
      prev.includes(dia) ? prev.filter((d) => d !== dia) : [...prev, dia]
    );
  };

  return (
    <>
      <Typography
        sx={{ color: "#1F1F25", mb: 4 }}
        variant="h4"
        fontFamily="bitter"
        fontWeight="bold"
      >
        Metas de Páginas Diárias
      </Typography>

      <Box>
        {diasDaSemana.map((dia) => (
          <CheckBox
            key={dia}
            label={dia}
            checked={diasSelecionados.includes(dia)}
            onChange={() => toggleDia(dia)}
          />
        ))}
      </Box>
    </>
  );
};

export default ReadGoals;
