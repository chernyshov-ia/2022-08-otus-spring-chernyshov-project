set search_path = "public";

create sequence if not exists sq_pack_volumes;

create table if not exists pack_volumes(
    id int default nextval('sq_pack_volumes') not null,
    name varchar(64) not null,
    volume float not null,
    height int,
    length int,
    width int,
    constraint pk_pack_volumes primary key (id)
);

comment on table pack_volumes is 'Типовые объемы упаковки';

