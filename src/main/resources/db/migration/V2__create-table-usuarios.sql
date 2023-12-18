create table usuarios(
	id bigint not null auto_increment,
	nome varchar(100) not null,
	login varchar(100) not null,
	senha varchar(100) not null,
	primary key(id)
);

insert into usuarios values(1,'Rafaela Cliente','rafaela', '$2a$10$w0tFdtmW9JKiZqklTQO7FuIzPvkvHOqVuj8Q49rrHdlvVV1qqoTMG')