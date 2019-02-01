delete from rating;
delete from user_authority;
delete from AUTHORITY;
delete from korisnik;
delete from aviokompanija_brzi_letovi;
delete from let;
delete from aviokompanija;

delete from vozilo_rezervacije;
delete from rezervacija_vozila;
delete from vozilo_popusti;
delete from rentacar_servis_usluge;
delete from vozilo;
delete from filijala;
delete from rentacar_servis;

delete from soba_rezervacije;
delete from rezervacija_hotel_usluge;
delete from rezervacija_hotel;
delete from hotel_usluge;
delete from popust_usluge;
delete from usluga;

delete from soba_popusti;
delete from popust_usluge;
delete from usluga;
delete from popust;
delete from soba;
delete from hotel;

delete from adresa;
delete from grad;

insert into grad (id, naziv) values (1, 'Novi Sad');
insert into grad (id, naziv) values (2, 'Beograd');
insert into grad (id, naziv) values (3, 'Stepanovicevo');
insert into grad (id, naziv) values (4, 'Ruma');
insert into grad (id, naziv) values (5, 'Sabac');
insert into grad (id, naziv) values (6, 'Zrenjanin');

insert into korisnik (id, email, lozinka, ime, prezime, grad_id, telefon, bonuspoeni, aktiviran) values (1, 'andrijana.jeremi@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Andrijana', 'Jeremic', 4, '062522006', 0, true);
insert into AUTHORITY (id, name) values (1, 'ROLE_USER');
insert into user_authority (user_id, authority_id) values (1, 1);

insert into adresa (id, grad_id, ulica, broj, latitude, longitude) values (1, 1, 'Novosadskog sajma', '35', 45.252529, 19.826855);
insert into adresa (id, grad_id, ulica, broj, latitude, longitude) values (2, 1, 'Bulevar Jase Tomica', '1', 45.263826, 19.824309);
insert into adresa (id, grad_id, ulica, broj, latitude, longitude) values (3, 1, 'Novosadski put', '115', 45.244017, 19.781183);
insert into adresa (id, grad_id, ulica, broj, latitude, longitude) values (4, 1, 'Zmaj Jovina', '3', 45.255903, 19.846423);
insert into adresa (id, grad_id, ulica, broj, latitude, longitude) values (5, 3, 'Njegoseva', '46', 45.413690, 19.700000);
insert into adresa (id, grad_id, ulica, broj, latitude, longitude) values (6, 1, 'Sentandrejski put', '165', 45.310406, 19.825943);
insert into adresa (id, grad_id, ulica, broj, latitude, longitude) values (7, 1, 'Tolstojeva', '66', 45.242086, 19.831200);
insert into adresa (id, grad_id, ulica, broj, latitude, longitude) values (8, 1, 'Vojvode Supljikca', '20', 45.260170, 19.839039);
insert into adresa (id, grad_id, ulica, broj, latitude, longitude) values (9, 1, 'Novosadski put', '19', 45.247340, 19.766725);
insert into adresa (id, grad_id, ulica, broj, latitude, longitude) values (10, 2, 'Bulevar Mihajla Pupina', '52', 44.833884, 20.410505);
insert into adresa (id, grad_id, ulica, broj, latitude, longitude) values (11, 1, 'Janka Cmelika', '20', 45.256904, 19.814087);
insert into adresa (id, grad_id, ulica, broj, latitude, longitude) values (12, 1, 'Mise Dimitrijevica', '55', 45.243127, 19.830059);
insert into adresa (id, grad_id, ulica, broj, latitude, longitude) values (13, 6, 'Puskinova', '15', 45.378302, 20.366988);
insert into adresa (id, grad_id, ulica, broj, latitude, longitude) values (14, 2, 'Bulevar kralja Aleksandra', '56', 44.807612, 20.470238);

