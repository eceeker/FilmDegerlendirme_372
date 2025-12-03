import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './Header.css';

const Header = () => {
  const navigate = useNavigate();
  const kullaniciAdi = localStorage.getItem('kullanici_adi');

  const [searchQuery, setSearchQuery] = useState('');

  const handleLogout = () => {
    localStorage.removeItem('kullanici_adi');
    navigate('/login');
  };

  const handleSearch = (e) => {
    e.preventDefault();
    if (searchQuery.trim()) {
      navigate(`/search?query=${encodeURIComponent(searchQuery)}`);
      setSearchQuery('');
    }
  };

  return (
    <header className="site-header">
      <div className="header-left">
        <Link to="/" className="site-logo">FilmDeğerlendirme</Link>
      </div>

      {/* Search bar */}
      <form className="header-search" onSubmit={handleSearch}>
        <input
          type="text"
          placeholder="Film ara..."
          value={searchQuery}
          onChange={(e) => setSearchQuery(e.target.value)}
        />
        <button type="submit">Ara</button>
      </form>

      <div className="header-right">
        {kullaniciAdi ? (
          <>
            <span className="welcome-text">Hoşgeldin, {kullaniciAdi}</span>
            <Link to="/listeler" className="btn listeler-btn">Listelerim</Link>
            <button className="btn logout-btn" onClick={handleLogout}>Çıkış</button>
          </>
        ) : (
          <>
            <Link to="/login" className="btn login-btn">Giriş</Link>
            <Link to="/register" className="btn register-btn">Kayıt</Link>
          </>
        )}
      </div>
    </header>
  );
};

export default Header;
