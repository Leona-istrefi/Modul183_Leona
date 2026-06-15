import React from 'react';
import { FaHeart, FaRegHeart } from 'react-icons/fa';
import '../styles/molecules.css';

interface ListingCardProps {
    name: string;
    preis: number;
    imageUrl: string | null;
    isPublic: boolean;
    isFavorite: boolean;
    showFavoriteIcon: boolean;
    onClick: () => void;
    onToggleFavorite: () => void;
}

const ListingCard: React.FC<ListingCardProps> = ({ name, preis, imageUrl, isPublic, isFavorite, showFavoriteIcon, onClick, onToggleFavorite }) => {
    return (
        <div className="listing-card" onClick={onClick}>
            {!isPublic && <span className="listing-card-badge">Privat</span>}

            {showFavoriteIcon && (
                <button
                    className="listing-card-heart"
                    onClick={(e) => {
                        e.stopPropagation();
                        onToggleFavorite();
                    }}
                    title={isFavorite ? 'Favorit entfernen' : 'Zu Favoriten hinzufügen'}
                >
                    {isFavorite ? <FaHeart color="#e74c3c" /> : <FaRegHeart />}
                </button>
            )}

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