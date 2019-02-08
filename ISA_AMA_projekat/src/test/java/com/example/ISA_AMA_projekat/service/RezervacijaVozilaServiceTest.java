package com.example.ISA_AMA_projekat.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.example.ISA_AMA_projekat.constants.FilijalaConstants;
import com.example.ISA_AMA_projekat.constants.RezervacijaVozilaConstants;
import com.example.ISA_AMA_projekat.model.Filijala;
import com.example.ISA_AMA_projekat.model.RezervacijaVozila;
import com.example.ISA_AMA_projekat.repository.RezervacijaVozilaRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RezervacijaVozilaServiceTest {

	@Mock
	private RezervacijaVozilaRepository rezRepositoryMock;
	
	@Mock
	private RezervacijaVozila rezMock;
	
	@InjectMocks
	private RezervacijaVozilaService rezService;
	
	
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdateDatumRez() {
		
		RezervacijaVozila rv = new RezervacijaVozila();
		rv.setId(RezervacijaVozilaConstants.DB_ID_RV);
		rv.setAktivirana(RezervacijaVozilaConstants.DB_AKTIVIRANA_RV);
		rv.setDatum_preuzimanja(RezervacijaVozilaConstants.DB_DAT_PREUZ_RV);
		rv.setDatum_vracanja(RezervacijaVozilaConstants.DB_DAT_VRACA_RV);
		rv.setBrza(RezervacijaVozilaConstants.DB_BRZA_RV);
		rv.setUkupna_cena(RezervacijaVozilaConstants.DB_CENA_RV);
		rv.setPopust(RezervacijaVozilaConstants.DB_POPUST_RV);
		rv.setDatum_rezervacije(new Date());
		
		when(rezRepositoryMock.findById(FilijalaConstants.DB_ID_F)).thenReturn(Optional.of(rv));
		
		RezervacijaVozila dbRez = rezService.findById(RezervacijaVozilaConstants.DB_ID_RV).get();
		dbRez.setDatum_rezervacije(new Date());
        
		doNothing().when(rezRepositoryMock).updateDatumRez(new Date(),RezervacijaVozilaConstants.DB_ID_RV );
		rezService.updateDatumRez(new Date(),RezervacijaVozilaConstants.DB_ID_RV);
		
		when(rezRepositoryMock.findById(RezervacijaVozilaConstants.DB_ID_RV)).thenReturn(Optional.of(dbRez));
		
		RezervacijaVozila rezChanged = rezService.findById(RezervacijaVozilaConstants.DB_ID_RV).get();
		
		assertEquals(dbRez, rezChanged);
        verify(rezRepositoryMock, times(2)).findById(RezervacijaVozilaConstants.DB_ID_RV);
        verify(rezRepositoryMock, times(1)).updateDatumRez(new Date(), RezervacijaVozilaConstants.DB_ID_RV);
        verifyNoMoreInteractions(rezRepositoryMock);
	}

	
	

}
