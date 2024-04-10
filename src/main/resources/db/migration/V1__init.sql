CREATE TABLE IF NOT EXISTS address
(
    id       SERIAL primary key,
    province varchar(200),
    district varchar(200),
    village  varchar(200)
);

CREATE TABLE IF NOT EXISTS student
(
    id         varchar(200) primary key,
    name       varchar(200) not null,
    age        int          not null,
    gender     varchar(6)   not null,
    address_id INTEGER,
    FOREIGN KEY (address_id) REFERENCES address (id)
);

