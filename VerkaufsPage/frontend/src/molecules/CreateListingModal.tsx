import React, { useState } from 'react';
import axios from 'axios';
import Button from '../atoms/Button';
import Input from '../atoms/Input';
import ErrorMessage from '../atoms/ErrorMessage';
import '../styles/molecules.css';

interface CreateListingModalProps {
    onClose: () => void;
    onCreated: () => void;
}

const CreateListingModal: React.FC<CreateListingModalProps> = ({ onClose, onCreated }) => {
    const [name, setName] = useState('');
    const [zustand, setZustand] = useState('');
    const [groesse, setGroesse] = useState('');
    const [preis, setPreis] = useState('');
    const [beschreibung, setBeschreibung] = useState('');
    const [isPublic, setIsPublic] = useState(true);
    const [image, setImage] = useState<File | null>(null);
    const [error, setError] = useState('');

    const handleSubmit = async () => {
        try {
            const token = localStorage.getItem('token');

            const params = new URLSearchParams();
            params.append('name', name);
            params.append('zustand', zustand);
            params.append('groesse', groesse);
            params.append('preis', preis);
            params.append('beschreibung', beschreibung);
            params.append('isPublic', String(isPublic));

            await axios.post('http://localhost:8080/listings', params, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            });

            if (image) {
                const allListings = await axios.get('http://localhost:8080/listings', {
                    headers: { Authorization: `Bearer ${token}` }
                });
                const myListings = allListings.data.filter((l: any) => l.name === name);
                const newListing = myListings[myListings.length - 1];

                if (newListing) {
                    await axios.post(`http://localhost:8080/image/${newListing.id}`, image, {
                        headers: {
                            Authorization: `Bearer ${token}`,
                            'X-Filename': image.name,
                            'Content-Type': 'application/octet-stream'
                        }
                    });
                }
            }

            onCreated();
            onClose();
        } catch (e: any) {
            setError('Fehler beim Erstellen: ' + (e.response?.data || e.message));
        }
    };

    return (
        <div className="modal-overlay" onClick={onClose}>
            <div className="modal-content" onClick={(e) => e.stopPropagation()}>
                <button className="modal-close" onClick={onClose}>×</button>
                <h2>Neues Inserat</h2>
                <ErrorMessage message={error} />
                <div className="modal-form">
                    <Input type="text" placeholder="Name" value={name} onChange={(e) => setName(e.target.value)} />
                    <Input type="text" placeholder="Zustand (z.B. Neu, Gut)" value={zustand} onChange={(e) => setZustand(e.target.value)} />
                    <Input type="text" placeholder="Grösse (z.B. M)" value={groesse} onChange={(e) => setGroesse(e.target.value)} />
                    <Input type="number" placeholder="Preis" value={preis} onChange={(e) => setPreis(e.target.value)} />
                    <Input type="text" placeholder="Beschreibung" value={beschreibung} onChange={(e) => setBeschreibung(e.target.value)} />

                    <input
                        className="input"
                        type="file"
                        accept="image/*"
                        onChange={(e) => setImage(e.target.files ? e.target.files[0] : null)}
                    />

                    <label className="toggle-label">
                        <input type="checkbox" checked={isPublic} onChange={(e) => setIsPublic(e.target.checked)} />
                        Öffentlich sichtbar
                    </label>

                    <Button label="Erstellen" onClick={handleSubmit} />
                </div>
            </div>
        </div>
    );
};

export default CreateListingModal;