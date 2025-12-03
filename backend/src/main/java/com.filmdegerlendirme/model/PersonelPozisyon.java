package com.filmdegerlendirme.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Personel_Pozisyon")
@IdClass(PersonelPozisyonId.class)
public class PersonelPozisyon {

    @Id
    @Column(name = "personel_id")
    private String personelId;

    @Id
    @Column(name = "pozisyon_id")
    private Integer pozisyonId;

    // Getter & Setter
    public String getPersonelId() { return personelId; }
    public void setPersonelId(String personelId) { this.personelId = personelId; }

    public Integer getPozisyonId() { return pozisyonId; }
    public void setPozisyonId(Integer pozisyonId) { this.pozisyonId = pozisyonId; }
}
