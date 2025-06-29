import { createContext, useContext, useState, useEffect } from "react";
import axios from "axios";

import type { IUserData } from "../interface/IUserData";

interface AuthContextType {
  isLogged: boolean;
  userData: IUserData | null;
  login: (username: string, password: string) => Promise<boolean>;
  logout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({
  children,
}) => {
  // Inicializa estado lendo do localStorage (se existir)
  const [isLogged, setIsLogged] = useState<boolean>(() => {
    const stored = localStorage.getItem("isLogged");
    return stored === "true"; // converte string para boolean
  });

  const [userData, setUserData] = useState<IUserData | null>(() => {
    const stored = localStorage.getItem("userData");
    return stored ? JSON.parse(stored) : null;
  });

  // Sincroniza estado com localStorage sempre que isLogged ou userData mudam
  useEffect(() => {
    localStorage.setItem("isLogged", isLogged.toString());
  }, [isLogged]);

  useEffect(() => {
    if (userData) {
      localStorage.setItem("userData", JSON.stringify(userData));
    } else {
      localStorage.removeItem("userData");
    }
  }, [userData]);

  const login = async (
    username: string,
    password: string
  ): Promise<boolean> => {
    try {
      // Requisição para autenticação (ajuste se precisar capturar token)
      await axios.post<IUserData>("http://localhost:8080/auth/login", {
        username,
        password,
      });

      // Busca dados do usuário
      const userResponse = await axios.get(
        `http://localhost:8080/compradores/${username}`
      );
      setUserData(userResponse.data as IUserData);
      setIsLogged(true);

      console.log("LOGADO");
      return true;
    } catch (error) {
      setIsLogged(false);
      setUserData(null);
      console.log("ERRO", error);
      return false;
    }
  };

  const logout = () => {
    setIsLogged(false);
    setUserData(null);
    localStorage.removeItem("isLogged");
    localStorage.removeItem("userData");
  };

  return (
    <AuthContext.Provider value={{ isLogged, login, logout, userData }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = (): AuthContextType => {
  const context = useContext(AuthContext);
  if (!context)
    throw new Error("useAuth deve ser usado dentro do AuthProvider");
  return context;
};
