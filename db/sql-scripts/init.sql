DROP TABLE IF EXISTS trains;
DROP TABLE IF EXISTS compartments;
DROP TABLE IF EXISTS people

CREATE TABLE trains (
    id              INT AUTO_INCREMENT NOT NULL,
    current_station VARCHAR(255),
    going_to_gdansk BOOLEAN DEFAULT FALSE,
    PRIMARY KEY     (id)
);

CREATE TABLE compartments (
    id              INT AUTO_INCREMENT NOT NULL,
    capacity        INT NOT NULL,
    train_id        INT NOT NULL,
    PRIMARY KEY     (id),
    FOREIGN KEY (train_id) REFERENCES trains(id)
);

CREATE TABLE people (
    id              INT AUTO_INCREMENT NOT NULL,
    first_name      VARCHAR(255),
    last_name       VARCHAR(255),
    destination     VARCHAR(255),
    compartment_id  INT NOT NULL,
    PRIMARY KEY     (id),
    FOREIGN KEY (compartment_id) REFERENCES compartments(id)
);