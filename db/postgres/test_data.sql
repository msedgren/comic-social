
INSERT INTO name(first_name, last_name) VALUES ('foo', 'bar');

INSERT INTO social_user(username, name_id)
SELECT 'dude', min(id) from name;
