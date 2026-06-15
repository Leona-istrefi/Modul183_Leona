import React from 'react';
import '../styles/molecules.css';

interface ListingCardProps {
    name: string;
    preis: number;
    imageUrl: string | null;
    onClick: () => void;
}

const ListingCard: React.FC<ListingCardProps> = ({ name, preis, imageUrl, onClick }) => {
    return (
        <div className="listing-card" onClick={onClick}>
            <div className="listing-card-image">
                {imageUrl ? (
                    <img src={imageUrl} alt={name} />
                ) : (
                    <div className="listing-card-placeholder">Kein Bild</div>
                )}
            </div>
            <div className="listing-card-info">
                <h3>{name}</h3>
                <p className="listing-card-price">CHF {preis.toFixed(2)}</p>
            </div>
        </div>
    );
};

export default ListingCard;