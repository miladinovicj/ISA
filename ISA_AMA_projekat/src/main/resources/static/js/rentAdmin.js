$(document).ready(function()
{
	var search = window.location.search;
	var splitted = search.split('&');
	
    id = splitted[0].substring(4);
    
	$.ajax({
        type: 'GET',
        url: 'api/rents/admin/' + id,
        success: function (rentacar)
		{
            longitude = rentacar.adresa.longitude;
        	latitude = rentacar.adresa.latitude;
            console.log(rentacar);
            showRentacar(rentacar);
            initMap();
		}
    });
	
	$('#izmenaCENE').submit(function(event){
		
		event.preventDefault();
		
		let izabrano = $("#lista_usluga").val();
		let cena = $('input[name="promena_cene"]').val();
		
		$.post({
			url: '/api/usluge/admin/izmenaUsluge/' + izabrano + '/'  + cena,
			contentType: 'application/json',
			success: function(data) {
				if(data==null || data==""){
					//$('#validacijaEmail').text('User with this email already exists!');
					alert('Nije izmenjena!');
				}
				else {
					
								alert('Cost of additional service successfully changed!');
								window.location.href="rentAdmin.html?id="+id;
								
								
					
					
				}
			}
		
			
	});
		
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
	 var dodajFil = "dodajFilijalu.html?id="+rentacar.id;
	 $('#dugmeFil').attr("href", dodajFil);
	 
	 var dodajVozilo = "dodajVozilo.html?id="+rentacar.id;
	 $('#dugmeVozilo').attr("href", dodajVozilo);
	 
	 
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
	    		addFilijala(rentacar, filijala);
	    	}
	    	
	    	
		}
	    
	    if(rentacar.usluge.length == 0)
		{
	    	$("#text_no_services").text("There are no additional services.");
		}
	    else
		{
	    	$("#text_no_services").text("Additional services: ");
	    	$("#izmenaCENE").attr("hidden", false);
	    	$('input[name="promena_cene"]').val(0);
	    	for(let usluga of rentacar.usluge)
	    		{
	    		var opcija='<option value="' + usluga.id + '">' + usluga.naziv +  '</option>';
            	$("#lista_usluga").append(opcija);
	    		}
	    	for (let usluga of rentacar.usluge) 
	    	{
	    		let li = $('<li>' + usluga.naziv + ' - $' + usluga.cena + '/per day</li>');
	    		$('#usluge_rentacar').append(li);
	    	}
		}
}

function addFilijala(rentacar, filijala)
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
    	temp.content.getElementById("vozila_u_ponudi").innerHTML = '<a id="vozila_dugme'+filijala.id + '" style="cursor:pointer; color:white;" onclick="javascript:izlistajVozila(' + filijala.id + ',' + rentacar.id + ')">Cars on offer</a>';
    	
	}
    
    temp.content.getElementById("brisanje_filijale").innerHTML = '<a style="cursor:pointer; color:white;" onclick="javascript:brisanjeFil('  + filijala.id + ');">Delete branch</a>'
    temp.content.getElementById("izmena_filijale").innerHTML = '<a style="cursor:pointer; color:white;" href="izmenaFil.html?idr=' + rentacar.id + '&id=' + filijala.id + '">Edit branch</a>'
	
    a = document.importNode(div, true);
    document.getElementById("ubaci_filijale_template").appendChild(a);
}

function brisanjeFil(filijala_id)
{
	$.ajax({
        type: 'DELETE',
        url: 'api/filijale/admin/delete/' + filijala_id,
        contentType: 'application/json',
        success: function (filijala)
		{
            alert('Branch with id: ' + filijala.id + 'successfully deleted!');
            window.location.href="rentAdmin.html?id="+id;
		}
    });
}
function izlistajVozila(filijala_id, rentacar_id)
{
	$("#vozila_dugme" + filijala_id).hide();
	$.get({
		url: '/api/filijale/' + filijala_id,
		success: function(filijala) {
			console.log('filijala_uspesno_vracena ' + filijala.id);
			for(let vozilo of filijala.vozila)
				{
				if(vozilo.zauzeto==false)
					addVozilo(vozilo, rentacar_id);
				}
			
		},
		error : function(data){
			alert('Greska prilikom vracanja filijale.');
		}
	});


}

function addVozilo(car, rentacar_id)
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
	temp.content.getElementById("izmena_auta").innerHTML = '<a style="cursor:pointer; color:white;" href="izmenaAuta.html?idr=' + rentacar_id + '&id=' + car.id + '">Edit car</a>'
	temp.content.getElementById("brisanje_auta").innerHTML = '<a style="cursor:pointer; color:white;" onclick="javascript:brisanjeVozila('  + car.id + ');">Delete car</a>'
		
	a = document.importNode(div, true);
    document.getElementById("ubaci_auto_template").appendChild(a);
    
}

function brisanjeVozila(car_id)
{
	$.ajax({
        type: 'DELETE',
        url: 'api/vozila/admin/delete/' + car_id,
        contentType: 'application/json',
        success: function (vozilo)
		{
            alert('Car with id: ' + vozilo.id + 'successfully deleted!');
            window.location.href="rentAdmin.html?id="+id;
		}
    });
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