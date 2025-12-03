package com.filmdegerlendirme.repository;

import com.filmdegerlendirme.model.PersonelPozisyon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonelPozisyonRepository extends JpaRepository<PersonelPozisyon, Integer> {

    @Query("SELECT p.pozisyonId FROM PersonelPozisyon p WHERE p.personelId = :personelId")
    List<Integer> findPozisyonIdsByPersonelId(String personelId);
}
