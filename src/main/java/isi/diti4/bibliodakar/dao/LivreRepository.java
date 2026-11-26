package isi.diti4.bibliodakar.dao;

import isi.diti4.bibliodakar.entity.Livre;

public class LivreRepository extends BaseRepository<Livre> {

    public LivreRepository() {
        super(Livre.class);
    }
}
