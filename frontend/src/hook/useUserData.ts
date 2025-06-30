import axios from "axios";
import {
  useQuery,
  useMutation,
  type UseMutationResult,
} from "@tanstack/react-query";
import type { IUserData } from "../interface/IUserData";

const API_URL = "http://localhost:8080";


// Função para buscar dados
const fetchData = async (): Promise<IUserData[]> => {
  console.log("🔄 Fazendo requisição para:", API_URL + "/compradores");
  const response = await axios.get<IUserData[]>(API_URL + "/compradores");
  console.log("✅ Resposta recebida:", response.data);
  return response.data;
};

// Função para enviar dados
const sendData = async (userData: IUserData): Promise<IUserData> => {
  const response = await axios.post<IUserData>(
    API_URL + "/compradores",
    userData
  );
  return response.data;
};

// Hook GET
export function useUserData() {
  return useQuery({
    queryKey: ["user-data"],
    queryFn: fetchData,
    retry: 2,
  });
}

// Hook POST
export function useSendData(): UseMutationResult<IUserData, Error, IUserData> {
  return useMutation({
    mutationFn: sendData,
    onSuccess: (data: IUserData) => {
      console.log("User cadastrado com sucesso:", data);
    },
    onError: (error: any) => {
      console.error("Erro ao cadastrar usuario:", error);
    },
  });
}

// Função para atualizar dados (PUT)
const updateData = async (userData: IUserData): Promise<IUserData> => {
  const response = await axios.put<IUserData>(
    `${API_URL}/compradores/atualizar/${userData.id}`,
    userData
  );
  return response.data;
};

// Hook PUT
export function useUpdateData(): UseMutationResult<IUserData, Error, IUserData> {
  return useMutation({
    mutationFn: updateData,
    onSuccess: (data: IUserData) => {
      console.log("Usuário atualizado com sucesso:", data);
    },
    onError: (error: any) => {
      console.error("Erro ao atualizar usuário:", error);
    },
  });
}
