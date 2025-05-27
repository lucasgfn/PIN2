import React from "react";
import { useNavigate } from "react-router-dom";

export function Header(){
    
    const navigate = useNavigate();

    const goToHome = () => {
        navigate("/home");
    }
  
    return (
        <header>
            <div className="logo"></div>

            <div className="acessos">
                <button id="btnHome" className="acesso" onClick={goToHome}> Home </button>
            </div>
         </header>
    );
}