create table university (
	id int not null,
	name varchar(100) not null,
	constraint university_uindex
        unique (id, name)
);