insert into hotel (id, naziv, adresa_id, promotivni_opis, prosecna_ocena) values (1, 'Hotel Park', 1, 'hotel sa 4 zvezdice', 4.8);
insert into hotel (id, naziv, adresa_id, promotivni_opis, prosecna_ocena) values (2, 'Hotel Novi Sad', 2, 'fantastican hotel', 3.79);
insert into hotel (id, naziv, adresa_id, promotivni_opis, prosecna_ocena) values (3, 'Hotel Moskva', 3, 'Svaka soba ima terasu.', 5);
insert into hotel (id, naziv, adresa_id, promotivni_opis, prosecna_ocena) values (4, 'Hotel Stari krovovi', 3, 'Potpuno nov hotel sa svim mogucim uslugama.', 5);
insert into hotel (id, naziv, adresa_id, promotivni_opis, prosecna_ocena) values (5, 'Grand hotel', 4, 'Hotel u srcu Novog Sada', 3.87);
insert into hotel (id, naziv, adresa_id, promotivni_opis, prosecna_ocena) values (6, 'Rezidencija Makarica', 5, 'Luksuzna vila sa 4 apartmana. Svaki apartman poseduje tv, klima uredjaj, mini bar, terasu, kupatilo. Odlican Wi-Fi signal. Prostrano dvoriste i obezbedjen parking za goste. Najupecatljiviji utisak ostavljaju ljubaznost i gostoprimljivost domacina.', 4.99);
insert into hotel (id, naziv, adresa_id, promotivni_opis, prosecna_ocena) values (7, 'Sole mio', 6, 'Hotel sa tradicijom dugom 78 godina.', 4.5);

insert into aviokompanija(id, adresa_id, opis, max_kapacitet, naziv, ocena, info) values (1, 7,'Najjaci popusti najjace zezanje drugari',300, 'CoaAir', null, '20kg rucna torba, kofer 100kg drugari');

insert into let(id,cena,dokle,odakle,popust,trajanje,udaljenost,vreme_poletanja,vreme_sletanja, aviokompanija_id) values (6,750,'Tokyo - JPN', 'Belgrade - SRB', 10, 15, 15000,'2018-12-30 11:59:59.999999','2018-12-31 23:59:59.999999',1);
insert into let(id,cena,dokle,odakle,popust,trajanje,udaljenost,vreme_poletanja,vreme_sletanja, aviokompanija_id) values (1,500,'Tokyo - JPN', 'Belgrade - SRB', 10, 15, 15000,'2018-12-30 11:59:59.999999','2018-12-31 23:59:59.999999',1);
insert into let(id,cena,dokle,odakle,popust,trajanje,udaljenost,vreme_poletanja,vreme_sletanja, aviokompanija_id) values (5,5000,'Tokyo - JPN', 'Belgrade - SRB', 10, 15, 15000,'2018-12-30 11:59:59.999999','2018-12-31 23:59:59.999999',1);
insert into let(id,cena,dokle,odakle,popust,trajanje,udaljenost,vreme_poletanja,vreme_sletanja, aviokompanija_id) values (2,250,'Milano - ITA', 'Belgrade - SRB', 10, 15, 15000,'2018-12-30 11:59:59.999999','2018-12-31 23:59:59.999999',1);
insert into let(id,cena,dokle,odakle,popust,trajanje,udaljenost,vreme_poletanja,vreme_sletanja, aviokompanija_id) values (3,500,'Washington DC - USA', ' Nis - SRB', 0, 15, 15000,'2019-1-15 02:59:59.999999','2019-1-15 23:59:59.999999',1);
insert into let(id,cena,dokle,odakle,popust,trajanje,udaljenost,vreme_poletanja,vreme_sletanja, aviokompanija_id) values (4,500,'Madrid - SPN', 'Podgorica - MNG', 0, 15, 15000,'2018-12-25 3:00:00.999999','2018-12-25 6:00:00.999999',1);

insert into aviokompanija_brzi_letovi(aviokompanija_id, brzi_letovi_id) values (1,1);
insert into aviokompanija_brzi_letovi(aviokompanija_id, brzi_letovi_id) values (1,2);

insert into soba (id, prosecna_ocena, cena_nocenja, broj_kreveta, hotel_id, zauzeta, opis, cena_popust) values (1, 4.8, 120, 3, 6, false, 'Nalazi se na prvom spratu.\r\nTip: apartman.', 0);
insert into soba (id, prosecna_ocena, cena_nocenja, broj_kreveta, hotel_id, zauzeta, opis, cena_popust) values (2, 3.77, 53, 2, 6, false, 'Nalazi se na drugom spratu.\r\nTip: apartman.', 0);
insert into soba (id, prosecna_ocena, cena_nocenja, broj_kreveta, hotel_id, zauzeta, opis, cena_popust) values (3, 4.8, 120.5, 3, 1, false, 'Nalazi se na prvom spratu.\r\nTip: apartman.', 0);

