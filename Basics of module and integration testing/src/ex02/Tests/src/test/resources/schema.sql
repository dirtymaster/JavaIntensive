drop table if exists product;

create table product
(
    id bigint primary key,
    name varchar(100),
    price bigint
);