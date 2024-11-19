CREATE SEQUENCE vehicle_seq
INCREMENT BY 50;


CREATE TABLE IF NOT EXISTS vehicle (
    vehicle_id INTEGER PRIMARY KEY DEFAULT NEXTVAL('vehicle_seq'),
    matriculation_number VARCHAR(255) NOT NULL,
    brand VARCHAR(255),
    model VARCHAR(255),
    color VARCHAR(255)
);



CREATE SEQUENCE offer_seq
INCREMENT BY 50;

CREATE TABLE IF NOT EXISTS offer (
    id INTEGER PRIMARY KEY DEFAULT NEXTVAL('offer_seq'),
    dep_location VARCHAR(255) NOT NULL,
    arr_location VARCHAR(255) NOT NULL,
    time VARCHAR(255),
    price NUMERIC(10, 2),
    nb_place VARCHAR(255),
    vehicle_id INTEGER,
    CONSTRAINT fk_vehicle
        FOREIGN KEY (vehicle_id)
        REFERENCES vehicle(vehicle_id)
        ON DELETE CASCADE
);


