DROP TABLE IF EXISTS report;


CREATE TABLE IF NOT EXISTS report
(
    id             SERIAL PRIMARY KEY,
    user_id        INTEGER REFERENCES users (id),
    rating_id      INTEGER REFERENCES rating (id)  NULL     DEFAULT NULL,
    comment_id     INTEGER REFERENCES comment (id) NULL     DEFAULT NULL,
    type           VARCHAR(255)                    NOT NULL,
    reasons        TEXT                            NOT NULL,
    description    TEXT                            NOT NULL,
    status         VARCHAR(255)                    NOT NULL DEFAULT 'PENDING',
    approved_by_id INTEGER REFERENCES users (id)   NULL     DEFAULT NULL,
    created_at     TIMESTAMP                       NOT NULL DEFAULT NOW(),
    updated_at     TIMESTAMP                       NOT NULL DEFAULT NOW(),

    CONSTRAINT report_rating_id_comment_id_check CHECK (rating_id IS NOT NULL OR comment_id IS NOT NULL),
    CONSTRAINT report_type_check CHECK (type = 'COMMENT' OR type = 'REVIEW'),
    CONSTRAINT report_status_check CHECK (status = 'PENDING' OR status = 'APPROVED' OR status = 'REJECTED'),
    CONSTRAINT report_status_approved_by_check CHECK (status = 'PENDING' OR report.approved_by_id IS NOT NULL)
)