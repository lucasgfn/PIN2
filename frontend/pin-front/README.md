# PIN Frontend

Este projeto é o frontend da aplicação **PIN2**, desenvolvido com **React 19**, **TypeScript**, **TailwindCSS**, e empacotado com **Vite**.

---

## Tecnologias Utilizadas

- [React](https://reactjs.org/)
- [TypeScript](https://www.typescriptlang.org/)
- [Vite](https://vitejs.dev/)
- [TailwindCSS](https://tailwindcss.com/)
- [React Router DOM](https://reactrouter.com/)
- [PostCSS](https://postcss.org/)
- [Autoprefixer](https://github.com/postcss/autoprefixer)

---

## Instalação

> Requisitos: Node.js (18+ recomendado) e npm 

```bash
# Clone o repositório
git clone https://github.com/lucasgfn/PIN2.git

# Acesse a pasta
cd pin-front

# Instale as dependências
npm install


pin-front/
├── public/                 # Arquivos estáticos (imagens, favicon, etc.)
├── src/
│   ├── components/         # Componentes React reutilizáveis (botões, headers, cards, etc.)
│   ├── pages/              # Páginas principais da aplicação (Home, About, etc.)
│   ├── routes/             # Configuração e definição das rotas do React Router
│   ├── style/              # Arquivos CSS e Tailwind (ex: style.css, tailwind.css)
│   └── App.tsx             # Componente raiz que monta a aplicação
├── .vscode/                # Configurações específicas do VSCode (ex: launch.json, settings.json)
├── tailwind.config.js      # Configuração do TailwindCSS (customização de tema, plugins, etc.)
├── postcss.config.js       # Configuração do PostCSS (plugins como autoprefixer)
├── tsconfig.json           # Configuração do compilador TypeScript
└── vite.config.ts          # Configuração do bundler Vite (plugins, paths, etc.)
