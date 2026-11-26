package isi.diti4.bibliodakar.entity;

import isi.diti4.bibliodakar.interfaces.Empruntable;
import jakarta.persistence.*;

import java.time.Year;
import java.util.Objects;

@Entity
@Table(name = "document")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class Document implements Empruntable {

    public static final int ANNEE_MIN = 1900;

    @Id
    @Column(length = 20)
    private String code;

    @Column(nullable = false, length = 255)
    private String titre;

    @Column(nullable = false)
    private int annee;

    @Column(nullable = false)
    private boolean emprunte;

    protected Document() {
        // constructeur requis par JPA
    }

    public Document(String code, String titre, int annee) {
        this.code = code;
        this.titre = titre;
        validerAnnee(annee);
        this.annee = annee;
    }

    public Document(String code, String titre) {
        this(code, titre, Year.now().getValue());
    }

    public String getCode() {
        return code;
    }

    public String getTitre() {
        return titre;
    }

    public int getAnnee() {
        return annee;
    }

    public boolean isEmprunte() {
        return emprunte;
    }

    public void setAnnee(int annee) {
        validerAnnee(annee);
        this.annee = annee;
    }

    private static void validerAnnee(int annee) {
        int anneeMax = Year.now().getValue();
        if (annee < ANNEE_MIN || annee > anneeMax) {
            throw new IllegalArgumentException(
                    "L'année doit être comprise entre " + ANNEE_MIN + " et " + anneeMax + " inclus.");
        }
    }

    @Override
    public void emprunter() {
        if (emprunte) {
            System.out.println("Déjà emprunté");
        } else {
            this.emprunte = true;
        }
    }

    @Override
    public void rendre() {
        this.emprunte = false;
    }

    @Override
    public boolean estDisponible() {
        return !emprunte;
    }

    public abstract double calculerPrix();

    @Override
    public String toString() {
        return "[" + getCode() + "] " + getTitre() + " (" + getAnnee() + ") ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Document)) return false;
        Document autre = (Document) o;
        return Objects.equals(code, autre.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
