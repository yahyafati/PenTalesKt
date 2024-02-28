ALTER TABLE user_profile
    DROP COLUMN IF EXISTS goodreads_profile,
    DROP COLUMN IF EXISTS youtube_profile,
    DROP COLUMN IF EXISTS twitter_profile,
    DROP COLUMN IF EXISTS facebook_profile,
    DROP COLUMN IF EXISTS instagram_profile,
    DROP COLUMN IF EXISTS linkedin_profile;