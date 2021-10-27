CREATE TABLE IF NOT EXISTS courses
(
    id          bigint auto_increment,
    name        varchar(512) not null,
    create_time timestamp    null,
    update_time timestamp    null,
    constraint courses_pk
        primary key (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;

CREATE TABLE IF NOT EXISTS users
(
    id          bigint auto_increment,
    email       VARCHAR(512) not null,
    first_name  varchar(128) not null,
    second_name varchar(128) not null,
    password    varchar(128) not null,
    role        varchar(64)  not null,
    create_time timestamp    null,
    update_time timestamp    null,
    constraint users_pk
        primary key (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;

create unique index users_email_uindex
    on users (email);

CREATE TABLE IF NOT EXISTS students
(
    id          bigint auto_increment,
    user_id     bigint    not null,
    create_time timestamp null,
    update_time timestamp null,
    constraint students_pk
        primary key (id),
    constraint students_users__fk
        foreign key (user_id) references users (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;

create table students_courses
(
    student_id bigint not null,
    course_id  bigint not null,
    constraint students_courses__courses_fk
        foreign key (course_id) references courses (id),
    constraint students_courses__students_fk
        foreign key (student_id) references students (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8
