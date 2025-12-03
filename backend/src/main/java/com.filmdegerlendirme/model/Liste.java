package com.filmdegerlendirme.model;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class Liste {
    private Integer liste_id;
    private String liste_adi;
    private String kullanici_adi;
    private LocalDate olusturma_tarihi;

    private List<ListeFilm> filmler = new ArrayList<>(); // frontend için

    // getter ve setter
    public Integer getListe_id() { return liste_id; }
    public void setListe_id(Integer liste_id) { this.liste_id = liste_id; }

    public String getListe_adi() { return liste_adi; }
    public void setListe_adi(String liste_adi) { this.liste_adi = liste_adi; }

    public String getKullanici_adi() { return kullanici_adi; }
    public void setKullanici_adi(String kullanici_adi) { this.kullanici_adi = kullanici_adi; }

    public LocalDate getOlusturma_tarihi() { return olusturma_tarihi; }
    public void setOlusturma_tarihi(LocalDate olusturma_tarihi) { this.olusturma_tarihi = olusturma_tarihi; }

    public List<ListeFilm> getFilmler() { return filmler; }
    public void setFilmler(List<ListeFilm> filmler) { this.filmler = filmler; }
}
