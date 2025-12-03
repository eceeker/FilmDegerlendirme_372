package com.filmdegerlendirme.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Personel")
public class Personel {

    @Id
    @Column(name = "personel_id")
    private String personel_id;

    private String isim;
    private String soyisim;
    private String dogum_yeri;
    private LocalDate dogum_tarihi;

    // Getter & Setter
    public String getPersonel_id() { return personel_id; }
    public void setPersonel_id(String personel_id) { this.personel_id = personel_id; }

    public String getIsim() { return isim; }
    public void setIsim(String isim) { this.isim = isim; }

    public String getSoyisim() { return soyisim; }
    public void setSoyisim(String soyisim) { this.soyisim = soyisim; }

    public String getDogum_yeri() { return dogum_yeri; }
    public void setDogum_yeri(String dogum_yeri) { this.dogum_yeri = dogum_yeri; }

    public LocalDate getDogum_tarihi() { return dogum_tarihi; }
    public void setDogum_tarihi(LocalDate dogum_tarihi) { this.dogum_tarihi = dogum_tarihi; }
}
