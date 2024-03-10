CREATE TYPE state AS ENUM ('TRACK','UNTRACK','DEFAULT');
create table if not exists Chats
(
    id     bigint primary key,
    status state not null
);
create table if not exists Links
(
    id  int primary key generated by default as identity,
    url varchar unique not null,
    last_activity timestamp with time zone not null
);
create table if not exists Chats_Links
(
    chat_id bigint references Chats (id),
    link_id int references Links (id),
    primary key (chat_id, link_id)
)

