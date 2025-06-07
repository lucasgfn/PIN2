export interface IUserData {
        tipo_usuario: string,
        id_usuario? : number,
        cpf: string,
        email: string,
        password: string,
        nome: string,
        username: string,
        tipo_admin: boolean,
        bairro: string,
        cep: string,
        cidade: string,
        estado: string,
        img?: string,
        nivel: number,
        desconto: boolean,
        rua: string,
        telefone: string
}