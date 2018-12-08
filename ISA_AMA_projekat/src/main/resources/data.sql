delete from hotel;
delete from korisnik;
delete from soba;

insert into hotel (naziv, adresa, promotivni_opis, prosecna_ocena) values ('Hotel Park', 'Novosadskog sajma 35 Novi Sad', 'hotel sa 4 zvezdice', 4.8);
insert into hotel (naziv, adresa, promotivni_opis, prosecna_ocena) values ('Hotel Novi Sad', 'Jase Tomica 2 Novi Sad', 'fantastican hotel', 3);
insert into hotel (naziv, adresa, promotivni_opis, prosecna_ocena) values ('Hotel Moskva', 'Futoški put bb Novi Sad', 'prefekcija', 5);
insert into hotel (naziv, adresa, promotivni_opis, prosecna_ocena) values ('Hotel Stari krovovi', 'Futoški put bb Novi Sad', 'prefekcija', 5);
insert into hotel (naziv, adresa, promotivni_opis, prosecna_ocena) values ('Hotel neki', 'Futoški put bb Novi Sad', 'prefekcija', 5);
insert into hotel (naziv, adresa, promotivni_opis, prosecna_ocena) values ('Hotel neki 2', 'Futoški put bb Novi Sad', 'prefekcija', 5);
insert into hotel (naziv, adresa, promotivni_opis, prosecna_ocena) values ('Hotel neki 3', 'Futoški put bb Novi Sad', 'prefekcija', 5);
insert into hotel (naziv, adresa, promotivni_opis, prosecna_ocena) values ('Hotel neki 3', 'Futoški put bb Novi Sad', 'prefekcija', 5);

insert into korisnik (email, lozinka, ime, prezime, grad, telefon, bonuspoeni, aktiviran) values ('makaric.milica@gmail.com', 'makarica', 'Milica', 'Makaric', 'Stepanovicevo', '0652034133', 0, false);

insert into soba (prosecna_ocena, cena_nocenja, broj_kreveta, brza_soba, popust, hotel_id) values (4.8, 120.5, 3, false, 20.5, 133);