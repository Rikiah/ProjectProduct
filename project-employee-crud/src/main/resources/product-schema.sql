drop table if exists product CASCADE;
create table product
 (
 	id integer primary key auto_increment,
 	name varchar(255),
 	description varchar(255),
 	price varchar(255)
 );