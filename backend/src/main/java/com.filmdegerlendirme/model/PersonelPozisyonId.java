package com.filmdegerlendirme.model;

import java.io.Serializable;

public class PersonelPozisyonId implements Serializable {
    private String personelId;
    private Integer pozisyonId;

    // equals ve hashCode gerekli
    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        PersonelPozisyonId that = (PersonelPozisyonId) o;
        return personelId.equals(that.personelId) && pozisyonId.equals(that.pozisyonId);
    }

    @Override
    public int hashCode() {
        return personelId.hashCode() + pozisyonId.hashCode();
    }
}
