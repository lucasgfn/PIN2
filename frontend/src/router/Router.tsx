import { createBrowserRouter } from "react-router-dom";
import { Home } from "../pages/Home";
import App from "../App";
import LoginForm from "../components/forms/LoginForm";


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





])

export default  router;