package com.filmdegerlendirme.repository;

import com.filmdegerlendirme.model.Liste;
import com.filmdegerlendirme.model.ListeFilm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ListeRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<ListeFilm> listeFilmRowMapper = new RowMapper<>() {
        @Override
        public ListeFilm mapRow(ResultSet rs, int rowNum) throws SQLException {
            ListeFilm lf = new ListeFilm();
            lf.setListe_id(rs.getInt("liste_id"));
            lf.setFilm_id(rs.getString("film_id"));
            return lf;
        }
    };

    // Liste oluştur
    public Liste createList(Liste liste) {
        String sql = "INSERT INTO Liste (liste_adi, kullanici_adi, olusturma_tarihi) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, liste.getListe_adi(), liste.getKullanici_adi(), liste.getOlusturma_tarihi());

        // Oluşturulan listeyi almak için son eklenen kaydı getir
        String fetchSql = "SELECT * FROM Liste WHERE liste_id = LAST_INSERT_ID()";
        return jdbcTemplate.queryForObject(fetchSql, (rs, rowNum) -> {
            Liste l = new Liste();
            l.setListe_id(rs.getInt("liste_id"));
            l.setListe_adi(rs.getString("liste_adi"));
            l.setKullanici_adi(rs.getString("kullanici_adi"));
            l.setOlusturma_tarihi(rs.getDate("olusturma_tarihi").toLocalDate());
            l.setFilmler(new ArrayList<>());
            return l;
        });
    }

    // Filme ekle
    public void addFilmToList(Integer listeId, String filmId) {
        String sql = "INSERT INTO Liste_Film (liste_id, film_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, listeId, filmId);
    }

   public List<Liste> findByUser(String kullaniciAdi) {
    String sql = "SELECT * FROM Liste WHERE TRIM(LOWER(kullanici_adi)) = TRIM(LOWER(?))";

    System.out.println("Fetching lists for: '" + kullaniciAdi + "'"); // debug

    List<Liste> lists = jdbcTemplate.query(sql, (rs, rowNum) -> {
        Liste l = new Liste();
        l.setListe_id(rs.getInt("liste_id"));
        l.setListe_adi(rs.getString("liste_adi"));
        l.setKullanici_adi(rs.getString("kullanici_adi"));
        l.setOlusturma_tarihi(rs.getDate("olusturma_tarihi").toLocalDate());
        l.setFilmler(new ArrayList<>());
        return l;
    }, kullaniciAdi);

    System.out.println("Fetched lists count: " + lists.size()); // debug
    for (Liste l : lists) {
        System.out.println("Liste: " + l.getListe_adi() + " - " + l.getKullanici_adi());
    }

    return lists;
}


    // Listeye ait filmler
    public List<ListeFilm> getFilmsByListId(Integer listeId) {
        String sql = "SELECT * FROM Liste_Film WHERE liste_id = ?";
        return jdbcTemplate.query(sql, listeFilmRowMapper, listeId);
    }

    // Film çıkar
    public void removeFilmFromList(Integer listeId, String filmId) {
        String sql = "DELETE FROM Liste_Film WHERE liste_id = ? AND film_id = ?";
        jdbcTemplate.update(sql, listeId, filmId);
    }
}
