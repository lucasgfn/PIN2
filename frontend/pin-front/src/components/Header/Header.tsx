import { useNavigate } from "react-router-dom";
import '../../style.css';

export function Header(){
    
    const navigate = useNavigate();

    const goToHome = () => {
        navigate("/Home");
    }
  
    return (
        <header>
            <div className="logo"></div>

            <div className="3xl:grid-cols-6 grid grid-cols-2 md:grid-cols-4">
                <button id="btnHome" className="class" onClick={goToHome}> Home </button>
            </div>
         </header>
    );
}