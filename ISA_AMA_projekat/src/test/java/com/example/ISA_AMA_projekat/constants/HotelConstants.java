package com.example.ISA_AMA_projekat.constants;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class HotelConstants {
	
    public static final Integer NEW_ID = 2;
    public static final String NEW_NAZIV = "Hotel Palisad";
    public static final String NEW_PROMOTIVNI_OPIS = "Hotel sa 5 zvezdica";
    public static final double NEW_PROSECNA_OCENA = 4.8;
    public static final Integer NEW_ADMIN_ID = 1;
    
    public static final Integer DB_ID = 3;
    public static final String DB_NAZIV = "Hotel Moskva";
    public static final String DB_PROMOTIVNI_OPIS = "Odlican hotel";
    public static final double DB_PROSECNA_OCENA = 3.5;
    
    public static final int DB_ADULTS = 2;
    
    public static final int DB_COUNT = 3;
    
    public static final Calendar dateCalendar;

	static {
	    dateCalendar = GregorianCalendar.getInstance();
	    dateCalendar.clear();
	    dateCalendar.set(2019, 2, 7);
	}
	
	public static final Date DB_DATE = dateCalendar.getTime();
	public static final String DB_DATE_STR = "2019-02-07";

}
