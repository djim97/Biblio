package isi.diti4.bibliodakar;

import isi.diti4.bibliodakar.dao.DvdRepository;
import isi.diti4.bibliodakar.dao.JpaUtil;
import isi.diti4.bibliodakar.dao.LivreRepository;
import isi.diti4.bibliodakar.entity.Document;
import isi.diti4.bibliodakar.entity.Dvd;
import isi.diti4.bibliodakar.entity.Livre;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        LivreRepository livreRepository = new LivreRepository();
        DvdRepository dvdRepository = new DvdRepository();

        try {
            initialiserDonnees(livreRepository, dvdRepository);

            List<Document> liste = new ArrayList<>();
            liste.addAll(livreRepository.findAll());
            liste.addAll(dvdRepository.findAll());

            double total = 0;
            for (Document document : liste) {
                System.out.printf("%s -> %.0f F%n", document, document.calculerPrix());
                total += document.calculerPrix();
            }
            System.out.printf("Total : %.0f F%n", total);
            System.out.println("Documents en base : "
                    + (livreRepository.count() + dvdRepository.count()));

            Document choisi = liste.stream()
                    .filter(Document::estDisponible)
                    .findFirst()
                    .orElse(null);

            if (choisi == null) {
                System.out.println("Tous les documents sont déjà empruntés.");
                return;
            }

            System.out.println();
            System.out.println("Démonstration sur " + choisi.getCode() + " :");
            changerEmprunt(choisi, true, livreRepository, dvdRepository);
            System.out.println("  après emprunt, disponible ? " + choisi.estDisponible());

            changerEmprunt(choisi, false, livreRepository, dvdRepository);
            System.out.println("  après retour, disponible ? " + choisi.estDisponible());
        } finally {
            JpaUtil.close();
        }
    }

    private static void initialiserDonnees(LivreRepository livreRepository,
                                           DvdRepository dvdRepository) {
        if (livreRepository.findAll().isEmpty()) {
            livreRepository.save(new Livre("L01", "Une si longue lettre", 1979, "Mariama Bâ", 165));
            livreRepository.save(new Livre("L02", "L’Aventure ambiguë", 1961, "Cheikh Hamidou Kane", 190));
        }

        if (dvdRepository.findAll().isEmpty()) {
            dvdRepository.save(new Dvd("D01", "Atlantique", 2019, "Mati Diop", 105));
            dvdRepository.save(new Dvd("D02", "Tey", 2012, "Alain Gomis", 130));
        }
    }

    private static void changerEmprunt(Document document, boolean emprunte,
                                       LivreRepository livreRepository,
                                       DvdRepository dvdRepository) {
        if (document instanceof Livre livre) {
            livreRepository.updateEmprunt(livre.getCode(), emprunte);
        } else if (document instanceof Dvd dvd) {
            dvdRepository.updateEmprunt(dvd.getCode(), emprunte);
        } else {
            throw new IllegalArgumentException(
                    "Type de document non géré : " + document.getClass().getSimpleName());
        }
    }
}
