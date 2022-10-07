-- DDL used to generate the DB needed for this API.

CREATE TABLE name
(
    id INTEGER not null auto_increment primary key,
    first_name varchar2(50),
    last_name varchar2(50)
);


CREATE TABLE social_user
(
    id INTEGER not null auto_increment primary key,
    external_ID UUID not null default random_uuid(),
    username varchar2(50) unique not null,
    name_id INTEGER,

    foreign key (name_id) references name
);

CREATE INDEX ON social_user (username);
CREATE INDEX ON social_user (external_ID);