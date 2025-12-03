package com.filmdegerlendirme.repository;

import com.filmdegerlendirme.model.Kullanici;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class KullaniciRowMapper implements RowMapper<Kullanici> {
    @Override
    public Kullanici mapRow(ResultSet rs, int rowNum) throws SQLException {
        Kullanici u = new Kullanici();

        u.setKullanici_adi(rs.getString("kullanici_adi"));
        u.setSifre(rs.getString("sifre"));
        u.setEmail(rs.getString("email"));

        return u;
    }
}
