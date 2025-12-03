package com.filmdegerlendirme.controller;

import com.filmdegerlendirme.model.Yorum;
import com.filmdegerlendirme.repository.YorumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping("/api/yorum")
@CrossOrigin(origins = "http://localhost:60450")
public class YorumController {

    @Autowired
    private YorumRepository yorumRepository;

    // GET: Film yorumlarını çek
    @GetMapping("/yorum/{filmId}/comments")
public List<Yorum> getYorumlarByFilm(@PathVariable String filmId) {
    return yorumRepository.findByFilmId(filmId);
}

@PostMapping("/{filmId}/comments")
public Yorum addComment(@PathVariable String filmId,
                        @RequestBody Yorum yorum) {
    // Film ID ve tarih ayarlanır
    yorum.setFilmId(filmId);
    yorum.setYorum_tarihi(LocalDate.now());

    // Eğer kullanıcı adı null ise default bir değer atayabiliriz
    if (yorum.getKullanici_adi() == null || yorum.getKullanici_adi().isEmpty()) {
        yorum.setKullanici_adi("Anonim");
    }

    return yorumRepository.save(yorum);
}


}
