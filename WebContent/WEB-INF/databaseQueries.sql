drop table EVENT_TICKET;
drop table EVENT;
drop table USER;


create table USER(
user_id varchar(120) primary key,
first_name varchar(20) not null,
last_name varchar(20) not null,
user_type varchar(20) not null,
email_address varchar(120) not null unique,
password varchar(8) not null
);

create table EVENT(
event_id varchar(120) primary key,
name varchar(120) not null,
description varchar(120) not null,
location varchar(120) not null,
event_date date not null,
event_time varchar(20) not null,
ticket_price double(5,2) not null,
capacity int(5) not null,
available_tickets int(5) not null,
user_id varchar(120) not null,
foreign key (user_id) references USER (user_id),
unique (name, event_date)
);

create table EVENT_TICKET(
ticket_id varchar(120) primary key,
user_id varchar(120) not null,
event_id varchar(120) not null,
number_of_tickets int(5) not null,
total_cost double(5,2) not null,
purchase_time timestamp not null,
foreign key (user_id) references USER (user_id),
foreign key (event_id) references EVENT (event_id)
);