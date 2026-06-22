CREATE TABLE IF NOT EXISTS users (
                                     id SERIAL PRIMARY KEY,
                                     username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    salt VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    role VARCHAR(20) DEFAULT 'user'
    );

CREATE TABLE IF NOT EXISTS listings (
                                        id SERIAL PRIMARY KEY,
                                        user_id INT REFERENCES users(id),
    name VARCHAR(100) NOT NULL,
    zustand VARCHAR(50) NOT NULL,
    groesse VARCHAR(20) NOT NULL,
    preis DECIMAL(10,2) NOT NULL,
    beschreibung TEXT,
    is_public BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS images (
                                      id SERIAL PRIMARY KEY,
                                      listing_id INT REFERENCES listings(id),
    filepath VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS favorites (
                                         id SERIAL PRIMARY KEY,
                                         user_id INT REFERENCES users(id),
    listing_id INT REFERENCES listings(id),
    UNIQUE (user_id, listing_id)
    );