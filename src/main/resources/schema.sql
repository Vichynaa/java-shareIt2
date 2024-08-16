CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);


CREATE TABLE IF NOT EXISTS items (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    available BOOLEAN NOT NULL,
    owner_id BIGINT,
    FOREIGN KEY (owner_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS bookings (
    id SERIAL PRIMARY KEY,
    item_id BIGINT NOT NULL,
    booker_id BIGINT NOT NULL,
    start_time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    end_time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    status VARCHAR(255) NOT NULL,
    FOREIGN KEY (item_id) REFERENCES items(id),
    FOREIGN KEY (booker_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS comments (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    text VARCHAR(255) NOT NULL,
    writer_id BIGINT,
    item_id BIGINT,
    created TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    FOREIGN KEY (writer_id) REFERENCES users(id),
    FOREIGN KEY (item_id) REFERENCES items(id)
);

