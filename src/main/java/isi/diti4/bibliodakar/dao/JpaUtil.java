package isi.diti4.bibliodakar.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public final class JpaUtil {

    private static final String FICHIER_CONFIG = "db.properties";

    private static final EntityManagerFactory EMF = createEntityManagerFactory();

    private JpaUtil() {
    }

    private static EntityManagerFactory createEntityManagerFactory() {
        Path fichier = Path.of(System.getProperty("db.config", FICHIER_CONFIG));

        if (!Files.exists(fichier)) {
            throw new IllegalStateException(
                    "Fichier de configuration introuvable : " + fichier.toAbsolutePath()
                            + ". Copie db.properties.example vers db.properties à la racine du projet, "
                            + "puis renseigne les identifiants PostgreSQL.");
        }

        Properties fichierProperties = new Properties();
        try (Reader reader = Files.newBufferedReader(fichier, StandardCharsets.UTF_8)) {
            fichierProperties.load(reader);
        } catch (IOException e) {
            throw new IllegalStateException("Impossible de lire " + fichier.toAbsolutePath(), e);
        }

        Map<String, Object> proprietes = new HashMap<>();
        for (String nom : fichierProperties.stringPropertyNames()) {
            proprietes.put(nom, fichierProperties.getProperty(nom));
        }

        return Persistence.createEntityManagerFactory("biblioPU", proprietes);
    }

    public static EntityManager getEntityManager() {
        return EMF.createEntityManager();
    }

    public static void close() {
        if (EMF.isOpen()) {
            EMF.close();
        }
    }
}
