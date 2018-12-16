delete from hotel;
delete from korisnik;
delete from aviokompanija_brzi_letovi;
delete from aviokompanija;
delete from let;
delete from soba;
delete from usluga;
delete from hotel_usluge;

insert into hotel (naziv, adresa, promotivni_opis, prosecna_ocena) values ('Hotel Park', 'Novosadskog sajma 35 Novi Sad', 'hotel sa 4 zvezdice', 4.8);
insert into hotel (naziv, adresa, promotivni_opis, prosecna_ocena) values ('Hotel Novi Sad', 'Jase Tomica 2 Novi Sad', 'fantastican hotel', 3.79);
insert into hotel (naziv, adresa, promotivni_opis, prosecna_ocena) values ('Hotel Moskva', 'Futoški put bb Novi Sad', 'Svaka soba ima terasu.', 5);
insert into hotel (naziv, adresa, promotivni_opis, prosecna_ocena) values ('Hotel Stari krovovi', 'Futoški put bb Novi Sad', 'Potpuno nov hotel sa svim mogućim uslugama.', 5);
insert into hotel (naziv, adresa, promotivni_opis, prosecna_ocena) values ('Grand hotel', 'Zmaj Jovina 3', 'Hotel u srcu Novog Sada', 3.87);
insert into hotel (naziv, adresa, promotivni_opis, prosecna_ocena) values ('Rezidencija Makarića', 'Njegoševa 46 Stepanovićevo', 'Luksuzna vila sa 4 apartmana. Svaki apartman poseduje tv, klima uređaj, mini bar, terasu, kupatilo. Odličan Wi-Fi signal. Prostrano dvorište i obezbeđen parking za goste. Najupečatljiviji utisak ostavljaju ljubaznost i gostoprimljivost domaćina.', 4.99);
insert into hotel (naziv, adresa, promotivni_opis, prosecna_ocena) values ('Sole mio', 'Bulevar oslobođenja 56a', 'Hotel sa tradicijom dugom 78 godina.', 4.5);

insert into korisnik (aktiviran, bonuspoeni, email, grad, ime, lozinka, prezime, telefon) values (true, 0, 'krsmanovicc.aleksa@gmail.com', 'Savac', 'Aleksa', '12345', 'Krsmanovic', '1234567');
insert into korisnik (email, lozinka, ime, prezime, grad, telefon, bonuspoeni, aktiviran) values ('makaric.milica@gmail.com', 'makarica', 'Milica', 'Makaric', 'Stepanovicevo', '0652034133', 0, true);

insert into aviokompanija(id,adresa, info, max_kapacitet, naziv, ocena, opis) values 
(1, 'Tolstojeva 66, Novi Sad','Najjaci popusti najjace zezanje drugari',300, 'CoaAir', null, '20kg rucna torba, kofer 100kg drugari');





insert into let(id,cena,dokle,odakle,popust,trajanje,udaljenost,vreme_poletanja,vreme_sletanja, aviokompanija_id) values (6,750,'Tokyo - JPN', 'Belgrade - SRB', 10, 15, 15000,'2018-12-30 11:59:59.999999','2018-12-31 23:59:59.999999',1);


insert into let(id,cena,dokle,odakle,popust,trajanje,udaljenost,vreme_poletanja,vreme_sletanja, aviokompanija_id) values (1,500,'Tokyo - JPN', 'Belgrade - SRB', 10, 15, 15000,'2018-12-30 11:59:59.999999','2018-12-31 23:59:59.999999',1);

insert into let(id,cena,dokle,odakle,popust,trajanje,udaljenost,vreme_poletanja,vreme_sletanja, aviokompanija_id) values (5,5000,'Tokyo - JPN', 'Belgrade - SRB', 10, 15, 15000,'2018-12-30 11:59:59.999999','2018-12-31 23:59:59.999999',1);

insert into let(id,cena,dokle,odakle,popust,trajanje,udaljenost,vreme_poletanja,vreme_sletanja, aviokompanija_id) values (2,250,'Milano - ITA', 'Belgrade - SRB', 10, 15, 15000,'2018-12-30 11:59:59.999999','2018-12-31 23:59:59.999999',1);

insert into let(id,cena,dokle,odakle,popust,trajanje,udaljenost,vreme_poletanja,vreme_sletanja, aviokompanija_id) values (3,500,'Washington DC - USA', ' Nis - SRB', 0, 15, 15000,'2019-1-15 02:59:59.999999','2019-1-15 23:59:59.999999',1);

insert into let(id,cena,dokle,odakle,popust,trajanje,udaljenost,vreme_poletanja,vreme_sletanja, aviokompanija_id) values (4,500,'Madrid - SPN', 'Podgorica - MNG', 0, 15, 15000,'2018-12-25 3:00:00.999999','2018-12-25 6:00:00.999999',1);


insert into aviokompanija_brzi_letovi(aviokompanija_id, brzi_letovi_id) values (1,1);
insert into aviokompanija_brzi_letovi(aviokompanija_id, brzi_letovi_id) values (1,2);






insert into soba (prosecna_ocena, cena_nocenja, broj_kreveta, brza_soba, popust, hotel_id) values (4.8, 120.5, 3, false, 20.5, 6);
insert into soba (prosecna_ocena, cena_nocenja, broj_kreveta, brza_soba, popust, hotel_id) values (3.77, 53, 2, false, 0, 6);



insert into usluga (naziv, cena) values ('air-conditioner', 5);
insert into usluga (naziv, cena) values ('Wi-Fi', 2);
insert into usluga (naziv, cena) values ('swimming pool', 3);
insert into usluga (naziv, cena) values ('gym', 2);
insert into usluga (naziv, cena) values ('sauna', 4);

insert into hotel_usluge (hotel_id, usluge_id) values (6, 1);
insert into hotel_usluge (hotel_id, usluge_id) values (6, 2);
insert into hotel_usluge (hotel_id, usluge_id) values (6, 3);