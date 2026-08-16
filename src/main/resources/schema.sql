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
