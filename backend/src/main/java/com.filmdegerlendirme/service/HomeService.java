package com.filmdegerlendirme.service;

import com.filmdegerlendirme.model.Film;
import com.filmdegerlendirme.repository.HomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service // İş mantığı katmanı için özel Spring bileşeni
public class HomeService {

    @Autowired
    private HomeRepository homeRepository;

    /**
     * React frontend'in beklediği tüm verileri hazırlar ve döndürür.
     */
    public Map<String, Object> getHomePageData() {
        
        // 1. Öne çıkan ve son eklenen filmleri çek (Paralel olarak düşünebiliriz)
        List<Film> featuredFilms = homeRepository.findFeaturedFilms();
        List<Film> latestFilms = homeRepository.findLatestFilms();
        
        // 2. Türleri ve bu türlere ait filmleri grupla
        Map<String, List<Film>> genreMovies = new HashMap<>();
        List<Map<String, Object>> genres = homeRepository.findAllGenres();

        for (Map<String, Object> genre : genres) {
            Integer turId = (Integer) genre.get("tur_id");
            String turAdi = (String) genre.get("tur_adi");
            
            // Her bir tür için filmleri çek
            List<Film> films = homeRepository.findFilmsByGenreId(turId);
            
            if (!films.isEmpty()) {
                genreMovies.put(turAdi, films);
            }
        }

        // 3. Tüm verileri tek bir Map içinde birleştir (React'in beklediği JSON formatı)
        Map<String, Object> homeData = new HashMap<>();
        homeData.put("featured", featuredFilms);
        homeData.put("latest", latestFilms);
        homeData.put("genres", genreMovies);
        
        return homeData;
    }
    public List<Film> getFeaturedFilms() {
        return homeRepository.findFeaturedFilms();
    }

    public List<Film> getLatestFilms() {
        return homeRepository.findLatestFilms();
    }

    public List<Map<String, Object>> getAllGenres() {
        return homeRepository.findAllGenres();
    }

    public List<Film> getFilmsByGenre(int turId) {
        return homeRepository.findFilmsByGenreId(turId);
    }
}