-- create a new table for liking


CREATE TABLE IF NOT EXISTS rating_like
(
    user_id    bigint REFERENCES users (id),
    rating_id  bigint REFERENCES rating (id),
    created_at TIMESTAMP without time zone DEFAULT NOW(),
    updated_at TIMESTAMP without time zone DEFAULT NOW(),

    CONSTRAINT unique_user_rating UNIQUE (user_id, rating_id)
);

ALTER TABLE ONLY rating_like
    ADD CONSTRAINT rating_like_pkey PRIMARY KEY (user_id, rating_id);