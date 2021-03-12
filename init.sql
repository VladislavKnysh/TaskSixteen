create table contact_type(
contact_type_id serial primary key,
contact_type_name varchar(10) not null unique
);

create table contacts_users(
contacts_user_id serial primary key,
contacts_user_login varchar(100) unique,
contacts_user_password varchar(100),
contacts_user_dateborn varchar(20)

);

create table contacts(
contacts_id serial  primary key,
contact_name varchar(255) not null,
contact_info varchar(100) not null,
contact_type int not null,
contact_user_id int not null,
constraint contacts_contact_type_fk foreign key (contact_type) references contact_type(contact_type_id),
constraint contacts_contact_user_id_fk foreign key (contact_user_id) references contacts_users(contacts_user_id)
) ;

 SELECT contact_name, contact_info, contact_type_name from contacts c inner join contact_type ct on  c.contact_type = ct.contact_type_id;

SELECT contacts_user_login,contacts_user_dateborn FROM contacts_users WHERE contacts_user_login = 'vlad' AND contacts_user_password = '123';

insert into contact_type (contact_type_name) values ('phone');
insert into contact_type (contact_type_name) values ('email');

SELECT contact_name, contact_info, contact_type_name FROM contacts c inner join contact_type ct on c.contact_type = ct.contact_type_id WHERE contact_user_id = (SELECT contacts_user_id FROM contacts_users WHERE contacts_user_login =  ?) AND contact_name ILIKE '%kir%';


insert into contacts_users(contacts_user_login, contacts_user_password, contacts_user_dateborn)
values ('default_user','password','2021-03-10');