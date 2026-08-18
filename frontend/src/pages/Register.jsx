import { useState } from 'react'
import { useNavigate, Link } from 'react-router-dom'
import api from '../services/api'

function Register() {
  const [name, setName] = useState('')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [baseCurrency, setBaseCurrency] = useState('BRL')
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)
  const navigate = useNavigate()

  async function handleSubmit(e) {
    e.preventDefault()
    setError('')
    setLoading(true)

    try {
      await api.post('/users/register', { name, email, password, baseCurrency })
      navigate('/')
    } catch (err) {
      if (err.response?.status === 400) {
        setError('Verifique os dados informados')
      } else {
        setError('Não foi possível criar a conta')
      }
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="min-h-screen bg-background text-white flex items-center justify-center">
      <div className="backdrop-blur-xl bg-white/5 border border-white/10 rounded-2xl p-8 w-full max-w-sm">
        <h1 className="text-2xl font-semibold text-neon-cyan mb-6">Criar conta</h1>

        {error && (
          <p className="text-red-400 text-sm mb-4">{error}</p>
        )}

        <form onSubmit={handleSubmit} className="flex flex-col gap-4">
          <input
            type="text"
            placeholder="Nome"
            value={name}
            onChange={(e) => setName(e.target.value)}
            className="bg-white/5 border border-white/10 rounded-lg px-4 py-2 outline-none focus:border-neon-cyan"
            required
          />
          <input
            type="email"
            placeholder="E-mail"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            className="bg-white/5 border border-white/10 rounded-lg px-4 py-2 outline-none focus:border-neon-cyan"
            required
          />
          <input
            type="password"
            placeholder="Senha"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            className="bg-white/5 border border-white/10 rounded-lg px-4 py-2 outline-none focus:border-neon-cyan"
            required
            minLength={6}
          />
          <select
            value={baseCurrency}
            onChange={(e) => setBaseCurrency(e.target.value)}
            className="bg-white/5 border border-white/10 rounded-lg px-4 py-2 outline-none focus:border-neon-cyan text-white"
          >
            <option value="BRL" className="bg-background">BRL - Real</option>
            <option value="USD" className="bg-background">USD - Dólar</option>
          </select>
          <button
            type="submit"
            disabled={loading}
            className="bg-neon-cyan text-background font-semibold rounded-lg py-2 mt-2 hover:opacity-90 transition disabled:opacity-50"
          >
            {loading ? 'Criando...' : 'Criar conta'}
          </button>
        </form>

        <p className="text-white/50 text-sm mt-4 text-center">
          Já tem conta?{' '}
          <Link to="/" className="text-neon-cyan hover:underline">
            Entrar
          </Link>
        </p>
      </div>
    </div>
  )
}

export default Register