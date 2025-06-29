import axios from "axios";
import {
  useQuery,
  useMutation,
  type UseMutationResult,
} from "@tanstack/react-query";
import type { IGoalData } from "../interface/IGoalData";

const API_URL = "http://localhost:8080";


const fetchGoal = async (compradorId: number): Promise<IGoalData> => {
  const response = await axios.get<IGoalData>(
    `${API_URL}/goals/paginas/${compradorId}`
  );
  return response.data;
};

export function useFetchGoal(compradorId: number) {
  return useQuery({
    queryKey: ["goal", compradorId],
    queryFn: () => fetchGoal(compradorId),
    retry: 2,
  });
}

const postQuantidadePaginas = async (
  compradorId: number,
  goalData: IGoalData
): Promise<IGoalData> => {
  const response = await axios.post<IGoalData>(
    `${API_URL}/goals/paginas/${compradorId}`,
    goalData
  );
  return response.data;
};

export const usePostQuantidadePaginas = (compradorId?: number) => {
  return useMutation({
    mutationFn: async (dados: {
      quantidadePaginas: number;
      diasLidos: string[];
    }) => {
      if (compradorId === undefined) {
        throw new Error("CompradorId é undefined");
      }
      return await axios.post(
        `http://localhost:8080/goals/paginas/${compradorId}`,
        dados
      );
    },
    onError: (error) => {
      console.error("❌ Erro ao criar meta: ", error);
    },
  });
};


//
// ========== 📆 GET - Buscar dias lidos pelo compradorId ==========
//
const fetchDiasLidos = async (compradorId: number): Promise<string[]> => {
  const response = await axios.get<string[]>(
    `${API_URL}/goals/dias/${compradorId}`
  );
  return response.data;
};

export function useFetchDiasLidos(compradorId: number) {
  return useQuery({
    queryKey: ["dias-lidos", compradorId],
    queryFn: () => fetchDiasLidos(compradorId),
  });
}

//
// ========== 📅 PUT - Atualizar dias lidos por goalId (id da meta) ==========
//
const putDiasLidos = async ({ id, dias }: { id: number; dias: string[] }) => {
  await axios.put(`${API_URL}/goals/dias/${id}`, dias);
};

export function usePutDiasLidos(): UseMutationResult<
  void,
  Error,
  { id: number; dias: string[] }
> {
  return useMutation({
    mutationFn: putDiasLidos,
    onSuccess: () => {
      console.log("📅 Dias lidos atualizados com sucesso");
    },
    onError: (error) => {
      console.error("❌ Erro ao atualizar dias lidos:", error);
    },
  });
}

//
// ========== 📅 POST - Criar ou atualizar dias lidos para compradorId ==========
//
const postDiasLidos = async (
  compradorId: number,
  dias: string[]
): Promise<IGoalData> => {
  const response = await axios.post<IGoalData>(
    `${API_URL}/goals/dias/${compradorId}`,
    dias
  );
  return response.data;
};

export function usePostDiasLidos(
  compradorId: number
): UseMutationResult<IGoalData, Error, string[]> {
  return useMutation({
    mutationFn: (dias) => postDiasLidos(compradorId, dias),
    onSuccess: (data) => {
      console.log("📅 Dias lidos criados/atualizados com sucesso:", data);
    },
    onError: (error) => {
      console.error("❌ Erro ao criar/atualizar dias lidos:", error);
    },
  });
}
