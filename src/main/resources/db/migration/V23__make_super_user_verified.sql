-- make users with username 'super' verified

UPDATE users
SET is_verified = true
WHERE username = 'superadmin';