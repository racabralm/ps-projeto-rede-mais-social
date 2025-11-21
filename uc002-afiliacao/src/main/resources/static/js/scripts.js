const API = "http://localhost:8080/api";

/* ============================
      LOGIN → FORMULÁRIO
===============================*/
function continuarLogin() {
    const email = document.getElementById("email").value.trim();
    const tipo = document.getElementById("tipo").value;
    const idnum = document.getElementById("documento").value.trim();

    if (!email || !idnum) {
        alert("Preencha todos os campos!");
        return;
    }

    localStorage.setItem("af_email", email);
    localStorage.setItem("af_tipo", tipo);
    localStorage.setItem("af_doc", idnum);

    window.location.href = "afiliacao.html";
}

/* ============================
    MUDAR ENTRE PF E PJ
===============================*/
function switchTab(tipo) {
    document.getElementById("tabPF").classList.remove("active");
    document.getElementById("tabPJ").classList.remove("active");
    document.getElementById("formPF").style.display = "none";
    document.getElementById("formPJ").style.display = "none";

    if (tipo === "pf") {
        document.getElementById("tabPF").classList.add("active");
        document.getElementById("formPF").style.display = "block";
    } else {
        document.getElementById("tabPJ").classList.add("active");
        document.getElementById("formPJ").style.display = "block";
    }
}

/* Funções de Tratamento de Erro Comum */
async function handleApiError(response) {
    // Tenta extrair a mensagem de erro do JSON do Spring
    try {
        const json = await response.json();
        let mensagemErro = json.message || json.error || "Erro desconhecido ao processar a afiliação.";

        // Limpa a mensagem se for o full trace (ex: 'br.com.redemaisocial.uc002_afiliacao.service.RuntimeException: CPF já existe.')
        if (mensagemErro.includes("br.com.redemaisocial")) {
             const partes = mensagemErro.split(': ');
             // Pega a última parte, que é a mensagem customizada da RuntimeException
             mensagemErro = partes[partes.length - 1];
        }

        alert("Erro: " + mensagemErro);

    } catch (e) {
        // Se o retorno não for um JSON válido
        alert(`Erro na requisição: Status ${response.status}`);
    }
}


/* ============================
      ENVIAR PF (COM TRATAMENTO DE ERRO LIMPO)
===============================*/
async function enviarPF() {
    const cpf = document.getElementById("pf-cpf").value;
    const nome = document.getElementById("pf-nome").value;
    const nasc = document.getElementById("pf-nasc").value;
    const sexo = document.getElementById("pf-sexo").value;

    const url = `${API}/afiliacao/solicitar/pf?cpf=${cpf}&nome=${nome}&dataNascimento=${nasc}&sexo=${sexo}`;

    const r = await fetch(url, { method: "POST" });

    if (r.ok) {
        const json = await r.json();
        localStorage.setItem("afiliacao-json", JSON.stringify(json));
        window.location.href = "termo.html";
    } else {
        await handleApiError(r);
    }
}

/* ============================
      ENVIAR PJ (COM TRATAMENTO DE ERRO LIMPO)
===============================*/
async function enviarPJ() {
    const form = new FormData();
    const cnpj = document.getElementById("pj-cnpj").value;
    const razaoSocial = document.getElementById("pj-razao").value;
    const arquivoInput = document.getElementById("pj-arquivo");

    if (!cnpj || !razaoSocial || arquivoInput.files.length === 0) {
        alert("Erro: Preencha todos os campos obrigatórios.");
        return;
    }

    form.append("cnpj", cnpj);
    form.append("razaoSocial", razaoSocial);
    form.append("arquivo", arquivoInput.files[0]);

    const r = await fetch(`${API}/afiliacao/solicitar/pj`, {
        method: "POST",
        body: form
    });

    if (r.ok) {
        const json = await r.json();
        localStorage.setItem("afiliacao-json", JSON.stringify(json));
        window.location.href = "termo.html";
    } else {
        await handleApiError(r);
    }
}

/* ============================
      TERMO – LÓGICA
===============================*/
async function carregarTermo() {
    const af = JSON.parse(localStorage.getItem("afiliacao-json"));

    const r = await fetch(`${API}/termos/atual`);
    const termo = await r.json();

    document.getElementById("termoBox").innerHTML =
        `<h3>Versão ${termo.versao}</h3><p>${termo.texto}</p>`;

    window.termoAtual = termo;
    window.afiliacaoAtual = af;
}

async function aceitarTermo() {
    const af = window.afiliacaoAtual.id;
    const t = window.termoAtual.id;

    await fetch(`${API}/termos/aceitar?afiliacaoId=${af}&termoId=${t}`, {
        method: "POST"
    });

    window.location.href = "sucesso.html";
}

function recusarTermo() {
    // Redireciona para a nova tela de recusa
    window.location.href = "recusa.html";
}