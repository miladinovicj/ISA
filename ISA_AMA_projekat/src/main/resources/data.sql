delete from hotel;
delete from korisnik;
delete from aviokompanija_brzi_letovi;
delete from aviokompanija;
delete from let;

insert into hotel (naziv, adresa, promotivni_opis, prosecna_ocena) values ('Hotel Park', 'Novosadskog sajma 35 Novi Sad', 'hotel sa 4 zvezdice', 4.8);
insert into hotel (naziv, adresa, promotivni_opis, prosecna_ocena) values ('Hotel Novi Sad', 'Jase Tomica 2 Novi Sad', 'fantastican hotel', 3);
insert into hotel (naziv, adresa, promotivni_opis, prosecna_ocena) values ('Hotel Moskva', 'Futoški put bb Novi Sad', 'prefekcija', 5);
insert into korisnik (aktiviran, bonuspoeni, email, grad, ime, lozinka, prezime, telefon) values (true, 0, 'krsmanovicc.aleksa@gmail.com', 'Savac', 'Aleksa', '12345', 'Krsmanovic', '1234567');
insert into aviokompanija(id,adresa, info, max_kapacitet, naziv, ocena, opis) values 
(1, 'Tolstojeva 66, Novi Sad','Najjaci popusti najjace zezanje drugari',300, 'CoaAir', null, '20kg rucna torba, kofer 100kg drugari');
insert into let(id,cena,dokle,odakle,popust,trajanje,udaljenost,vreme_poletanja,vreme_sletanja,aviokompanija_id) values (1,1000,'Tokyo - JPN', 'Belgrade - SRB', 0, 15, 60000, '2018-12-30 23:59:59', '2018-12-31 23:59:59',1);
insert into let(id,cena,dokle,odakle,popust,trajanje,udaljenost,vreme_poletanja,vreme_sletanja,aviokompanija_id) values (2,1000,'Palermo - ITA', 'Belgrade - SRB', 0, 15, 60000, '2018-12-30 23:59:59', '2018-12-31 23:59:59',1);
insert into aviokompanija_brzi_letovi(aviokompanija_id,brzi_letovi_id) values(1,1);
insert into hotel (naziv, adresa, promotivni_opis, prosecna_ocena) values ('Hotel Stari krovovi', 'Futoški put bb Novi Sad', 'prefekcija', 5);
insert into hotel (naziv, adresa, promotivni_opis, prosecna_ocena) values ('Hotel neki', 'Futoški put bb Novi Sad', 'prefekcija', 5);
insert into hotel (naziv, adresa, promotivni_opis, prosecna_ocena) values ('Hotel neki 2', 'Futoški put bb Novi Sad', 'prefekcija', 5);
insert into hotel (naziv, adresa, promotivni_opis, prosecna_ocena) values ('Hotel neki 3', 'Futoški put bb Novi Sad', 'prefekcija', 5);
insert into hotel (naziv, adresa, promotivni_opis, prosecna_ocena) values ('Hotel neki 3', 'Futoški put bb Novi Sad', 'prefekcija', 5);
