# BiblioDakar

Application console de démonstration JPA/Hibernate pour gérer les documents d'une bibliothèque (livres et DVD).

## Prérequis

- JDK 25
- PostgreSQL
- Le wrapper Maven fourni (`mvnw.cmd` sous Windows, `./mvnw` sous Linux et macOS)

## Base de données

Crée la base si elle n'existe pas :

```sql
CREATE DATABASE bibliodakar;
```

Le schéma est créé ou mis à jour automatiquement par Hibernate (`hibernate.hbm2ddl.auto=update`). Ce réglage est prévu pour le développement.

## Configuration

Les identifiants PostgreSQL ne sont pas versionnés. Copie le fichier d'exemple à la racine du projet 

Puis renseigne `db.properties` :

```properties
jakarta.persistence.jdbc.url=jdbc:postgresql://localhost:5432/bibliodakar
jakarta.persistence.jdbc.user=ton_utilisateur
jakarta.persistence.jdbc.password=ton_mot_de_passe
```

## Compiler et lancer

 lancer `isi.diti4.bibliodakar.Main` depuis IntelliJ IDEA.

## Ce que fait la démo

- Crée deux livres et deux DVD au premier lancement.
- Affiche les documents et leur prix.
- Emprunte un document disponible, affiche son état, puis le rend.
- La démonstration est rejouable : le document est rendu à la fin.

## Structure

```text
src/main/java/isi/diti4/bibliodakar/
  Main.java
  entity/          Document, Livre, Dvd
  interfaces/      Empruntable
  dao/             BaseRepository, LivreRepository, DvdRepository, JpaUtil
src/main/resources/META-INF/persistence.xml
db.properties.example   Exemple de configuration locale
```

## Remarques

- `target/` et `db.properties` ne sont pas versionnés.
- Le fichier `persistence.xml` ne contient plus d'identifiants.
- Pour un vrai déploiement, remplace `hibernate.hbm2ddl.auto=update` par des migrations (Flyway ou Liquibase) et désactive `hibernate.show_sql`.
