import { createBrowserRouter } from "react-router-dom";
import { Home } from "../pages/Home";
import App from "../App";
import LoginForm from "../components/forms/LoginForm";
import RegisterForm from "../components/forms/RegisterForm";


const router = createBrowserRouter([

    {
        path: "/",
        element: <App/>
    },
    {
        path: "/home",
        element: <Home/>
    },
    {
        path: "/login",
        element: <LoginForm/>
    },
    {
        path: "/cadastro",
        element: <RegisterForm/>
    },

])

export default  router;