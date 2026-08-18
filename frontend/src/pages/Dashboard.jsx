function Dashboard() {
  return (
    <div className="min-h-screen bg-background text-white p-6">
      <header className="flex items-center justify-between mb-8">
        <h1 className="text-2xl font-semibold text-neon-cyan">
          Dashboard Financeiro
        </h1>
        <div className="text-white/60">Olá, usuário</div>
      </header>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-4 mb-8">
        <div className="backdrop-blur-xl bg-white/5 border border-white/10 rounded-2xl p-6">
          <p className="text-white/60 text-sm">Saldo Total</p>
          <p className="text-2xl font-bold mt-1">R$ --</p>
        </div>
        <div className="backdrop-blur-xl bg-white/5 border border-white/10 rounded-2xl p-6">
          <p className="text-white/60 text-sm">Lucro / Prejuízo</p>
          <p className="text-2xl font-bold mt-1 text-neon-green">R$ --</p>
        </div>
        <div className="backdrop-blur-xl bg-white/5 border border-white/10 rounded-2xl p-6">
          <p className="text-white/60 text-sm">Maior Alocação</p>
          <p className="text-2xl font-bold mt-1">--</p>
        </div>
      </div>

      <div className="backdrop-blur-xl bg-white/5 border border-white/10 rounded-2xl p-6">
        <h2 className="text-lg font-semibold mb-4">Transações Recentes</h2>
        <p className="text-white/40">Nenhuma transação ainda.</p>
      </div>
    </div>
  )
}

export default Dashboard