insert into usluga (id, naziv, cena) values (1, 'air-conditioner', 5);
insert into usluga (id, naziv, cena) values (2, 'Wi-Fi', 2);
insert into usluga (id, naziv, cena) values (3, 'swimming pool', 3);
insert into usluga (id, naziv, cena) values (4, 'gym', 2);
insert into usluga (id, naziv, cena) values (5, 'sauna', 4);
insert into usluga (id, naziv, cena) values (6, 'driver', 17);
insert into usluga (id, naziv, cena) values (7, 'radio', 3);
insert into usluga (id, naziv, cena) values (8, 'navigation', 7);
insert into usluga (id, naziv, cena) values (9, 'anti-lock braking system', 12);
insert into usluga (id, naziv, cena) values (10, 'automatic car parking system', 8);
insert into usluga (id, naziv, cena) values (11, 'car video monitor', 10);

insert into hotel_usluge (hotel_id, usluge_id) values (6, 1);
insert into hotel_usluge (hotel_id, usluge_id) values (6, 2);
insert into hotel_usluge (hotel_id, usluge_id) values (6, 3);

insert into rezervacija_hotel (id, datum_dolaska, datum_odlaska, soba_id, broj_nocenja, brza, ukupna_cena, aktivirana, popust) values (1, '2018-12-25', '2018-12-26', 1, 2, false, 10, true, 0);
insert into rezervacija_hotel (id, datum_dolaska, datum_odlaska, soba_id, broj_nocenja, brza, ukupna_cena, aktivirana, popust) values (2, '2018-12-25', '2018-12-27', 3, 3, false, 10, true, 0);
insert into rezervacija_hotel (id, datum_dolaska, datum_odlaska, soba_id, broj_nocenja, brza, ukupna_cena, aktivirana, popust) values (3, '2018-12-20', '2018-12-23', 3, 4, false, 10, true, 0);

insert into soba_rezervacije (soba_id, rezervacije_id) values (1, 1);
insert into soba_rezervacije (soba_id, rezervacije_id) values (3, 2);
insert into soba_rezervacije (soba_id, rezervacije_id) values (3, 3);

insert into rentacar_servis (id, naziv, adresa_id, promotivni_opis, prosecna_ocena) values (1, 'Angel', 8, 'Mlada firma sa velikom vizijom. Iznajmljivanje automobila sa i bez vozaca.', 3.2);
insert into rentacar_servis (id, naziv, adresa_id, promotivni_opis, prosecna_ocena) values (2, 'Cartize Company', 9, 'Najpovoljnije cene rentiranja automobila u Novom Sadu', 4.8);
insert into rentacar_servis (id, naziv, adresa_id, promotivni_opis, prosecna_ocena) values (3, 'Stars', 10, 'U Beogradu svi iznajmljuju automobile od nas.', 4);

insert into filijala (id, adresa_id, rentacar_id) values (1, 11, 1);
insert into filijala (id, adresa_id, rentacar_id) values (2, 12, 1);
insert into filijala (id, adresa_id, rentacar_id) values (3, 13, 1);
insert into filijala (id, adresa_id, rentacar_id) values (4, 14, 3);

