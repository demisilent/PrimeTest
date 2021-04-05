
create table users_list
(
	u_id int not null
		constraint users_pk
			primary key,
	u_name varchar(20),
	u_password varchar(130),
	u_role varchar(20),
	u_birthday varchar(20)
);

create table persons_list
(
	p_id serial not null
		constraint persons_list_pk
			primary key,
	p_name varchar(20),
	p_second_name varchar(20),
	p_surname varchar(20),
	p_mid int,
	c_adress varchar(100)
);

create table contact_card
(
	cc_mid int4,
	cc_communication varchar(20),
	cc_data varchar(20),
	cc_id serial not null
		constraint contact_card_pk
			primary key
);

commit;

alter table persons_list
	add constraint persons_list_users__fk
		foreign key (p_mid) references users_list(u_id);

alter table contact_card
	add constraint contact_card_persons_list__fk
		foreign key (cc_mid) references persons_list(p_id);

commit;