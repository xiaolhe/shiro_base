create table if not exists users
(
    id       int(10) auto_increment
        primary key,
    username varchar(255) null comment '账号',
    password varchar(255) null comment '密码'
)
    comment '用户表';


