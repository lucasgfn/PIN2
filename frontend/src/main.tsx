import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'

// Router
import { RouterProvider } from 'react-router-dom'
import router from './router/Router'
// Tailwind
import "../src/style/tailwind.css"

// Auth Context
import { AuthProvider } from './contexts/AuthContext'

//Query Client
import { QueryClient, QueryClientProvider } from '@tanstack/react-query'
const queryClient = new QueryClient();


createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <QueryClientProvider client={queryClient}>
      <AuthProvider>
        <RouterProvider router={router} />
      </AuthProvider>
    </QueryClientProvider>
  </StrictMode>
);

