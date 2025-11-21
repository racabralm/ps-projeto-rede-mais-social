package br.com.redemaisocial.uc002_afiliacao.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Habilidade")
public class Habilidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private java.sql.Date data;

    private String status;

    @ManyToOne
    @JoinColumn(name = "perfil_id")
    private Perfil perfil;

    // GETTERS E SETTERS
    public Long getId() { return id; }

    public java.sql.Date getData() { return data; }
    public void setData(java.sql.Date data) { this.data = data; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Perfil getPerfil() { return perfil; }
    public void setPerfil(Perfil perfil) { this.perfil = perfil; }
}
