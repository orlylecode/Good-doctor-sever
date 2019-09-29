package io.cogitech.gooddoctor.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Traitement.
 */
@Entity
@Table(name = "traitement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Traitement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "auteur")
    private String auteur;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "traitement_remedes",
               joinColumns = @JoinColumn(name = "traitement_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "remedes_id", referencedColumnName = "id"))
    private Set<Remede> remedes = new HashSet<>();

    @ManyToMany(mappedBy = "traitements")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Maladie> maladies = new HashSet<>();

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

    public Traitement nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAuteur() {
        return auteur;
    }

    public Traitement auteur(String auteur) {
        this.auteur = auteur;
        return this;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getDescription() {
        return description;
    }

    public Traitement description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Remede> getRemedes() {
        return remedes;
    }

    public Traitement remedes(Set<Remede> remedes) {
        this.remedes = remedes;
        return this;
    }

    public Traitement addRemedes(Remede remede) {
        this.remedes.add(remede);
        remede.getTraitements().add(this);
        return this;
    }

    public Traitement removeRemedes(Remede remede) {
        this.remedes.remove(remede);
        remede.getTraitements().remove(this);
        return this;
    }

    public void setRemedes(Set<Remede> remedes) {
        this.remedes = remedes;
    }

    public Set<Maladie> getMaladies() {
        return maladies;
    }

    public Traitement maladies(Set<Maladie> maladies) {
        this.maladies = maladies;
        return this;
    }

    public Traitement addMaladies(Maladie maladie) {
        this.maladies.add(maladie);
        maladie.getTraitements().add(this);
        return this;
    }

    public Traitement removeMaladies(Maladie maladie) {
        this.maladies.remove(maladie);
        maladie.getTraitements().remove(this);
        return this;
    }

    public void setMaladies(Set<Maladie> maladies) {
        this.maladies = maladies;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Traitement)) {
            return false;
        }
        return id != null && id.equals(((Traitement) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Traitement{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", auteur='" + getAuteur() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
