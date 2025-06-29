import { Typography, Box } from "@mui/material";
import React from "react";
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

type ReadGoalsProps = {
  diasSelecionados: string[];
  setDiasSelecionados: React.Dispatch<React.SetStateAction<string[]>>;
};

const ReadGoals: React.FC<ReadGoalsProps> = ({
  diasSelecionados,
  setDiasSelecionados,
}) => {
  const toggleDia = (dia: string) => {
    if (diasSelecionados.includes(dia)) {
      setDiasSelecionados(diasSelecionados.filter((d) => d !== dia));
    } else {
      setDiasSelecionados([...diasSelecionados, dia]);
    }
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

      <Box sx={{ display: "flex", flexDirection: "column", gap: 1 }}>
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
