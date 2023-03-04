create sequence if not exists sq_pack_volumes;

create table if not exists pack_volumes
(
    id     int default nextval('sq_pack_volumes') not null,
    name   varchar(64)                            not null,
    volume float                                  not null,
    height int,
    length int,
    width  int,
    constraint pk_pack_volumes primary key (id)
);

comment on table pack_volumes is 'Типовые объемы упаковки';


create sequence if not exists sq_addresses;

create table if not exists addresses
(
    id           int default nextval('sq_addresses') not null,
    address_id   varchar(10)                         not null,
    address_name varchar(120)                        not null,
    address      varchar(256)                        not null,
    constraint pk_addresses primary key (id)
);

create sequence if not exists sq_req;


create table if not exists req
(
    id                     int default nextval('sq_req') not null,
    sender_id              int                           not null,
    recipient_id           int                           not null,
    seal                   varchar(32)                   not null,
    cargo_category_id      int                           not null,
    cargo_category_name    varchar(64)                   not null,
    recipient_person_name  varchar(120),
    recipient_person_phone varchar(120),
    description            varchar(256)                  not null,
    user_comment_text      varchar(256),
    volume                 float                         not null,
    weight                 float                         not null,
    value                  numeric(12, 2)                not null,
    user_id                int                           not null,
    user_full_name         varchar(256)                  not null,
    user_email             varchar(256),
    parcel_id              int,
    parcel_barcode         varchar(50),
    created_at              timestamp(6)                  not null,
    constraint pk_req primary key (id),
    constraint fk_req$s foreign key (sender_id) references addresses,
    constraint fk_req$r foreign key (recipient_id) references addresses
);

create index idx_req$u_cr on req (user_id, created_at);