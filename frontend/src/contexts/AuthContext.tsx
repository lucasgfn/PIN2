import { createContext, useContext, useState, useEffect } from "react";
import axios from "axios";
import type { IUserData } from "../interface/IUserData";

interface AuthContextType {
  isLogged: boolean;
  userData: IUserData | null;
  setUserData: (user: IUserData | null) => void; // adiciona setUserData
  login: (username: string, password: string) => Promise<boolean>;
  logout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({
  children,
}) => {
  const [isLogged, setIsLogged] = useState<boolean>(() => {
    return localStorage.getItem("isLogged") === "true";
  });

  const [userData, setUserData] = useState<IUserData | null>(() => {
    const stored = localStorage.getItem("userData");
    return stored ? JSON.parse(stored) : null;
  });

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
      await axios.post<IUserData>("http://localhost:8080/auth/login", {
        username,
        password,
      });

      const userResponse = await axios.get(
        `http://localhost:8080/compradores/${username}`
      );

      setUserData(userResponse.data as IUserData);
      setIsLogged(true);

      console.log("LOGADO");
      return true;
    } catch (error) {
      console.log("ERRO AO LOGAR:", error);
      setIsLogged(false);
      setUserData(null);
      return false;
    }
  };

  const logout = () => {
    setIsLogged(false);
    setUserData(null);
    localStorage.removeItem("isLogged");
    localStorage.removeItem("userData");

    axios.post("http://localhost:8080/auth/logoff").catch(() => {});
  };

  return (
    <AuthContext.Provider
      value={{ isLogged, userData, setUserData, login, logout }}
    >
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
