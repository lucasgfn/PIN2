import React from "react";
import { Avatar, Menu, MenuItem } from "@mui/material";
import { useNavigate } from "react-router-dom";

const UserMenu: React.FC = () => {
  const navigate = useNavigate();
  const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);
  const open = Boolean(anchorEl);

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
        navigate('/editar-dados');
        break;
      case 'Sair': 
        console.log("Sair");        //ADICIONAR LOGICA DE SAIDA 
        break;
    }
  };

  return (
    <>
      <Avatar
        alt="User"
        src="https://i.pravatar.cc/40" // MUDAR CAMINHO
        sx={{ width: 50, height: 50, cursor: "pointer" }}
        onClick={handleMenuOpen}
      />
      <Menu anchorEl={anchorEl} open={open} onClose={handleMenuClose}>
        <MenuItem onClick={() => handleOptionClick('Carrinho')}>Carrinho</MenuItem>
        <MenuItem onClick={() => handleOptionClick('Perfil')}>Perfil</MenuItem>
        <MenuItem onClick={() => handleOptionClick('EditarDados')}>Editar Dados</MenuItem>
        <MenuItem onClick={() => handleOptionClick('Sair')}>Sair</MenuItem>
      </Menu>
    </>
  );
};

export default UserMenu;
