DROP DATABASE IF EXISTS rede_social_uc002;
CREATE DATABASE rede_social_uc002;
USE rede_social_uc002;

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

-- Insere o termo para ser aceito/recusado nos fluxos
INSERT INTO Termo (versao, texto) VALUES ('v1.0', 'Termo de Uso Padrão - Aceite para continuar.');
