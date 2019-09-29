package io.cogitech.gooddoctor.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Symptome.
 */
@Entity
@Table(name = "symptome")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Symptome implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "description")
    private String description;

    @Column(name = "effet")
    private String effet;

    @ManyToMany(mappedBy = "symptomes")
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

    public Symptome nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public Symptome description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEffet() {
        return effet;
    }

    public Symptome effet(String effet) {
        this.effet = effet;
        return this;
    }

    public void setEffet(String effet) {
        this.effet = effet;
    }

    public Set<Maladie> getMaladies() {
        return maladies;
    }

    public Symptome maladies(Set<Maladie> maladies) {
        this.maladies = maladies;
        return this;
    }

    public Symptome addMaladies(Maladie maladie) {
        this.maladies.add(maladie);
        maladie.getSymptomes().add(this);
        return this;
    }

    public Symptome removeMaladies(Maladie maladie) {
        this.maladies.remove(maladie);
        maladie.getSymptomes().remove(this);
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
        if (!(o instanceof Symptome)) {
            return false;
        }
        return id != null && id.equals(((Symptome) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Symptome{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", effet='" + getEffet() + "'" +
            "}";
    }
}
