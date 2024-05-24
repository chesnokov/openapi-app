create table if not exists user_table (
    id bigint auto_increment primary key,
    name varchar(50) not null,
    surname varchar(50) not null,
    mail varchar(256)
);

create table if not exists task (
    id bigint auto_increment primary key,
    user_id bigint references user_table(id),
    name varchar(128) not null,
    description varchar(512),
    creation_date date not null,
    dead_line date not null
);