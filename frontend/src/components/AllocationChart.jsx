import { PieChart, Pie, Cell, Tooltip, ResponsiveContainer, Legend } from 'recharts'

const COLORS = ['#22d3ee', '#4ade80', '#f472b6', '#facc15', '#a78bfa', '#fb923c']

function AllocationChart({ assets }) {
  if (!assets || assets.length === 0) {
    return (
      <div className="backdrop-blur-xl bg-white/5 border border-white/10 rounded-2xl p-6 flex items-center justify-center h-64">
        <p className="text-white/40">Sem dados para exibir</p>
      </div>
    )
  }

  const data = assets.map((asset) => ({
    name: asset.assetSymbol,
    value: asset.totalInvested,
  }))

  return (
    <div className="backdrop-blur-xl bg-white/5 border border-white/10 rounded-2xl p-6">
      <h2 className="text-lg font-semibold mb-4">Alocação por Ativo</h2>
      <ResponsiveContainer width="100%" height={250}>
        <PieChart>
          <Pie
            data={data}
            dataKey="value"
            nameKey="name"
            cx="50%"
            cy="50%"
            outerRadius={80}
            label={(entry) => entry.name}
          >
            {data.map((entry, index) => (
              <Cell key={entry.name} fill={COLORS[index % COLORS.length]} />
            ))}
          </Pie>
          <Tooltip
            contentStyle={{ backgroundColor: '#0f172a', border: '1px solid rgba(255,255,255,0.1)', borderRadius: '8px' }}
            formatter={(value) => `R$ ${value.toFixed(2)}`}
          />
          <Legend />
        </PieChart>
      </ResponsiveContainer>
    </div>
  )
}

export default AllocationChart