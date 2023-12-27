create table students (
	id int auto_increment,
	fio varchar(100) not null,
	email varchar(100) not null,
	phone_number varchar(20) not null,
	university_id int,
	primary key(id),
    constraint students_uindex
        unique (fio, email, phone_number)
);