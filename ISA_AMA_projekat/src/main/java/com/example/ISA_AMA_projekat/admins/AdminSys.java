package com.example.ISA_AMA_projekat.admins;

public class AdminSys implements AdminRentACarInterface, AdminHotelInterface, AdminAvioInterface 
{
	
	AdminAvio aa;
	AdminHotel ah;
	AdminRentACar arc;
	
	
	
	public AdminSys() 
	{
		this.aa = new AdminAvio();
		this.ah = new AdminHotel();
		this.arc = new AdminRentACar();
	}



	public AdminSys(AdminAvio aa, AdminHotel ah, AdminRentACar arc) {
		super();
		this.aa = aa;
		this.ah = ah;
		this.arc = arc;
	}
	
	
	
	public void rentACar()
	{
		arc.rentACar();
	}
	
	public void hotel()
	{
		ah.hotel();
	}
	
	public void avio()
	{
		aa.avio();
	}
}
