create schema conferencedb collate utf8mb4_0900_ai_ci;

create table address
(
    id int auto_increment
        primary key,
    city char(30) not null,
    street char(50) not null,
    building char(10) not null,
    room char(5) not null
);

create table positions
(
    id int auto_increment
        primary key,
    position char(50) null
);

create table users
(
    id int auto_increment
        primary key,
    name char(50) not null,
    surname char(50) not null,
    email char(100) not null,
    password char(50) not null,
    position int null,
    constraint users_ibfk_1
        foreign key (position) references positions (id)
            on update cascade on delete set null
);

create table speakerratings
(
    speakerId int not null
        primary key,
    rating int default 0 null,
    constraint speakerratings_ibfk_1
        foreign key (speakerId) references users (id)
            on update cascade on delete cascade
);

create table reports
(
    id int auto_increment
        primary key,
    name char(255) not null,
    addressId int null,
    date date null,
    time time null,
    speakerId int null,
    constraint reports_ibfk_1
        foreign key (speakerId) references speakerratings (speakerId)
            on update cascade on delete set null,
    constraint reports_ibfk_2
        foreign key (addressId) references address (id)
            on update cascade on delete set null
);

create table presence
(
    id int auto_increment
        primary key,
    reportId int not null,
    count int null,
    constraint presence_ibfk_1
        foreign key (reportId) references reports (id)
            on update cascade on delete cascade
);

create index reportId
    on presence (reportId);

create table registeredlist
(
    reportId int not null,
    userid int not null,
    constraint registeredlist_ibfk_1
        foreign key (reportId) references reports (id)
            on update cascade on delete cascade,
    constraint registeredlist_ibfk_2
        foreign key (userid) references users (id)
            on update cascade on delete cascade
);

create index reportId
    on registeredlist (reportId);

create index userid
    on registeredlist (userid);

create index addressId
    on reports (addressId);

create index speakerId
    on reports (speakerId);

create index position
    on users (position);

