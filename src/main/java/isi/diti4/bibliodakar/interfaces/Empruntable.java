package isi.diti4.bibliodakar.interfaces;

public interface Empruntable {
    void emprunter();

    void rendre();

    default boolean estDisponible() {
        return true;
    }
}
