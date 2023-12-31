CREATE OR REPLACE VIEW activity AS
SELECT 'RATING' AS type, r.id AS id, r.updated_at AS updated_at
FROM rating AS r
UNION ALL
SELECT 'COMMENT' AS type, c.id AS id, c.updated_at AS updated_at
FROM comment AS c
UNION ALL
SELECT 'SHARE' AS type, s.id AS id, s.updated_at AS updated_at
FROM share AS s