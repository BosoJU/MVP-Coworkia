CREATE TABLE internal_role (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255)
);

CREATE TABLE internal_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL UNIQUE,
    nom VARCHAR(255),
    prenom VARCHAR(255),
    password VARCHAR(255),
    role_id BIGINT,
    FOREIGN KEY (role_id) REFERENCES internal_role(id)
);

CREATE TABLE zone (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    code VARCHAR(255),
    nom VARCHAR(255),
    capacite INT
);

CREATE TABLE reservation (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    zone_id BIGINT,
    user_id BIGINT,
    date_debut TIMESTAMP,
    date_fin TIMESTAMP,
    statut VARCHAR(255),
    FOREIGN KEY (zone_id) REFERENCES zone(id),
    FOREIGN KEY (user_id) REFERENCES internal_user(id)
);