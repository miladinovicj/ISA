var rezervacija = null;
var korisnik = null;
var flight = null;

$(document).ready(function(){
	
	getKorisnik();
	
	$('form#rentacar').submit(function(event){
		event.preventDefault();
		 datum = $('input[name="check_out_car"]').val();
		 mesto = $('input[name="check_out_town"]').val();
		 let split = flight.dokle.split("-");
		 let check_in_town=split[0];
		 check_in_town=check_in_town.substring(0, check_in_town.length-1);
		let check_in_date = flight.vremeSletanja.substring(0,10);
		 let passengers = rezervacija.osobe.length;
		console.log(datum);
		console.log(mesto);
		//window.location.replace("index.html");
		preusmeri(check_in_town, check_in_date, datum, mesto, passengers);
	});
	
	$('#hotel_form').submit(function(event){
		event.preventDefault();
		
		check_out_date = $('input[name="check_out_hotel"]').val();
		
		let split = flight.dokle.split("-");
		let check_in_town=split[0];
		check_in_town = check_in_town.substring(0, check_in_town.length-1);
		
		let check_in_date = flight.vremeSletanja.substring(0,10);
		let adults = rezervacija.osobe.length;
		
		preusmeriHotel(check_in_town, check_in_date, check_out_date, adults);
	});
		
});

function preusmeri(check_in_town, check_in_date, datum, mesto, passengers)
{
	//alert("Usao u preusmeri");
	
	var idiNa = "index.html?name_location_rentacar=" + check_in_town + "&check_in_car=" + check_in_date + "&check_in_town=" + check_in_town + "&check_out_car=" + datum + "&check_out_town=" + mesto + "&passengers_rent=" + passengers + "&id_rez=" + rezervacija.id;
	window.location.href = idiNa;
}

function preusmeriHotel(check_in_town, check_in_date, check_out_date, adults){
	
	window.location.href = "index.html?name_location_hotel=" + check_in_town + "&check_in_hotel=" + check_in_date + "&check_out_hotel=" + check_out_date + "&adults_hotel=" + adults + "&id_rez=" + rezervacija.id;
}

function getKorisnik()
{
	token = localStorage.getItem('jwtToken');
	$.post
	({
		url: "/auth/userprofile",
		headers: {"Authorization": "Bearer " + token},
		contentType: 'application/json',
		data : token,
		  
		success: function(user) 
		{
			if(user == null || user == "")
			{
				localStorage.clear();
				window.location.replace("index.html");
			}
			
			korisnik = user;
			getRezervacija();
		},
		error: function() 
		{
			window.location.replace("index.html");
		}
	});
}


function getRezervacija()
{
	var url = window.location.toString();
	var splitstr = url.split("=");
	var rezID = splitstr[1];
	
    $.ajax({
        type: 'GET',
        url: 'api/rezervacija/' + rezID,
        contentType: 'application/json',
        dataType: "json",
        complete: function (data)
		{
        	
        	if(data == null)
        	{
        		alert("Unauthorized.");
        		window.location.replace("index.html");
        	}
        	else
        	{
            	rezervacija = data.responseJSON;
            	console.log(rezervacija);
            	flight = rezervacija.let;
            	$('input[name=check_out_hotel]').attr("min", flight.vremeSletanja.substring(0,10));
            	$('input[name=check_out_car]').attr("min", flight.vremeSletanja.substring(0,10));
            	
            	if(rezervacija.korisnik.id != korisnik.id)
            	{
            		alert("Unauthorized.");
            		window.location.replace("index.html");
            	}  
            	
            	initWindow();
            	
        	}

		}
    });
}



