import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Button from '../atoms/Button';
import Input from '../atoms/Input';
import '../styles/molecules.css';

interface Listing {
    id: number;
    userId: number;
    name: string;
    zustand: string;
    groesse: string;
    preis: number;
    beschreibung: string;
    isPublic: boolean;
}

interface ListingDetailProps {
    listing: Listing;
    imageUrl: string | null;
    onClose: () => void;
    onDeleted: (id: number) => void;
    onUpdated: (listing: Listing) => void;
}

const ListingDetail: React.FC<ListingDetailProps> = ({ listing, imageUrl, onClose, onDeleted, onUpdated }) => {
    const role = localStorage.getItem('role');
    const currentUserId = Number(localStorage.getItem('userId'));
    const token = localStorage.getItem('token');
    const isOwnListing = listing.userId === currentUserId;
    const canEdit = role === 'admin' || isOwnListing;

    const [isEditing, setIsEditing] = useState(false);
    const [name, setName] = useState(listing.name);
    const [zustand, setZustand] = useState(listing.zustand);
    const [groesse, setGroesse] = useState(listing.groesse);
    const [preis, setPreis] = useState(listing.preis.toString());
    const [beschreibung, setBeschreibung] = useState(listing.beschreibung);
    const [isPublic, setIsPublic] = useState(listing.isPublic);
    const [inCart, setInCart] = useState(false);
    const [error, setError] = useState('');

    useEffect(() => {
        if (token && !isOwnListing) {
            axios.get('http://localhost:8080/cart', {
                headers: { Authorization: `Bearer ${token}` }
            }).then(res => {
                setInCart(res.data.some((l: any) => l.id === listing.id));
            }).catch(() => {});
        }
    }, []);

    const handleDelete = async () => {
        try {
            await axios.delete(`http://localhost:8080/listings/${listing.id}`, {
                headers: { Authorization: `Bearer ${token}` }
            });
            onDeleted(listing.id);
            onClose();
        } catch (e) {
            setError('Löschen fehlgeschlagen');
        }
    };

    const handleUpdate = async () => {
        try {
            const params = new URLSearchParams();
            params.append('name', name);
            params.append('zustand', zustand);
            params.append('groesse', groesse);
            params.append('preis', preis);
            params.append('beschreibung', beschreibung);
            params.append('isPublic', String(isPublic));

            await axios.put(`http://localhost:8080/listings/${listing.id}`, params, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            });

            onUpdated({ ...listing, name, zustand, groesse, preis: parseFloat(preis), beschreibung, isPublic });
            setIsEditing(false);
        } catch (e) {
            setError('Aktualisieren fehlgeschlagen');
        }
    };

    const handleAddToCart = async () => {
        try {
            await axios.post(`http://localhost:8080/cart/${listing.id}`, null, {
                headers: { Authorization: `Bearer ${token}` }
            });
            setInCart(true);
        } catch (e) {
            setError('Fehler beim Hinzufügen zum Warenkorb');
        }
    };

    return (
        <div className="modal-overlay" onClick={onClose}>
            <div className="modal-content" onClick={(e) => e.stopPropagation()}>
                <button className="modal-close" onClick={onClose}>×</button>

                {imageUrl ? (
                    <img src={imageUrl} alt={listing.name} className="modal-image" />
                ) : (
                    <div className="listing-card-placeholder modal-image">Kein Bild</div>
                )}

                {error && <p className="error-message">{error}</p>}

                {isEditing ? (
                    <div className="modal-form">
                        <Input type="text" placeholder="Name" value={name} onChange={(e) => setName(e.target.value)} />
                        <Input type="text" placeholder="Zustand" value={zustand} onChange={(e) => setZustand(e.target.value)} />
                        <Input type="text" placeholder="Grösse" value={groesse} onChange={(e) => setGroesse(e.target.value)} />
                        <Input type="number" placeholder="Preis" value={preis} onChange={(e) => setPreis(e.target.value)} />
                        <Input type="text" placeholder="Beschreibung" value={beschreibung} onChange={(e) => setBeschreibung(e.target.value)} />
                        <label className="toggle-label">
                            <input type="checkbox" checked={isPublic} onChange={(e) => setIsPublic(e.target.checked)} />
                            Öffentlich sichtbar
                        </label>
                        <div className="modal-actions">
                            <Button label="Speichern" onClick={handleUpdate} />
                            <Button label="Abbrechen" onClick={() => setIsEditing(false)} />
                        </div>
                    </div>
                ) : (
                    <>
                        <h2>{listing.name}</h2>
                        <p className="modal-price">CHF {listing.preis.toFixed(2)}</p>
                        <p><strong>Zustand:</strong> {listing.zustand}</p>
                        <p><strong>Grösse:</strong> {listing.groesse}</p>
                        <p><strong>Beschreibung:</strong> {listing.beschreibung}</p>
                        <p><strong>Sichtbarkeit:</strong> {listing.isPublic ? 'Öffentlich' : 'Privat'}</p>

                        {token && !isOwnListing && (
                            <div style={{ marginTop: '1rem' }}>
                                <Button
                                    label={inCart ? '✓ Im Warenkorb' : 'In den Warenkorb'}
                                    onClick={handleAddToCart}
                                />
                            </div>
                        )}

                        {canEdit && (
                            <div className="modal-actions">
                                <Button label="Bearbeiten" onClick={() => setIsEditing(true)} />
                                <Button label="Löschen" onClick={handleDelete} variant="danger" />
                            </div>
                        )}
                    </>
                )}
            </div>
        </div>
    );
};

export default ListingDetail;