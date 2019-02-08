insert into hotel (id, naziv, promotivni_opis, prosecna_ocena) values (1, 'Hotel Park', 'hotel sa 4 zvezdice', 4.8);
insert into hotel (id, naziv, promotivni_opis, prosecna_ocena) values (2, 'Hotel Novi Sad', 'fantastican hotel', 3.5);
insert into hotel (id, naziv, promotivni_opis, prosecna_ocena) values (3, 'Hotel Moskva', 'Odlican hotel', 4.8);

insert into AUTHORITY (id, name) values (3, 'ROLE_HOTELADMIN');
insert into grad (id, naziv) values (3, 'Stepanovicevo');

insert into korisnik (id, email, lozinka, ime, prezime, grad_id, telefon, bonuspoeni, aktiviran, admin_id) values (3, 'makaric.milica@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Milica', 'Makaric', 3, '0652034133', 5, false, 3);
insert into user_authority (user_id, authority_id) values (3, 3);
update hotel h set h.id_admin = 3 where h.id = 3;

insert into soba (id, prosecna_ocena, cena_nocenja, broj_kreveta, hotel_id, opis, cena_popust, zauzeta) values (1, 4.8, 120, 3, 3, 'Apartman', 0, false);
insert into soba (id, prosecna_ocena, cena_nocenja, broj_kreveta, hotel_id, opis, cena_popust, zauzeta) values (2, 3.77, 53, 2, 3, 'Soba na drugom spratu', 0, false);

insert into rezervacija_hotel (id, datum_dolaska, datum_odlaska, soba_id, broj_nocenja, brza, ukupna_cena, aktivirana, popust) values (1, '2019-02-03', '2019-02-06', 1, 0, false, 130, true, 0);
insert into rezervacija_hotel (id, datum_dolaska, datum_odlaska, soba_id, broj_nocenja, brza, ukupna_cena, aktivirana, popust) values (2, '2019-02-15', '2019-02-25', 1, 11, false, 1500, true, 0);

insert into soba_rezervacije (soba_id, rezervacije_id) values (1, 1);
insert into soba_rezervacije (soba_id, rezervacije_id) values (1, 2);

insert into popust (id, pocetak_vazenja, kraj_vazenja, popust) values (2, '2019-02-16', '2019-02-20', 30);
insert into popust (id, pocetak_vazenja, kraj_vazenja, popust) values (1, '2019-01-07', '2019-01-08', 20);

insert into soba_popusti (soba_id, popusti_id) values (1, 1);
insert into soba_popusti (soba_id, popusti_id) values (1, 2);

insert into usluga (id, naziv, cena, popust) values (1, 'air-conditioner', 5, 0);
insert into usluga (id, naziv, cena, popust) values (2, 'Wi-Fi', 2, 0);

insert into grad (id, naziv) values (1, 'Novi Sad');
insert into adresa (id, grad_id, ulica, broj, latitude, longitude) values (1, 1, 'Novosadskog sajma', '35', 45.252529, 19.826855);