package io.cogitech.gooddoctor.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Maladie.
 */
@Entity
@Table(name = "maladie")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Maladie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    private String type;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "maladie_conseils",
               joinColumns = @JoinColumn(name = "maladie_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "conseils_id", referencedColumnName = "id"))
    private Set<Conseil> conseils = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "maladie_traitements",
               joinColumns = @JoinColumn(name = "maladie_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "traitements_id", referencedColumnName = "id"))
    private Set<Traitement> traitements = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "maladie_symptomes",
               joinColumns = @JoinColumn(name = "maladie_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "symptomes_id", referencedColumnName = "id"))
    private Set<Symptome> symptomes = new HashSet<>();

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

    public Maladie nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public Maladie description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public Maladie type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Conseil> getConseils() {
        return conseils;
    }

    public Maladie conseils(Set<Conseil> conseils) {
        this.conseils = conseils;
        return this;
    }

    public Maladie addConseils(Conseil conseil) {
        this.conseils.add(conseil);
        conseil.getMaladies().add(this);
        return this;
    }

    public Maladie removeConseils(Conseil conseil) {
        this.conseils.remove(conseil);
        conseil.getMaladies().remove(this);
        return this;
    }

    public void setConseils(Set<Conseil> conseils) {
        this.conseils = conseils;
    }

    public Set<Traitement> getTraitements() {
        return traitements;
    }

    public Maladie traitements(Set<Traitement> traitements) {
        this.traitements = traitements;
        return this;
    }

    public Maladie addTraitements(Traitement traitement) {
        this.traitements.add(traitement);
        traitement.getMaladies().add(this);
        return this;
    }

    public Maladie removeTraitements(Traitement traitement) {
        this.traitements.remove(traitement);
        traitement.getMaladies().remove(this);
        return this;
    }

    public void setTraitements(Set<Traitement> traitements) {
        this.traitements = traitements;
    }

    public Set<Symptome> getSymptomes() {
        return symptomes;
    }

    public Maladie symptomes(Set<Symptome> symptomes) {
        this.symptomes = symptomes;
        return this;
    }

    public Maladie addSymptomes(Symptome symptome) {
        this.symptomes.add(symptome);
        symptome.getMaladies().add(this);
        return this;
    }

    public Maladie removeSymptomes(Symptome symptome) {
        this.symptomes.remove(symptome);
        symptome.getMaladies().remove(this);
        return this;
    }

    public void setSymptomes(Set<Symptome> symptomes) {
        this.symptomes = symptomes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Maladie)) {
            return false;
        }
        return id != null && id.equals(((Maladie) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Maladie{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
