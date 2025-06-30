import { Avatar, Menu, MenuItem } from "@mui/material";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

//AUTH
import { useAuth } from '../../contexts/AuthContext';

import gifCat from "../../assets/cat/catGif.gif";


const UserMenu: React.FC = () => {
    const navigate = useNavigate();
    const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
    const open = Boolean(anchorEl);

    const {isLogged, logout, userData} = useAuth();


    const handleMenuOpen = (event: React.MouseEvent<HTMLElement>) => {
        setAnchorEl(event.currentTarget);
    };

    const handleMenuClose = () => {
        setAnchorEl(null);
    };

    const handleOptionClick = (option: string) => {
        handleMenuClose();
        switch (option) {
        case 'Carrinho':
            navigate('/carrinho');
            break;
        case 'Perfil':
            navigate('/perfil');
            break;
        case 'EditarDados':
            navigate('/update');
            break;
        case 'Sair': 
            logout();
            navigate('/home');
            break;
        case 'Login':
            navigate("/login");
            break;
        case 'Cadastrar':
            navigate("/cadastro");
            break;
        }
    };

    return (
        <>
       <Avatar
            alt="User"
            src={userData?.img || gifCat} 
            sx={{ width: 50, height: 50, cursor: "pointer" }}
            onClick={handleMenuOpen}
        />
        <Menu anchorEl={anchorEl} open={open} onClose={handleMenuClose}>
           
            {isLogged ? (
                <>
                    <MenuItem onClick={() => handleOptionClick('Carrinho')}>Carrinho</MenuItem>
                    <MenuItem onClick={() => handleOptionClick('Perfil')}>Perfil</MenuItem>
                    <MenuItem onClick={() => handleOptionClick('EditarDados')}>Editar Dados</MenuItem>
                    <MenuItem onClick={() => handleOptionClick('Sair')}>Sair</MenuItem>
                </>
            ) : (
                <>
                     <MenuItem onClick={() => handleOptionClick('Login')}> Entrar </MenuItem>
                     <MenuItem onClick={() => handleOptionClick('Cadastrar')}>Cadastrar</MenuItem>
                </>
            )}
         </Menu>
        </>
    );
    };

export default UserMenu;
