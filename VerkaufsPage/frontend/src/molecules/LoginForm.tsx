import React, { useState } from 'react';
import axios from 'axios';
import { jwtDecode } from 'jwt-decode';
import Button from '../atoms/Button';
import Input from '../atoms/Input';
import ErrorMessage from '../atoms/ErrorMessage';
import '../styles/molecules.css';

interface DecodedToken {
    sub: string;
    role: string;
    userId: number;
}

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

            const decoded = jwtDecode<DecodedToken>(response.data.token);

            localStorage.setItem('token', response.data.token);
            localStorage.setItem('username', decoded.sub);
            localStorage.setItem('role', decoded.role);
            localStorage.setItem('userId', String(decoded.userId));

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
                <Input type="text" placeholder="Username oder Email" value={username} onChange={(e) => setUsername(e.target.value)} />
                <Input type="password" placeholder="Passwort" value={password} onChange={(e) => setPassword(e.target.value)} />
                <Button label="Einloggen" onClick={handleLogin} />
                <p className="form-footer">Noch kein Konto? <a href="/register">Registrieren</a></p>
            </div>
        </div>
    );
};

export default LoginForm;