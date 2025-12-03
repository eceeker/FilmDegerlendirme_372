package com.filmdegerlendirme.model;

import java.math.BigDecimal;

public class Film {
    private String film_id;
    private String baslik;
    private Integer sure;
    private String ozet;
    private Integer yayin_yili;
    private BigDecimal ortalama_puan;
    private String afis_url;

    // Getters and Setters
    public String getFilm_id() {
        return film_id;
    }           

    public void setFilm_id(String film_id) {
        this.film_id = film_id;
    }
    public String getBaslik() {
        return baslik;
    }
    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }
    public Integer getSure() {
        return sure;
    }
    public void setSure(Integer sure) {
        this.sure = sure;
    }
    public String getOzet() {
        return ozet;
    }
    public void setOzet(String ozet) {
        this.ozet = ozet;
    }
    public Integer getYayin_yili() {
        return yayin_yili;
    }
    public void setYayin_yili(Integer yayin_yili) {
        this.yayin_yili = yayin_yili;
    }
    public BigDecimal getOrtalama_puan() {
        return ortalama_puan;
    }
    public void setOrtalama_puan(BigDecimal ortalama_puan) {
        this.ortalama_puan = ortalama_puan;
    }
    public String getAfis_url() {
        return afis_url;
    }

    public void setAfis_url(String afis_url) {
        this.afis_url = afis_url;
    }
}