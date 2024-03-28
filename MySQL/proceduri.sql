use proiect;
-- -------------------------------------	CURS  ----------------------------------------------------------------- 
DROP PROCEDURE IF EXISTS creare_curs;
DELIMITER //
CREATE PROCEDURE creare_curs(nr_max int,nume varchar(20),descriere  varchar(80))
begin 
insert into curs (`nr_max_studenti`,`nume`,`descriere`) values (nr_max,nume,descriere);
end ;
//
DELIMITER 

DROP PROCEDURE IF EXISTS sterge_curs;
DELIMITER //
CREATE PROCEDURE sterge_curs(nume_curs varchar(20))
begin 
	delete from curs where nume = nume_curs;
end ;
//
DELIMITER ;

DROP PROCEDURE IF EXISTS vizualizare_profesori;
DELIMITER //
CREATE PROCEDURE vizualizare_profesori(id_c int)
begin 
select  utilizator.nume as "Nume" , utilizator.prenume as "Prenume" from utilizator inner join sustine_curs on utilizator.id =sustine_curs.id_profesor
where  sustine_curs.id_curs =id_c;
end ;
//
DELIMITER ;

DROP PROCEDURE IF EXISTS vizualizare_studenti;
DELIMITER //
CREATE PROCEDURE vizualizare_studenti(id_c int)
begin 
select  utilizator.nume as "Nume" , utilizator.prenume as "Prenume" from utilizator inner join inscris_curs on utilizator.id =inscris_curs.id_student
where  inscris_curs.id_curs =id_c;
end ;
//
DELIMITER ;
-- -------------------------------------	GRUP  ----------------------------------------------------------------- 

DROP PROCEDURE IF EXISTS creare_grup;
DELIMITER //
CREATE PROCEDURE creare_grup(nume_grup varchar(20),nume_curs varchar(20))
  begin
	set @id = 0;
    select @id := id_curs from curs where nume = nume_curs;
	Insert into grup(`nume`,`id_curs`) values (nume_grup,@id);
  END;
// DELIMITER ; 

DROP PROCEDURE IF EXISTS sterge_grup;
DELIMITER //
CREATE PROCEDURE sterge_grup(nume_grup varchar(20))
begin 
	delete from grup where nume = nume_grup;
end ;
//
DELIMITER ;

DROP PROCEDURE IF EXISTS membrii_grup;
DELIMITER //
CREATE procedure membrii_grup(id_grupp int)
begin
	select utilizator.nume as 'nume', utilizator.prenume as 'prenume' from inscris_grup inner join utilizator on inscris_grup.id_student = utilizator.id 
    where id_grupp = inscris_grup.id_grup; 
end;
//
DELIMITER ;

-- -------------------------------------	CONT  ----------------------------------------------------------------- 

-- nr_min : se ignora la admin / nr minim ore la profesor / nr ore la student
-- nr_max : se ignora la admin / nr maxim ore la profesor / an studiu la student
-- depart : se ignora la admin / departament la profesor / se ignora la admin
DROP PROCEDURE IF EXISTS creare_cont;
DELIMITER //
CREATE PROCEDURE creare_cont(
	cnp varchar(10),
	nume varchar(20),
	prenume varchar(20),
	adresa varchar(70),
	telefon varchar(12),
	mail varchar(30),
	parola varchar(30),
	cont_iban varchar(50),
	nr_contract  int,
	functie int,
    nr_min int,
    nr_max int,
    depart varchar(20)
)
begin 
	Insert into utilizator(`cnp`,`nume`,`prenume`,`adresa`,`telefon`,`mail`,`parola`,`cont_iban`,`nr_contract`,`functie`)
		values (cnp,nume,prenume,adresa,telefon,mail,parola,cont_iban,nr_contract,functie);
	select @id:=id from utilizator where cnp = cnp;
	if(functie = 0)
		then insert into administrator(`id_utilizator`) values (@id);
		else
			if(functie = 1)
				then 
                insert into profesor(`id_utilizator`,`nr_min_ore`,`nr_max_ore`,`departament`) values (@id,nr_min,nr_max,depart);
                else
				insert into student(`id_utilizator`,`nr_ore`,`an_studiu`) values (@id,nr_min,nr_max);
			end if;
	end if;
