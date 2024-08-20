create table if not exists users
(
    user_id           serial       not null,
    user_telegram_tag varchar(255) not null unique,
    user_telegram_id  bigint       not null unique,
    primary key (user_id)
);
create table if not exists notes
(
    note_id              serial not null,
    note_details_id      integer unique,
    content_url          varchar(255),
    status_of_completion bool   not null,
    primary key (note_id)
);

create table if not exists notes_specific_details
(
    note_details_id    serial       not null,
    content_short_name varchar(255) unique,
    description_id     integer      not null unique,
    content_type       varchar(255) not null check (content_type in ('PODCAST', 'VIDEO', 'BOOK')),
    primary key (note_details_id)
);

create table if not exists description
(
    description_id serial  not null,
    comment_id     integer not null, -- one to many. many comments to one description id
    primary key (description_id)
);

create table if not exists comment
(
    comment_id   serial not null,
    text_message text   not null,
    timecode     integer, -- in seconds
    page         integer
);

create table if not exists users_notes
(
    user_id serial not null,
    note_id serial not null, -- one to many: many notes per user
    primary key (user_id)
);

alter table if exists users_notes
    add constraint user_notes_user_id_constraint foreign key (user_id) references users on delete cascade;
alter table if exists users_notes
    add constraint users_notes_note_id_constraint foreign key (note_id) references notes on delete cascade;
alter table if exists notes
    add constraint notes_specific_details_note_details_id foreign key (note_details_id) references notes_specific_details on delete cascade;
alter table if exists notes_specific_details
    add constraint notes_specific_details_description_id foreign key (description_id) references description on delete cascade;
alter table if exists description
    add constraint description_comment_id foreign key (comment_id) references comment on delete cascade;