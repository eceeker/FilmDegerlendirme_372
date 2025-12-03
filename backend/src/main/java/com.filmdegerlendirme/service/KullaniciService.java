package com.filmdegerlendirme.service;

import com.filmdegerlendirme.model.Kullanici;
import com.filmdegerlendirme.repository.KullaniciRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import java.util.List;
import java.util.Optional;

@Service
public class KullaniciService {

    @Autowired
    private KullaniciRepository kullaniciRepository;

    // Register
    public Kullanici kayitOl(Kullanici kullanici) {

        if (kullaniciRepository.findByKullaniciAdi(kullanici.getKullanici_adi()).isPresent()) {
            throw new IllegalArgumentException("Bu kullanıcı adı zaten alınmış.");
        }

        kullaniciRepository.save(kullanici);
        return kullanici;
    }

    // Login
    public Optional<Kullanici> girisYap(String kullaniciAdi, String sifre) {
        Optional<Kullanici> opt = kullaniciRepository.findByKullaniciAdi(kullaniciAdi);

        if (opt.isPresent() && opt.get().getSifre().equals(sifre)) {
            return opt;
        }
        return Optional.empty();
    }

    public List<Kullanici> tumKullanicilar() {
        return kullaniciRepository.findAll();
    }

    // NOT: LoginRequest veya controller referansları servis içinde olmamalı.
}
