package com.filmdegerlendirme.controller;

import com.filmdegerlendirme.service.FilmService;
import com.filmdegerlendirme.model.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.filmdegerlendirme.repository.FilmRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import com.filmdegerlendirme.service.PersonelService;


import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:60450")
@RestController
@RequestMapping("/api/films")
public class FilmController {

    @Autowired
private PersonelService personelService;

    @Autowired
    private FilmService filmService;

      @Autowired
    private JdbcTemplate jdbcTemplate; 

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getFilmDetail(@PathVariable("id") String id) {
        Map<String, Object> filmDetail = filmService.getFilmDetail(id);
        if (filmDetail == null || filmDetail.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(filmDetail);
    }

     @GetMapping("/search")
public List<Film> searchFilms(@RequestParam String query) {
    String sql = "SELECT * FROM Film WHERE LOWER(baslik) LIKE ?";
    return jdbcTemplate.query(
        sql,
        new Object[]{"%" + query.toLowerCase() + "%"},
        new FilmRowMapper()
    );
}

@GetMapping("/{filmId}/cast")
public List<PersonelService.PersonelWithPozisyon> getCast(@PathVariable String filmId) {
    return personelService.getCastWithPositions(filmId);
}
};