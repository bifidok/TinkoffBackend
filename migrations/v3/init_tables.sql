CREATE TYPE state AS ENUM ('TRACK','UNTRACK','DEFAULT');
create table if not exists Chats
(
    id     bigint primary key,
    status state not null
);
create table if not exists Links
(
    id              int generated by default as identity,
    url             varchar unique           not null,
    last_activity   timestamp with time zone not null,
    last_check_time timestamp with time zone not null,
    primary key (id)
);
create table if not exists Chats_Links
(
    chat_id bigint references Chats (id) on delete cascade,
    link_id int references Links (id) on delete cascade,
    primary key (chat_id, link_id)
);
create table if not exists Repositories
(
    id               int generated by default as identity,
    link_id          int references Links (id) on delete cascade unique,
    last_commit_date timestamp with time zone not null,
    primary key (id)
);
create table Questions
(
    id bigint primary key,
    link_id int references Links(id) on delete cascade unique,
    answer_count int not null
)

