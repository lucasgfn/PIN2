import axios from "axios";
import type { IAutorData } from "../interface/IAutorData";
import { useQuery, useMutation, type UseMutationResult } from '@tanstack/react-query';

const API_URL = "http://localhost:8080";

// Buscar dados
const fetchData = async (): Promise<IAutorData[]> => {
    console.log("🔄 Fazendo requisição para Autor:", API_URL + '/autores');
    const response = await axios.get<IAutorData[]>(API_URL + '/autores');
    console.log("✅ Resposta recebida Autor:", response.data);
    return response.data;
};

// Enviar dados
const sendData = async (autorData: IAutorData): Promise<IAutorData> => {
    const response = await axios.post<IAutorData>(API_URL + '/autores', autorData);
    return response.data;
}

const fetchAutorById = async (id: number): Promise<IAutorData> => {
    const response = await axios.get<IAutorData>(`${API_URL}/autores/${id}`);
    return response.data;
  };


//HOOKS

//GET 
export function useAutorData() {
    return useQuery({
        queryKey: ['autor-data'],
        queryFn: fetchData,
        retry: 2
    });
}

// GET BY ID
export function useAutorById(id: number) {
    return useQuery({
      queryKey: ["autor-data", id],
      queryFn: () => fetchAutorById(id),
      enabled: !!id, // só executa quando o id estiver definido
    });
  }

// POST
export function useAutorSend(): UseMutationResult<IAutorData, Error, IAutorData> {
    return useMutation({
        mutationFn: sendData,
        onSuccess: (data: IAutorData) => {
            console.log('Autor cadastrado com sucesso:', data);
        },
        onError: (error: any) => {
            console.error('Erro ao cadastrar autor:', error);
        }
    });
}
