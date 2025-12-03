import React from 'react';
import FilmCard from './FilmCard';

const FilmRow = ({ title, films }) => {
  return (
    <div className="my-6">
      <h2 className="text-xl font-bold mb-2">{title}</h2>
      <div className="flex overflow-x-auto scrollbar-hide">
        {films.map((film) => (
          <FilmCard key={film.filmId} film={film} />
        ))}
      </div>
    </div>
  );
};

export default FilmRow;
