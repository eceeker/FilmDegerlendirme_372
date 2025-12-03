package com.filmdegerlendirme.controller;

import com.filmdegerlendirme.model.Film;
import com.filmdegerlendirme.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:60450")
@RestController
@RequestMapping("/api/home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @GetMapping("/featured")
    public List<Film> getFeaturedFilms() {
        return homeService.getFeaturedFilms();
    }

    @GetMapping("/latest")
    public List<Film> getLatestFilms() {
        return homeService.getLatestFilms();
    }

    @GetMapping("/genres")
    public List<Map<String, Object>> getAllGenres() {
        return homeService.getAllGenres();
    }

    @GetMapping("/genre/{id}")
    public List<Film> getFilmsByGenre(@PathVariable("id") int turId) {
        return homeService.getFilmsByGenre(turId);
    }
}
