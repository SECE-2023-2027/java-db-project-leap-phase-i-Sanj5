create database workshop;
use workshop;
create table account(
account_id int primary key auto_increment, 
customer_id int, 
account_type varchar(225),  
balance decimal(15, 2) not null,
 created_at timestamp default current_timestamp,
 address varchar(225), mobile varchar(50));
 
 select * from account;
 
 create table savings_account(
 account_id int primary key,
 foreign key(account_id) references account(account_id),
 interest_rate decimal(5, 2) not null);
 
 select * from savings_account;
 
 create table current_account(
 account_id int primary key,
 foreign key(account_id) references account(account_id),
 overdraft_limit decimal(15,2) not null);
 
 select * from current_account;
 
 create table transaction(
 transaction_id int primary key auto_increment,
 account_id int ,
 foreign key(account_id) references account(account_id),
 transaction_type varchar(50) not null,
 amount decimal(15, 2) not null,
 transaction_date timestamp default current_timestamp);
 
 select * from transaction;
 delete from transaction where transaction_id = 3;
 update account set balance = balance - 120 where account_id = 1;