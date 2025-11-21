package br.com.redemaisocial.uc002_afiliacao.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Candidato")
public class Candidato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;

    @OneToOne
    @JoinColumn(name = "pessoa_fisica_id")
    private PessoaFisica pessoaFisica;

    @OneToOne
    @JoinColumn(name = "pessoa_juridica_id")
    private PessoaJuridica pessoaJuridica;

    // GETTERS E SETTERS
    public Long getId() { return id; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public PessoaFisica getPessoaFisica() { return pessoaFisica; }
    public void setPessoaFisica(PessoaFisica pessoaFisica) { this.pessoaFisica = pessoaFisica; }

    public PessoaJuridica getPessoaJuridica() { return pessoaJuridica; }
    public void setPessoaJuridica(PessoaJuridica pessoaJuridica) { this.pessoaJuridica = pessoaJuridica; }
}
