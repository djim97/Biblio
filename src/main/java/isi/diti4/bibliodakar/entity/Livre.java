package isi.diti4.bibliodakar.entity;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("LIVRE")
public class Livre extends Document {

    @Column(length = 150)
    private String auteur;

    @Column(name = "nb_pages")
    private int nbPages;

    protected Livre() {
        // constructeur requis par JPA
    }

    public Livre(String code, String titre, int annee, String auteur, int nbPages) {
        super(code, titre, annee);
        this.auteur = auteur;
        this.nbPages = nbPages;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public int getNbPages() {
        return nbPages;
    }

    public void setNbPages(int nbPages) {
        this.nbPages = nbPages;
    }

    @Override
    public double calculerPrix() {
        return 200;
    }

    @Override
    public String toString() {
        return "[" + getCode() + "] " + getTitre() + " (" + getAnnee() + ") - "
                + (estDisponible() ? "disponible" : "emprunté") + " | "
                + getAuteur() + ", " + getNbPages() + " p.";
    }
}
