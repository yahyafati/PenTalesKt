CREATE TABLE IF NOT EXISTS user_book_file_progress
(
    user_id      INTEGER NOT NULL,
    book_file_id INTEGER NOT NULL,
    progress     TEXT    NOT NULL DEFAULT '0',

    created_at   TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (user_id, book_file_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (book_file_id) REFERENCES book_file (id) ON DELETE CASCADE
);

ALTER TABLE book_file
    ADD COLUMN IF NOT EXISTS is_public BOOLEAN NOT NULL DEFAULT FALSE;


