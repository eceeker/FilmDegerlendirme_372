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
                        @RequestBody Yorum yorum,
                        @RequestHeader(value = "Authorization", required = false) String authHeader) {
    // film id ve tarih ayarlanır
    yorum.setFilmId(filmId);
    yorum.setYorum_tarihi(LocalDate.now());

    System.out.println("[DEBUG] Auth header: " + authHeader);
    // Eğer Authorization header varsa, token'dan kullanıcı adını çöz ve yoruma ata
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
        String token = authHeader.substring(7);
        try {
            String username = Jwts.parser().setSigningKey("mysecretkey").parseClaimsJws(token).getBody().getSubject();
            System.out.println("[DEBUG] Extracted username: " + username);
            if (username != null) {
                yorum.setKullanici_adi(username);
            }
        } catch (Exception ex) {
            System.out.println("[DEBUG] Token parse error: " + ex.getMessage());
            // token parse hatası olsa da yorumu kaydetmeye devam edebiliriz
        }
    } else {
        System.out.println("[DEBUG] No auth header or invalid format");
    }
    System.out.println("[DEBUG] Final kullanici_adi: " + yorum.getKullanici_adi());

    return yorumRepository.save(yorum);
}

}
