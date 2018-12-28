
delete from AUTHORITY;
delete from user_authority;
delete from korisnik;
delete from aviokompanija_brzi_letovi;
delete from aviokompanija;
delete from let;
delete from filijala;
delete from rentacar_servis;

insert into rentacar_servis (id, naziv, adresa, promotivni_opis, prosecna_ocena) values (1, 'Angel', 'Vojvode Supljikca 20 Novi Sad', 'Mlada firma sa velikom vizijom. Iznajmljivanje automobila sa i bez vozaca.', 3.2);
insert into rentacar_servis (id, naziv, adresa, promotivni_opis, prosecna_ocena) values (2, 'Cartize Company', 'Novosadski put 19 Novi Sad', 'Najpovoljnije cene rentiranja automobila u Novom Sadu', 4.8);
insert into rentacar_servis (id, naziv, adresa, promotivni_opis, prosecna_ocena) values (3, 'Stars', 'Bulevar Mihajla Pupina 52 Beograd', 'U Beogradu svi iznajmljuju automobile od nas.', 4);

insert into filijala (id, adresa, rentacar_id) values (1, 'Janka Cmelika 20 Novi Sad', 1);
insert into filijala (id, adresa, rentacar_id) values (2, 'Mise Dimitrijevica 55 Novi Sad', 1);
insert into filijala (id, adresa, rentacar_id) values (3, 'Puskinova 15 Zrenjanin', 1);
insert into filijala (id, adresa, rentacar_id) values (4, 'Bulevar kralja Aleksandra 56 Beograd', 3);