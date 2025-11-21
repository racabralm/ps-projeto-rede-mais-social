package br.com.redemaisocial.uc002_afiliacao.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "PessoaJuridica")
public class PessoaJuridica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String cnpj;

    @Column(nullable = false)
    private String razao_social;

    // GETTERS E SETTERS
    public Long getId() { return id; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getRazao_social() { return razao_social; }
    public void setRazao_social(String razao_social) { this.razao_social = razao_social; }
}
