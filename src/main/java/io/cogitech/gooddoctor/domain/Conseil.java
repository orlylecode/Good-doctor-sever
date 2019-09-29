package io.cogitech.gooddoctor.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Conseil.
 */
@Entity
@Table(name = "conseil")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Conseil implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "auteur")
    private String auteur;

    @Column(name = "conseil")
    private String conseil;

    @ManyToMany(mappedBy = "conseils")
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

    public Conseil nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAuteur() {
        return auteur;
    }

    public Conseil auteur(String auteur) {
        this.auteur = auteur;
        return this;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getConseil() {
        return conseil;
    }

    public Conseil conseil(String conseil) {
        this.conseil = conseil;
        return this;
    }

    public void setConseil(String conseil) {
        this.conseil = conseil;
    }

    public Set<Maladie> getMaladies() {
        return maladies;
    }

    public Conseil maladies(Set<Maladie> maladies) {
        this.maladies = maladies;
        return this;
    }

    public Conseil addMaladies(Maladie maladie) {
        this.maladies.add(maladie);
        maladie.getConseils().add(this);
        return this;
    }

    public Conseil removeMaladies(Maladie maladie) {
        this.maladies.remove(maladie);
        maladie.getConseils().remove(this);
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
        if (!(o instanceof Conseil)) {
            return false;
        }
        return id != null && id.equals(((Conseil) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Conseil{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", auteur='" + getAuteur() + "'" +
            ", conseil='" + getConseil() + "'" +
            "}";
    }
}
