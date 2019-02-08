insert into hotel (id, naziv, promotivni_opis, prosecna_ocena, id_admin) values (2, 'Hotel 1', 'prvi hotel',4, 1);
insert into hotel (id, naziv, promotivni_opis, prosecna_ocena, id_admin) values (1, 'Petar', 'Petrovic', 3.5, 2);
insert into hotel (id, naziv, promotivni_opis, prosecna_ocena, id_admin) values (3, 'Hotel 2', 'drugi hotel', 3, 3);

insert into grad (id, naziv) values (1, 'Grad1');
insert into grad (id, naziv) values (2, 'Grad2');
insert into grad (id, naziv) values (3, 'Grad3');
insert into grad (id, naziv) values (4, 'Grad4');
insert into grad (id, naziv) values (5, 'Grad5');

insert into adresa (id, grad_id, ulica, broj, latitude, longitude) values (1, 1, 'Ulica1', '1', 1.1, 1.1);
insert into adresa (id, grad_id, ulica, broj, latitude, longitude) values (2, 2, 'Ulica2', '2', 2.2, 2.2);
insert into adresa (id, grad_id, ulica, broj, latitude, longitude) values (3, 3, 'Ulica3', '3', 3.3, 3.3);
insert into adresa (id, grad_id, ulica, broj, latitude, longitude) values (4, 4, 'Ulica4', '4', 4.4, 4.4);
insert into adresa (id, grad_id, ulica, broj, latitude, longitude) values (5, 5, 'Ulica5', '5', 5.5, 5.5);

insert into rentacar_servis (id, naziv, adresa_id, promotivni_opis, prosecna_ocena) values (1, 'Rent1', 1, 'Rent opis 1', 3.2);
insert into rentacar_servis (id, naziv, adresa_id, promotivni_opis, prosecna_ocena, id_admin) values (2, 'Rent2', 2, 'Rent opis 2', 4.2, 1);
insert into rentacar_servis (id, naziv, adresa_id, promotivni_opis, prosecna_ocena) values (3, 'Rent3', 3, 'Rent opis 3', 3.5);

insert into filijala (id, adresa_id, rentacar_id) values (1, 1, 1);
insert into filijala (id, adresa_id, rentacar_id) values (2, 4, 1);
insert into filijala (id, adresa_id, rentacar_id) values (3, 5, 1);

insert into vozilo (id, naziv, marka, model, godina_proizvodnje, broj_sedista, tip, cena_dan, prosecna_ocena, filijala_id, cena_popust, zauzeto) values (1, 'Vozilo1', 'V1', 'V', 2011, 5, 'Tip vozila 1', 400,4.5, 1 , 0, false);
insert into vozilo (id, naziv, marka, model, godina_proizvodnje, broj_sedista, tip, cena_dan, prosecna_ocena, filijala_id, cena_popust, zauzeto) values (2, 'Vozilo2', 'V2', 'VV', 2011, 5, 'Tip vozila 2', 400,4.5, 1 , 0, false);


insert into rezervacija_vozila (id, aktivirana, broj_putnika, brza, datum_preuzimanja, datum_vracanja, popust, ukupna_cena, mesto_preuzimanja_id, mesto_vracanja_id, vozilo_id) values (1, true, 3, false, '2019-03-03', '2019-03-11', 0, 1105, 1, 1, 1);
insert into rezervacija_vozila (id, aktivirana, broj_putnika, brza, datum_preuzimanja, datum_vracanja, popust, ukupna_cena, mesto_preuzimanja_id, mesto_vracanja_id, vozilo_id) values (2, true, 3, false, '2019-05-03', '2019-05-11', 0, 1105, 1, 1, 2);

