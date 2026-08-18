function Register() {
  return (
    <div className="min-h-screen bg-background text-white flex items-center justify-center">
      <div className="backdrop-blur-xl bg-white/5 border border-white/10 rounded-2xl p-8 w-full max-w-sm">
        <h1 className="text-2xl font-semibold text-neon-cyan mb-6">Criar conta</h1>
        <form className="flex flex-col gap-4">
          <input
            type="text"
            placeholder="Nome"
            className="bg-white/5 border border-white/10 rounded-lg px-4 py-2 outline-none focus:border-neon-cyan"
          />
          <input
            type="email"
            placeholder="E-mail"
            className="bg-white/5 border border-white/10 rounded-lg px-4 py-2 outline-none focus:border-neon-cyan"
          />
          <input
            type="password"
            placeholder="Senha"
            className="bg-white/5 border border-white/10 rounded-lg px-4 py-2 outline-none focus:border-neon-cyan"
          />
          <select
            className="bg-white/5 border border-white/10 rounded-lg px-4 py-2 outline-none focus:border-neon-cyan text-white"
          >
            <option value="BRL" className="bg-background">BRL - Real</option>
            <option value="USD" className="bg-background">USD - Dólar</option>
          </select>
          <button
            type="submit"
            className="bg-neon-cyan text-background font-semibold rounded-lg py-2 mt-2 hover:opacity-90 transition"
          >
            Criar conta
          </button>
        </form>
      </div>
    </div>
  )
}

export default Register