package io.cogitech.gooddoctor.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Remede.
 */
@Entity
@Table(name = "remede")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Remede implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "composition")
    private String composition;

    @Column(name = "possologie")
    private String possologie;

    @Column(name = "prevention")
    private String prevention;

    @Column(name = "prix")
    private String prix;

    @ManyToMany(mappedBy = "remedes")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Traitement> traitements = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Remede nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getComposition() {
        return composition;
    }

    public Remede composition(String composition) {
        this.composition = composition;
        return this;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public String getPossologie() {
        return possologie;
    }

    public Remede possologie(String possologie) {
        this.possologie = possologie;
        return this;
    }

    public void setPossologie(String possologie) {
        this.possologie = possologie;
    }

    public String getPrevention() {
        return prevention;
    }

    public Remede prevention(String prevention) {
        this.prevention = prevention;
        return this;
    }

    public void setPrevention(String prevention) {
        this.prevention = prevention;
    }

    public String getPrix() {
        return prix;
    }

    public Remede prix(String prix) {
        this.prix = prix;
        return this;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public Set<Traitement> getTraitements() {
        return traitements;
    }

    public Remede traitements(Set<Traitement> traitements) {
        this.traitements = traitements;
        return this;
    }

    public Remede addTraitements(Traitement traitement) {
        this.traitements.add(traitement);
        traitement.getRemedes().add(this);
        return this;
    }

    public Remede removeTraitements(Traitement traitement) {
        this.traitements.remove(traitement);
        traitement.getRemedes().remove(this);
        return this;
    }

    public void setTraitements(Set<Traitement> traitements) {
        this.traitements = traitements;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Remede)) {
            return false;
        }
        return id != null && id.equals(((Remede) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Remede{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", composition='" + getComposition() + "'" +
            ", possologie='" + getPossologie() + "'" +
            ", prevention='" + getPrevention() + "'" +
            ", prix='" + getPrix() + "'" +
            "}";
    }
}
