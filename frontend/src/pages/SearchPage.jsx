import React, { useEffect, useState } from 'react';
import { useLocation, Link } from 'react-router-dom';
import { searchFilms } from '../api';
import './SearchPage.css';

const SearchPage = () => {
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const query = queryParams.get('query');

  const [results, setResults] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (!query) return;

    const fetchResults = async () => {
      setLoading(true);
      const data = await searchFilms(query);
      const formattedData = data.map(film => ({
        film_id: film.film_id || film.FILM_ID,
        baslik: film.baslik || film.BASLIK,
        yayin_yili: film.yayin_yili || film.YAYIN_YILI,
        afis_url: film.afis_url || film.AFIS_URL,
      }));
      setResults(formattedData);
      setLoading(false);
    };

    fetchResults();
  }, [query]);

  if (loading) return <p>Yükleniyor...</p>;
  if (!results.length) return <p>Hiç film bulunamadı.</p>;

  return (
    <div className="search-page">
      <h2>Arama Sonuçları: "{query}"</h2>
      <div className="film-grid">
        {results.map(film => (
          <Link key={film.film_id} to={`/film/${film.film_id}`} className="film-card">
            <div className="poster-container">
              <img
                src={film.afis_url || '/default-poster.jpg'}
                alt={film.baslik}
                className="film-poster"
              />
            </div>
            <div className="film-info">
              <h3>{film.baslik}</h3>
              <p>{film.yayin_yili}</p>
            </div>
          </Link>
        ))}
      </div>
    </div>
  );
};

export default SearchPage;
