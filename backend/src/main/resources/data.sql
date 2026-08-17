INSERT INTO internal_role (id, name) VALUES (1, 'USER');
INSERT INTO internal_role (id, name) VALUES (2, 'ADMIN');

INSERT INTO internal_user (id, email, nom, prenom, password, role_id) VALUES (1, 'user@coworkia.fr','User', 'user', '$2y$10$JseIjP9wBn8QwJ9mIc1u/eunelfmNsWEScET2fWEzWlpU5BznDxam', 1);
INSERT INTO internal_user (id, email, nom, prenom, password, role_id) VALUES (2, 'admin@coworkia.fr','Admin', 'admin', '$2y$10$RM/ZthRVtzonkHr9dnvlIescYk7/R.jcIt6Dlas7eRDZNYkbw5oNi', 2);

ALTER TABLE internal_role ALTER COLUMN id RESTART WITH 3;
ALTER TABLE internal_user ALTER COLUMN id RESTART WITH 3;

INSERT INTO zone (id, code, nom, capacite) VALUES (1, 'AT', 'Atlas', 20);
INSERT INTO zone (id, code, nom, capacite) VALUES (2, 'BO', 'Boréal', 20);
INSERT INTO zone (id, code, nom, capacite) VALUES (3, 'CA', 'Calypso', 15);
INSERT INTO zone (id, code, nom, capacite) VALUES (4, 'DE', 'Delta', 15);
INSERT INTO zone (id, code, nom, capacite) VALUES (5, 'EC', 'Echo', 10);
INSERT INTO zone (id, code, nom, capacite) VALUES (6, 'FJ', 'Fjord', 10);
INSERT INTO zone (id, code, nom, capacite) VALUES (7, 'GA', 'Gaïa', 5);
INSERT INTO zone (id, code, nom, capacite) VALUES (8, 'HE', 'Hélios', 5);