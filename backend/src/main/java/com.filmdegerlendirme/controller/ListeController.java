package com.filmdegerlendirme.controller;

import com.filmdegerlendirme.model.Liste;
import com.filmdegerlendirme.model.ListeFilm;
import com.filmdegerlendirme.service.ListeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:60450")
@RestController
@RequestMapping("/api/liste")
public class ListeController {

    @Autowired
    private ListeService listeService;

    @PostMapping("/create")
    public ResponseEntity<Liste> createList(@RequestBody Liste liste) {
        Liste yeniListe = listeService.createList(liste);
        return ResponseEntity.ok(yeniListe);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addFilm(@RequestParam Integer listeId, @RequestParam String filmId) {
        if (listeId == null) return ResponseEntity.badRequest().body("Liste seçilmedi");
        listeService.addFilm(listeId, filmId);
        return ResponseEntity.ok("Film listeye eklendi");
    }

    @GetMapping("/{listeId}")
    public List<ListeFilm> getFilms(@PathVariable Integer listeId) {
        return listeService.getFilms(listeId);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeFilm(@RequestParam Integer listeId, @RequestParam String filmId) {
        listeService.removeFilm(listeId, filmId);
        return ResponseEntity.ok("Film listeden çıkarıldı");
    }

    @GetMapping("/user/{kullaniciAdi}")
    public List<Liste> getUserLists(@PathVariable String kullaniciAdi) {
        return listeService.getListsByUser(kullaniciAdi);
    }
}