end ;
//
DELIMITER 


DROP PROCEDURE IF EXISTS sterge_cont;
DELIMITER //
CREATE PROCEDURE sterge_cont(idd int)
begin 
	delete from utilizator where id = idd;
end ;
//
DELIMITER ;


DROP PROCEDURE IF EXISTS actualizare_cont;
DELIMITER //
CREATE PROCEDURE actualizare_cont(id_cont int,atribut varchar(20),val varchar(60))
BEGIN
	case atribut 
    when 'nume' then update utilizator set `nume`=val where id=id_cont;
    when 'prenume' then update utilizator set `prenume`=val where id=id_cont;
    when 'adresa' then update utilizator set `adresa`=val where id=id_cont;
    when 'telefon' then update utilizator set `telefon`=val where id=id_cont;
    when 'parola' then update utilizator set `parola`=val where id=id_cont;
    when 'cont_iban' then update utilizator set `cont_iban`=val where id=id_cont;
    when 'nr_contract' then update utilizator set `nr_contract`=val where id=id_cont;
    when 'an_studiu'  then update student set `an_studiu`=val where id_utilizator=id_cont;
    when 'nr_ore'  then update student set `nr_ore`=val where id_utilizator=id_cont;
    when 'nr_min_ore'  then update profesor set `nr_min_ore`=val where id_utilizator=id_cont;
    when 'nr_max_ore'  then update profesor set `nr_max_ore`=val where id_utilizator=id_cont;
    when 'departament'  then update profesor set `departament`=val where id_utilizator=id_cont;
	end case;
END;
//
DELIMITER ; 


drop function if exists logare;
DELIMITER //
CREATE FUNCTION logare(
	maill varchar(30),
	parolaa varchar(30)
    )
RETURNS int	
DETERMINISTIC
BEGIN
	declare idd int;
    set idd =-1;
	select id into idd from utilizator where mail = maill and parolaa = parola;
    return idd;
END ;
//
DELIMITER ;


-- -------------------------------------	SUSTINE CURS   ----------------------------------------------------------------- 

DROP PROCEDURE IF EXISTS inserare_sustine_curs;
DELIMITER //
CREATE PROCEDURE inserare_sustine_curs(id_curs int, id_profesor int)
begin 
set @id_c :=id_curs;
set @id_p := id_profesor;
insert into sustine_curs (`id_curs`,`id_profesor`)values (@id_c,@id_p); 

end ;
//
DELIMITER ;

-- facem procedura -1 -> nu sustine; >=0 sustine si se pune la procent valoarea respectiva
DROP PROCEDURE IF EXISTS update_procente_sustine_curs;
DELIMITER //
CREATE PROCEDURE update_procente_sustine_curs(id_c int, id_p int, procente_s int,procente_c int,procente_e int,procente_lab int,procente_col int)
begin 
update sustine_curs set procente_seminar=procente_s where  id_curs=id_c and id_profesor=id_p;
update sustine_curs set procente_curs=procente_c where  id_curs=id_c and id_profesor=id_p;
update sustine_curs set procente_examen=procente_e where  id_curs=id_c and id_profesor=id_p;
update sustine_curs set procente_laborator=procente_lab where  id_curs=id_c and id_profesor=id_p;
update sustine_curs set procente_colocviu=procente_col where id_curs=id_c and id_profesor=id_p;

end ;
//
DELIMITER ;




-- -------------------------------------	INSCRIS CURS   ----------------------------------------------------------------- 

DROP PROCEDURE IF EXISTS inserare_inscris_curs;
DELIMITER //
CREATE PROCEDURE inserare_inscris_curs(id_curss int, id_student int, id_profesor int)
begin 
declare nr_stud int;
declare nr_max int;
select count(*) into nr_stud from inscris_curs where id_curss = id_curs;
select nr_max_studenti into nr_max from curs where id_curss = id_curs;
if(nr_stud<nr_max)then
insert into inscris_curs (`id_curs`,`id_student`,`id_profesor`)values (id_curss,id_student,id_profesor); 
else
select 'curs plin';
end if;
end ;
//
DELIMITER ;

