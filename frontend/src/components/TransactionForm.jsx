import { useState } from 'react'
import api from '../services/api'

function TransactionForm({ onSuccess, onClose }) {
  const [type, setType] = useState('BUY')
  const [assetSymbol, setAssetSymbol] = useState('')
  const [amount, setAmount] = useState('')
  const [pricePerUnit, setPricePerUnit] = useState('')
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)

  async function handleSubmit(e) {
    e.preventDefault()
    setError('')
    setLoading(true)

    try {
      await api.post('/transactions', {
        type,
        assetSymbol: assetSymbol.toUpperCase(),
        amount: parseFloat(amount),
        pricePerUnit: parseFloat(pricePerUnit),
      })
      onSuccess()
    } catch (err) {
      setError('Não foi possível registrar a transação')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="fixed inset-0 bg-black/60 flex items-center justify-center z-50 p-4">
      <div className="backdrop-blur-xl bg-white/5 border border-white/10 rounded-2xl p-8 w-full max-w-sm">
        <div className="flex items-center justify-between mb-6">
          <h2 className="text-xl font-semibold text-neon-cyan">Nova Transação</h2>
          <button onClick={onClose} className="text-white/50 hover:text-white">✕</button>
        </div>

        {error && <p className="text-red-400 text-sm mb-4">{error}</p>}

        <form onSubmit={handleSubmit} className="flex flex-col gap-4">
          <select
            value={type}
            onChange={(e) => setType(e.target.value)}
            className="bg-white/5 border border-white/10 rounded-lg px-4 py-2 outline-none focus:border-neon-cyan text-white"
          >
            <option value="BUY" className="bg-background">Compra</option>
            <option value="SELL" className="bg-background">Venda</option>
            <option value="DEPOSIT" className="bg-background">Depósito</option>
            <option value="WITHDRAW" className="bg-background">Saque</option>
          </select>

          <input
            type="text"
            placeholder="Ativo (ex: BTC)"
            value={assetSymbol}
            onChange={(e) => setAssetSymbol(e.target.value)}
            className="bg-white/5 border border-white/10 rounded-lg px-4 py-2 outline-none focus:border-neon-cyan"
            required
          />

          <input
            type="number"
            step="any"
            placeholder="Quantidade"
            value={amount}
            onChange={(e) => setAmount(e.target.value)}
            className="bg-white/5 border border-white/10 rounded-lg px-4 py-2 outline-none focus:border-neon-cyan"
            required
          />

          <input
            type="number"
            step="any"
            placeholder="Preço por unidade"
            value={pricePerUnit}
            onChange={(e) => setPricePerUnit(e.target.value)}
            className="bg-white/5 border border-white/10 rounded-lg px-4 py-2 outline-none focus:border-neon-cyan"
            required
          />

          <button
            type="submit"
            disabled={loading}
            className="bg-neon-cyan text-background font-semibold rounded-lg py-2 mt-2 hover:opacity-90 transition disabled:opacity-50"
          >
            {loading ? 'Salvando...' : 'Registrar'}
          </button>
        </form>
      </div>
    </div>
  )
}

export default TransactionForm