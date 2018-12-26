
delete from hotel;
delete from korisnik;
delete from AUTHORITY;
delete from user_authority;
delete from aviokompanija_brzi_letovi;
delete from aviokompanija;
delete from let;
delete from soba;
delete from usluga;
delete from hotel_usluge;

insert into korisnik (id, email, lozinka, ime, prezime, grad, telefon, bonuspoeni, aktiviran) values (1, 'andrijana.jeremi@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Andrijana', 'Jeremic', 'Ruma', '062522006', 0, true);
insert into AUTHORITY (id, name) values (1, 'ROLE_USER');
insert into user_authority (user_id, authority_id) values (1, 1);