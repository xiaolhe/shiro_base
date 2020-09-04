create table if not exists user_role
(
    role_id    int auto_increment comment '角色id'
        primary key,
    role_name  varchar(255) null comment '角色名称',
    username   varchar(30)  null comment '用户名',
    permission varchar(255) null comment '权限( add,delete,query,update)'
)
    comment '用户角色权限表';


