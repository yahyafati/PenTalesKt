-- add column to book shelf table

ALTER TABLE book_shelf
    ADD COLUMN IF NOT EXISTS read_later BOOLEAN DEFAULT FALSE;

-- add owner column to book shelf table

ALTER TABLE book_shelf
    ADD COLUMN IF NOT EXISTS owner_id INTEGER REFERENCES users (id);

-- add constraint to book shelf table so that only one bookshelf can be read later for each user

CREATE UNIQUE INDEX IF NOT EXISTS owner_read_later_unique
    ON book_shelf (owner_id)
    WHERE read_later = TRUE;

-- remove read later from user profile

ALTER TABLE user_profile
    DROP COLUMN IF EXISTS want_to_read_shelf_id;

-- create a trigger to create book shelf when user is created

CREATE EXTENSION IF NOT EXISTS plpgsql;

CREATE OR REPLACE FUNCTION create_book_shelf()
    RETURNS TRIGGER AS
$$
BEGIN
    INSERT INTO book_shelf (created_at, updated_at, access, description, title, owner_id, read_later)
    VALUES (NOW(), NOW(), 'PRIVATE', 'Books I want to read', 'Want to Read', NEW.id, TRUE);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER create_book_shelf
    AFTER INSERT
    ON users
    FOR EACH ROW
EXECUTE PROCEDURE create_book_shelf();

