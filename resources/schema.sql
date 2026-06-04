DROP TABLE IF EXISTS inregistrare_bid CASCADE;
DROP TABLE IF EXISTS produs CASCADE;
DROP TABLE IF EXISTS licitator CASCADE;
DROP TABLE IF EXISTS vanzator CASCADE;
DROP TABLE IF EXISTS utilizator CASCADE;

CREATE TABLE utilizator (
                            id SERIAL PRIMARY KEY,
                            nume VARCHAR(100) NOT NULL,
                            email VARCHAR(100) UNIQUE NOT NULL,
                            tip VARCHAR(20) NOT NULL
);

CREATE TABLE licitator (
                           id INT PRIMARY KEY,
                           buget DOUBLE PRECISION NOT NULL,
                           FOREIGN KEY (id) REFERENCES utilizator(id) ON DELETE CASCADE
);

CREATE TABLE vanzator (
                          id INT PRIMARY KEY,
                          rating DOUBLE PRECISION NOT NULL,
                          FOREIGN KEY (id) REFERENCES utilizator(id) ON DELETE CASCADE
);

CREATE TABLE produs (
                        id SERIAL PRIMARY KEY,
                        nume VARCHAR(100) NOT NULL,
                        pret_pornire DOUBLE PRECISION NOT NULL,
                        vanzator_id INT,
                        FOREIGN KEY (vanzator_id) REFERENCES vanzator(id) ON DELETE SET NULL
);

CREATE TABLE inregistrare_bid (
                                  id SERIAL PRIMARY KEY,
                                  produs_id INT NOT NULL,
                                  licitator_id INT NOT NULL,
                                  suma DOUBLE PRECISION NOT NULL,
                                  timestamp TIMESTAMP NOT NULL,
                                  FOREIGN KEY (produs_id) REFERENCES produs(id) ON DELETE CASCADE,
                                  FOREIGN KEY (licitator_id) REFERENCES licitator(id) ON DELETE CASCADE
);