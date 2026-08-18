function Login() {
  return (
    <div className="min-h-screen bg-background text-white flex items-center justify-center">
      <div className="backdrop-blur-xl bg-white/5 border border-white/10 rounded-2xl p-8 w-full max-w-sm">
        <h1 className="text-2xl font-semibold text-neon-cyan mb-6">Entrar</h1>
        <form className="flex flex-col gap-4">
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
          <button
            type="submit"
            className="bg-neon-cyan text-background font-semibold rounded-lg py-2 mt-2 hover:opacity-90 transition"
          >
            Entrar
          </button>
        </form>
      </div>
    </div>
  )
}

export default Login