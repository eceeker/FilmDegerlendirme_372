import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate, Link } from 'react-router-dom';
import './AuthPages.css';

const API_BASE = 'http://localhost:8080/api/auth';

const LoginPage = () => {
  const navigate = useNavigate();
  const [kullaniciAdi, setKullaniciAdi] = useState('');
  const [sifre, setSifre] = useState('');
  const [error, setError] = useState('');

  const handleLogin = async (e) => {
  e.preventDefault();
  setError('');

  try {
    const res = await axios.post(`${API_BASE}/login`, {
      kullanici_adi: kullaniciAdi,
      sifre
    });

    // Başarılı login
    localStorage.setItem('token', res.data.token); // res ile düzeltildi
    localStorage.setItem('kullanici_adi', kullaniciAdi);
    alert(res.data);
    navigate('/'); // Anasayfaya yönlendir
  } catch (err) {
    console.error(err.response || err);
    setError(err.response?.data || 'Giriş başarısız!');
  }
};


  return (
    <div className="auth-container">
      <h2>Giriş Yap</h2>
      {error && <p className="error">{error}</p>}
      <form onSubmit={handleLogin}>
        <input
          type="text"
          placeholder="Kullanıcı Adı"
          value={kullaniciAdi}
          onChange={e => setKullaniciAdi(e.target.value)}
          required
        />
        <input
          type="password"
          placeholder="Şifre"
          value={sifre}
          onChange={e => setSifre(e.target.value)}
          required
        />
        <button type="submit">Giriş Yap</button>
      </form>
      <p>
        Hesabın yok mu? <Link to="/register">Kayıt Ol</Link>
      </p>
    </div>
  );
};

export default LoginPage;
