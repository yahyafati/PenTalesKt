-- create a unique id
DROP VIEW IF EXISTS public.activity CASCADE;

CREATE OR REPLACE VIEW public.activity AS
SELECT 'RATING'::text AS type,
       r.id,
       r.id           AS activity_id,
       r.updated_at,
       r.created_at
FROM public.rating r
UNION ALL
SELECT 'COMMENT'::text AS type,
       c.rating_id     AS id,
       c.id            AS activity_id,
       c.updated_at,
       c.created_at
FROM public.comment c
UNION ALL
SELECT 'SHARE'::text AS type,
       s.rating_id   AS id,
       s.id          AS activity_id,
       s.updated_at,
       s.created_at
FROM public.share s;


ALTER VIEW public.activity OWNER TO postgres;