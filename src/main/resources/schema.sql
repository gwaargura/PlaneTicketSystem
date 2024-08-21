CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    password VARCHAR(255),
    email VARCHAR(255),
    phone VARCHAR(255),
    active BOOLEAN DEFAULT FALSE
);

-- CREATE TABLE IF NOT EXISTS purchases (
--       id SERIAL PRIMARY KEY,
--       userId INTEGER NOT NULL,
--       dateCreated VARCHAR(50) DEFAULT CURRENT_VARCHAR(50),
--       paid BOOLEAN DEFAULT FALSE,
--       expired BOOLEAN DEFAULT FALSE,
--       FOREIGN KEY (userId) REFERENCES users(id)
-- );
--
CREATE TABLE IF NOT EXISTS pilots (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age INTEGER NOT NULL,
    totalHourFlew DOUBLE PRECISION NOT NULL,
    active BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS planes (
    id SERIAL PRIMARY KEY,
    brand VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    size INTEGER NOT NULL,
    active BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS flights (
    id SERIAL PRIMARY KEY,
    planeId INTEGER NOT NULL,
    pilotId INTEGER NOT NULL,
    copilotId INTEGER NOT NULL,
    origin VARCHAR(255) NOT NULL,
    destination VARCHAR(255) NOT NULL,
    departureTime VARCHAR(50),
    duration DOUBLE PRECISION NOT NULL,
    FOREIGN KEY (planeId) REFERENCES planes(id),
    FOREIGN KEY (pilotId) REFERENCES pilots(id),
    FOREIGN KEY (copilotId) REFERENCES pilots(id)
    );

-- CREATE TABLE IF NOT EXISTS tickets (
--     id SERIAL PRIMARY KEY,
--     purchaseId INTEGER NOT NULL,
--     seat INTEGER NOT NULL,
--     origin VARCHAR(255) NOT NULL,
--     destination VARCHAR(255) NOT NULL,
--     departureTime VARCHAR(50) NOT NULL,
--     planeId INTEGER NOT NULL,
--     gate VARCHAR(1) NOT NULL,
--     flightId INTEGER NOT NULL,
--     FOREIGN KEY (purchaseId) REFERENCES purchases(id),
--     FOREIGN KEY (origin) REFERENCES flights(origin),
--     FOREIGN KEY (destination) REFERENCES flights(destination),
--     FOREIGN KEY (departureTime) REFERENCES flights(departureTime),
--     FOREIGN KEY (planeId) REFERENCES planes(id),
--     FOREIGN KEY (flightId) REFERENCES flights(id)
-- );
