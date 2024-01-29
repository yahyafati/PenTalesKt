CREATE TABLE IF NOT EXISTS authority
(
    id   INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,

    UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS role
(
    id   INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,

    UNIQUE (name)
);

-- CREATE TABLE IF NOT EXISTS authority_role
-- (
--     role_id      INT NOT NULL,
--     authority_id INT NOT NULL,
--     FOREIGN KEY (role_id) REFERENCES role (id),
--     FOREIGN KEY (authority_id) REFERENCES authority (id),
--
--     UNIQUE (role_id, authority_id)
-- );

CREATE TABLE IF NOT EXISTS authority_user
(
    user_id      INT NOT NULL,
    authority_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (authority_id) REFERENCES authority (id),

    UNIQUE (user_id, authority_id)
);

ALTER TABLE users
    DROP COLUMN IF EXISTS authorities;

ALTER TABLE users
    DROP COLUMN IF EXISTS roles;

ALTER TABLE users
    ADD COLUMN IF NOT EXISTS role_id INT,
    ADD FOREIGN KEY (role_id) REFERENCES role (id);

-- Insert default roles
INSERT INTO role (id, name)
VALUES (1, 'ROLE_CUSTOM'),
       (2, 'ROLE_USER'),
       (3, 'ROLE_MODERATOR'),
       (4, 'ROLE_MANAGER'),
       (5, 'ROLE_ADMIN'),
       (6, 'ROLE_SUPER_ADMIN');

-- Insert default authorities
INSERT INTO authority (id, name)
VALUES (1, 'ADMIN_READ'),
       (2, 'ADMIN_WRITE'),
       (3, 'ADMIN_DELETE'),
       (4, 'MANAGER_READ'),
       (5, 'MANAGER_WRITE'),
       (6, 'MANAGER_DELETE'),
       (7, 'USER_READ'),
       (8, 'USER_WRITE'),
       (9, 'USER_DELETE'),
       (10, 'MODERATOR_ACCESS');

