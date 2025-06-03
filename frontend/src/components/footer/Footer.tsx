
import logo from "../../assets/logo.png"


const Footer : React.FC = () => {

    return (
        <>
            <footer className="flex flex-col py-6 pb-8 shadow-inner shadow-red-900/20">
                <div className="mx-auto text-2xl">
                        <img src={logo} alt={"logo"} style={{width: "150px", height: "auto"}}/>
                </div>
                <div className="mx-auto text-center text-yellow-900">
                    <h2 className="text-2xl font-semibold">Universidade do Estado de Santa Catarina</h2>
                    <p>2025</p>
                    <p className="text-xs">Projeto Integrador II do Curso Engenharia de Software</p>
                    <p className="text-xs">Realizado por: Clara dos Santos Becker e Lucas Gabriel Falcade Nunes</p>
                </div>
            </footer>
        </>
    )

}

export default Footer;