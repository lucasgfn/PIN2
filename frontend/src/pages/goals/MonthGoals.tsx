import { Typography, TextField, Box, Button, IconButton } from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";
import { useEffect, useState } from "react";
import {
  useFetchGoal,
  useUpdateMonthGoals,
  useDeleteMonthGoal,
} from "../../hook/useGoalData";
import { useAuth } from "../../contexts/AuthContext";

const MonthGoals: React.FC = () => {
  const [labelText, setLabelText] = useState("");
  const [labels, setLabels] = useState<string[]>([]);

  const { userData } = useAuth();
  const compradorId = userData?.id ?? 0;

  const { data: goalData } = useFetchGoal(compradorId);
  const { mutate: updateMonthGoals } = useUpdateMonthGoals(goalData?.id ?? 0);
  const { mutate: deleteMonthGoal } = useDeleteMonthGoal(goalData?.id ?? 0);

  // Carrega metas mensais do backend
  useEffect(() => {
    if (goalData?.metasMensais) {
      setLabels(goalData.metasMensais);
    }
  }, [goalData]);

  const handleAddLabel = () => {
    const novaMeta = labelText.trim();
    if (novaMeta && !labels.includes(novaMeta)) {
      const novasMetas = [novaMeta, ...labels];
      setLabels(novasMetas);
      updateMonthGoals(novasMetas);
      setLabelText("");
    }
  };

  const handleDeleteLabel = (index: number) => {
    const livro = labels[index];
    const novasMetas = labels.filter((_, i) => i !== index);
    setLabels(novasMetas);
    deleteMonthGoal(livro);
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
        <Box sx={{ display: "flex", gap: 2 }}>
          <TextField
            value={labelText}
            onChange={(e) => setLabelText(e.target.value)}
            onKeyDown={handleKeyDown}
            sx={{
              width: 300,
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
        <Box sx={{ display: "flex", flexDirection: "column", gap: 2 }}>
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
