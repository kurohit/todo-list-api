drop database todolistdb;
drop user todolist_user;
create user todolist_user with password 'password';
create database todolistdb with template=template0 owner=todolist_user;
\connect todolistdb;
alter default privileges grant all on tables to todolist_user;
alter default privileges grant all on sequences to todolist_user;

create table users(
    user_id integer primary key not null,
    name varchar(20) not null,
    email varchar(30) not null,
    password text not null
);

create table todolist(
    todolist_id integer primary key not null,
    uid integer not null,
    title varchar(20) not null,
    notes text
);

alter table todolist add constraint cat_users_fk
foreign key (uid) references users(user_id);

create sequence users_seq increment 1 start 1;
create sequence todolist_seq increment 1 start 1;
