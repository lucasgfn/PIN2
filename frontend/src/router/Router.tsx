import { createBrowserRouter } from "react-router-dom";
import { Home } from "../pages/Home/Home";
import App from "../App";
import LoginForm from "../components/forms/LoginForm";
import RegisterForm from "../components/forms/RegisterForm";
import Goals from "../components/goals/Goals";
import BookDetails from "../pages/BookDetails/BookDetails";
import AutorProfile from "../pages/Autor/AutorProfile";

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
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
    path: "/livro/:id",
    element: <BookDetails/>,
  },
  {
    path: "/autores/:id",
    element: <AutorProfile/>,
  },

]);

export default  router;