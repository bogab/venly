create table public.word_relations
(
    id       serial primary key,
    word_one text not null,
    word_two text not null,
    relation text not null
);