import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'

// Router
import { RouterProvider } from 'react-router-dom'
import router from './router/Router'
// Tailwind
import "../src/style/tailwind.css"

// Auth Context
import { AuthProvider } from './contexts/AuthContext'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <AuthProvider>
      <RouterProvider router={router} />
    </AuthProvider>
  </StrictMode>
)
