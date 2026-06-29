import React from 'react';
import '../styles/atoms.css';

interface ButtonProps {
    label: string;
    onClick: () => void;
    variant?: 'primary' | 'danger';
}

const Button: React.FC<ButtonProps> = ({ label, onClick, variant = 'primary' }) => {
    return (
        <button className={`btn ${variant === 'danger' ? 'btn-danger' : ''}`} onClick={onClick}>
            {label}
        </button>
    );
};

export default Button;