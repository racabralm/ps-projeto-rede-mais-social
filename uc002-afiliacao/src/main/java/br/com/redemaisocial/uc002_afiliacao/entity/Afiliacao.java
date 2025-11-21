package br.com.redemaisocial.uc002_afiliacao.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Afiliacao")
public class Afiliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;

    @OneToOne
    @JoinColumn(name = "candidato_id")
    private Candidato candidato;

    // GETTERS E SETTERS
    public Long getId() { return id; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Candidato getCandidato() { return candidato; }
    public void setCandidato(Candidato candidato) { this.candidato = candidato; }
}
