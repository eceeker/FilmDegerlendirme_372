package com.filmdegerlendirme.repository;

import com.filmdegerlendirme.model.Yorum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface YorumRepository extends JpaRepository<Yorum, Integer> {
    List<Yorum> findByFilmId(String film_id); // film_id kullan
}

