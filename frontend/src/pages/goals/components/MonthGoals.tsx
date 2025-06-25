import { Typography, TextField, Box, Button } from "@mui/material";
import CheckBox from "./CheckBox";
import { useState } from "react";

const MonthGoals: React.FC = () => {
  const [labelText, setLabelText] = useState("");
  const [labels, setLabels] = useState<string[]>([]);

  const handleAddLabel = () => {
    if (labelText.trim()) {
      setLabels([...labels, labelText.trim()]);
      setLabelText("");
    }
  };

  const handleKeyDown = (e: React.KeyboardEvent) => {
    if (e.key === "Enter") {
      e.preventDefault();
      handleAddLabel();
    }
  };

  return (
    <>
      <Typography className="align-center text-[#1F1F25]">
        <p className="mb-2 text-4xl font-[bitter] font-bold">
          Metas Leitura Mensal
        </p>
      </Typography>

      <Box sx={{ marginTop: 5, display: "flex", flexDirection: "column", gap: 2 }}>
        {labels.map((label, index) => (
         // <CheckBox key={index} label={label} />
        ))}

        <Box sx={{ display: "flex", gap: 1 }}>
          <TextField
            value={labelText}
            onChange={(e) => setLabelText(e.target.value)}
            onKeyDown={handleKeyDown}
            sx={{
              width: 200,
              "& .MuiOutlinedInput-root": {
                borderRadius: 20,
                "& fieldset": {
                  borderColor: "#8A2BE2",
                },
              },
            }}
            placeholder="Digite o nome do livro"
          />
          <Button
            onClick={handleAddLabel}
            variant="outlined"
            sx={{ borderRadius: 20, borderColor: "#8A2BE2",
                px: 2,
                color: "#8A2BE2",
            }}
          >
            Adicionar
          </Button>
        </Box>
      </Box>
    </>
  );
};

export default MonthGoals;
