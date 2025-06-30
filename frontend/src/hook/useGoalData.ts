import axios from "axios";
import {
  useQuery,
  useMutation,
  type UseMutationResult,
} from "@tanstack/react-query";
import type { IGoalData } from "../interface/IGoalData";

const API_URL = "http://localhost:8080";

// ================== GOALS (PÁGINAS & DIAS) ==================

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

export const usePostQuantidadePaginas = (compradorId?: number) => {
  return useMutation({
    mutationFn: async (dados: {
      quantidadePaginas: number;
      diasLidos: string[];
      metasMensais: string[];
    }) => {
      if (compradorId === undefined) {
        throw new Error("CompradorId é undefined");
      }
      return await axios.post(`${API_URL}/goals/paginas/${compradorId}`, dados);
    },
    onError: (error) => {
      console.error("❌ Erro ao criar meta: ", error);
    },
  });
};

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

// ================== MONTH GOALS (metasMensais) ==================

export function useFetchMonthGoals(compradorId: number) {
  return useQuery<IGoalData>({
    queryKey: ["month-goals", compradorId],
    queryFn: async () => {
      const response = await axios.get<IGoalData>(
        `${API_URL}/monthgoals/${compradorId}`
      );
      return response.data;
    },
    retry: 2,
  });
}

export function useUpdateMonthGoals(goalId: number, p0: { onSuccess: (data: any) => void; }) {
  return useMutation({
    mutationFn: async (metasMensais: string[]) => {
      const response = await axios.put<IGoalData>(
        `${API_URL}/monthgoals/${goalId}/monthgoals`,
        metasMensais
      );
      return response.data;
    },
    onSuccess: (data) => {
      console.log("📘 Metas mensais atualizadas:", data);
    },
    onError: (error) => {
      console.error("❌ Erro ao atualizar metas mensais:", error);
    },
  });
}

export function useDeleteMonthGoal(goalId: number) {
  return useMutation({
    mutationFn: async (livro: string) => {
      const response = await axios.delete<IGoalData>(
        `${API_URL}/monthgoals/${goalId}/monthgoals`,
        {
          params: { livro },
        }
      );
      return response.data;
    },
    onSuccess: (data) => {
      console.log("🗑️ Livro removido das metas mensais:", data);
    },
    onError: (error) => {
      console.error("❌ Erro ao remover livro da meta mensal:", error);
    },
  });
}
