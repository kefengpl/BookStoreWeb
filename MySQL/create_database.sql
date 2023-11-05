drop database if exists book;

create database book; 
use book;
/* 创建用户表 */
create table t_user(
	/* 加反引号是为了说明不是MySQL的关键字 */
	`id` int primary key auto_increment, /* 自增 */
    `username` varchar(20) not null unique,
    `password` varchar(32) not null,
    `email` varchar(200)
);

insert into t_user(`username`, `password`,`email`) values('admin', 'admin', 'admin@ruc.edu.cn');

select * from t_user;