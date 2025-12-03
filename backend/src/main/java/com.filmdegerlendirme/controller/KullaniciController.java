package com.filmdegerlendirme.controller;

import com.filmdegerlendirme.model.Kullanici;
import com.filmdegerlendirme.service.KullaniciService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:60450")
@RestController
@RequestMapping("/api/auth")
public class KullaniciController {

    @Autowired
    private KullaniciService kullaniciService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Kullanici kullanici) {
        try {
            kullaniciService.kayitOl(kullanici);
            return ResponseEntity.ok("Kayıt başarılı!");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> req) {

        String kullaniciAdi = req.get("kullanici_adi");
        String sifre = req.get("sifre");

        if (kullaniciService.girisYap(kullaniciAdi, sifre).isPresent()) {
            return ResponseEntity.ok("Giriş başarılı!");
        }

        return ResponseEntity.status(401).body("Hatalı kullanıcı adı veya şifre!");
    }
}
