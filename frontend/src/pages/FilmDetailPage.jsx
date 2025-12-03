import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import {
  getFilmById,
  getUserLists,
  createList,
  addFilmToList,
  removeFilmFromList
} from '../api';
import './FilmDetailPage.css';
import axios from 'axios';

const FilmDetailPage = () => {
  const { id } = useParams(); // Film ID
  const [film, setFilm] = useState(null);
  const [turlar, setTurlar] = useState([]);
  const [oduller, setOduller] = useState([]);
  const [yorumlar, setYorumlar] = useState([]);
  const [loading, setLoading] = useState(true);
  const [cast, setCast] = useState([]);


  const [commentText, setCommentText] = useState('');
  const [rating, setRating] = useState(5);

  const [userLists, setUserLists] = useState([]);
  const [selectedList, setSelectedList] = useState('');
  const [newListName, setNewListName] = useState('');

  const token = localStorage.getItem('token');
  const kullaniciAdi = localStorage.getItem('kullanici_adi');

  useEffect(() => {
    const fetchFilmData = async () => {
      try {
        const res = await getFilmById(id);
        setFilm(res.film || {});
        setTurlar(res.turlar || []);
        setOduller(res.oduller || []);
        setYorumlar(res.yorumlar || []);
        setCast(res.cast || []);
      } catch (err) {
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    const fetchLists = async () => {
      if (!token) return;
      try {
        const lists = await getUserLists(kullaniciAdi, token);
        console.log('Fetched lists:', lists);
        setUserLists(lists || []);
      } catch (err) {
        console.error(err);
      }
    };

    fetchFilmData();
    fetchLists();
  }, [id, token, kullaniciAdi]);

  // Listeye ekle
  const handleAddToList = async () => {
    if (!selectedList) return alert("Lütfen önce bir liste seçin!");
    const listeIdInt = parseInt(selectedList);
    if (isNaN(listeIdInt)) return alert("Geçersiz liste seçimi!");
    try {
      await addFilmToList(listeIdInt, id, token);
      const updatedLists = await getUserLists(kullaniciAdi, token); // listeleri tekrar çek
setUserLists(updatedLists);
      
      alert("Film listeye eklendi!");
    } catch (err) {
      console.error(err);
      alert("Film listeye eklenemedi!");
    }
  };

  // Listeden çıkar
  const handleRemoveFromList = async () => {
    if (!selectedList) return alert("Lütfen önce bir liste seçin!");
    const listeIdInt = parseInt(selectedList);
    if (isNaN(listeIdInt)) return alert("Geçersiz liste seçimi!");
    try {
      await removeFilmFromList(listeIdInt, id, token);
      alert("Film listeden çıkarıldı!");
    } catch (err) {
      console.error(err);
      alert("Listeden çıkarılamadı!");
    }
  };

  // Yeni liste oluştur ve filme ekle
  const handleCreateNewList = async () => {
    if (!newListName.trim()) return alert("Lütfen liste adı girin!");
    try {
      const yeniListe = await createList(newListName, kullaniciAdi, token);
      if (!yeniListe?.liste_id) return alert("Liste oluşturulamadı!");

      // Kullanıcının listelerini tekrar çek
      const lists = await getUserLists(kullaniciAdi, token);
      setUserLists(lists);
      setSelectedList(yeniListe.liste_id.toString());
      setNewListName("");

      // Yeni listeye filmi ekle
      await addFilmToList(yeniListe.liste_id, id, token);

      alert("Yeni liste oluşturuldu ve film eklendi!");
    } catch (err) {
      console.error(err);
      alert("Liste oluşturulamadı!");
    }
  };

  // Yorum ekleme
  const handleAddComment = async () => {
    if (!commentText.trim()) return;
    try {
      const res = await axios.post(
        `http://localhost:8080/api/yorum/${id}/comments`,
        { yorum_metni: commentText, derece: rating },
        { headers: { Authorization: `Bearer ${token}` } }
      );
      setYorumlar([...yorumlar, res.data]);
      setCommentText('');
      setRating(5);
    } catch (err) {
      console.error(err);
      alert('Yorum gönderilemedi!');
    }
  };

  if (loading) return <p>Yükleniyor...</p>;
  if (!film) return <p>Film bulunamadı</p>;

  return (
    <div className="film-detail-container">
      <h1>{film.baslik} ({film.yayin_yili})</h1>

      {film.afis_url && (
        <div className="film-poster-container">
          <img src={film.afis_url} alt={film.baslik} className="film-poster" />
        </div>
      )}

      <p><strong>Süre:</strong> {film.sure || 'Bilgi yok'} dk</p>
      <p><strong>Ortalama Puan:</strong> {film.ortalama_puan || 'Bilgi yok'}</p>
      <p><strong>Özet:</strong> {film.ozet || 'Bilgi yok'}</p>
      <p><strong>Türler:</strong> {turlar.length ? turlar.map(t => t.tur_adi).join(', ') : 'Bilgi yok'}</p>
      <p><strong>Oyuncular:</strong> {cast.length ? cast.join(', ') : 'Bilgi yok'}</p>
      <p><strong>Ödüller:</strong> {oduller.length ? oduller.map(o => o.odul_adi).join(', ') : 'Bilgi yok'}</p>

      {/* Liste yönetimi */}
      <section className="film-list-section">
        <h3>Listeye Ekle / Çıkar</h3>
        <select value={selectedList} onChange={e => setSelectedList(e.target.value)}>
          <option value="">Liste seç</option>
          {userLists.map(list => (
            <option key={list.liste_id} value={list.liste_id.toString()}>
              {list.liste_adi}
            </option>
          ))}
        </select>
        <button onClick={handleAddToList}>Ekle</button>
        <button onClick={handleRemoveFromList}>Çıkar</button>

        <div className="new-list-container">
          <input
            placeholder="Yeni Liste Adı"
            value={newListName}
            onChange={e => setNewListName(e.target.value)}
          />
          <button onClick={handleCreateNewList}>Yeni Liste Oluştur ve Filme Ekle</button>
        </div>
      </section>

      {/* Yorum ekleme */}
      <section className="film-comment-section">
        <h3>Yorum Ekle</h3>
        <textarea
          placeholder="Yorumunuzu yazın"
          value={commentText}
          onChange={e => setCommentText(e.target.value)}
          rows={3}
        />
        <div>
          <label>Puan: </label>
          <select value={rating} onChange={e => setRating(parseInt(e.target.value))}>
            {[...Array(10)].map((_, i) => (
              <option key={i+1} value={i+1}>{i+1}</option>
            ))}
          </select>
          <button onClick={handleAddComment}>Yorum Gönder</button>
        </div>
      </section>

      {/* Yorumlar */}
      <h2>Yorumlar</h2>
      {yorumlar.length ? (
        yorumlar.map(y => (
          <div key={y.yorum_id} className="film-comment-card">
            <p><strong>{y.kullanici_adi} ({y.email})</strong> - {y.derece}/10</p>
            <p>{y.yorum_metni}</p>
            <p><small>{y.yorum_tarihi}</small></p>
          </div>
        ))
      ) : (
        <p>Henüz yorum yok</p>
      )}
    </div>
  );
};

export default FilmDetailPage;
