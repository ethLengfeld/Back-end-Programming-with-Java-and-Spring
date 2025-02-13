create table PATIENTS (
	id SERIAL primary key,
	family varchar(256),
	given varchar(256),
	dob date,
	sex char(1),
	address varchar(256),
	phone varchar(256)
);
