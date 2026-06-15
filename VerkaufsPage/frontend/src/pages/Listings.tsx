import React, { useEffect, useState } from 'react';
import axios from 'axios';
import ListingCard from '../molecules/ListingCard';
import ListingDetail from '../molecules/ListingDetail';
import '../styles/molecules.css';

interface Listing {
    id: number;
    userId: number;
    name: string;
    zustand: string;
    groesse: string;
    preis: number;
    beschreibung: string;
}

const Listings = () => {
    const [listings, setListings] = useState<Listing[]>([]);
    const [images, setImages] = useState<{ [key: number]: string | null }>({});
    const [selected, setSelected] = useState<Listing | null>(null);

    useEffect(() => {
        loadListings();
    }, []);

    const loadListings = async () => {
        try {
            const response = await axios.get('http://localhost:8080/listings');
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
                        onClick={() => setSelected(listing)}
                    />
                ))}
            </div>

            {selected && (
                <ListingDetail
                    listing={selected}
                    imageUrl={images[selected.id] || null}
                    onClose={() => setSelected(null)}
                    onDeleted={handleDeleted}
                    onUpdated={handleUpdated}
                />
            )}
        </div>
    );
};

export default Listings;