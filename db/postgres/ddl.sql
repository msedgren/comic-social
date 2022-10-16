
CREATE TABLE IF NOT EXISTS name
(
    id SERIAL PRIMARY KEY ,
    first_name varchar(50),
    last_name varchar(50)
);


CREATE TABLE IF NOT EXISTS social_user
(
    id SERIAL PRIMARY KEY,
    external_ID UUID not null default gen_random_uuid(),
    username varchar(50) unique not null,
    name_id INTEGER,

    foreign key (name_id) references name
);

CREATE INDEX ON social_user (username);
CREATE INDEX ON social_user (external_ID);

