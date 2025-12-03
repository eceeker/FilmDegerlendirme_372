package com.filmdegerlendirme.service;

import com.filmdegerlendirme.model.Personel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonelService {

    private final JdbcTemplate jdbcTemplate;

    public PersonelService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // DTO benzeri sınıf, cast ile pozisyonu birlikte döndürmek için
    public static class PersonelWithPozisyon {
        private String adiSoyadi;
        private String pozisyon;

        public PersonelWithPozisyon(String adiSoyadi, String pozisyon) {
            this.adiSoyadi = adiSoyadi;
            this.pozisyon = pozisyon;
        }

        public String getAdiSoyadi() {
            return adiSoyadi;
        }

        public String getPozisyon() {
            return pozisyon;
        }
    }

    public List<PersonelWithPozisyon> getCastWithPositions(String filmId) {
        String sql = """
            SELECT CONCAT(p.isim, ' ', p.soyisim) AS adiSoyadi, poz.pozisyon_adi
FROM Personel p
JOIN Personel_Pozisyon pp ON p.personel_id = pp.personel_id
JOIN Pozisyon poz ON pp.pozisyon_id = poz.pozisyon_id
JOIN Film_Oyuncu fo ON p.personel_id = fo.personel_id
WHERE fo.film_id = ?

        """;

        return jdbcTemplate.query(
            sql,
            new Object[]{filmId},
            (rs, rowNum) -> new PersonelWithPozisyon(
                rs.getString("adiSoyadi"),
                rs.getString("pozisyon_adi")
            )
        );
    }
}
