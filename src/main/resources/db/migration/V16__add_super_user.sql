DO
$$
    DECLARE
        new_user_id   INTEGER;
        password_hash TEXT := '$2a$10$vIZIjOCLYq3BTHIndWv6A.UuwaeLpE29KsxECW8xqegjomFEYs.RG';
    BEGIN
        INSERT INTO users (created_at, updated_at, email, is_account_non_expired, is_account_non_locked, is_enabled,
                           password, username, role_id)
        VALUES (NOW(), NOW(), 'superadmin@readingrealm.com', true, true, true, password_hash, 'superadmin', 6)
        RETURNING id INTO new_user_id;

        INSERT INTO user_profile (created_at, updated_at, user_id, first_name, last_name, gender, bio, date_of_birth,
                                  display_name)
        VALUES (NOW(), NOW(), new_user_id, 'Super', 'Admin', 'MALE', 'I am the SUPER ADMIN', '1999-01-22',
                'Super Admin');

        INSERT INTO authority_user (authority_id, user_id)
        VALUES (1, new_user_id),
               (2, new_user_id),
               (3, new_user_id),
               (4, new_user_id),
               (5, new_user_id),
               (6, new_user_id),
               (7, new_user_id),
               (8, new_user_id),
               (9, new_user_id),
               (10, new_user_id);
    END;
$$