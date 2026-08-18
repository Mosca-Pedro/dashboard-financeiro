import { useState } from 'react'
import { Download, Mail } from 'lucide-react'
import api from '../services/api'

function ReportActions() {
  const [downloading, setDownloading] = useState(false)
  const [sending, setSending] = useState(false)
  const [message, setMessage] = useState('')

  async function handleDownload() {
    setDownloading(true)
    try {
      const response = await api.get('/portfolio/report/pdf', {
        responseType: 'blob',
      })

      const url = window.URL.createObjectURL(new Blob([response.data]))
      const link = document.createElement('a')
      link.href = url
      link.setAttribute('download', 'relatorio-carteira.pdf')
      document.body.appendChild(link)
      link.click()
      link.remove()
      window.URL.revokeObjectURL(url)
    } catch (err) {
      setMessage('Erro ao baixar o relatório')
    } finally {
      setDownloading(false)
    }
  }

  async function handleSendEmail() {
    setSending(true)
    setMessage('')
    try {
      const response = await api.post('/portfolio/report/email')
      setMessage(response.data)
    } catch (err) {
      setMessage('Erro ao enviar o e-mail')
    } finally {
      setSending(false)
      setTimeout(() => setMessage(''), 4000)
    }
  }

  return (
    <div className="backdrop-blur-xl bg-white/5 border border-white/10 rounded-2xl p-6 flex flex-col sm:flex-row items-start sm:items-center justify-between gap-4">
      <div>
        <h2 className="text-lg font-semibold">Relatório da Carteira</h2>
        {message && <p className="text-neon-cyan text-sm mt-1">{message}</p>}
      </div>
      <div className="flex gap-3">
        <button
          onClick={handleDownload}
          disabled={downloading}
          className="flex items-center gap-2 bg-white/10 border border-white/10 rounded-lg px-4 py-2 text-sm hover:bg-white/20 transition disabled:opacity-50"
        >
          <Download size={16} />
          {downloading ? 'Baixando...' : 'Baixar PDF'}
        </button>
        <button
          onClick={handleSendEmail}
          disabled={sending}
          className="flex items-center gap-2 bg-neon-cyan text-background font-semibold rounded-lg px-4 py-2 text-sm hover:opacity-90 transition disabled:opacity-50"
        >
          <Mail size={16} />
          {sending ? 'Enviando...' : 'Enviar por E-mail'}
        </button>
      </div>
    </div>
  )
}

export default ReportActions