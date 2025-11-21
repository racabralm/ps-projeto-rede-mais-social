-- ######################################################################
-- # 1. SETUP INICIAL DO BANCO (ANTES DE MOSTRAR OS FLUXOS NO SISTEMA)
-- ######################################################################

DROP DATABASE IF EXISTS rede_social_uc002;
CREATE DATABASE rede_social_uc002;
USE rede_social_uc002;

-- ######################################################################
-- # 2. DEFINIÇÃO DAS TABELAS
-- ######################################################################

CREATE TABLE PessoaFisica (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    nome VARCHAR(255) NOT NULL,
    data_nascimento DATE,
    sexo CHAR(1)
);

CREATE TABLE PessoaJuridica (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cnpj VARCHAR(18) UNIQUE NOT NULL,
    razao_social VARCHAR(255) NOT NULL
);

CREATE TABLE Candidato (
    id INT AUTO_INCREMENT PRIMARY KEY,
    data DATE NOT NULL DEFAULT (CURRENT_DATE()),
    status VARCHAR(50) NOT NULL,
    pessoa_fisica_id INT UNIQUE,
    pessoa_juridica_id INT UNIQUE,
    CONSTRAINT chk_tipo_pessoa CHECK (
        (pessoa_fisica_id IS NOT NULL AND pessoa_juridica_id IS NULL) OR
        (pessoa_fisica_id IS NULL AND pessoa_juridica_id IS NOT NULL)
    ),
    FOREIGN KEY (pessoa_fisica_id) REFERENCES PessoaFisica(id),
    FOREIGN KEY (pessoa_juridica_id) REFERENCES PessoaJuridica(id)
);

CREATE TABLE Afiliacao (
    id INT AUTO_INCREMENT PRIMARY KEY,
    data DATE NOT NULL DEFAULT (CURRENT_DATE()),
    status VARCHAR(50) NOT NULL,
    candidato_id INT NOT NULL UNIQUE,
    FOREIGN KEY (candidato_id) REFERENCES Candidato(id)
);

CREATE TABLE Perfil (
    id INT AUTO_INCREMENT PRIMARY KEY,
    descricao TEXT,
    candidato_id INT NOT NULL UNIQUE,
    FOREIGN KEY (candidato_id) REFERENCES Candidato(id)
);

CREATE TABLE Habilidade (
    id INT AUTO_INCREMENT PRIMARY KEY,
    data DATE,
    status VARCHAR(50),
    nome_habilidade VARCHAR(100) NOT NULL,
    perfil_id INT NOT NULL,
    FOREIGN KEY (perfil_id) REFERENCES Perfil(id) ON DELETE CASCADE
);

CREATE TABLE Interesse (
    id INT AUTO_INCREMENT PRIMARY KEY,
    data DATE,
    status VARCHAR(50),
    nome_interesse VARCHAR(100) NOT NULL,
    perfil_id INT NOT NULL,
    FOREIGN KEY (perfil_id) REFERENCES Perfil(id) ON DELETE CASCADE
);

CREATE TABLE Termo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    versao VARCHAR(20) NOT NULL,
    texto TEXT NOT NULL,
    creation_date_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE AceiteTermo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    data DATE NOT NULL DEFAULT (CURRENT_DATE()),
    status VARCHAR(50) NOT NULL,
    afiliacao_id INT NOT NULL UNIQUE,
    termo_id INT NOT NULL,
    FOREIGN KEY (afiliacao_id) REFERENCES Afiliacao(id),
    FOREIGN KEY (termo_id) REFERENCES Termo(id)
);

CREATE TABLE ItemTermo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    aceite_termo_id INT NOT NULL,
    descricao_item TEXT,
    FOREIGN KEY (aceite_termo_id) REFERENCES AceiteTermo(id) ON DELETE CASCADE
);

CREATE TABLE Certidao (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(100) NOT NULL,
    arquivo LONGBLOB,
    pessoa_juridica_id INT NOT NULL,
    FOREIGN KEY (pessoa_juridica_id) REFERENCES PessoaJuridica(id)
);

-- ######################################################################
-- # 3. INSERÇÃO DO TERMO INICIAL (PARA OS DOIS FLUXOS)
-- ######################################################################

INSERT INTO Termo (versao, texto) VALUES ('v1.0', 'Termo de Uso Padrão - Aceite para continuar.');

-- ######################################################################
-- # 4. ESTADO INICIAL DO BANCO (ANTES DE QUALQUER FLUXO)
-- ######################################################################

SELECT 'ESTADO INICIAL DO BANCO' AS STATUS;
SELECT * FROM Termo;
SELECT * FROM PessoaFisica;
SELECT * FROM PessoaJuridica;
SELECT * FROM Candidato;
SELECT * FROM Afiliacao;
SELECT * FROM Perfil;
SELECT * FROM AceiteTermo;
SELECT * FROM Certidao;

-- ######################################################################
-- # 5. FLUXO 1 — PESSOA FÍSICA → APÓS AS TELAS DO SEU SISTEMA
-- ######################################################################
-- (Depois de mostrar PF no vídeo, rode esta parte)

SELECT 'FLUXO 1 - APÓS CADASTRO PF' AS STATUS;
SELECT * FROM PessoaFisica;
SELECT id, status, pessoa_fisica_id, pessoa_juridica_id FROM Candidato;
SELECT * FROM Afiliacao;
SELECT * FROM Perfil;
SELECT 'AceiteTermo ainda vazio (pois o termo não foi aceito ainda):' AS Info;
SELECT * FROM AceiteTermo;

-- Agora PF aceitou o termo
SELECT 'FLUXO 1 - APÓS ACEITE DO TERMO (PF)' AS STATUS;
SELECT * FROM AceiteTermo;

-- ######################################################################
-- # 6. FLUXO 2 — PESSOA JURÍDICA → APÓS AS TELAS DO SISTEMA
-- ######################################################################
-- (Depois de mostrar PJ no vídeo, rode esta parte)

SELECT 'FLUXO 2 - APÓS CADASTRO PJ' AS STATUS;
SELECT * FROM PessoaJuridica;
SELECT * FROM Certidao;
SELECT 'Candidato, Afiliacao e Perfil agora têm 2 registros (PF + PJ):' AS Info;
SELECT id, status, pessoa_fisica_id, pessoa_juridica_id FROM Candidato;
SELECT * FROM Afiliacao;
SELECT * FROM Perfil;

-- Termo recusado — nada muda
SELECT 'FLUXO 2 - APÓS RECUSA DO TERMO' AS STATUS;
SELECT 'AceiteTermo continua com apenas o registro do fluxo PF:' AS Info;
SELECT * FROM AceiteTermo;

-- FIM DO SCRIPT
