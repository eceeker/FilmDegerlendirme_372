package com.filmdegerlendirme.service;

import com.filmdegerlendirme.model.Film;
import com.filmdegerlendirme.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;



import java.util.List;

@Service
public class FilmService {

    private final JdbcTemplate jdbcTemplate;
    private final PersonelService personelService;
    @Autowired
    public FilmService(JdbcTemplate jdbcTemplate, PersonelService personelService) {
        this.jdbcTemplate = jdbcTemplate;
        this.personelService = personelService;
    }
    @Autowired
    private FilmRepository filmRepository;

    // İş kuralı: Tüm filmleri listeleme
    public List<Film> tumFilmleriGetir() {
        return filmRepository.findAll();
    }

      public Map<String, Object> getFilmDetail(String filmId) {
        // Film temel bilgileri
        String sqlFilm = "SELECT * FROM Film WHERE film_id = ?";
        Map<String, Object> film = jdbcTemplate.queryForMap(sqlFilm, filmId);

        if (film == null || film.isEmpty()) return null;

        // Türler
        String sqlTurlar = """
            SELECT t.tur_id, t.tur_adi
            FROM Tur t
            JOIN Film_Tur ft ON t.tur_id = ft.tur_id
            WHERE ft.film_id = ?
        """;
        List<Map<String, Object>> turlar = jdbcTemplate.queryForList(sqlTurlar, filmId);

        // Ödüller
        String sqlOduller = """
            SELECT o.odul_adi
            FROM Odul o
            JOIN Film_Odul fo ON o.odul_id = fo.odul_id
            WHERE fo.film_id = ?
        """;
        List<Map<String, Object>> oduller = jdbcTemplate.queryForList(sqlOduller, filmId);

        // Yorumlar
        String sqlYorumlar = "SELECT * FROM Yorum WHERE film_id = ?";
        List<Map<String, Object>> yorumlar = jdbcTemplate.queryForList(sqlYorumlar, filmId);

        // Cast + pozisyon
        List<PersonelService.PersonelWithPozisyon> cast = personelService.getCastWithPositions(filmId);

        // Sonuç map
        Map<String, Object> result = new HashMap<>();
        result.put("film", film);
        result.put("turlar", turlar);
        result.put("oduller", oduller);
        result.put("yorumlar", yorumlar);
        result.put("cast", cast);

        return result;
    }
    
    // İş kuralı: Yeni film kaydı yapmadan önce bazı kontrolleri yap
    public Film filmKaydet(Film film) {
        // İş Mantığı: Örneğin, film_id'nin benzersiz olup olmadığını kontrol et
        if (film.getBaslik() == null || film.getBaslik().isEmpty()) {
            throw new IllegalArgumentException("Film başlığı boş olamaz.");
        }
        
        filmRepository.save(film);
        return film;
    }

}