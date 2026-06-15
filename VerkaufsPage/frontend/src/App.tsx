import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate, useLocation } from 'react-router-dom';
import Login from './pages/Login';
import Register from './pages/Register';
import Navbar from './molecules/Navbar';
import Listings from "./pages/Listings";

const AppContent = () => {
    const location = useLocation();
    const hideNavbar = location.pathname === '/login' || location.pathname === '/register';

    return (
        <>
            {!hideNavbar && <Navbar />}
            <Routes>
                <Route path="/" element={<Navigate to="/login" />} />
                <Route path="/login" element={<Login />} />
                <Route path="/register" element={<Register />} />
                <Route path="/listings" element={<Listings />} />
            </Routes>
        </>
    );
};

function App() {
    return (
        <Router>
            <AppContent />
        </Router>
    );
}

export default App;