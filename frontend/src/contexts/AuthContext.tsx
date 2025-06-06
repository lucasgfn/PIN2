// context/AuthContext.tsx
import { createContext, useContext, useState } from 'react';
import axios from "axios";

import type { IUserData } from '../interface/IUserData';

interface AuthContextType {
  isLogged: boolean;
  userData: IUserData | null; 
  login: (username: string, password: string) => Promise<boolean>;
  logout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [isLogged, setIsLogged] = useState<boolean>(false);
  const [userData, setUserData] = useState<IUserData | null>(null);

  const login = async (username: string, password: string): Promise<boolean> => {
    try {
      await axios.post<IUserData>('http://localhost:8080/auth/login', { username, password });
      //setUserData(response.data as IUserData);
      setIsLogged(true);

      const userResponse = await axios.get(`http://localhost:8080/compradores/${username}`);
      setUserData(userResponse.data as IUserData);
     

      console.log("LOGADO");
      return true;
    } catch (error) {
      setIsLogged(false);
      setUserData(null);

      //console.log("ERRO", username, password);
      return false;
    }
  };

  const logout = () => {
    setIsLogged(false);
    setUserData(null); 
  
  };

  return (
    <AuthContext.Provider value={{ isLogged, login, logout, userData}}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = (): AuthContextType => {
  const context = useContext(AuthContext);
  if (!context) throw new Error('useAuth deve ser usado como AuthProvider');
  return context;
};
