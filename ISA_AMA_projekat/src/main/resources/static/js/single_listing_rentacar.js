$(document).ready(function()	
{
	
    var id_presented = window.location.search.substring(4);
    console.log('[single_listing_rentacar: document.ready()]: id rentacara: ' + id_presented);

    $.ajax({
        type: 'GET',
        url: 'api/rents/' + id_presented,
        contentType: 'application/json',
        dataType: "json",
        complete: function (data)
		{
            var rentacar = data.responseJSON;
            longitude = rentacar.adresa.longitude;
        	latitude = rentacar.adresa.latitude;
            console.log(rentacar);
            showRentacar(rentacar);
            initMap();
		}
    });
	
});

function showRentacar(rentacar)
{

    $("#title_rentacar").text(rentacar.naziv);
    $("#rentacar_name").text(rentacar.naziv);
    $('#rentacar_info_text').text(rentacar.promotivni_opis);
    $('#adresa_rentacar').text(rentacar.adresa.ulica + ' ' + rentacar.adresa.broj + ', ' + rentacar.adresa.grad.naziv);
    
    if(rentacar.filijale == 0)
	{
    	$("#text_no_filijale").text("There are no branches in this rentacar service.");
	}
    else
	{
    	$("#text_no_filijale").text("Branches:");
    	for (let filijala of rentacar.filijale) 
    	{
    		addFilijala(filijala);
    	}
	}
    
    if(rentacar.usluge.length == 0)
	{
    	$("#text_no_services").text("There are no additional services.");
	}
    else
	{
    	$("#text_no_services").text("Additional services: ");
    	for (let usluga of rentacar.usluge) 
    	{
    		let li = $('<li>' + usluga.naziv + ' - $' + usluga.cena + '/per day</li>');
    		$('#usluge_rentacar').append(li);
    	}
	}

}

function addFilijala(filijala)
{
	var temp, div, a;
	temp = document.getElementById("template_filijala");
	div = temp.content.querySelector("div#ubaci_filijalu");
	
	
	temp.content.getElementById("adresa_filijale").innerHTML =  filijala.adresa.ulica + ' ' + filijala.adresa.broj + ', ' + filijala.adresa.grad.naziv; ;
	
	a = document.importNode(div, true);
    document.getElementById("ubaci_filijale_template").appendChild(a);
    if(filijala.vozila.length == 0)
	{
    	//$("#text_no_cars").text("There are no cars in this branch of rentacar service.");
	}
    else
	{
    	$("#text_no_cars").text("");
    	for (let car of filijala.vozila) 
    	{
    		addVozilo(car);
    	}
	}
}

function addVozilo(car)
{
	var temp, div, a;
	temp = document.getElementById("template_auto");
	div = temp.content.querySelector("div#ubaci_auto");
	var token = localStorage.getItem('jwtToken');
	if(token!=null)
		temp.content.getElementById("dugme_book").removeAttribute("hidden");
	var broj_sedista_string = "Number of seats: " + car.broj_sedista;
	var god_proizvodnje_string = "The year of production: " + car.godina_proizvodnje;
	var naziv_vozila= car.marka + " " + car.model + " " + car.naziv + " " + car.tip;
	
	
	temp.content.getElementById("naziv_auta").innerHTML = naziv_vozila;
	temp.content.getElementById("godina_proizvodnje").innerHTML = god_proizvodnje_string;
	temp.content.getElementById("broj_sedista").innerHTML = broj_sedista_string;
	temp.content.getElementById("cena_auta").innerHTML = '$' + car.cena_dan + '/day';
	temp.content.getElementById("prosecna_ocena").innerHTML = car.prosecna_ocena;
	a = document.importNode(div, true);
    document.getElementById("ubaci_auto_template").appendChild(a);
}

function initMap()
{
	var map = new ol.Map({
        target: 'map',
        layers: [
          new ol.layer.Tile({
            source: new ol.source.OSM()
          })
        ],
        view: new ol.View({
          center: ol.proj.fromLonLat([longitude, latitude]),
          zoom: 17
        })
      });
}

function odjava()
{
	localStorage.clear();
}