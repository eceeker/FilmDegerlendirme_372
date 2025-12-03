package com.filmdegerlendirme.repository;

import com.filmdegerlendirme.model.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class HomeRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final FilmRowMapper filmRowMapper = new FilmRowMapper();

    /** 1. Öne Çıkan Filmler */
    public List<Film> findFeaturedFilms() {
        String sql =
                "SELECT f.film_id, f.baslik, f.afis_url, f.sure, f.ozet, f.yayin_yili, f.ortalama_puan " +
                "FROM Film f " +
                "LEFT JOIN Yorum y ON f.film_id = y.film_id " +
                "GROUP BY f.film_id " +
                "ORDER BY AVG(y.derece) DESC " +
                "LIMIT 10";

        return jdbcTemplate.query(sql, filmRowMapper);
    }

    /** 2. Son Eklenen Filmler */
    public List<Film> findLatestFilms() {
        String sql =
                "SELECT f.film_id, f.baslik, f.afis_url, f.sure, f.ozet, f.yayin_yili, f.ortalama_puan " +
                "FROM Film f " +
                "ORDER BY f.yayin_yili DESC " +
                "LIMIT 10";

        return jdbcTemplate.query(sql, filmRowMapper);
    }

    /** 3. Tür listesi */
    public List<Map<String, Object>> findAllGenres() {
        return jdbcTemplate.queryForList("SELECT tur_id, tur_adi FROM Tur");
    }

    /** 4. Türe göre filmler */
    public List<Film> findFilmsByGenreId(int turId) {
        String sql =
                "SELECT f.film_id, f.baslik, f.afis_url, f.sure, f.ozet, f.yayin_yili, f.ortalama_puan " +
                "FROM Film f " +
                "JOIN Film_Tur ft ON f.film_id = ft.film_id " +
                "WHERE ft.tur_id = ? " +
                "ORDER BY f.yayin_yili DESC " +
                "";

        return jdbcTemplate.query(sql, filmRowMapper, turId);
    }
}
