package com.filmdegerlendirme.repository;

import com.filmdegerlendirme.model.Film;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;

public class FilmRowMapper implements RowMapper<Film> {

    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        Film film = new Film();

        // Temel alanlar
        film.setFilm_id(rs.getString("film_id"));
        film.setBaslik(rs.getString("baslik"));
        film.setAfis_url(rs.getString("afis_url"));
        film.setSure(rs.getInt("sure"));
        film.setOzet(rs.getString("ozet"));
        film.setYayin_yili(rs.getInt("yayin_yili"));

        // Ortalama puan (AVG ile hesaplanan veya doğrudan tablodan)
        BigDecimal ortalamaPuan = null;
        try {
            ortalamaPuan = rs.getBigDecimal("ortalama_puan");
        } catch (SQLException e) {
            // Eğer sorguda yoksa null kalacak
        }
        film.setOrtalama_puan(ortalamaPuan);

        return film;
    }
}
