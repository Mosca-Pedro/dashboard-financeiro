import { useState } from 'react'
import { useNavigate, useSearchParams, Link } from 'react-router-dom'
import api from '../services/api'

function ResetPassword() {
  const [searchParams] = useSearchParams()
  const token = searchParams.get('token')

  const [newPassword, setNewPassword] = useState('')
  const [confirmPassword, setConfirmPassword] = useState('')
  const [error, setError] = useState('')
  const [success, setSuccess] = useState(false)
  const [loading, setLoading] = useState(false)
  const navigate = useNavigate()

  async function handleSubmit(e) {
    e.preventDefault()
    setError('')

    if (newPassword !== confirmPassword) {
      setError('As senhas não coincidem')
      return
    }

    setLoading(true)

    try {
      await api.post('/auth/reset-password', { token, newPassword })
      setSuccess(true)
      setTimeout(() => navigate('/'), 2500)
    } catch (err) {
      setError('Link inválido ou expirado. Solicite um novo.')
    } finally {
      setLoading(false)
    }
  }

  if (!token) {
    return (
      <div className="min-h-screen bg-background text-white flex items-center justify-center">
        <div className="backdrop-blur-xl bg-white/5 border border-white/10 rounded-2xl p-8 w-full max-w-sm text-center">
          <p className="text-red-400">Link inválido.</p>
          <Link to="/forgot-password" className="text-neon-cyan hover:underline text-sm mt-2 inline-block">
            Solicitar novo link
          </Link>
        </div>
      </div>
    )
  }

  return (
    <div className="min-h-screen bg-background text-white flex items-center justify-center">
      <div className="backdrop-blur-xl bg-white/5 border border-white/10 rounded-2xl p-8 w-full max-w-sm">
        <h1 className="text-2xl font-semibold text-neon-cyan mb-6">Redefinir senha</h1>

        {success ? (
          <p className="text-neon-green text-sm">
            Senha redefinida com sucesso! Redirecionando para o login...
          </p>
        ) : (
          <>
            {error && <p className="text-red-400 text-sm mb-4">{error}</p>}

            <form onSubmit={handleSubmit} className="flex flex-col gap-4">
              <input
                type="password"
                placeholder="Nova senha"
                value={newPassword}
                onChange={(e) => setNewPassword(e.target.value)}
                className="bg-white/5 border border-white/10 rounded-lg px-4 py-2 outline-none focus:border-neon-cyan"
                required
                minLength={6}
              />
              <input
                type="password"
                placeholder="Confirme a nova senha"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
                className="bg-white/5 border border-white/10 rounded-lg px-4 py-2 outline-none focus:border-neon-cyan"
                required
                minLength={6}
              />
              <button
                type="submit"
                disabled={loading}
                className="bg-neon-cyan text-background font-semibold rounded-lg py-2 mt-2 hover:opacity-90 transition disabled:opacity-50"
              >
                {loading ? 'Salvando...' : 'Redefinir senha'}
              </button>
            </form>
          </>
        )}
      </div>
    </div>
  )
}

export default ResetPassword