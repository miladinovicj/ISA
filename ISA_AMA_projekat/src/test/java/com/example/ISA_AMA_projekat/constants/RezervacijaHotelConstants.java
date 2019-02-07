package com.example.ISA_AMA_projekat.constants;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class RezervacijaHotelConstants {

	public static final Calendar dateCalendar;

	static {
	    dateCalendar = GregorianCalendar.getInstance();
	    dateCalendar.clear();
	    dateCalendar.set(2019, 2, 7);
	}
	
	public static final Date DB_DATE = dateCalendar.getTime();
	
    public static final Integer DB_ID = 1;  
    public static final int DB_BROJ_NOCENJA = 2;
	public static final double DB_UKUPNA_CENA = 3.5;
	public static final boolean DB_BRZA = false;
	public static final double DB_POPUST = 3.5;
	public static final boolean DB_AKTIVIRANA = false;
}
