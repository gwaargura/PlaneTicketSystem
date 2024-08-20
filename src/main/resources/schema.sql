CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(20),
    password VARCHAR(255),
    email VARCHAR(255),
    phone VARCHAR(255),
    active BOOLEAN DEFAULT FALSE
    );
