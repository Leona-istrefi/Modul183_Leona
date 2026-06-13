import React, { useState } from 'react';
import axios from 'axios';
import Button from '../atoms/Button';
import Input from '../atoms/Input';
import ErrorMessage from '../atoms/ErrorMessage';
import '../styles/molecules.css';

const RegisterForm = () => {
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    const handleRegister = async () => {
        try {
            const params = new URLSearchParams();
            params.append('username', username);
            params.append('email', email);
            params.append('password', password);

            await axios.post('http://localhost:8080/register', params, {
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
            });
            setSuccess('Registrierung erfolgreich! Du kannst dich jetzt einloggen.');
            setError('');
        } catch (e: any) {
            if (e.response && e.response.status === 200) {
                setSuccess('Registrierung erfolgreich!');
            } else {
                setError('Registrierung fehlgeschlagen: ' + (e.response?.data || e.message));
            }
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