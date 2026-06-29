import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Input from '../atoms/Input';
import Button from '../atoms/Button';
import ErrorMessage from '../atoms/ErrorMessage';
import '../styles/molecules.css';

const Profile = () => {
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [newUsername, setNewUsername] = useState('');
    const [newEmail, setNewEmail] = useState('');
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
            setEmail(response.data.email);
            setProfilePicture(response.data.profilePicture
                ? `http://localhost:8080/${response.data.profilePicture}`
                : null);
        } catch (e) {
            setError('Fehler beim Laden des Profils');
        }
    };

    const handleUpdate = async () => {
        if (!newUsername && !newEmail && !newPassword) {
            setError('Bitte mindestens ein Feld ausfüllen');
            return;
        }

        try {
            const params = new URLSearchParams();
            if (newUsername) params.append('username', newUsername);
            if (newEmail) params.append('email', newEmail);
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
                setNewUsername('');
            }
            if (newEmail) {
                setEmail(newEmail);
                setNewEmail('');
            }

            setSuccess('Profil aktualisiert!');
            setError('');
            setNewPassword('');
        } catch (e: any) {
            setError('Fehler: ' + (e.response?.data || e.message));
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
            setImage(null);
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
                    {image && <Button label="Bild hochladen" onClick={handleProfilePicture} />}
                </div>

                <ErrorMessage message={error} />
                <ErrorMessage message={success} type="success" />

                <div className="profile-info">
                    <p><strong>Username:</strong> {username}</p>
                    <p><strong>Email:</strong> {email}</p>
                </div>

                <h3 style={{ fontSize: '1rem', fontWeight: 500, marginTop: '0.5rem' }}>Ändern</h3>

                <Input
                    type="text"
                    placeholder="Neuer Username"
                    value={newUsername}
                    onChange={(e) => setNewUsername(e.target.value)}
                />
                <Input
                    type="email"
                    placeholder="Neue Email"
                    value={newEmail}
                    onChange={(e) => setNewEmail(e.target.value)}
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