CREATE TABLE IF NOT EXISTS card
(
    id              INT NOT NULL PRIMARY KEY,
    card_stock_id   INT NOT NULL,
    card_key        VARCHAR(255),
    card_value      VARCHAR(255),
    point_from_key  INT NOT NULL,
    point_to_key    INT NOT NULL,
    status_from_key VARCHAR(255),
    status_to_Key   VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS card_stock
(
    id                     INT     NOT NULL PRIMARY KEY,
    storage_id             INT     NOT NULL,
    card_stock_name        VARCHAR(255),
    description            VARCHAR(255),
    keyType                VARCHAR(255),
    valueType              VARCHAR(255),
    max_point              INT     NOT NULL,
    test_mode_is_available BOOLEAN NOT NULL,
    only_from_key          BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS storage
(
    id      INT NOT NULL PRIMARY KEY,
    user_id INT NOT NULL,
    storage_name        VARCHAR(255)
);

ALTER TABLE IF EXISTS card_stock
    ADD CONSTRAINT FK_card_stock_storage_id FOREIGN KEY (storage_id) references storage;

ALTER TABLE IF EXISTS card
    ADD CONSTRAINT FK_card_card_stock_id FOREIGN KEY (card_stock_id) references card;

