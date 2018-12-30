$(document).ready(function()	
{
    id_presented = window.location.search.substring(4, 5);
    console.log('[single_listing_hotel: document.ready()]: id hotela: ' + id_presented);
    
    cena_usluge = 0;

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
    	broj_soba = 1;
    	
    	$.ajax({
    		url: 'api/hotels/get_number_of_days',
    		type: 'GET',
    		success: function(number) {
    			console.log("number of days: " + number);
    			number_of_days = number;
    			
    			for (let soba of hotel.sobe) 
    	    	{
    	    		addSoba(soba);
    	    	}
    		}
    	});
    	
    	
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
    		let li = $('<li><span>' + usluga.naziv + ' - $' + usluga.cena + '/per day</span></li>');
    		
    		let buttonAdd = $('<span class="button_add book_button button trans_200" style="cursor: pointer; width: 70px; height: 30px; margin-left: 10px; text-align: center; color: white; display: inline-table; vertical-align: middle" id="add'+usluga.id+'"><a style="padding-left: 0px; padding-right: 0px;">Add<a/></span>');
    		buttonAdd.click(clickAddUsluga(usluga));
    		li.append(buttonAdd);
    		
    		$('#usluge_hotela').append(li);
    	}
	}

}

function clickAddUsluga(usluga){
	return function(){
		
		if($(this).hasClass('dodata_usluga'))
		{
			$(this).removeClass('dodata_usluga');
			var button = document.getElementById('add' + usluga.id);
			button.childNodes[0].innerHTML = 'Add';
			
			$.ajax({
				url: 'api/hotels/remove_usluga/' + usluga.id,
				type: 'DELETE',
				success: function(usluga_uklonjena) {
					console.log("uspesno uklanjanje usluge sa id: " + usluga_uklonjena.id);
					cena_usluge -= usluga_uklonjena.cena;
				}
			});
		}
		else
		{
			var id = "#add" + usluga.id;
			$(id).addClass("dodata_usluga");
			var button = document.getElementById('add' + usluga.id);
			button.childNodes[0].innerHTML = 'Remove';
			
			$.ajax({
				url: 'api/hotels/add_usluga/' + usluga.id,
				type: 'PUT',
				success: function(usluga_dodata) {
					console.log("uspesno dodavanje usluge sa id: " + usluga_dodata.id);
					cena_usluge += usluga_dodata.cena;
				}
			});
		}
		
	};
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
	
	cena_rez = number_of_days * soba.cena_nocenja;
	temp.content.getElementById("cena_nocenja").innerHTML = '$' + soba.cena_nocenja + '/night\r\nTotal: $' + cena_rez;
	temp.content.getElementById("prosecna_ocena").innerHTML = soba.prosecna_ocena;
	
	a = document.importNode(div, true);
    document.getElementById("ubaci_sobe_template").appendChild(a);
    

	$(".button_book_room").addClass('soba_id_' + soba.id);
	var elements = document.getElementsByClassName('button_book_room');
	for(element of elements)
	{
		$(element).removeClass('button_book_room');
		$(element).click(bookRoom(soba.id));
	}
}

function bookRoom(id)
{
	return function(){
		
		$.ajax({
			url: 'api/hotels/book_room/' + id,
			type: 'PUT',
			success: function(soba) {
				console.log("uspesna rezervacija sobe sa id_sobe: " + soba.id);
				alert('uspesna rezervacija sobe sa id_sobe: ' + soba.id);
				window.location.href = 'index.html';
			}
		});
	};
	
}
