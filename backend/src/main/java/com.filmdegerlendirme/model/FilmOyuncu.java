package com.filmdegerlendirme.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Film_Oyuncu")
public class FilmOyuncu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "film_id")
    private String filmId;

    @Column(name = "personel_id")
    private String personelId;

    // Getter & Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFilmId() { return filmId; }
    public void setFilmId(String filmId) { this.filmId = filmId; }

    public String getPersonelId() { return personelId; }
    public void setPersonelId(String personelId) { this.personelId = personelId; }
}