DROP PROCEDURE IF EXISTS renuntare_curs_student;
DELIMITER //
CREATE PROCEDURE renuntare_curs_student(id_s int, id_c int)
begin 
delete from inscris_curs where id_student=id_s and id_curs=id_c;
end ;
//
DELIMITER ;


DROP PROCEDURE IF EXISTS vizualizare_note;
DELIMITER //
CREATE PROCEDURE vizualizare_note(id_s int)
begin 
select  curs.id_curs as "ID",inscris_curs.id_profesor as "ID_PROF",curs.nume as "CURS" , inscris_curs.nota_curs as "Nota curs",inscris_curs.nota_laborator as "Nota laborator", inscris_curs.nota_seminar as "Nota seminar",inscris_curs.nota_colocviu as "Nota colocviu",
inscris_curs.nota_examen  as "Nota examen" from inscris_curs inner join curs using (id_curs) where inscris_curs.id_student = id_s;
end ;
//
DELIMITER ;

DROP PROCEDURE IF EXISTS update_note_inscris_curs;
DELIMITER //
CREATE PROCEDURE update_note_inscris_curs(id_c int, id_s int, id_p int, nota_s int,nota_c int,nota_e int,nota_lab int,nota_col int)
begin 
update inscris_curs set nota_seminar=nota_s where  id_curs=id_c and id_profesor=id_p and id_student=id_s;
update inscris_curs set nota_curs=nota_c where  id_curs=id_c and id_profesor=id_p and id_student=id_s;
update inscris_curs set nota_examen=nota_e where  id_curs=id_c and id_profesor=id_p and id_student=id_s;
update inscris_curs set nota_laborator=nota_lab where  id_curs=id_c and id_profesor=id_p and id_student=id_s;
update inscris_curs set nota_colocviu=nota_col where  id_curs=id_c and id_profesor=id_p and id_student=id_s;
end ;
//
DELIMITER ;

-- -------------------------------------  ACTIVITATE  ----------------------------------------------------------------- 


-- tip activitate: curs-0 /  seminar -1 / laborator-2 / colocviu-3  /examem -4
DROP PROCEDURE IF EXISTS creare_activitate;
DELIMITER //
CREATE PROCEDURE creare_activitate(ora_fin time,ora_inc time,id_curss int,id_prof int,nr_max int,tip_act int,dataa date)
BEGIN
	Insert into activitate(`ora_finalizare`,`ora_incepere`,`id_curs`,`id_profesor`,`nr_max_studenti`,`tip_ativitate`,`data_activitate`)
	values(ora_fin,ora_inc,id_curss,id_prof,nr_max,tip_act,dataa);
END;
//
DELIMITER ;

DROP PROCEDURE IF EXISTS sterge_activitate;
DELIMITER //
CREATE PROCEDURE sterge_activitate(idd int)
BEGIN
	delete from activitate where id = idd;
END;
//
DELIMITER ;

-- -------------------------------------  PARTICIPA ACTIVITATE  ----------------------------------------------------------------- 


DROP procedure IF EXISTS participare_activitate;
DELIMITER //
CREATE procedure participare_activitate(id_stud int, id_act int)
begin
	declare nr_stud int;
	declare nr_max int;
	select count(*) into nr_stud from participa_activitate where id_activitate = id_act;
	select nr_max_studenti into nr_max from activitate where id = id_act;
    if(nr_stud < nr_max)
    then
		insert into participa_activitate(`id_student`,`id_activitate`)
		values(id_stud,id_act);
	else 
		select 'activitate plina';
	end if;
end;
//
DELIMITER ;

-- -------------------------------------  INSCRIRERE GRUP ----------------------------------------------------------------- 

DROP procedure IF EXISTS inscriere_grup;
DELIMITER //
CREATE procedure inscriere_grup(id_stud int, id_gr int)
begin
	insert into inscris_grup(`id_student`,`id_grup`)
    values(id_stud,id_gr);
