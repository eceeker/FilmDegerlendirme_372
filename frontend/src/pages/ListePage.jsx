import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './ListePage.css';

const ListePage = () => {
  const [userLists, setUserLists] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  const token = localStorage.getItem('token');
  const kullaniciAdi = localStorage.getItem('kullanici_adi');

  // Film detayını çek ve afiş URL'sini ekle
  const fetchPoster = async (filmId) => {
    try {
      const res = await axios.get(`http://localhost:8080/api/films/${filmId}`);
      return res.data.film?.afis_url || "https://via.placeholder.com/140x210?text=Poster";
    } catch (err) {
      console.error("Poster alınamadı:", err);
      return "https://via.placeholder.com/140x210?text=Poster";
    }
  };

  useEffect(() => {
    const fetchLists = async () => {
      try {
        const res = await axios.get(
          `http://localhost:8080/api/liste/user/${kullaniciAdi}`,
          { headers: { Authorization: `Bearer ${token}` } }
        );
        const lists = res.data || [];

        // Her filmin afiş URL'sini ayrı istekte çek
        for (const list of lists) {
          for (const film of list.filmler) {
            film.afis_url = await fetchPoster(film.film_id);
          }
        }

        setUserLists(lists);
      } catch (err) {
        console.error("Listeler yüklenemedi:", err);
      } finally {
        setLoading(false);
      }
    };

    if (kullaniciAdi && token) fetchLists();
  }, [kullaniciAdi, token]);

  if (loading) return <p>Yükleniyor...</p>;
  if (!userLists.length) return <p>Henüz listeniz yok.</p>;

  return (
    <div className="liste-container">
      <h1 className="title">Listelerim</h1>

      {userLists.map((list) => (
        <div key={list.liste_id} className="list-row">
          <h2 className="list-title">{list.liste_adi}</h2>

          <div className="film-row">
            {list.filmler.map((film) => (
              <div
                key={film.film_id}
                className="film-card"
                onClick={() => navigate(`/film/${film.film_id}`)}
              >
                <img
                  src={film.afis_url}
                  alt={film.film_id}
                  className="film-poster"
                />
                <p className="film-title">{film.baslik || film.film_id}</p>
              </div>
            ))}
          </div>
        </div>
      ))}
    </div>
  );
};

export default ListePage;
