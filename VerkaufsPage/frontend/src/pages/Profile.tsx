import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Input from '../atoms/Input';
import Button from '../atoms/Button';
import ErrorMessage from '../atoms/ErrorMessage';
import '../styles/molecules.css';

const Profile = () => {
    const [username, setUsername] = useState('');
    const [newUsername, setNewUsername] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [profilePicture, setProfilePicture] = useState<string | null>(null);
    const [image, setImage] = useState<File | null>(null);
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');
    const token = localStorage.getItem('token');

    useEffect(() => {
        loadProfile();
    }, []);

    const loadProfile = async () => {
        try {
            const response = await axios.get('http://localhost:8080/profile', {
                headers: { Authorization: `Bearer ${token}` }
            });
            setUsername(response.data.username);
            setProfilePicture(response.data.profilePicture
                ? `http://localhost:8080/${response.data.profilePicture}`
                : null);
        } catch (e) {
            setError('Fehler beim Laden des Profils');
        }
    };

    const handleUpdate = async () => {
        try {
            const params = new URLSearchParams();
            if (newUsername) params.append('username', newUsername);
            if (newPassword) params.append('password', newPassword);

            await axios.put('http://localhost:8080/profile', params, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            });

            if (newUsername) {
                localStorage.setItem('username', newUsername);
                setUsername(newUsername);
            }

            setSuccess('Profil aktualisiert!');
            setError('');
            setNewUsername('');
            setNewPassword('');
        } catch (e) {
            setError('Fehler beim Aktualisieren');
        }
    };

    const handleProfilePicture = async () => {
        if (!image) return;
        try {
            await axios.post('http://localhost:8080/profile', image, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'X-Filename': image.name,
                    'Content-Type': 'application/octet-stream'
                }
            });
            setSuccess('Profilbild hochgeladen!');
            loadProfile();
        } catch (e) {
            setError('Fehler beim Hochladen des Profilbilds');
        }
    };

    return (
        <div className="form-container">
            <div className="form-card">
                <h2>Mein Profil</h2>

                <div className="profile-picture-section">
                    {profilePicture ? (
                        <img src={profilePicture} alt="Profilbild" className="profile-picture" />
                    ) : (
                        <div className="profile-picture-placeholder">
                            {username.charAt(0).toUpperCase()}
                        </div>
                    )}
                    <input
                        className="input"
                        type="file"
                        accept="image/*"
                        onChange={(e) => setImage(e.target.files ? e.target.files[0] : null)}
                    />
                    <Button label="Bild hochladen" onClick={handleProfilePicture} />
                </div>

                <ErrorMessage message={error} />
                <ErrorMessage message={success} type="success" />

                <p style={{ color: 'var(--color-text-secondary)', fontSize: '0.9rem' }}>
                    Aktueller Username: <strong>{username}</strong>
                </p>

                <Input
                    type="text"
                    placeholder="Neuer Username"
                    value={newUsername}
                    onChange={(e) => setNewUsername(e.target.value)}
                />
                <Input
                    type="password"
                    placeholder="Neues Passwort"
                    value={newPassword}
                    onChange={(e) => setNewPassword(e.target.value)}
                />
                <Button label="Speichern" onClick={handleUpdate} />
            </div>
        </div>
    );
};

export default Profile;