-- cascade delete share alter table

-- remove constraint fk8w9gobrtcmuyifxiyh1gvlnka
ALTER TABLE share
    DROP CONSTRAINT IF EXISTS fk8w9gobrtcmuyifxiyh1gvlnka;

ALTER TABLE share
    DROP CONSTRAINT IF EXISTS fk_rating_share;

ALTER TABLE share
    ADD CONSTRAINT fk_rating_share
        FOREIGN KEY (rating_id)
            REFERENCES rating (id)
            ON DELETE CASCADE;


ALTER TABLE comment
    DROP CONSTRAINT IF EXISTS fk_rating_comment;

ALTER TABLE comment
    ADD CONSTRAINT fk_rating_comment
        FOREIGN KEY (rating_id)
            REFERENCES rating (id)
            ON DELETE CASCADE;


ALTER TABLE rating_like
    DROP CONSTRAINT IF EXISTS rating_like_rating_id_fkey;

ALTER TABLE rating_like
    ADD CONSTRAINT rating_like_rating_id_fkey
        FOREIGN KEY (rating_id)
            REFERENCES rating (id)
            ON DELETE CASCADE;

ALTER TABLE rating
    ALTER COLUMN review TYPE TEXT;