package com.filmdegerlendirme.repository;

import com.filmdegerlendirme.model.Personel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonelRepository extends JpaRepository<Personel, String> {

    @Query("SELECT p FROM Personel p JOIN FilmOyuncu fo ON p.personel_id = fo.personelId WHERE fo.filmId = :filmId")
    List<Personel> findByFilmId(String filmId);
}
