ALTER TABLE users
    drop constraint if exists users_role_check;

alter table users
    add constraint users_role_check
        check ((role)::text = ANY
               (ARRAY [
                   ('GUEST'::character varying)::text,
                   ('CUSTOM'::character varying)::text,
                   ('USER'::character varying)::text,
                   ('MANAGER'::character varying)::text,
                   ('ADMIN'::character varying)::text,
                   ('SUPER_ADMIN'::character varying)::text,
                   ('MODERATOR'::character varying)::text
                   ]));

alter table users
    alter column authorities type character varying(255)[] using authorities::character varying(255)[];

