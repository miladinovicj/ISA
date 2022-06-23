
insert into grad (id, naziv, verzija) values (1, 'Grad1', 0);
insert into grad (id, naziv, verzija) values (2, 'Grad2', 0);
insert into grad (id, naziv, verzija) values (3, 'Grad3', 0);
insert into grad (id, naziv, verzija) values (4, 'Grad4', 0);
insert into grad (id, naziv, verzija) values (5, 'Grad5', 0);
insert into grad (id, naziv, verzija) values (6, 'Nis', 0);
insert into grad (id, naziv, verzija) values (7, 'Stepanovicevo', 0);

insert into adresa (id, grad_id, ulica, broj, latitude, longitude, verzija) values (1, 1, 'Ulica1', '1', 1.1, 1.1, 0);
insert into adresa (id, grad_id, ulica, broj, latitude, longitude, verzija) values (2, 2, 'Ulica2', '2', 2.2, 2.2, 0);
insert into adresa (id, grad_id, ulica, broj, latitude, longitude, verzija) values (3, 3, 'Ulica3', '3', 3.3, 3.3, 0);
insert into adresa (id, grad_id, ulica, broj, latitude, longitude, verzija) values (4, 4, 'Ulica4', '4', 4.4, 4.4, 0);
insert into adresa (id, grad_id, ulica, broj, latitude, longitude, verzija) values (5, 5, 'Ulica5', '5', 5.5, 5.5, 0);
insert into adresa (id, grad_id, ulica, broj, latitude, longitude, verzija) values (6, 7, 'Njegoseva', '46', 19.36, 42.58, 0);




insert into AUTHORITY (id, name) values (3, 'ROLE_HOTELADMIN');


insert into korisnik (id, email, lozinka, ime, prezime, grad_id, telefon, bonuspoeni, aktiviran, admin_id, verzija) values (3, 'jefimijamiladinovic2@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Jefimija', 'Miladinovic', 7, '0652034133', 5, false, 3, 0);
insert into user_authority (user_id, authority_id) values (3, 3);
update hotel h set h.id_admin = 3 where h.id = 3;
