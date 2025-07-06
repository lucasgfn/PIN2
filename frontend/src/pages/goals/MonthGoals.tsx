import {
  Typography,
  TextField,
  Box,
  Button,
  IconButton,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogContentText,
  DialogActions,
} from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";
import { useState } from "react";

interface MonthGoalsProps {
  metasMensais: string[];
  setMetasMensais: (metas: string[]) => void;
}

const MonthGoals: React.FC<MonthGoalsProps> = ({
  metasMensais,
  setMetasMensais,
}) => {
  const [labelText, setLabelText] = useState("");
  const [openDialog, setOpenDialog] = useState(false);
  const [indexToDelete, setIndexToDelete] = useState<number | null>(null);

  const handleAddLabel = () => {
    const novaMeta = labelText.trim();
    if (novaMeta && !metasMensais.includes(novaMeta)) {
      const novasMetas = [novaMeta, ...metasMensais];
      setMetasMensais(novasMetas);
      setLabelText("");
    }
  };

  const handleKeyDown = (e: React.KeyboardEvent) => {
    if (e.key === "Enter") {
      e.preventDefault();
      handleAddLabel();
    }
  };

  const confirmDelete = () => {
    if (indexToDelete !== null) {
      const novasMetas = metasMensais.filter((_, i) => i !== indexToDelete);
      setMetasMensais(novasMetas);
    }
    setOpenDialog(false);
    setIndexToDelete(null);
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

        <Box sx={{ display: "flex", flexDirection: "column", gap: 2 }}>
          {metasMensais.map((label, index) => (
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
                onClick={() => {
                  setIndexToDelete(index);
                  setOpenDialog(true);
                }}
              >
                <DeleteIcon fontSize="small" sx={{ color: "#8A2BE2" }} />
              </IconButton>
            </Box>
          ))}
        </Box>
      </Box>

      <Dialog
        open={openDialog}
        onClose={() => setOpenDialog(false)}
        aria-labelledby="confirm-dialog-title"
      >
        <DialogTitle id="confirm-dialog-title">Confirmar exclusão</DialogTitle>
        <DialogContent>
          <DialogContentText>
            Tem certeza de que deseja excluir essa meta de leitura?
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setOpenDialog(false)} color="inherit">
            Cancelar
          </Button>
          <Button onClick={confirmDelete} autoFocus color="error">
            Excluir
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
};

export default MonthGoals;
