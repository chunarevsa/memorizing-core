ALTER TABLE storage
    ALTER COLUMN user_id SET NOT NULL;


ALTER TABLE storage
    ADD CONSTRAINT unique_user_id
        UNIQUE (user_id);


ALTER TABLE storage
    ADD CONSTRAINT unique_storage_id_user_id
        UNIQUE (id, user_id);
