$(document).ready(function()	
{
	let token = localStorage.getItem('jwtToken');
	
	var search = window.location.search;
    id_presented = search.substring(4);
    
    console.log('[hotelAdmin.js: document.ready()]: id hotela: ' + id_presented);

    $.ajax({
        type: 'GET',
        url: 'api/hotels/admin/' + id_presented,
        headers: {"Authorization": "Bearer " + token},
        contentType: 'application/json',
        success: function (hotel)
		{
        	longitude = hotel.adresa.longitude;
        	latitude = hotel.adresa.latitude;
            console.log(hotel);
            showHotel(hotel);
            initMap();
		},
		error: function(data){
			console.log('greska prilikom ucitavanja stranice za admina hotela');
		}
    });
});

function showHotel(hotel)
{

    $("#title_hotel").text(hotel.naziv);
    $("#hotel_name").text(hotel.naziv);
    $('#hotel_info_text').text(hotel.promotivni_opis);
    $('#adresa_hotel').text(hotel.adresa.ulica + ' ' + hotel.adresa.broj + ', ' + hotel.adresa.grad.naziv);
    
    if(hotel.sobe.length == 0)
	{
    	$("#text_no_rooms").text("There are no rooms in this hotel.");
    	
	}
    else
	{
    	$("#text_no_rooms").text("");
    	broj_soba = 1;
    
		for (let soba of hotel.sobe) 
    	{
    		addSoba(soba);
    	}
    	
	}
    
    if(hotel.usluge.length == 0)
	{
    	$("#text_no_services").text("There are no additional services for this hotel.");
	}
    else
	{
    	$("#text_no_services").text("Additional services: ");
    	for (let usluga of hotel.usluge) 
    	{
    		let li = $('<li><span>' + usluga.naziv + ' - $' + usluga.cena + '/per day</span></li>');
    		$('#usluge_hotela').append(li);
    	}
	}

}

function addSoba(soba)
{
	var temp, div, a;
	temp = document.getElementById("template_room");
	div = temp.content.querySelector("div#ubaci_sobu");
	
	var broj_kreveta_string;
	if(soba.broj_kreveta == 1)
	{
		broj_kreveta_string = "Jednokrevetna soba. ";
	}
	else if(soba.broj_kreveta == 2)
	{
		broj_kreveta_string = "Dvokrevetna soba. ";
	}
	else if(soba.broj_kreveta == 3)
	{
		broj_kreveta_string = "Trokrevetna soba. ";
	}
	else if(soba.broj_kreveta == 4)
	{
		broj_kreveta_string = "Četvorokrevetna soba. ";
	}
	else if(soba.broj_kreveta == 5)
	{
		broj_kreveta_string = "Petokrevetna soba. ";
	}
	
	temp.content.getElementById("naziv").innerHTML = 'Soba ' + broj_soba;
	broj_soba = broj_soba + 1;
	temp.content.getElementById("broj_kreveta").innerHTML = broj_kreveta_string + '\r\n' + soba.opis;
	temp.content.getElementById("cena_nocenja").innerHTML = '$' + soba.cena_nocenja + '/night';
	temp.content.getElementById("prosecna_ocena").innerHTML = soba.prosecna_ocena;
	
	a = document.importNode(div, true);
    document.getElementById("ubaci_sobe_template").appendChild(a);
   
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