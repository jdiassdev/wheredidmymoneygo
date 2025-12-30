# WhereDidMyMoneyGo API

API backend do app **WhereDidMyMoneyGo** em Spring Boot, backend organizado por feature/domínio.

---

## 🔹 Features & Routes | Domains

### 1️⃣ User

| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/api/v1/users/auth/me` | Retorna dados do usuário logado |
| POST | `/api/v1/users/register` | Cria novo usuário |
| POST | `/api/v1/users/auth/login` | Login de usuário, retorna token |
| PUT | `/api/v1/users/auth/me` | Atualiza dados do usuário (ex: email, nome) |

---

### 2️⃣ Categoriy

| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/api/v1/categories` | Lista todas as categorias (ordenadas por nome) |


---

### 3️⃣ Transaction

| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/api/v1/transactions` | Lista todas as transações do usuário logado (ordenadas por data) |
| GET | `/api/v1/transactions/:id` | Detalhes de uma transação |
| POST | `/api/v1/transactions` | Cria nova transação |
| PUT | `/api/v1/transactions/:id` | Atualiza uma transação existente |
| DELETE | `/api/v1/transactions/:id` | Deleta transação |

---

### 5️⃣ Auth / Segurança

- Autenticação via JWT ou sessão (Spring Security)
---

Cada feature contém:  
`Entity → Repository → Service → Controller → DTO`
