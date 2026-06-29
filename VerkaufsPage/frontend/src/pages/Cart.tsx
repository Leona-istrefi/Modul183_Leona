import React, { useEffect, useState } from 'react';
import axios from 'axios';
import '../styles/molecules.css';

interface Listing {
    id: number;
    userId: number;
    name: string;
    preis: number;
    zustand: string;
    groesse: string;
    beschreibung: string;
    isPublic: boolean;
}

const Cart = () => {
    const [listings, setListings] = useState<Listing[]>([]);
    const [images, setImages] = useState<{ [key: number]: string | null }>({});
    const [total, setTotal] = useState(0);
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');
    const token = localStorage.getItem('token');

    useEffect(() => {
        loadCart();
    }, []);

    const loadCart = async () => {
        try {
            const response = await axios.get('http://localhost:8080/cart', {
                headers: { Authorization: `Bearer ${token}` }
            });
            const data: Listing[] = response.data;
            setListings(data);
            setTotal(data.reduce((sum, l) => sum + l.preis, 0));

            const imageMap: { [key: number]: string | null } = {};
            for (const listing of data) {
                try {
                    const imgRes = await axios.get(`http://localhost:8080/image/${listing.id}`);
                    imageMap[listing.id] = imgRes.data.filepath
                        ? `http://localhost:8080/${imgRes.data.filepath}`
                        : null;
                } catch {
                    imageMap[listing.id] = null;
                }
            }
            setImages(imageMap);
        } catch (e) {
            setError('Fehler beim Laden des Warenkorbs');
        }
    };

    const handleRemove = async (listingId: number) => {
        try {
            await axios.delete(`http://localhost:8080/cart/${listingId}`, {
                headers: { Authorization: `Bearer ${token}` }
            });
            const updated = listings.filter(l => l.id !== listingId);
            setListings(updated);
            setTotal(updated.reduce((sum, l) => sum + l.preis, 0));
        } catch (e) {
            setError('Fehler beim Entfernen');
        }
    };

    const handleBuy = async (listingId: number) => {
        try {
            await axios.post(`http://localhost:8080/cart/${listingId}/buy`, null, {
                headers: { Authorization: `Bearer ${token}` }
            });
            setSuccess('Erfolgreich gekauft!');
            const updated = listings.filter(l => l.id !== listingId);
            setListings(updated);
            setTotal(updated.reduce((sum, l) => sum + l.preis, 0));
        } catch (e) {
            setError('Fehler beim Kaufen');
        }
    };

    return (
        <div className="listings-page">
            <h2 className="listings-title">Warenkorb</h2>
            {error && <p className="error-message">{error}</p>}
            {success && <p className="success-message">{success}</p>}
            {listings.length === 0 ? (
                <p>Dein Warenkorb ist leer.</p>
            ) : (
                <>
                    <div className="cart-list">
                        {listings.map(listing => (
                            <div key={listing.id} className="cart-item">
                                {images[listing.id] && (
                                    <img src={images[listing.id]!} alt={listing.name} className="cart-item-image" />
                                )}
                                <div className="cart-item-info">
                                    <h3>{listing.name}</h3>
                                    <p>{listing.zustand} · {listing.groesse}</p>
                                    <p className="listing-card-price">CHF {listing.preis.toFixed(2)}</p>
                                </div>
                                <div className="cart-item-actions">
                                    <button className="btn" onClick={() => handleBuy(listing.id)}>Kaufen</button>
                                    <button className="btn btn-danger" onClick={() => handleRemove(listing.id)}>Entfernen</button>
                                </div>
                            </div>
                        ))}
                    </div>
                    <div className="cart-total">
                        <strong>Total: CHF {total.toFixed(2)}</strong>
                    </div>
                </>
            )}
        </div>
    );
};

export default Cart;