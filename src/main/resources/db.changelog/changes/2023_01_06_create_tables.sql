create table users (
    id int auto_increment primary key,
    name varchar(255) not null,
    username varchar(255) not null,
    password varchar(255) not null
);
create table roles (
    id int auto_increment primary key,
    name varchar(255) not null
);
create table items (
    id int auto_increment primary key,
    name varchar(255) not null,
    price int not null,
    description varchar(255) not null
);
create table users_roles (
    app_user_id int not null references users(id),
    roles_id int not null references roles(id)
);