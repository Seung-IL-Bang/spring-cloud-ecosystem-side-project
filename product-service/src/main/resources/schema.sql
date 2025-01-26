drop table if exists product;

create table product (
     id int auto_increment primary key,
     product_id varchar(20),
     product_name varchar(50) not null,
     stock int not null,
     unit_price int not null,
     created_at timestamp default current_timestamp
);