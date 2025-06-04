import axios from "axios";
import type { IBookData } from "../interface/IBookData";
import { useQuery, useMutation, type UseMutationResult } from '@tanstack/react-query';

const API_URL = "http://localhost:8080";

// Função para buscar dados
const fetchData = async (): Promise<IBookData[]> => {
    console.log("🔄 Fazendo requisição para:", API_URL + '/livros');
    const response = await axios.get<IBookData[]>(API_URL + '/livros');
    console.log("✅ Resposta recebida:", response.data);
    return response.data;
};

// Função para enviar dados
const sendData = async (booktData: IBookData): Promise<IBookData> => {
    const response = await axios.post<IBookData>(API_URL + '/livros', booktData);
    return response.data;
}

// Hook GET
export function useBookData() {
    return useQuery({
        queryKey: ['product-data'],
        queryFn: fetchData,
        retry: 2
    });
}

// Hook POST
export function useSendData(): UseMutationResult<IBookData, Error, IBookData> {
    return useMutation({
        mutationFn: sendData,
        onSuccess: (data: IBookData) => {
            console.log('Produto cadastrado com sucesso:', data);
        },
        onError: (error: any) => {
            console.error('Erro ao cadastrar produto:', error);
        }
    });
}
