package com.filmdegerlendirme.model;

public class Kullanici {
    
    // Veritabanındaki sütunlarla birebir eşleşir
    private String kullanici_adi;
    private String sifre;
    private String email;

    // --- Yapıcı (Constructor) Metotlar ---
    public Kullanici() {
    }

    public Kullanici(String kullanici_adi, String sifre, String email) {
        this.kullanici_adi = kullanici_adi;
        this.sifre = sifre;
        this.email = email;
    }

    // --- Getter ve Setter Metotları ---
    
    public String getKullanici_adi() {
        return kullanici_adi;
    }

    public void setKullanici_adi(String kullanici_adi) {
        this.kullanici_adi = kullanici_adi;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}