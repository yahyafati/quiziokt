-- auto-generated definition
-- User SQL
create table if not exists user (
    id                      int auto_increment primary key,
    account_non_expired     bit          not null,
    account_non_locked      bit          not null,
    creation_time           datetime(6)  null,
    credentials_non_expired bit          not null,
    enabled                 bit          not null,
    password                varchar(255) null,
    update_time             datetime(6)  null,
    username                varchar(255) null,
    email                   varchar(255) null,
    profile_id              int          null,
--     constraint UK_ob8kqyqqgmefl0aco34akdtpe
    unique (email),
--     constraint UK_sb8bbouer5wak8vyiiy4pf2bx
    unique (username)
--     constraint FK5hv52mjjufrwrx302p37tq262
--     foreign key (profile_id) references user_profile (id)
);

-- auto-generated definition
-- User Profile
create table if not exists user_profile (
    id            int auto_increment primary key,
    bio           varchar(255) null,
    creation_time datetime(6)  null,
    update_time   datetime(6)  null,
    user_id       int          null,
--     constraint FK6kwj5lk78pnhwor4pgosvb51r
    foreign key (user_id) references user (id) on delete cascade
);

-- auto-generated definition
-- Quiz
create table if not exists quiz (
    id                  int auto_increment primary key,
    created_at          datetime(6)  null,
    updated_at          datetime(6)  null,
    description         varchar(255) null,
    permalink           varchar(255) null,
    published           bit          not null,
    shufflable          bit          not null,
    title               varchar(255) not null,
    created_by_id       int          null,
    last_modified_by_id int          null,
--     constraint FKjdf2822csq8qv3rbiywwayrn6
    foreign key (last_modified_by_id) references user (id),
--     constraint FKs8lo0w5qbupnrp6y8i6hrdqb8
    foreign key (created_by_id) references user (id) on delete cascade
);

-- auto-generated definition
-- Question
create table if not exists question (
    id                  int auto_increment
        primary key,
    created_at          datetime(6)  null,
    updated_at          datetime(6)  null,
    multi               bit          not null,
    text                varchar(255) not null,
    created_by_id       int          null,
    last_modified_by_id int          null,
    quiz_id             int          null,
--     constraint FK2lg4o3vff5f3s5fsibw9ei82v
    foreign key (last_modified_by_id) references user (id),
--     constraint FKb0yh0c1qaxfwlcnwo9dms2txf
    foreign key (quiz_id) references quiz (id) on delete cascade,
--     constraint FKehgqq6539br96ih88in0y2two
    foreign key (created_by_id) references user (id)
);

-- auto-generated definition
-- Choice
create table if not exists choice (
    id          int auto_increment primary key,
    answer      bit          not null,
    text        varchar(255) not null,
    question_id int          null,
--     constraint FKcaq6r76cswke5b9fk6fyx3y5w
    foreign key (question_id) references question (id) on delete cascade
);

