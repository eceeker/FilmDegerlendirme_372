package com.filmdegerlendirme.repository;

import com.filmdegerlendirme.model.Pozisyon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PozisyonRepository extends JpaRepository<Pozisyon, Integer> {}
