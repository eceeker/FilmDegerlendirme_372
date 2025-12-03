package com.filmdegerlendirme.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Pozisyon")
public class Pozisyon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pozisyon_id")
    private Integer pozisyonId;

    @Column(name = "pozisyon_adi", nullable = false, unique = true)
    private String pozisyonAdi;

    // Getter & Setter
    public Integer getPozisyonId() { return pozisyonId; }
    public void setPozisyonId(Integer pozisyonId) { this.pozisyonId = pozisyonId; }

    public String getPozisyonAdi() { return pozisyonAdi; }
    public void setPozisyonAdi(String pozisyonAdi) { this.pozisyonAdi = pozisyonAdi; }
}
