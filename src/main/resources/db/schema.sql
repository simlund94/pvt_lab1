-- Schema copied from the "production" MySql database, to construct an H2 database for integration testing

create table lab_employees
(
    id         int auto_increment
        primary key,
    name       varchar(100) not null,
    birth_year int          not null,
    constraint check_birth_year
        check (`birth_year` >= 1900)
);

create table lab_sites
(
    id                   int auto_increment
        primary key,
    name                 varchar(100) not null,
    address              varchar(100) not null,
    postal_code          int          not null,
    postal_area          varchar(50)  not null,
    property_designation varchar(50)  not null
);

create table lab_rooms
(
    id          int auto_increment
        primary key,
    size_in_sqm decimal(10, 2) not null,
    description varchar(50)    not null,
    site_id     int            not null,
    constraint lab_rooms_lab_sites_id_fk
        foreign key (site_id) references lab_sites (id)
            on delete cascade
);

create table lab_cleaning_orders
(
    id             int auto_increment
        primary key,
    employee_id    int                            not null,
    room_id        int                            not null,
    time_scheduled datetime                       not null,
    time_finished  datetime                       null,
    order_status   enum ('SCHEDULED', 'FINISHED') not null,
    constraint lab_cleaning_orders_lab_employees_id_fk
        foreign key (employee_id) references lab_employees (id)
            on delete cascade,
    constraint lab_cleaning_orders_lab_rooms_id_fk
        foreign key (room_id) references lab_rooms (id)
            on delete cascade
);



