create table table_name
(
    id int,
    name varchar(30) null
);

create unique index table_name_id_uindex
    on table_name (id);

alter table table_name
    add constraint table_name_pk
        primary key (id);

alter table table_name modify id int auto_increment;

