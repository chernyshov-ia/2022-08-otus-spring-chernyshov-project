create table if not exists address_types(
    id varchar(16) not null,
    name varchar(120) not null,
    constraint pk_address_types primary key (id)
);

create table if not exists addresses(
  id varchar(10) not null,
  name varchar(120) not null,
  address varchar(256) not null,
  type_id varchar(16) not null,
  working boolean not null default true,
  constraint pk_address primary key (id),
  constraint fk_address$typ foreign key (type_id) references address_types
);

create sequence if not exists sq_hu start with 10000;

create table if not exists hu
(
    id           int default nextval('sq_hu') not null,
    order_id     varchar(20),
    hu_num       varchar(32) not null ,
    barcode      varchar(50) not null ,
    seal         varchar(32),
    sender_id    varchar(10) not null,
    recipient_id varchar(10) not null,
    descr        varchar(512),
    volume       FLOAT,
    weight       FLOAT,
    ctime        timestamp(6) not null,
    constraint pk_hu primary key (id),
    constraint fk_hu$s foreign key (sender_id) references addresses,
    constraint fk_hu$r foreign key (recipient_id) references addresses,
    constraint uidx_hu$barcode unique (barcode),
    constraint uidx_hu$hu_num unique (hu_num)
);

create index idx_hu$ord on hu(order_id);
create index idx_hu$seal on hu(seal);

comment on table hu is 'Таблица ЕО(единиц обработки) - грузовых мест';
