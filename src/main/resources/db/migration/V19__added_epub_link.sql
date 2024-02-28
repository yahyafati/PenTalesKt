DROP TABLE IF EXISTS book_file;

CREATE TABLE book_file
(
    id         SERIAL PRIMARY KEY,
    book_id    INTEGER REFERENCES book (id),
    owner_id   INTEGER REFERENCES users (id),
    path       TEXT NOT NULL,


    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT book_file_book_id_path_type_key UNIQUE (book_id, owner_id)
);

CREATE INDEX book_file_book_id ON book_file (book_id);


