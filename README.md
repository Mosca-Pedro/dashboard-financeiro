# Dashboard Financeiro Inteligente

Aplicação fullstack em tempo real para gestão de carteira de investimentos e criptoativos, com um agente de IA que analisa riscos de concentração e sugere rebalanceamento.

**Acesse:** [dashboard-financeiro-two-psi.vercel.app](https://dashboard-financeiro-two-psi.vercel.app/)

> Obs: o backend roda em um plano gratuito (Render) e pode levar até 1 minuto para "acordar" no primeiro acesso.

---

## Funcionalidades

- **Autenticação JWT** — registro, login e proteção de rotas
- **Gestão de carteira** — registro de compras, vendas, depósitos e saques
- **Cálculo automático** — quantidade líquida, preço médio ponderado e total investido por ativo
- **Gráfico de alocação** — visualização interativa da distribuição da carteira
- **Agente de IA** — análise de risco de concentração via API da Groq (Llama 3.3), com pareceres em linguagem natural
- **Tempo real** — atualizações instantâneas via WebSocket (STOMP), sem precisar recarregar a página
- **Relatórios em PDF** — geração e download do resumo da carteira
- **Envio por e-mail** — relatório enviado automaticamente para o usuário
- **UI Glassmorphism** — interface dark mode com efeito de vidro fosco

---

## Stack Tecnológica

### Backend
- Java 17 + Spring Boot 4
- Spring Security + JWT
- Spring Data JPA / Hibernate
- MySQL
- Spring WebSocket (STOMP)
- OpenPDF
- Spring Mail
- Groq API (LLM)

### Frontend
- React 18 + Vite
- Tailwind CSS v4
- React Router
- Axios
- Recharts
- STOMP.js + SockJS

### Infraestrutura
- **Banco de dados:** Aiven (MySQL gerenciado)
- **Backend:** Render (Docker)
- **Frontend:** Vercel

---

## Arquitetura

Frontend (React/Vite) → API REST (Spring Boot) → MySQL
↘ WebSocket (tempo real)
↘ Groq API (análise de IA)


O backend segue uma arquitetura em camadas:

Controller → Service → Repository → Entity → Banco de Dado


- **entity/** — mapeamento JPA das tabelas (User, Transaction, AIInsight)
- **repository/** — interfaces de acesso a dados
- **service/** — regras de negócio (cálculo de carteira, autenticação, geração de PDF, integração com IA)
- **dto/** — objetos de entrada/saída da API
- **controller/** — endpoints REST
- **config/** — segurança, JWT, WebSocket

---

## Segurança

- Senhas criptografadas com BCrypt
- Autenticação stateless via JWT (token no header `Authorization: Bearer`)
- Usuário identificado automaticamente pelo token em toda rota autenticada — nenhum dado sensível depende de parâmetros vindos do cliente
- Credenciais e chaves de API nunca versionadas no código — resolvidas via variáveis de ambiente

---

## Rodando localmente

### Pré-requisitos
- Java 17+
- Node.js 18+
- MySQL local (ou uma instância na nuvem)

### Backend
```bash
cd backend/dashboard-financeiro
# configure as variáveis de ambiente (ver application.yml)
./mvnw spring-boot:run
```

### Frontend
```bash
cd frontend
npm install
npm run dev
```

---

## Roteiro do projeto

- [x] Modelagem e API REST completa
- [x] Autenticação JWT
- [x] Cálculo de carteira
- [x] WebSocket em tempo real
- [x] Agente de IA (Groq)
- [x] Exportação de PDF e envio por e-mail
- [x] Frontend completo (React + Tailwind)
- [x] Deploy em produção (Render + Vercel + Aiven)

---

## Autor

Desenvolvido por Pedro como projeto de portfólio.