import React from 'react';
import '../styles/atoms.css';

interface ErrorMessageProps {
    message: string;
    type?: 'error' | 'success';
}

const ErrorMessage: React.FC<ErrorMessageProps> = ({ message, type = 'error' }) => {
    if (!message) return null;
    return (
        <p className={type === 'error' ? 'error-message' : 'success-message'}>
            {message}
        </p>
    );
};

export default ErrorMessage;