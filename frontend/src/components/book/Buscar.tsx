import { useLocation } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";
import BookListContainer from "./BookListCotainer";
import type { IBookData } from "../../interface/IBookData";
import Header from "../header/Header";


const Buscar = () => {
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const query = queryParams.get("query");

  const [livros, setLivros] = useState<IBookData[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    if (!query) return;
  
    setLoading(true);
    setError("");
  
    axios
      .get<IBookData[]>("http://localhost:8080/livros/buscar", {
        params: { nomeLivro: query },
      })
      .then((res) => {
        setLivros(res.data);
        setLoading(false);
      })
      .catch(() => {
        setError("Erro ao buscar livros");
        setLoading(false);
      });
  }, [query]);
  
  

  return (
    <div>
      <Header/>
      {loading && <p>Carregando...</p>}
      {!loading && !error && livros.length === 0 && <p>Nenhum livro encontrado</p>}
      {!loading && !error && livros.length > 0 && (
        <BookListContainer dataBook={livros} />

      )}
    </div>
  );
};

export default Buscar;
