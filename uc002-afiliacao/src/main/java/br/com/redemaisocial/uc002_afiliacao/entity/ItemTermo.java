package br.com.redemaisocial.uc002_afiliacao.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ItemTermo")
public class ItemTermo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String descricao_item;

    @ManyToOne
    @JoinColumn(name = "aceite_termo_id")
    private AceiteTermo aceiteTermo;

    // GETTERS E SETTERS
    public Long getId() { return id; }

    public String getDescricao_item() { return descricao_item; }
    public void setDescricao_item(String descricao_item) { this.descricao_item = descricao_item; }

    public AceiteTermo getAceiteTermo() { return aceiteTermo; }
    public void setAceiteTermo(AceiteTermo aceiteTermo) { this.aceiteTermo = aceiteTermo; }
}
