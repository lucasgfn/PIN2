import { createBrowserRouter } from "react-router-dom";
import { Home } from "../pages/Home/Home";
import App from "../App";
import LoginForm from "../components/forms/LoginForm";
import RegisterForm from "../components/forms/RegisterForm";
import Goals from "../pages/goals/Goals";
import BookDetails from "../pages/MainPage/BookDetails/BookDetails";
import AutorProfile from "../pages/MainPage/Autor/AutorProfile";
import MainPage from "../pages/MainPage/MainPage";
import PerfilDetails from "../pages/MainPage/Perfil/PerfilDetails";

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
  },
  {
    path: "/main",
    element: <MainPage />,
  },
  {
    path: "/home",
    element: <Home />,
  },
  {
    path: "/login",
    element: <LoginForm />,
  },
  {
    path: "/cadastro",
    element: <RegisterForm />,
  },
  {
    path: "/metas",
    element: <Goals/>,
  },
  {
    path: "/livro/:idLivro",
    element: <BookDetails />,
  },
  {
    path: "/autores/:id",
    element: <AutorProfile/>,
  },
  {
    path: "/perfil",
    element: <PerfilDetails/>,
  },

]);

export default  router;