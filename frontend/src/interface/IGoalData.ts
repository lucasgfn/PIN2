export interface IGoalData {
  id?: number; // id da meta, pode não existir ainda (ex: na criação)
  quantidadePaginas: number; // quantidade de páginas meta
  diasLidos: string[]; // lista de dias lidos (ex: ["2025-06-29", "2025-06-30"])
  metasMensais: string[]; // lista de metas mensais (ex: nomes dos livros)
}
