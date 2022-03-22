
CREATE TABLE public.file (
    id serial not null,
    created timestamp not null,
    creator varchar,
    updated timestamp,
    updater varchar,
    archived timestamp,
    archiver varchar,
    filename varchar NOT NULL,
    directory varchar NOT NULL,
    size varchar NOT NULL,
    content_type varchar NOT NULL,
    PRIMARY KEY(id)
);