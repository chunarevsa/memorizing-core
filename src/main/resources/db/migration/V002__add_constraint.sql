ALTER TABLE card_stock
    ADD CONSTRAINT unique_storage_id_card_stock_name
        UNIQUE (storage_id, card_stock_name);

ALTER TABLE card
    ALTER COLUMN card_key SET NOT NULL;

ALTER TABLE card
    ADD CONSTRAINT unique_card_stock_key
        UNIQUE (card_stock_id, card_key);
