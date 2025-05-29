import './App.css'
import Footer from './components/footer/Footer'
import Header from './components/header/Header'

const App: React.FC = () => {


  return (
    <>
      <div className="min-h-screen flex flex-col">
      <Header/>
          <main className="flex-grow">
            {/* conteudo */}
            <h1>Conteudo</h1>
          </main>
        <Footer/>
       </div>
    </>
  )
}

export default App
