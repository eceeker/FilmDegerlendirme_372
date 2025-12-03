package com.filmdegerlendirme.service;

import com.filmdegerlendirme.model.Liste;
import com.filmdegerlendirme.model.ListeFilm;
import com.filmdegerlendirme.repository.ListeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListeService {

    @Autowired
    private ListeRepository listeRepository;

    public Liste createList(Liste liste) {
        return listeRepository.createList(liste);
    }

    public void addFilm(Integer listeId, String filmId) {
        listeRepository.addFilmToList(listeId, filmId);
    }

    public List<ListeFilm> getFilms(Integer listeId) {
        return listeRepository.getFilmsByListId(listeId);
    }

    public void removeFilm(Integer listeId, String filmId) {
        listeRepository.removeFilmFromList(listeId, filmId);
    }

  public List<Liste> getListsByUser(String kullaniciAdi) {
    List<Liste> lists = listeRepository.findByUser(kullaniciAdi);
    for (Liste liste : lists) {
        List<ListeFilm> filmler = listeRepository.getFilmsByListId(liste.getListe_id());
        liste.setFilmler(filmler);
    }
    return lists;
}

}
