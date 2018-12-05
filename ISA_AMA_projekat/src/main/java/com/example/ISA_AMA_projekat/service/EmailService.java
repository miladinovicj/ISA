package com.example.ISA_AMA_projekat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.ISA_AMA_projekat.model.Korisnik;



@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private Environment env;

	
	@Async
	public void sendNotificaitionAsync(Korisnik korisnik) throws MailException, InterruptedException {

		//Simulacija duze aktivnosti da bi se uocila razlika
		Thread.sleep(10000);
		System.out.println("Slanje emaila...");

		SimpleMailMessage mail = new SimpleMailMessage();
		String confirmationUrl 
        =  "http://localhost:8080/api/users/registrationConfirm/" + korisnik.getEmail();
		mail.setTo(korisnik.getEmail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Confirm your registration!");
		mail.setText("Hello, " + korisnik.getIme() + " " + korisnik.getPrezime() + "\n To confirm your registration on our site, click on this link below.\n "
				+ confirmationUrl + "\n Sincerely,\n your WhereTo.");
		javaMailSender.send(mail);

		System.out.println("Email poslat!");
	}

}
