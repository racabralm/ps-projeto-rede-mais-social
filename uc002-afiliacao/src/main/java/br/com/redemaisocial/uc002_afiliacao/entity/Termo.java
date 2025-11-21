package br.com.redemaisocial.uc002_afiliacao.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Termo")
public class Termo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String versao;

    @Column(columnDefinition = "TEXT")
    private String texto;

    private java.sql.Timestamp creation_date_time;

    // GETTERS E SETTERS
    public Long getId() { return id; }

    public String getVersao() { return versao; }
    public void setVersao(String versao) { this.versao = versao; }

    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }

    public java.sql.Timestamp getCreation_date_time() { return creation_date_time; }
    public void setCreation_date_time(java.sql.Timestamp creation_date_time) { this.creation_date_time = creation_date_time; }
}
