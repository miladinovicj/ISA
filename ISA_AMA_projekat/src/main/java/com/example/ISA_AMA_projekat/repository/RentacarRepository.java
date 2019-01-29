package com.example.ISA_AMA_projekat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.ISA_AMA_projekat.model.RentacarServis;

public interface RentacarRepository extends JpaRepository<RentacarServis, Long>{

		RentacarServis findOneByNaziv(String naziv);
		
		@Modifying
		@Query("select rentacar_servis from RentacarServis rentacar_servis where rentacar_servis.adresa.grad.naziv like ?1 or rentacar_servis.adresa.ulica like ?1 or rentacar_servis.adresa.broj like ?1 or rentacar_servis.naziv like ?1")
		List<RentacarServis> search(String name_location);
		
		@Modifying
		@Query("update RentacarServis rs set rs.naziv = ?1 and rs.adresa_id=?2 and rs.promotivni_opis=?3 where u.id = ?4")
		public void updateAktiviran(String naziv, Long adresa_id, String promotivni_opis,  Long id);
}
