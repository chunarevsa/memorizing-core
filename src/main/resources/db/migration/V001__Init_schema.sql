CREATE TABLE IF NOT EXISTS storage
(
    id           SERIAL PRIMARY KEY,
    user_id      BIGINT NOT NULL,
    storage_name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS card_stock
(
    id                     SERIAL PRIMARY KEY,
    storage_id             INT     NOT NULL REFERENCES storage (id),
    card_stock_name        VARCHAR(255),
    description            VARCHAR(255),
    key_type               VARCHAR(255),
    value_type             VARCHAR(255),
    max_point              INT     NOT NULL,
    test_mode_is_available BOOLEAN NOT NULL,
    only_from_key          BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS card
(
    id              SERIAL PRIMARY KEY,
    card_stock_id   INT NOT NULL REFERENCES card_stock (id),
    card_key        VARCHAR(255),
    card_value      VARCHAR(255),
    point_from_key  INT NOT NULL,
    point_to_key    INT NOT NULL,
    status_from_key VARCHAR(255),
    status_to_Key   VARCHAR(255)
);
