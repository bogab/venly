drop table if exists word_relations;

create table word_relations
(
    id       serial primary key,
    word_one text not null,
    word_two text not null,
    relation text not null
);