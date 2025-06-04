import CheckBox from "./CheckBox";
import { Typography, Container, Paper, TextField } from "@mui/material";

const ReadGoals: React.FC = () => {
    return (
    <>
    <Typography className="align-center text-[#1F1F25]">
    <p className="mb-8 text-4xl font-[bitter] font-bold">
        Metas de Páginas Diárias
    </p>
    </Typography>
    <CheckBox label="Segunda-feira" />
    <CheckBox label="Terça-feira" />
    <CheckBox label="Quarta-feira" />
    <CheckBox label="Quinta-feira" />
    <CheckBox label="Sexta-feira" />
    <CheckBox label="Sábado" />
    <CheckBox label="Domingo" />
    </>
    );
}

export default ReadGoals;