package com.example.ISA_AMA_projekat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.ISA_AMA_projekat.admins.AdminSys;

@SpringBootApplication
public class IsaAmaProjekatApplication {

	public static void main(String[] args) {
		
		
		AdminSys asys = new AdminSys();
		
		asys.hotel();asys.rentACar();asys.avio();
		
		SpringApplication.run(IsaAmaProjekatApplication.class, args);

		
	}
}
