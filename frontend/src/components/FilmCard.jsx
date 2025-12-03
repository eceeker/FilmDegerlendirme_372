import React from 'react';
import { useNavigate } from 'react-router-dom';

const FilmCard = ({ film }) => {
  const navigate = useNavigate();

  return (
    <div
      className="w-40 m-2 cursor-pointer hover:scale-105 transform transition duration-200"
      onClick={() => navigate(`/film/${film.film_id}`)}
    >
      <img
        src={film.afis_url}
        alt={film.baslik}
        className="w-full h-60 object-cover rounded"
      />
      <h3 className="mt-2 text-sm font-semibold">{film.baslik}</h3>
      <p className="text-xs text-gray-400">{film.yayin_yili}</p>
    </div>
  );
};

export default FilmCard;
