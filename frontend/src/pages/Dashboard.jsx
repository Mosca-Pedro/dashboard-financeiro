import { useCallback, useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { Plus } from 'lucide-react'
import api from '../services/api'
import TransactionForm from '../components/TransactionForm'
import AllocationChart from '../components/AllocationChart'
import InsightsFeed from '../components/InsightsFeed'
import ReportActions from '../components/ReportActions'
import useWebSocket from '../hooks/useWebSocket'

function Dashboard() {
  const [summary, setSummary] = useState(null)
  const [transactions, setTransactions] = useState([])
  const [insights, setInsights] = useState([])
  const [loading, setLoading] = useState(true)
  const [showForm, setShowForm] = useState(false)
  const [liveUpdate, setLiveUpdate] = useState(false)
  const navigate = useNavigate()

  const userName = localStorage.getItem('userName') || 'usuário'
  const userId = localStorage.getItem('userId')

  const fetchData = useCallback(async () => {
    try {
      const [summaryRes, transactionsRes, insightsRes] = await Promise.all([
        api.get('/portfolio/summary'),
        api.get('/transactions'),
        api.get('/insights'),
      ])
      setSummary(summaryRes.data)
      setTransactions(transactionsRes.data)
      setInsights(insightsRes.data)
    } catch (err) {
      if (err.response?.status === 401 || err.response?.status === 403) {
        localStorage.removeItem('token')
        navigate('/')
      }
    } finally {
      setLoading(false)
    }
  }, [navigate])

  useEffect(() => {
    const token = localStorage.getItem('token')
    if (!token) {
      navigate('/')
      return
    }
    fetchData()
  }, [navigate, fetchData])

  const handleLiveTransaction = useCallback(() => {
    setLiveUpdate(true)
    fetchData()
    setTimeout(() => setLiveUpdate(false), 2000)
  }, [fetchData])

  useWebSocket(userId, handleLiveTransaction)

  function handleLogout() {
    localStorage.removeItem('token')
    localStorage.removeItem('userName')
    localStorage.removeItem('userId')
    navigate('/')
  }

  function handleTransactionSuccess() {
    setShowForm(false)
    setLoading(true)
    fetchData()
  }

  const maiorAlocacao = summary?.assets?.length
    ? summary.assets.reduce((max, a) => (a.totalInvested > max.totalInvested ? a : max))
    : null

  if (loading) {
    return (
      <div className="min-h-screen bg-background text-white flex items-center justify-center">
        Carregando...
      </div>
    )
  }

  return (
    <div className="min-h-screen bg-background text-white p-6">
      <header className="flex items-center justify-between mb-8">
        <div className="flex items-center gap-3">
          <h1 className="text-2xl font-semibold text-neon-cyan">
            Dashboard Financeiro
          </h1>
          {liveUpdate && (
            <span className="text-xs bg-neon-green/20 text-neon-green rounded-full px-3 py-1 animate-pulse">
              atualizado em tempo real
            </span>
          )}
        </div>
        <div className="flex items-center gap-4">
          <span className="text-white/60">Olá, {userName}</span>
          <button
            onClick={handleLogout}
            className="text-sm text-white/50 hover:text-neon-cyan transition"
          >
            Sair
          </button>
        </div>
      </header>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-4 mb-8">
        <div className="backdrop-blur-xl bg-white/5 border border-white/10 rounded-2xl p-6">
          <p className="text-white/60 text-sm">Total Investido</p>
          <p className="text-2xl font-bold mt-1">
            R$ {summary?.totalInvested?.toFixed(2) ?? '0.00'}
          </p>
        </div>
        <div className="backdrop-blur-xl bg-white/5 border border-white/10 rounded-2xl p-6">
          <p className="text-white/60 text-sm">Ativos na Carteira</p>
          <p className="text-2xl font-bold mt-1 text-neon-green">
            {summary?.assets?.length ?? 0}
          </p>
        </div>
        <div className="backdrop-blur-xl bg-white/5 border border-white/10 rounded-2xl p-6">
          <p className="text-white/60 text-sm">Maior Alocação</p>
          <p className="text-2xl font-bold mt-1">
            {maiorAlocacao ? maiorAlocacao.assetSymbol : '--'}
          </p>
        </div>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-4 mb-8">
        <div className="lg:col-span-1">
          <AllocationChart assets={summary?.assets} />
        </div>

        <div className="lg:col-span-2 backdrop-blur-xl bg-white/5 border border-white/10 rounded-2xl p-6">
          <div className="flex items-center justify-between mb-4">
            <h2 className="text-lg font-semibold">Transações Recentes</h2>
            <button
              onClick={() => setShowForm(true)}
              className="flex items-center gap-2 bg-neon-cyan text-background font-semibold rounded-lg px-4 py-2 text-sm hover:opacity-90 transition"
            >
              <Plus size={16} />
              Nova Transação
            </button>
          </div>

          {transactions.length === 0 ? (
            <p className="text-white/40">Nenhuma transação ainda.</p>
          ) : (
            <div className="flex flex-col gap-2">
              {transactions.map((tx) => (
                <div
                  key={tx.id}
                  className="flex items-center justify-between border-b border-white/5 py-2 text-sm"
                >
                  <span className="font-medium">{tx.type}</span>
                  <span>{tx.assetSymbol}</span>
                  <span className="text-white/60">{tx.amount}</span>
                  <span className="text-white/60">R$ {tx.pricePerUnit}</span>
                </div>
              ))}
            </div>
          )}
        </div>
      </div>

      <InsightsFeed insights={insights} onRefresh={fetchData} />

      <div className="mt-8">
        <ReportActions />
      </div>

      {showForm && (
        <TransactionForm
          onSuccess={handleTransactionSuccess}
          onClose={() => setShowForm(false)}
        />
      )}
    </div>
  )
}

export default Dashboard