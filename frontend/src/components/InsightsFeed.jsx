import { useState } from 'react'
import { Sparkles } from 'lucide-react'
import api from '../services/api'

function InsightsFeed({ insights, onRefresh }) {
  const [generating, setGenerating] = useState(false)
  const [error, setError] = useState('')

  async function handleGenerate() {
    setGenerating(true)
    setError('')
    try {
      await api.post('/insights/generate')
      onRefresh()
    } catch (err) {
      setError('Não foi possível gerar a análise agora')
    } finally {
      setGenerating(false)
    }
  }

  async function handleMarkAsRead(id) {
    try {
      await api.patch(`/insights/${id}/read`)
      onRefresh()
    } catch (err) {
      // silencioso, não é crítico
    }
  }

  return (
    <div className="backdrop-blur-xl bg-white/5 border border-white/10 rounded-2xl p-6">
      <div className="flex items-center justify-between mb-4">
        <h2 className="text-lg font-semibold">Insights da IA</h2>
        <button
          onClick={handleGenerate}
          disabled={generating}
          className="flex items-center gap-2 bg-neon-cyan text-background font-semibold rounded-lg px-4 py-2 text-sm hover:opacity-90 transition disabled:opacity-50"
        >
          <Sparkles size={16} />
          {generating ? 'Analisando...' : 'Gerar Análise'}
        </button>
      </div>

      {error && <p className="text-red-400 text-sm mb-4">{error}</p>}

      {insights.length === 0 ? (
        <p className="text-white/40">Nenhum insight gerado ainda.</p>
      ) : (
        <div className="flex flex-col gap-3">
          {insights.map((insight) => (
            <div
              key={insight.id}
              onClick={() => insight.status === 'UNREAD' && handleMarkAsRead(insight.id)}
              className={`border rounded-xl p-4 cursor-pointer transition ${
                insight.status === 'UNREAD'
                  ? 'border-neon-cyan/40 bg-neon-cyan/5'
                  : 'border-white/5 bg-white/[0.02]'
              }`}
            >
              <div className="flex items-center justify-between mb-2">
                <span className="text-xs text-neon-cyan font-medium">{insight.agentName}</span>
                {insight.status === 'UNREAD' && (
                  <span className="text-xs bg-neon-cyan text-background rounded-full px-2 py-0.5">
                    novo
                  </span>
                )}
              </div>
              <p className="text-sm text-white/80">{insight.insightText}</p>
            </div>
          ))}
        </div>
      )}
    </div>
  )
}

export default InsightsFeed