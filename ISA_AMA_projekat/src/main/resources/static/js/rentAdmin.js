$(document).ready(function()
{
	var search = window.location.search;
	var splitted = search.split('&');
	
    id = splitted[0].substring(4);
    
	$.ajax({
        type: 'GET',
        url: 'api/rents/' + id,
        success: function (rentacar)
		{
            longitude = rentacar.adresa.longitude;
        	latitude = rentacar.adresa.latitude;
            console.log(rentacar);
            showRentacar(rentacar);
           // initMap();
		}
    });
});

function showRentacar(rentacar)
{
	console.log(rentacar.naziv);
	 $("#title_rentacar").text(rentacar.naziv);
	 $("#rentacar_name").text(rentacar.naziv);
	 $('#rentacar_info_text').text(rentacar.promotivni_opis);
	 var idiNa = "izmenaRent.html?id="+rentacar.id;
	 $('#dugmeIzmena').attr("href", idiNa);
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
	
	
    if(filijala.vozila.length == 0)
	{
    	//$("#text_no_cars").text("There are no cars in this branch of rentacar service.");
    	 temp.content.getElementById("vozila_u_ponudi").innerHTML = "";
    	 
	}
    else
	{
    	temp.content.getElementById("vozila_u_ponudi").innerHTML = '<a id="vozila_dugme'+filijala.id + '" style="cursor:pointer; color:white;" onclick="javascript:izlistajVozila(' + filijala.id + ')">Cars on offer</a>';
    	
	}
    
   
    a = document.importNode(div, true);
    document.getElementById("ubaci_filijale_template").appendChild(a);
}

function izlistajVozila(filijala_id)
{
	$("#vozila_dugme" + filijala_id).hide();
	$.get({
		url: '/api/filijale/' + filijala_id,
		success: function(filijala) {
			console.log('filijala_uspesno_vracena ' + filijala.id);
			for(let vozilo of filijala.vozila)
				{
				if(vozilo.zauzeto==false)
					addVozilo(vozilo);
				}
			
		},
		error : function(data){
			alert('Greska prilikom vracanja filijale.');
		}
	});


}

function addVozilo(car)
{
	var temp, div, a;
	temp = document.getElementById("template_auto");
	div = temp.content.querySelector("div#ubaci_auto");
	
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