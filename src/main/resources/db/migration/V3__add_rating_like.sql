-- create a new table for liking


CREATE TABLE IF NOT EXISTS rating_like
(
    user_id    bigint NOT NULL,
    rating_id  bigint NOT NULL,
    created_at TIMESTAMP without time zone DEFAULT NOW(),
    updated_at TIMESTAMP without time zone DEFAULT NOW(),

    CONSTRAINT unique_user_rating UNIQUE (user_id, rating_id)
);

ALTER TABLE ONLY rating_like
    ADD CONSTRAINT rating_like_pkey PRIMARY KEY (user_id, rating_id);

ALTER TABLE ONLY rating_like
    ADD CONSTRAINT fk_rating_like_rating_id
        FOREIGN KEY (rating_id) REFERENCES rating (id) ON DELETE CASCADE;

ALTER TABLE ONLY rating_like
    ADD CONSTRAINT fk_rating_like_user_id
        FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE;