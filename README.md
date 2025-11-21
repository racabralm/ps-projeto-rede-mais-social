# Projeto de Software: Rede Mais Social

<p align="center">
  <em>Uma plataforma digital para conectar voluntários, ONGs e pessoas que precisam de ajuda, facilitando o engajamento social e o crescimento mútuo.</em>
</p>

---

## 📋 Sobre o Projeto

A Rede Mais Social é um projeto acadêmico que visa criar uma solução tecnológica para um problema social comum: a dificuldade de conectar pessoas dispostas a ajudar (voluntários e doadores) com organizações e indivíduos que precisam de auxílio. A plataforma servirá como uma ponte, centralizando oportunidades de voluntariado, campanhas de doação e pedidos de ajuda.

O desenvolvimento deste projeto segue os princípios de engenharia de software, com documentação detalhada, prototipagem e implementação baseada em requisitos bem definidos.

Este repositório contém a **implementação completa do Cenário UC002 – Solicitar Afiliação**, que corresponde ao fluxo em que o voluntário (Pessoa Física ou Pessoa Jurídica) realiza a solicitação inicial de afiliação e interage com o Termo de Uso da plataforma.

### ✨ Features

* Cadastro e afiliação de Voluntários (Pessoa Física e Jurídica).
* Cadastro e afiliação de ONGs.
* Análise e aprovação de cadastros pela plataforma. 
* Recomendação de ONGs e campanhas para voluntários. 

## 📂 Estrutura do Repositório

```
uc002-afiliacao/
│
├── src/main/java/br/com/redemaisocial/uc002_afiliacao/
│   ├── controller/       → Endpoints REST (PF, PJ, Termo)
│   ├── entity/           → Entidades do sistema
│   ├── repository/       → Interfaces JPA para o MySQL
│   ├── service/          → Lógica dos fluxos (afiliacao + termo)
│   ├── WebConfig.java
│   └── Uc002AfiliacaoApplication.java
│
├── src/main/resources/
│   ├── static/
│   │    ├── login.html
│   │    ├── afiliacao.html
│   │    ├── termo.html
│   │    ├── sucesso.html
│   │    ├── recusa.html
│   │    ├── css/styles.css
│   │    └── js/scripts.js
│   └── application.properties
│
├── Mais_Social_1.sql                → Script principal para criação do banco
├── Mais_Social_apresentacao.sql     → Script para demonstrar os fluxos no vídeo
└── README.md
```

---

## 🧪 O que foi implementado

- Backend em **Spring Boot**, com integração completa ao **MySQL**.  
- Fluxo completo de criação de Candidato, Afiliacao, Perfil e Certidao.  
- Upload de arquivo para PJ (Multipart).  
- Aceite do termo via endpoint dedicado.  
- Frontend simples funcional com **HTML + CSS + JS**, reproduzindo as telas do diagrama de sequência.  
- Armazenamento temporário de informações via `localStorage` para simular sessão entre telas.

---

## ▶️ Como executar

1. Rodar o arquivo `Mais_Social_1.sql` no MySQL Workbench.  
2. Ajustar `application.properties` com seu usuário e senha.  
3. Rodar o projeto no IntelliJ via `Uc002AfiliacaoApplication`.  
4. Abrir no navegador:  
   ```
   http://localhost:8080/login.html
   ```

---

## 👥 Equipe

| Nome                       | RA        |
| -------------------------- | --------- |
| Bruna Amorim Maia          | 10431883  |
| Rafael Araujo Cabral Moreira | 10441919  |

**Turma:** 04N

**Prof.:** Ana Claudia Rossi

---