function initWindow()
{ 
	//info o letu
	$("#flightFrom").text("From: " + capitalize(flight.odakle));
	$("#flightTo").text("To: " + capitalize(flight.dokle));
	$("#flightDuration").text("Duration: " + flight.trajanje + " hours");
	$("#flightDeparture").text("Departure time: " + processTime(flight.vremePoletanja));
	$("#flightArrival").text("Arrival time: " + processTime(flight.vremeSletanja));
	
	fillPasengerInfo();
	
	
	if(rezervacija.rezevacijaHotel == null)
	{
		$("#noHotel").prop("hidden",false);
		$("#hasHotelReservation").attr("hidden", true);
	}
	else
	{
		var id_rez_hotel = rezervacija.rezevacijaHotel.id;
		
		$.get({
			url: "/api/hotels/get_room/" + id_rez_hotel,
			  
			success: function(data) {
				if(data == null || data == "")
				{
					console.log('nije pronadjena soba');
				}
				else
				{
					console.log('pronadjena soba');
					$("#hotel_res").text("Hotel: " + data.hotel.naziv);
					$("#room_res").text("Number of beds in room: " + data.soba.broj_kreveta);
				}
			},
			
			error: function() {
				alert('Error with loading room');
			}
		
		});
		
		 $("#noHotel").prop("hidden",true);
		 $("#hasHotelReservation").attr("hidden", false);
		 
		 var check_in = new Date(rezervacija.rezevacijaHotel.datum_dolaska.substring(0, 10) + "Z");
	     var pravi = check_in.toString();
	     var date_check_in = new Date(pravi);
	     date_check_in.setDate(date_check_in.getDate()+1);
	     
	     var check_out = new Date(rezervacija.rezevacijaHotel.datum_odlaska.substring(0, 10) + "Z");
	     var pravi = check_out.toString();
	     var date_check_out = new Date(pravi);
	     date_check_out.setDate(date_check_out.getDate()+1);
		 
		 $("#check_in_hotel_res").text("Check in date: " + date_check_in.toString().substring(0, 10));
		 $("#check_out_hotel_res").text("Check out date: " + date_check_out.toString().substring(0, 10));
		 $("#cost_hotel_res").text("Cost: $" + rezervacija.rezevacijaHotel.ukupna_cena);
	}
	
	if(rezervacija.rezervacijaVozila == null)
	{
		$("#noRental").prop("hidden",false);
		$("#hasCar").attr("hidden", true);
	}
	else
	{
	 $("#noRental").prop("hidden",true);
	 $("#hasCar").attr("hidden", false);
	 
	 var check_in_car = new Date(rezervacija.rezervacijaVozila.datum_preuzimanja.substring(0, 10) + "Z");
     var pravi = check_in_car.toString();
     var date_check_in_car = new Date(pravi);
     date_check_in_car.setDate(date_check_in_car.getDate()+1);
     
     var check_out_car = new Date(rezervacija.rezervacijaVozila.datum_vracanja.substring(0, 10) + "Z");
     var pravi = check_out_car.toString();
     var date_check_out_car = new Date(pravi);
     date_check_out_car.setDate(date_check_out_car.getDate()+1);
	 
	 $("#check_in").text("Check in date and town: " + date_check_in_car.toString().substring(0, 10) + " " + rezervacija.rezervacijaVozila.mesto_preuzimanja.naziv);
	 $("#check_out").text("Check out date and town: " + date_check_out_car.toString().substring(0, 10) + " " + rezervacija.rezervacijaVozila.mesto_vracanja.naziv);
	 $("#cost").text("Cost: $" + rezervacija.rezervacijaVozila.ukupna_cena);
	}
}

function rezervisiVozilo()
{
	$("#noRental").attr("hidden", true);
	$("#hocuVozilo").attr("hidden", false);
	
}

function bookHotel(){ 
	
	$("#noHotel").attr("hidden", true);
	$("#infoHotel").attr("hidden", false);
	
}


function capitalize(string) 
{
    return string.charAt(0).toUpperCase() + string.slice(1);
}

function processTime(string)
{
	temp = string.substring(0,16);
	
	temp = temp.replace("-","/");
	temp = temp.replace("-","/");
	
	temp = temp.replace("T"," at ");
	
	return temp;
}



function fillPasengerInfo()
{
	
	var $putniciTabela = $("#putniciTabela");
	
	var $personTemplate = $("#itemTemplate");
    
    
    
    
    for(var i = 0 ; i < rezervacija.osobe.length ; i++)
    {
    	
    	console.log("personTemplate.html: " + $personTemplate.html());

    	
    	var $item = $($personTemplate.html());
    	var osoba = rezervacija.osobe[i];
    	
    	
    	var prtljag = ""
    	if(osoba.prtljag == 0) prtljag = "CARRY ON";
    	if(osoba.prtljag == 1) prtljag = "STANDARD - 20kg MAX";
    	if(osoba.prtljag == 2) prtljag = "SUPERSIZED - 35kg MAX";
    	
    	var potvrdjeno = ""
    	if(osoba.potvrdjeno) potvrdjeno = "✔"
    	else potvrdjeno = "✖"
    	
    	$item.find("#itemNumber").text(i+1);
    	$item.find("#itemName").text(capitalize(osoba.ime) + " " + capitalize(osoba.prezime));
    	$item.find("#itemPass").text(osoba.brojPasosa);
    	$item.find("#itemLug").text(prtljag);
    	$item.find("#itemSeat").text(osoba.sediste);
    	$item.find("#itemConfirmed").text(potvrdjeno);

    	$putniciTabela.append($item);
    	console.log("item.outterHTML: " + $item.prop('outterHTML'));    	
    }
    
    
    
}

