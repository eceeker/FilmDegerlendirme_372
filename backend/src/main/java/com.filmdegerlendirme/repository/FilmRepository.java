package com.filmdegerlendirme.repository;

import com.filmdegerlendirme.model.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;


import java.util.List;
import java.util.Map;

@Repository
public class FilmRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

        private final FilmRowMapper filmRowMapper = new FilmRowMapper();


    // Tüm filmleri getir
    public List<Film> findAll() {
        String sql = "SELECT * FROM Film";
        return jdbcTemplate.query(sql, filmRowMapper);
    }

    // Yeni film ekle
    public int save(Film film) {
        String sql = "INSERT INTO Film (film_id, baslik, sure, ozet, yayin_yili, afis_url) VALUES (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                film.getFilm_id(),
                film.getBaslik(),
                film.getSure(),
                film.getOzet(),
                film.getYayin_yili(),
                film.getAfis_url()
        );
    }

    // Film detayını getir
    public Optional<Film> findFilmById(String filmId) {
    String sql = "SELECT f.film_id, f.baslik, f.afis_url, f.sure, f.ozet, f.yayin_yili, f.ortalama_puan " +
                 "FROM Film f WHERE f.film_id = ?";

    try {
        Film film = jdbcTemplate.queryForObject(sql, filmRowMapper, filmId);
        return Optional.ofNullable(film);
    } catch (EmptyResultDataAccessException e) {
        return Optional.empty(); // Film bulunamazsa boş döner
    }
}

    // Film türleri
    public List<Map<String, Object>> findFilmGenres(String filmId) {
    String sql = "SELECT t.tur_id, t.tur_adi " +
                 "FROM Film_Tur ft " +
                 "JOIN Tur t ON ft.tur_id = t.tur_id " +
                 "WHERE ft.film_id = ?";
    return jdbcTemplate.queryForList(sql, filmId);
}


    // Film ödülleri
    public List<Map<String, Object>> findFilmAwards(String filmId) {
    String sql = "SELECT o.odul_id, o.odul_adi " +
                 "FROM Film_Odul fo " +
                 "JOIN Odul o ON fo.odul_id = o.odul_id " +
                 "WHERE fo.film_id = ?";
    return jdbcTemplate.queryForList(sql, filmId);
}

    // Film yorumları
    public List<Map<String, Object>> findFilmComments(String filmId) {
        String sql = "SELECT y.yorum_id, y.yorum_metni, y.derece, y.yorum_tarihi, " +
                     "COALESCE(k.kullanici_adi, y.kullanici_adi) AS kullanici_adi, k.email " +
                     "FROM Yorum y " +
                     "LEFT JOIN Kullanici k ON y.kullanici_adi = k.kullanici_adi " +
                     "WHERE y.film_id = ? " +
                     "ORDER BY y.yorum_tarihi DESC";
        return jdbcTemplate.queryForList(sql, filmId);
    }

    public List<Film> searchFilmsByTitle(String query) {
        String sql = "SELECT * FROM Film WHERE LOWER(baslik) LIKE ?";
        return jdbcTemplate.query(sql, filmRowMapper, "%" + query.toLowerCase() + "%");
    }

     public List<String> findFilmCast(String filmId) {
    String sql = "SELECT p.isim, p.soyisim " +
                 "FROM Personel p " +
                 "JOIN Film_Oyuncu fo ON p.personel_id = fo.personel_id " +
                 "WHERE fo.film_id = ?";
    
    return jdbcTemplate.query(sql, new Object[]{filmId}, 
        (rs, rowNum) -> rs.getString("isim") + " " + rs.getString("soyisim"));
}
}