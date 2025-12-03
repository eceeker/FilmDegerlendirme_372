import axios from 'axios';

const API_BASE = 'http://localhost:8080/api'; // Backend URL

export const getFeaturedFilms = async () => {
  const res = await axios.get(`${API_BASE}/home/featured`);
  return res.data;
};

export const getLatestFilms = async () => {
  const res = await axios.get(`${API_BASE}/home/latest`);
  return res.data;
};

export const getFilmsByGenre = async (genreId) => {
  const res = await axios.get(`${API_BASE}/home/genre/${genreId}`);
  return res.data;
};

export const getFilmById = async (filmId) => {
  const res = await axios.get(`${API_BASE}/films/${filmId}`); // burada films ekledik
  return res.data;
};

export const getAllGenres = async () => {
  const res = await axios.get(`${API_BASE}/home/genres`);
  return res.data;
};

// Kullanıcının listelerini çekme
export const getUserLists = async (kullaniciAdi, token) => {
  const res = await axios.get(`${API_BASE}/liste/user/${kullaniciAdi}`, {
    headers: { Authorization: `Bearer ${token}` }
  });
  return res.data; // Burada liste array’ini dönüyor olmalı
};

// Yeni liste oluştur
export const createList = async (listeAdi, kullaniciAdi, token) => {
  const today = new Date().toISOString().split('T')[0]; // yyyy-mm-dd
  const res = await axios.post(
    `${API_BASE}/liste/create`,
    {
      liste_adi: listeAdi,
      kullanici_adi: kullaniciAdi,
      olusturma_tarihi: today
    },
    { headers: { Authorization: `Bearer ${token}` } }
  );
  return res.data; // Liste objesi döner, ID içerir
};

// Filme listeye ekleme
export const addFilmToList = async (listeId, filmId, token) => {
  await axios.post(
    `${API_BASE}/liste/add?listeId=${listeId}&filmId=${filmId}`,
    null,
    { headers: { Authorization: `Bearer ${token}` } }
  );
};

// Film listeden çıkarma
export const removeFilmFromList = async (listeId, filmId, token) => {
  await axios.delete(`${API_BASE}/liste/remove`, {
    params: { listeId, filmId },
    headers: { Authorization: `Bearer ${token}` }
  });
};

export const getCommentsByFilm = async (filmId) => {
  const res = await axios.get(`${API_BASE}/yorum/${filmId}/comments`);
  return res.data;
};

// Yorum ekle
export const addCommentToFilm = async (filmId, yorum, token) => {
  const res = await axios.post(
    `${API_BASE}/yorum/${filmId}/comments`,
    yorum,
    { headers: { Authorization: `Bearer ${token}` } }
  );
  return res.data;
};

export const searchFilms = async (query) => {
  try {
    const res = await axios.get(`${API_BASE}/films/search`, {  // films ile güncelledik
      params: { query }
    });
    return res.data;
  } catch (err) {
    console.error('Film arama hatası:', err);
    return [];
  }
};

