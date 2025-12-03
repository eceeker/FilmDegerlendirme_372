import Header from './components/Header';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import HomePage from './pages/HomePage';
import FilmDetailPage from './pages/FilmDetailPage';
import ListePage from './pages/ListePage';
import SearchPage from './pages/SearchPage';

function App() {
  return (
    <Router>
      <Header />
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/film/:id" element={<FilmDetailPage />} />
        <Route path="/listeler" element={<ListePage />} />
        <Route path="/search" element={<SearchPage />} />

      </Routes>
    </Router>
  );
}

export default App;
