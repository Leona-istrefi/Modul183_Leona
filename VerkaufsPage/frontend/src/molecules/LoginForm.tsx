import React, { useState } from 'react';
import axios from 'axios';
import Button from '../atoms/Button';
import Input from '../atoms/Input';
import ErrorMessage from '../atoms/ErrorMessage';
import '../styles/molecules.css';

const LoginForm = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    const handleLogin = async () => {
        try {
            const params = new URLSearchParams();
            params.append('username', username);
            params.append('password', password);

            const response = await axios.post('http://localhost:8080/login', params, {
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
            });
            localStorage.setItem('token', response.data.token);
            localStorage.setItem('username', username);
            window.location.href = '/listings';
        } catch (e: any) {
            setError('Falscher Username oder Passwort: ' + (e.response?.data || e.message));
        }
    };

    return (
        <div className="form-container">
            <div className="form-card">
                <h2>Login</h2>
                <ErrorMessage message={error} />
                <Input type="text" placeholder="Username" value={username} onChange={(e) => setUsername(e.target.value)} />
                <Input type="password" placeholder="Passwort" value={password} onChange={(e) => setPassword(e.target.value)} />
                <Button label="Einloggen" onClick={handleLogin} />
                <p className="form-footer">Noch kein Konto? <a href="/register">Registrieren</a></p>
            </div>
        </div>
    );
};

export default LoginForm;