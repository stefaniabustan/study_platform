create database if not exists proiect;
use proiect;

-- 0 admin / 1 profesor / 2 student
Drop table if exists utilizator;
create table if not exists utilizator(
id int primary key auto_increment not null,
cnp varchar(10) unique,
nume varchar(20),
prenume varchar(20),
adresa varchar(70),
telefon varchar(12),
mail varchar(30) not null unique,
parola varchar(30) not null,
cont_iban varchar(50),
nr_contract  int not null,
functie int
);

Drop table if exists student;
create table if not exists student(
id_utilizator int not null,
nr_ore int,
an_studiu int,
foreign key (id_utilizator) references utilizator(id) ON DELETE CASCADE,
primary key(id_utilizator)
);

Drop table if exists profesor;
create table if not exists profesor(
id_utilizator int not null,
nr_min_ore int,
nr_max_ore int,
departament varchar(20),
foreign key (id_utilizator) references utilizator(id) ON DELETE CASCADE,
primary key(id_utilizator)
);


drop table if exists administrator;
create table if not exists administrator(
id_utilizator int not null,
foreign key (id_utilizator) references utilizator(id) ON DELETE CASCADE,
primary key(id_utilizator)
);

drop table if exists curs;
create table if not exists curs(
id_curs int primary key auto_increment not null,
nr_max_studenti int,
nume varchar(20) unique,
descriere varchar(80)
);

drop table if exists sustine_curs;
create table if not exists sustine_curs(
id_curs int,
id_profesor int,
procente_seminar int,
procente_curs int,
procente_examen int,
procente_laborator int,
procente_colocviu int,
foreign key (id_curs) references curs(id_curs) ON DELETE CASCADE,
foreign key (id_profesor) references profesor(id_utilizator) ON DELETE CASCADE,
primary key(id_curs,id_profesor)
);

drop table if exists inscris_curs;
create table if not exists inscris_curs(
id_curs int,
id_profesor int,
id_student int,
nota_curs int default(0) ,
nota_laborator int default(0),
nota_seminar int default(0),
nota_colocviu int default(0),
nota_examen int default(0),
foreign key (id_curs) references curs(id_curs) ON DELETE CASCADE,
foreign key (id_student) references student(id_utilizator) ON DELETE CASCADE,
primary key(id_curs,id_student)
);

-- tip activitate: curs-0 /  seminar -1 / laborator-2 / colocviu-3  /examem -4
drop table if exists activitate;
create table if not exists activitate(
id int primary key auto_increment not null,
ora_finalizare time,
ora_incepere time,
id_curs int,
id_profesor int,
nr_max_studenti int,
tip_ativitate int, 
data_activitate date,
foreign key (id_curs) references curs(id_curs) ON DELETE CASCADE,
foreign key (id_profesor) references profesor(id_utilizator) ON DELETE CASCADE
);

drop table if exists participa_activitate;
create table if not exists participa_activitate(
id_student int,
id_activitate int,
foreign key (id_student) references student(id_utilizator) ON DELETE CASCADE,
foreign key (id_activitate) references activitate(id) ON DELETE CASCADE,
primary key(id_student, id_activitate)
);

drop table if exists grup;
create table if not exists grup(
id int primary key auto_increment not null,
id_curs int not null, 
nume varchar(30),
foreign key (id_curs) references curs(id_curs) ON DELETE CASCADE
);

drop table if exists inscris_grup;
create table if not exists inscris_grup(
id_grup int,
id_student int,
foreign key (id_grup) references grup(id) ON DELETE CASCADE,
foreign key (id_student) references student(id_utilizator) ON DELETE CASCADE,
primary key(id_grup,id_student)
);

drop table if exists mesaj;
create table if not exists mesaj(
id_grup int,
id_student int,
mesaj varchar(100),
data_mesaj date,
ora_mesaj time,
foreign key (id_grup) references grup(id) ON DELETE CASCADE,
foreign key (id_student) references student(id_utilizator),
primary key(id_grup,id_student, ora_mesaj, data_mesaj)
);

drop table if exists activitate_grup;
create table if not exists activitate_grup(
id int primary key auto_increment not null,
id_grup int,
durata time,
id_profesor int default 0,
data_creare date,
ora_creare time,
data_incepere date,
ora_inceput time,
durata_expirare int not null,
nr_min_participanti int,
foreign key (id_grup) references grup(id) ON DELETE CASCADE
);

drop table if exists participa_activitate_grup;
create table if not exists participa_activitate_grup(
id_student int,
id_activitate_grup int,
foreign key (id_student) references student(id_utilizator) ON DELETE CASCADE,
foreign key (id_activitate_grup) references activitate_grup(id) ON DELETE CASCADE,
primary key(id_student, id_activitate_grup)
);