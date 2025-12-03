import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate, Link } from 'react-router-dom';
import './AuthPages.css';

const API_BASE = 'http://localhost:8080/api/auth';

const RegisterPage = () => {
  const navigate = useNavigate();
  const [kullaniciAdi, setKullaniciAdi] = useState('');
  const [sifre, setSifre] = useState('');
  const [email, setEmail] = useState('');
  const [error, setError] = useState('');

  const handleRegister = async (e) => {
    e.preventDefault();
    setError('');

    try {
      const res = await axios.post(`${API_BASE}/register`, {
        kullanici_adi: kullaniciAdi,
        sifre,
        email
      });

      alert(res.data);
      navigate('/login'); // Kayıt sonrası login sayfasına yönlendir
    } catch (err) {
      console.error(err.response || err);
      setError(err.response?.data || 'Kayıt başarısız!');
    }
  };

  return (
    <div className="auth-container">
      <h2>Kayıt Ol</h2>
      {error && <p className="error">{error}</p>}
      <form onSubmit={handleRegister}>
        <input
          type="text"
          placeholder="Kullanıcı Adı"
          value={kullaniciAdi}
          onChange={e => setKullaniciAdi(e.target.value)}
          required
        />
        <input
          type="email"
          placeholder="Email"
          value={email}
          onChange={e => setEmail(e.target.value)}
          required
        />
        <input
          type="password"
          placeholder="Şifre"
          value={sifre}
          onChange={e => setSifre(e.target.value)}
          required
        />
        <button type="submit">Kayıt Ol</button>
      </form>
      <p>
        Hesabın var mı? <Link to="/login">Giriş Yap</Link>
      </p>
    </div>
  );
};

export default RegisterPage;
