package com.filmdegerlendirme.repository;

import com.filmdegerlendirme.model.Kullanici;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class KullaniciRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // RowMapper, ResultSet'teki satırları Kullanici nesnesine dönüştürmeye yarar
    private RowMapper<Kullanici> kullaniciRowMapper = (rs, rowNum) -> {
        Kullanici kullanici = new Kullanici();
        kullanici.setKullanici_adi(rs.getString("kullanici_adi"));
        kullanici.setSifre(rs.getString("sifre"));
        kullanici.setEmail(rs.getString("email"));
        return kullanici;
    };

    // Kullanıcı Kaydetme (Create)
    public int save(Kullanici kullanici) {
        String sql = "INSERT INTO Kullanici (kullanici_adi, sifre, email) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, 
            kullanici.getKullanici_adi(), 
            kullanici.getSifre(), 
            kullanici.getEmail());
    }

    // Kullanıcı Adına Göre Bulma (Read - Tekil)
    public Optional<Kullanici> findByKullaniciAdi(String kullaniciAdi) {
        try {
            String sql = "SELECT * FROM Kullanici WHERE kullanici_adi = ?";
            Kullanici kullanici = jdbcTemplate.queryForObject(sql, kullaniciRowMapper, kullaniciAdi);
            return Optional.ofNullable(kullanici);
        } catch (Exception e) {
            // Kullanıcı bulunamazsa boş Optional döner
            return Optional.empty(); 
        }
    }

    // Tüm Kullanıcıları Listeleme (Read - Çoklu)
    public List<Kullanici> findAll() {
        String sql = "SELECT * FROM Kullanici";
        return jdbcTemplate.query(sql, kullaniciRowMapper);
    }
    
    // Kullanıcı Güncelleme (Update)
    public int update(Kullanici kullanici) {
        String sql = "UPDATE Kullanici SET sifre = ?, email = ? WHERE kullanici_adi = ?";
        return jdbcTemplate.update(sql, 
            kullanici.getSifre(), 
            kullanici.getEmail(), 
            kullanici.getKullanici_adi());
    }

    // Kullanıcı Silme (Delete)
    public int deleteByKullaniciAdi(String kullaniciAdi) {
        String sql = "DELETE FROM Kullanici WHERE kullanici_adi = ?";
        return jdbcTemplate.update(sql, kullaniciAdi);
    }
}