end;
//
DELIMITER ;


-- -------------------------------------  MESAJE GRUP ----------------------------------------------------------------- 
DROP PROCEDURE IF EXISTS mesaje_grup;
DELIMITER //
CREATE procedure mesaje_grup(id_grupp int)
begin
	select mesaj.mesaj as 'mesaj',utilizator.nume as 'nume', utilizator.prenume as 'prenume',mesaj.ora_mesaj as 'ora',mesaj.data_mesaj as 'data'  from mesaj inner join utilizator on mesaj.id_student = utilizator.id 
    where id_grupp = mesaj.id_grup; 
end;
//
DELIMITER ;


DROP PROCEDURE IF EXISTS creare_mesaj;
DELIMITER //
CREATE PROCEDURE creare_mesaj(id_stud int,id_grup int, msg varchar(100))
begin
	declare ora time;
	declare dat date;
	select CURRENT_TIME() into  ora;
	select  CURDATE() into  dat;
    insert into mesaj(`id_grup`,`id_student`,`mesaj`,`data_mesaj`,`ora_mesaj`) 
    values(id_grup,id_stud,msg,dat,ora);
end;
//
DELIMITER ;

-- -------------------------------------  ACTIVITATE GRUP ----------------------------------------------------------------- 
DROP PROCEDURE IF EXISTS creare_activitate_grup;
DELIMITER //
CREATE PROCEDURE creare_activitate_grup(id_gr int,durata int,dataa date,ora_inc time,durata_exp int,nr_min int,id_prof int)
begin
	declare ora time;
	declare dat date;
	select CURRENT_TIME() into  ora;
	select  CURDATE() into  dat;
    insert into activitate_grup(`id_grup`,`durata`,`data_creare`,`ora_creare`,`data_incepere`,`ora_inceput`,`durata_expirare`,`nr_min_participanti`,`id_profesor`)
    values(id_gr,durata,dat,ora,dataa,ora_inc,durata_exp+hour(ora),nr_min,id_prof);
end;
//
DELIMITER ;

-- ------------------------------------- PARTICIPA ACTIVITATE GRUP ----------------------------------------------------------------- 

DROP procedure IF EXISTS participare_activitate_grup;
DELIMITER //
CREATE procedure participare_activitate_grup(id_stud int, id_act int)
begin

		insert into participa_activitate_grup(`id_student`,`id_activitate_grup`)
		values(id_stud,id_act);
end;
//
DELIMITER ;

drop procedure sterge_activitate_grup;
DELIMITER //
create procedure sterge_activitate_grup(id_act int)
begin
	declare ora time;
	declare durata int;
    declare dat date;
    declare nr_st int;
    declare nr_min int;
    select count(*) into nr_st from participa_activitate_grup where id_act=id_activitate_grup;
    select nr_min_participanti into nr_min from activitate_grup where id_act=id;
    select ora_creare into ora from activitate_grup where id_act = id;
    select data_creare into dat from activitate_grup where id_act = id;
    select durata_expirare into durata from activitate_grup where id_act = id;
    if(datediff(CURDATE(),dat) > durata/24 ) then
		if(nr_st < nr_min)
			then 
            delete from activitate_grup where id = id_act;
		end if;
        else if(datediff(CURDATE(),dat) = mod(durata,24) and  (hour(ora) -  mod(durata,24)>0) )
				then
					if(nr_st < nr_min)
						then 
						delete from activitate_grup where id = id_act;
					end if;	
				end if;
	end if;
end;
//
DELIMITER ;


drop procedure if exists actualizare_activitati;
DELIMITER //
create procedure actualizare_activitati()
begin 
	  declare done int;
	  DECLARE idd int;
	  DECLARE cur CURSOR FOR SELECT id FROM activitate_grup;
      DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
      OPEN cur;
      label: LOOP
      FETCH cur INTO idd;
      call sterge_activitate_grup(idd);
      IF(done = 1) 
		THEN LEAVE label;
      END IF;
      END LOOP;
      CLOSE cur;

end;
//
DELIMITER ;