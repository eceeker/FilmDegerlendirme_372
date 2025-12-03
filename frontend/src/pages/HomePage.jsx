import React, { useEffect, useState } from "react";
import { getFeaturedFilms, getLatestFilms, getAllGenres, getFilmsByGenre} from "../api";
import { useNavigate } from "react-router-dom";
import "./HomePage.css";

const HomePage = () => {
  const [featured, setFeatured] = useState([]);
  const [latest, setLatest] = useState([]);
  const [genres, setGenres] = useState([]); // { name, films[] }
  const navigate = useNavigate();

  useEffect(() => {
    const fetchData = async () => {
      try {
        const featuredFilms = await getFeaturedFilms();
        setFeatured(featuredFilms);

        const latestFilms = await getLatestFilms();
        setLatest(latestFilms);

        // Backend'den türler ve filmleri al
        const genreList = await getAllGenres(); // { "Action": [...], "Comedy": [...], ... }
        const genreWithFilms = await Promise.all(
  genreList.map(async (g) => {
    const films = await getFilmsByGenre(g.tur_id); // <<< burası g.tur_id olmalı
    return { name: g.tur_adi, films }; // name: tür adı
  })
);
setGenres(genreWithFilms);

      } catch (err) {
        console.error("Error fetching home page data:", err);
      }
    };

    fetchData();
  }, []);

  const goToDetail = (filmId) => {
    navigate(`/film/${filmId}`);
  };

  return (
    <div className="home-page">
      <h2>Top Rated Films</h2>
      <div className="film-row">
        {featured.map((film) => (
          <div key={film.film_id} className="film-card" onClick={() => goToDetail(film.film_id)}>
            <img src={film.afis_url} alt={film.baslik} className="film-poster" />
            <p className="film-title">{film.baslik}</p>
          </div>
        ))}
      </div>

      <h2>Latest Films</h2>
      <div className="film-row">
        {latest.map((film) => (
          <div key={film.film_id} className="film-card" onClick={() => goToDetail(film.film_id)}>
            <img src={film.afis_url} alt={film.baslik} className="film-poster" />
            <p className="film-title">{film.baslik}</p>
          </div>
        ))}
      </div>

      {genres.map((g) => (
        <div key={g.name}>
          <h2>{g.name}</h2>
          <div className="film-row">
            {g.films.map((film) => (
              <div key={film.film_id} className="film-card" onClick={() => goToDetail(film.film_id)}>
                <img src={film.afis_url} alt={film.baslik} className="film-poster" />
                <p className="film-title">{film.baslik}</p>
              </div>
            ))}
          </div>
        </div>
      ))}
    </div>
  );
};

export default HomePage;
