-- create a unique id
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP VIEW IF EXISTS public.activity;

CREATE OR REPLACE VIEW public.activity AS
SELECT 'RATING'::text                                             AS type,
       uuid_generate_v5(uuid_nil(), concat('RATING', r.id::text)) AS id,
       r.id                                                       AS rating_id,
       r.id                                                       AS activity_id,
       r.user_id                                                  AS user_id,
       r.updated_at,
       r.created_at
FROM public.rating r
WHERE r.hidden = FALSE
UNION ALL
SELECT 'COMMENT'::text                                             AS type,
       uuid_generate_v5(uuid_nil(), concat('COMMENT', c.id::text)) AS id,
       c.rating_id                                                 AS rating_id,
       c.id                                                        AS activity_id,
       c.user_id                                                   AS user_id,
       c.updated_at,
       c.created_at
FROM public.comment c
WHERE c.hidden = FALSE
UNION ALL
SELECT 'SHARE'::text                                             AS type,
       uuid_generate_v5(uuid_nil(), concat('SHARE', s.id::text)) AS id,
       s.rating_id                                               AS rating_id,
       s.id                                                      AS activity_id,
       s.user_id                                                 AS user_id,
       s.updated_at,
       s.created_at
FROM public.share s
-- where rating with id = s.rating_id is not hidden
WHERE NOT EXISTS(SELECT 1
                 FROM public.rating r
                 WHERE r.id = s.rating_id
                   AND r.hidden = TRUE);

ALTER VIEW public.activity OWNER TO postgres;