$(document).ready(function()	
{
    var id_presented = window.location.search.substring(4);
    console.log('[single_listing_hotel: document.ready()]: id hotela: ' + id_presented);

    $.ajax({
        type: 'GET',
        url: 'api/hotels/' + id_presented,
        contentType: 'application/json',
        dataType: "json",
        complete: function (data)
		{
            var hotel = data.responseJSON;
            console.log(hotel);
            showHotel(hotel)
		}
    });
	
});

function showHotel(hotel)
{

    $("#title_hotel").text(hotel.naziv);
    $("#hotel_name").text(hotel.naziv);
    $('#hotel_info_text').text(hotel.promotivni_opis);
    $('#adresa_hotel').text(hotel.adresa);
    
    if(hotel.sobe.length == 0)
	{
    	$("#text_no_rooms").text("There are no rooms in this hotel.");
	}
    else
	{
    	$("#text_no_rooms").text("");
    	for (let soba of hotel.sobe) 
    	{
    		addSoba(soba);
    	}
	}
    
    if(hotel.usluge.length == 0)
	{
    	$("#text_no_services").text("There are no additional services this hotel.");
	}
    else
	{
    	$("#text_no_services").text("Additional services: ");
    	for (let usluga of hotel.usluge) 
    	{
    		let li = $('<li>' + usluga.naziv + ' - $' + usluga.cena + '/per day</li>');
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
		broj_kreveta_string = "Jednokrevetna soba";
	}
	else if(soba.broj_kreveta == 2)
	{
		broj_kreveta_string = "Dvokrevetna soba";
	}
	else if(soba.broj_kreveta == 3)
	{
		broj_kreveta_string = "Trokrevetna soba";
	}
	else if(soba.broj_kreveta == 4)
	{
		broj_kreveta_string = "Četvorokrevetna soba";
	}
	else if(soba.broj_kreveta == 5)
	{
		broj_kreveta_string = "Petokrevetna soba";
	}
	
	temp.content.getElementById("broj_kreveta").innerHTML = broj_kreveta_string;
	temp.content.getElementById("cena_nocenja").innerHTML = '$' + soba.cena_nocenja + '/night';
	temp.content.getElementById("prosecna_ocena").innerHTML = soba.prosecna_ocena;
	
	a = document.importNode(div, true);
    document.getElementById("ubaci_sobe_template").appendChild(a);
}
