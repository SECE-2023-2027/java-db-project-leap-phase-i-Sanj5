create database Banking;
use Banking;
create table banking_application(id int, name varchar(20), age int);
select * from banking_application;
insert into banking_application values(101, 'Anshul', 23);
create table bank_account(id int, name varchar(20), age int, city varchar(30));
select * from bank_account;
drop table banking_application;
insert into bank_account  (id, name, age, city) values(1, 'arshiya', 34, 'pollachi'), (3, 'sanjana', 18, 'ooty'), (4, 'hari', 25, 'coimbatore');
update bank_account set name = 'kumar' where id = 1;
set sql_safe_updates = 0;
delete from bank_account where id = 3;
truncate table bank_account;
alter table bank_account add state varchar(30);
alter table bank_account drop column state;

alter table bank_account modify column age varchar(20);
select id, name, age from bank_account where id between 1 and 4;
select id, name, age from bank_account where name in('arshiya');
select id, name, age from bank_account where name not in('arshiya');
select id, name, age from bank_account where name like ('r%');
select id, name, age from bank_account where name is null;
select id, name, age from bank_account where name is not null;
create table home(id int not null, name varchar(20) not null);
insert into home values(1, null);
drop table home;
create table persons(id int not null, firstname varchar(45), lastname varchar(255), Age int, UNIQUE (ID));
insert into Persons values(1, 'raj', 's', 20);
drop table persons;

create table user(id int, name varchar(20), amount int);
insert into user values(1, 'akshaya', 3000), (3, 'Lakshmi', 1000), (20, 'Sanjana', 250), (15, 'Vijay', 450), (18, 'Poorni', 450);
insert into user values(2, 'priya', 1000), (10, 'Deepak', 2000), (12, 'Sandhya', 250), (13, 'Ramya', 1050), (22, 'Pavithra', 2000);
select id, name, amount from user order by amount desc limit 1;

create database Crud_;
use Crud_;
create table crud_pro(id int, name varchar(20), age int);
drop table crud_pro;
select * from crud_pro;