insert into vozilo (id, naziv, marka, model, godina_proizvodnje, broj_sedista, tip, cena_dan, prosecna_ocena, filijala_id, cena_popust, zauzeto) values (1, 'SportTourer', 'Opel', 'Astra', 2011, 5, 'Executive car', 400,4.5, 1 , 0, false);
insert into vozilo (id, naziv, marka, model, godina_proizvodnje, broj_sedista, tip, cena_dan, prosecna_ocena, filijala_id, cena_popust, zauzeto) values (3, 'Comfortline', 'Volkswagen', 'Polo', 2015, 5, 'City car', 300,4.7, 1 , 0, false);
insert into vozilo (id, naziv, marka, model, godina_proizvodnje, broj_sedista, tip, cena_dan, prosecna_ocena, filijala_id, cena_popust, zauzeto) values (2, '70 Trend', 'Ford', 'Fiesta', 2018, 5, 'Supermini', 280, 4, 4 , 0, false);
insert into vozilo (id, naziv, marka, model, godina_proizvodnje, broj_sedista, tip, cena_dan, prosecna_ocena, filijala_id, cena_popust, zauzeto) values (4, 'S', 'Porche', 'Macan', 2019, 5, 'City car', 500, 4.8, 2 , 0, false);
insert into vozilo (id, naziv, marka, model, godina_proizvodnje, broj_sedista, tip, cena_dan, prosecna_ocena, filijala_id, cena_popust, zauzeto) values (5, 'C 1.4', 'Opel', 'Corsa', 1999, 5, 'Comfort', 210, 3, 3 , 0, false);



insert into rentacar_servis_usluge (rentacar_servis_id, usluge_id) values (1, 6);
insert into rentacar_servis_usluge (rentacar_servis_id, usluge_id) values (1, 7);
insert into rentacar_servis_usluge (rentacar_servis_id, usluge_id) values (1, 8);
insert into rentacar_servis_usluge (rentacar_servis_id, usluge_id) values (1, 9);
insert into rentacar_servis_usluge (rentacar_servis_id, usluge_id) values (1, 10);
insert into rentacar_servis_usluge (rentacar_servis_id, usluge_id) values (1, 11);

insert into rentacar_servis_usluge (rentacar_servis_id, usluge_id) values (2, 6);
insert into rentacar_servis_usluge (rentacar_servis_id, usluge_id) values (2, 8);

insert into rentacar_servis_usluge (rentacar_servis_id, usluge_id) values (3, 11);
insert into rentacar_servis_usluge (rentacar_servis_id, usluge_id) values (3, 6);
insert into rentacar_servis_usluge (rentacar_servis_id, usluge_id) values (3, 9);

insert into popust (id, pocetak_vazenja, kraj_vazenja, popust) values (2, '2019-01-25', '2019-01-27', 30);
insert into popust (id, pocetak_vazenja, kraj_vazenja, popust) values (1, '2019-01-06', '2019-01-08', 20);
insert into popust (id, pocetak_vazenja, kraj_vazenja, popust) values (3, '2019-01-24', '2019-01-30', 25);

insert into soba_popusti (soba_id, popusti_id) values (1, 1);
insert into soba_popusti (soba_id, popusti_id) values (1, 2);
insert into vozilo_popusti (vozilo_id, popusti_id) values (3, 3);

insert into popust_usluge (popust_id, usluge_id) values (2, 1);
insert into popust_usluge (popust_id, usluge_id) values (2, 3);
insert into popust_usluge (popust_id, usluge_id) values (2, 4);
insert into popust_usluge (popust_id, usluge_id) values (1, 1);
insert into popust_usluge (popust_id, usluge_id) values (1, 2);
insert into popust_usluge (popust_id, usluge_id) values (1, 4);
insert into popust_usluge (popust_id, usluge_id) values (1, 5);

insert into popust_usluge (popust_id, usluge_id) values (3, 8);
insert into popust_usluge (popust_id, usluge_id) values (3, 10);

insert into korisnik (id, email, lozinka, ime, prezime, grad_id, telefon, bonuspoeni, aktiviran, admin_id) values (2, 'mojmejl@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Endzi', 'Jeremic', 1, '062522006', 0, true, 1);
insert into AUTHORITY (id, name) values (2, 'ROLE_RENTADMIN');
insert into user_authority (user_id, authority_id) values (2, 2);
update rentacar_servis rs set rs.id_admin = 2 where rs.id = 1;

insert into AUTHORITY (id, name) values (3, 'ROLE_HOTELADMIN');
insert into AUTHORITY (id, name) values (4, 'ROLE_AVIOADMIN');
insert into AUTHORITY (id, name) values (5, 'ROLE_SYSADMIN');

insert into korisnik (id, email, lozinka, ime, prezime, grad_id, telefon, bonuspoeni, aktiviran, admin_id) values (3, 'makaric.milica@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Milica', 'Makaric', 3, '0652034133', 0, true, 6);
insert into user_authority (user_id, authority_id) values (3, 3);

