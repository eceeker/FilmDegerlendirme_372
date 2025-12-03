package com.filmdegerlendirme.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

import java.time.LocalDate;

@Entity
@Table(name = "Yorum")
public class Yorum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer yorum_id;

    private String kullanici_adi;

    @Column(name = "film_id")  // DB’deki kolon adı film_id
    private String filmId;       // Entity’de kullandığın field adı filmId

    private int derece;

    private String yorum_metni;

    private LocalDate yorum_tarihi;

    // Getter ve Setter’lar
    public Integer getYorum_id() { return yorum_id; }
    public void setYorum_id(Integer yorum_id) { this.yorum_id = yorum_id; }

    public String getKullanici_adi() { return kullanici_adi; }
    public void setKullanici_adi(String kullanici_adi) { this.kullanici_adi = kullanici_adi; }

    public String getFilmId() { return filmId; }
    public void setFilmId(String filmId) { this.filmId = filmId; }

    public int getDerece() { return derece; }
    public void setDerece(int derece) { this.derece = derece; }

    public String getYorum_metni() { return yorum_metni; }
    public void setYorum_metni(String yorum_metni) { this.yorum_metni = yorum_metni; }

    public LocalDate getYorum_tarihi() { return yorum_tarihi; }
    public void setYorum_tarihi(LocalDate yorum_tarihi) { this.yorum_tarihi = yorum_tarihi; }
}
