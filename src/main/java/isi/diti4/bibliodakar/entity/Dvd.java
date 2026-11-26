package isi.diti4.bibliodakar.entity;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("DVD")
public class Dvd extends Document {

    @Column(length = 150)
    private String realisateur;

    private int duree;

    protected Dvd() {
        // constructeur requis par JPA
    }

    public Dvd(String code, String titre, int annee, String realisateur, int duree) {
        super(code, titre, annee);
        this.realisateur = realisateur;
        this.duree = duree;
    }

    public String getRealisateur() {
        return realisateur;
    }

    public void setRealisateur(String realisateur) {
        this.realisateur = realisateur;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    @Override
    public double calculerPrix() {
        if (duree > 120) {
            return 600;
        }
        return 500;
    }

    public double calculerPrix(int nbJours) {
        return nbJours * calculerPrix();
    }

    @Override
    public String toString() {
        return "[" + getCode() + "] " + getTitre() + " (" + getAnnee() + ") - "
                + (estDisponible() ? "disponible" : "emprunté") + " | "
                + getRealisateur() + ", " + getDuree() + " min.";
    }
}
