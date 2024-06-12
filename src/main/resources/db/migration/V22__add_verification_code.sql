DROP TABLE IF EXISTS verification_code;

CREATE TABLE verification_code
(
    id              SERIAL PRIMARY KEY,
    code            VARCHAR(255) NOT NULL,
    user_id         INT          NOT NULL,
    request_address VARCHAR(255) NOT NULL,
    created_at      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

ALTER TABLE users
    DROP COLUMN IF EXISTS is_verified;

ALTER TABLE users
    ADD COLUMN is_verified BOOLEAN DEFAULT FALSE;