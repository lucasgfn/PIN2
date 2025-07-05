import { AppBar, Box, Button, InputBase, Toolbar, styled } from "@mui/material"
import SearchIcon from "@mui/icons-material/Search"
import { useNavigate } from "react-router-dom";
import logo from "../../assets/logo/logo_short.jpg"
import MenuUser from "../header/MenuUser.tsx";
import { useState } from "react";

const Search = styled('div')(() => ({
    position: "relative",
    backgroundColor: "#F5F5F5",
    width: "100%",
    margin: "10px",
    maxWidth: 1000,
    borderRadius: "99px",
    border: "2px solid #623333",

}));


const SearchIconWrapper = styled('div')(() => ({
    padding: '0 1rem',
    height: '100%',
    position: 'absolute',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    color: '#7c4a4a',
  }));
  
  const SearchInputBase = styled(InputBase)(() => ({
    color: '#7c4a4a',
    paddingLeft: '3em',
    width: '100%',
  }));



const Header: React.FC = () => {
    const navigate = useNavigate()

    const goToHome = () => {
        navigate('/')
    };


    const goToMain = () => {
      navigate("/main");
    };

    const [searchTerm, setSearchTerm] = useState("");

    const handleSearch = () => {
      if (!searchTerm.trim()) return;
    
      navigate(`/busca?query=${encodeURIComponent(searchTerm.trim())}`);
    };

    return (
      <>
        <AppBar
          position="relative"
          sx={{ backgroundColor: "#f5f5f5", padding: "20px" }}
        >
          <Toolbar className="flex justify-between">
            <Box sx={{ display: "flex", alignItems: "center", gap: 10 }}>
              <Box>
                <img
                  src={logo}
                  alt={"logo"}
                  style={{ width: "200px", height: "auto" }}
                />
              </Box>

              <Box sx={{ display: "flex", alignItems: "center", gap: 2 }}>
                <Button
                  onClick={goToHome}
                  size="small"
                  sx={{
                    fontWeight: "bold",
                    color: "#623333",
                    textTransform: "none",
                    fontSize: "18px",
                  }}
                >
                  Home
                </Button>
                <Button
                  onClick={goToMain}
                  size="small"
                  sx={{
                    fontWeight: "bold",
                    color: "#623333",
                    textTransform: "none",
                    fontSize: "18px",
                  }}
                >
                  Main
                </Button>
              </Box>
            </Box>
            <div className="flex justify-center flex-1 p-px, ">
              <Search className="w-full">
                <SearchIconWrapper>
                  <SearchIcon />
                </SearchIconWrapper>
                <SearchInputBase
                  placeholder="Buscar..."
                  inputProps={{ "aria-label": "search" }}
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                  onKeyDown={(e) => {
                    if (e.key === "Enter") {
                      handleSearch(); // Chama a busca ao apertar Enter
                  }}}
                />
              </Search>
            </div>

            <div className="flex items-center">
              <MenuUser />
            </div>
          </Toolbar>
        </AppBar>
      </>
    );
}

export default Header