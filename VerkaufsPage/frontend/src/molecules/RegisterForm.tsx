import React, { useState } from 'react';
import axios from 'axios';
import Button from '../atoms/Button';
import Input from '../atoms/Input';
import ErrorMessage from '../atoms/ErrorMessage';
import '../styles/molecules.css';
import { jwtDecode } from 'jwt-decode';

interface DecodedToken {
    sub: string;
    role: string;
    userId: number;
}

const RegisterForm = () => {
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    const handleRegister = async () => {
        if (!username || !email || !password) {
            setError('Alle Felder sind Pflichtfelder');
            return;
        }

        if (!email.includes('@')) {
            setError('Ungültige Email-Adresse');
            return;
        }

        if (password.length < 4) {
            setError('Passwort muss mindestens 4 Zeichen haben');
            return;
        }

        try {
            const params = new URLSearchParams();
            params.append('username', username);
            params.append('email', email);
            params.append('password', password);

            await axios.post('http://localhost:8080/register', params, {
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
            });

            const loginParams = new URLSearchParams();
            loginParams.append('username', username);
            loginParams.append('password', password);

            const loginResponse = await axios.post('http://localhost:8080/login', loginParams, {
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
            });

            const decoded = jwtDecode<DecodedToken>(loginResponse.data.token);
            localStorage.setItem('token', loginResponse.data.token);
            localStorage.setItem('username', decoded.sub);
            localStorage.setItem('role', decoded.role);
            localStorage.setItem('userId', String(decoded.userId));

            window.location.href = '/listings';
        } catch (e: any) {
            setError('Registrierung fehlgeschlagen: ' + (e.response?.data || e.message));
        }
    };

    return (
        <div className="form-container">
            <div className="form-card">
                <h2>Registrieren</h2>
                <ErrorMessage message={error} />
                <ErrorMessage message={success} type="success" />
                <Input type="text" placeholder="Username" value={username} onChange={(e) => setUsername(e.target.value)} />
                <Input type="email" placeholder="Email" value={email} onChange={(e) => setEmail(e.target.value)} />
                <Input type="password" placeholder="Passwort" value={password} onChange={(e) => setPassword(e.target.value)} />
                <Button label="Registrieren" onClick={handleRegister} />
                <p className="form-footer">Bereits ein Konto? <a href="/login">Einloggen</a></p>
            </div>
        </div>
    );
};

export default RegisterForm;