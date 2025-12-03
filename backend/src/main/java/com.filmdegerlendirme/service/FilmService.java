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



import java.util.List;

@Service
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;

    // İş kuralı: Tüm filmleri listeleme
    public List<Film> tumFilmleriGetir() {
        return filmRepository.findAll();
    }

     public Map<String, Object> getFilmDetail(String filmId) {
        Optional<Film> filmOpt = filmRepository.findFilmById(filmId);
Film film = filmOpt.orElseThrow(() -> new RuntimeException("Film bulunamadı"));

        List<Map<String, Object>> turlar = filmRepository.findFilmGenres(filmId);
        List<Map<String, Object>> oduller = filmRepository.findFilmAwards(filmId);
        List<Map<String, Object>> yorumlar = filmRepository.findFilmComments(filmId);
        List<String> cast = filmRepository.findFilmCast(filmId); 

        Map<String, Object> response = new HashMap<>();
        response.put("film", film);
        response.put("turlar", turlar);
        response.put("oduller", oduller);
        response.put("yorumlar", yorumlar);
        response.put("cast", cast);

        return response;
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