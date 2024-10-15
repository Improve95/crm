create table sellers (
    id int primary key generated by default as identity ,
    name varchar(50) not null ,
    contact_info varchar(100) not null unique,
    registration_date timestamp not null default current_timestamp
);

create table transactions (
    id int primary key generated by default as identity ,
    seller int not null references sellers("id") on delete cascade ,
    amount int not null check ( amount > 0 ) ,
    payment_type varchar(8) not null ,
    transaction_date timestamp not null default current_timestamp
);

select from
