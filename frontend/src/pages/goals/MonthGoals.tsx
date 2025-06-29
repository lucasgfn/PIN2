import { Typography, TextField, Box, Button, IconButton } from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";
import { useState } from "react";

const MonthGoals: React.FC = () => {
  const [labelText, setLabelText] = useState("");
  const [labels, setLabels] = useState<string[]>([]);

  const handleAddLabel = () => {
    if (labelText.trim()) {
      setLabels([labelText.trim(), ...labels]); 
      setLabelText("");
    }
  };
  

  const handleDeleteLabel = (index: number) => {
    setLabels(labels.filter((_, i) => i !== index));
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

      <Box
        sx={{ marginTop: 5, display: "flex", flexDirection: "column", gap: 2 }}
      >
        <Box sx={{ display: "flex", gap: 2}}>
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
            sx={{
              borderRadius: 20,
              borderColor: "#8A2BE2",
              px: 2,
              color: "#8A2BE2",
            }}
          >
            Adicionar
          </Button>
        </Box>

        {/* Lista de metas adicionadas */}
        <Box sx={{ display: "flex", flexDirection: "column", gap: 2}}>
          {labels.map((label, index) => (
            <Box
              key={index}
              sx={{
                display: "flex",
                alignItems: "center",
                justifyContent: "space-between",
                border: "1px solid #8A2BE2",
                borderRadius: 20,
                padding: "16px 32px",
                width: "fit-content",
                maxWidth: "100%",
                wordBreak: "break-word",
              }}
            >
              <Typography>{label}</Typography>
              <IconButton
                aria-label="delete"
                size="small"
                onClick={() => handleDeleteLabel(index)}
              >
                <DeleteIcon fontSize="small" sx={{ color: "#8A2BE2" }} />
              </IconButton>
            </Box>
          ))}
        </Box>
      </Box>
    </>
  );
};

export default MonthGoals;
