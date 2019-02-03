var rezervacija = null;
var korisnik = null;
var flight = null;

$(document).ready(function(){
	
	getKorisnik();
	
});


function getKorisnik()
{
	token = localStorage.getItem('jwtToken');
	$.post
	({
		url: "/auth/userprofile",
		headers: 'Authorization',
		contentType: 'application/json',
		data : token,
		  
		success: function(user) 
		{
			if(user == null)
			{
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
	var rezID = url[url.length-1]
	
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
	}
	if(rezervacija.rezervacijaVozila == null)
	{
		$("#noRental").prop("hidden",false);
	}
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

