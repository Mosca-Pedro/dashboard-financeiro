import { useState } from 'react'
import { Link } from 'react-router-dom'
import api from '../services/api'

function ForgotPassword() {
  const [email, setEmail] = useState('')
  const [message, setMessage] = useState('')
  const [loading, setLoading] = useState(false)

  async function handleSubmit(e) {
    e.preventDefault()
    setMessage('')
    setLoading(true)

    try {
      const response = await api.post('/auth/forgot-password', { email })
      setMessage(response.data)
    } catch (err) {
      setMessage('Não foi possível processar a solicitação')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="min-h-screen bg-background text-white flex items-center justify-center">
      <div className="backdrop-blur-xl bg-white/5 border border-white/10 rounded-2xl p-8 w-full max-w-sm">
        <h1 className="text-2xl font-semibold text-neon-cyan mb-2">Esqueci minha senha</h1>
        <p className="text-white/50 text-sm mb-6">
          Digite seu e-mail e enviaremos um link para redefinir sua senha.
        </p>

        {message && (
          <p className="text-neon-cyan text-sm mb-4">{message}</p>
        )}

        <form onSubmit={handleSubmit} className="flex flex-col gap-4">
          <input
            type="email"
            placeholder="E-mail"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            className="bg-white/5 border border-white/10 rounded-lg px-4 py-2 outline-none focus:border-neon-cyan"
            required
          />
          <button
            type="submit"
            disabled={loading}
            className="bg-neon-cyan text-background font-semibold rounded-lg py-2 mt-2 hover:opacity-90 transition disabled:opacity-50"
          >
            {loading ? 'Enviando...' : 'Enviar link'}
          </button>
        </form>

        <p className="text-white/50 text-sm mt-4 text-center">
          Lembrou a senha?{' '}
          <Link to="/" className="text-neon-cyan hover:underline">
            Entrar
          </Link>
        </p>
      </div>
    </div>
  )
}

export default ForgotPassword