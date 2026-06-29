import React, { useEffect, useState } from 'react';
import axios from 'axios';
import ListingCard from '../molecules/ListingCard';
import ListingDetail from '../molecules/ListingDetail';
import CreateListingModal from '../molecules/CreateListingModal';
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

const Listings = () => {
    const [listings, setListings] = useState<Listing[]>([]);
    const [images, setImages] = useState<{ [key: number]: string | null }>({});
    const [favoriteIds, setFavoriteIds] = useState<number[]>([]);
    const [selected, setSelected] = useState<Listing | null>(null);
    const [showCreate, setShowCreate] = useState(false);
    const token = localStorage.getItem('token');
    const currentUserId = Number(localStorage.getItem('userId'));

    useEffect(() => {
        loadListings();
        if (token) loadFavorites();
    }, []);

    const loadListings = async () => {
        try {
            const response = await axios.get('http://localhost:8080/listings', {
                headers: token ? { Authorization: `Bearer ${token}` } : {}
            });
            const data: Listing[] = response.data;
            setListings(data);

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
            console.error('Fehler beim Laden der Inserate', e);
        }
    };

    const loadFavorites = async () => {
        try {
            const response = await axios.get('http://localhost:8080/favorites', {
                headers: { Authorization: `Bearer ${token}` }
            });
            setFavoriteIds(response.data.map((l: Listing) => l.id));
        } catch (e) {
            console.error('Fehler beim Laden der Favoriten', e);
        }
    };

    const handleToggleFavorite = async (listingId: number) => {
        try {
            await axios.post(`http://localhost:8080/favorites/${listingId}`, null, {
                headers: { Authorization: `Bearer ${token}` }
            });
            setFavoriteIds((prev) =>
                prev.includes(listingId) ? prev.filter((id) => id !== listingId) : [...prev, listingId]
            );
        } catch (e) {
            console.error('Fehler beim Favorisieren', e);
        }
    };

    const handleDeleted = (id: number) => {
        setListings(listings.filter((l) => l.id !== id));
    };

    const handleUpdated = (updated: Listing) => {
        setListings(listings.map((l) => (l.id === updated.id ? updated : l)));
        setSelected(updated);
    };

    return (
        <div className="listings-page">
            <h2 className="listings-title">Alle Inserate</h2>
            <div className="listings-grid">
                {listings.map((listing) => (
                    <ListingCard
                        key={listing.id}
                        name={listing.name}
                        preis={listing.preis}
                        imageUrl={images[listing.id] || null}
                        isPublic={listing.isPublic}
                        isFavorite={favoriteIds.includes(listing.id)}
                        showFavoriteIcon={!!token && listing.isPublic && listing.userId !== currentUserId}
                        onClick={() => setSelected(listing)}
                        onToggleFavorite={() => handleToggleFavorite(listing.id)}
                    />
                ))}
            </div>

            {token && (
                <button className="fab-button" onClick={() => setShowCreate(true)} title="Inserat erstellen">
                    +
                </button>
            )}

            {selected && (
                <ListingDetail
                    listing={selected}
                    imageUrl={images[selected.id] || null}
                    onClose={() => setSelected(null)}
                    onDeleted={handleDeleted}
                    onUpdated={handleUpdated}
                />
            )}

            {showCreate && (
                <CreateListingModal
                    onClose={() => setShowCreate(false)}
                    onCreated={loadListings}
                />
            )}
        </div>
    );
};

export default Listings;