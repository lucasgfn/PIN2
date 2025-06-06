import { useParams } from "react-router-dom";
import { useAutorById } from "../../../hook/useAutorData";

const AutorProfile : React.FC = () => {

  const { id } = useParams<{ id: string }>();
  const { data: autor, isLoading, isError } = useAutorById(Number(id));

  if (isLoading) return <p>Carregando autor...</p>;
  if (isError) return <p>Erro ao carregar autor.</p>;

  return (
    <div>
      <h1>{autor?.nome}</h1>
      <img src={autor?.img} alt={autor?.nome} />
        PAGINA DO AUTOR
    </div>
  );
}

export default AutorProfile;  
