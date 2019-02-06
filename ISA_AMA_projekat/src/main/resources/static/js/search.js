function showContentHotel() {
	
	if(window.location.search.indexOf('id_rez') == -1)
	{
		if(document.getElementById('ubaci_hotele_template').style.display=='none') { 
	        document.getElementById('ubaci_hotele_template').style.display='block'; 
	        document.getElementById("hotel_title").style.display='block';
	    }
	    
	    if(document.getElementById('ubaci_rentacar_template').style.display=='block') { 
	        document.getElementById('ubaci_rentacar_template').style.display='none'; 
	        document.getElementById("rentacar_title").style.display='none';
	    }
	    
	    if(document.getElementById("aviokompanije").style.display=='block'){
		    document.getElementById("aviokompanije").style.display='none';
			document.getElementById("aviokompanije_naslov").style.display='none';
	    }
	    
		console.log("[search.js: showContentHotel()]: ucitavanje svih hotela");
		
		$('input[name="name_location_hotel"]').val('');
		var help = document.getElementById("ubaci_hotele_template");
		if(help.childElementCount > 2)
		{
			for(var i=help.childElementCount; i>=3; i--)
			{
				help.removeChild(help.children[i-1]);
			}
		}
		
		$.get({
			url: "/api/hotels/all",
			success: function(hoteli) {

				if(hoteli == null){
					alert('There are no hotels');
					document.getElementById('hotel_title').innerHTML = '';
				}
				else if(help.childElementCount == 2){
					console.log('There are ' + hoteli.length + ' hotels in memory.');
					document.getElementById('hotel_title').innerHTML = 'Hotels';
					document.getElementById('ubaci_hotele_template').style.display='block';
					document.getElementById("aviokompanije").style.display='none';
					document.getElementById("aviokompanije_naslov").style.display='none';
					for (let hotel of hoteli) 
					{
						addHotelLi(hotel);
					}
				}
			},
			error : function(data){
				alert('Error with loading hotels');
				document.getElementById('hotel_title').innerHTML = '';
			}
		});
	}

}

function addHotelLi(hotel) {
	console.log("naziv hotela: " + hotel.naziv);
	var temp, div, a, i, text, name;
	temp = document.getElementById("template_hotel");
	div = temp.content.querySelector("div#ubaci");
	
    temp.content.getElementById("name_hotel").innerHTML = hotel.naziv;
    temp.content.getElementById("text_hotel").innerHTML = hotel.adresa.ulica + ' ' + hotel.adresa.broj + ', ' + hotel.adresa.grad.naziv;
    temp.content.getElementById("rating_hotel").innerHTML = hotel.prosecna_ocena;
    temp.content.getElementById("dugme_view_details").innerHTML = '<a style="color: white;  cursor: pointer;" onclick="javascript:singleListingHotel(' + hotel.id + ')">view details</a>';
    
    a = document.importNode(div, true);
    document.getElementById("ubaci_hotele_template").appendChild(a);
    
}

function singleListingHotel(hotel_id)
{
	var search = window.location.search;
	var splitted = search.split('&');
	
	if(splitted != "")
	{
		var check_in_fake = splitted[1].substring(15);
		var check_out_fake = splitted[2].substring(16);
		
		var check_in = check_in_fake.substring(0, 4) + '-' + check_in_fake.substring(5, 7) + '-' + check_in_fake.substring(8, 10);
		var check_out = check_out_fake.substring(0, 4) + '-' + check_out_fake.substring(5, 7) + '-' + check_out_fake.substring(8, 10);
		
		var adults = 0;
		var id_rez = 0;
		
		if(splitted[3].length > 13)
	    {
	    	adults = splitted[3].substring(13);
	    }
		
		if(splitted.length > 4)
			id_rez=splitted[4].substring(7);
		
		window.location.href='single_listing_hotel.html?id=' + hotel_id +'&check_in=' + check_in + '&check_out=' + check_out + '&adults=' + adults + '&id_rez=' + id_rez;
	}
	else
	{
		window.location.href='single_listing_hotel.html?id=' + hotel_id +'&check_in=0001-01-01&check_out=0001-01-01&adults=0&id_rez=0';
	}
		
}

function showContentAvio() {
	
	if(window.location.search.indexOf('id_rez') == -1)
	{
	    if(document.getElementById('ubaci_hotele_template').style.display=='block') { 
	        document.getElementById('ubaci_hotele_template').style.display='none';
	        document.getElementById("hotel_title").style.display='none';
	    }
	    
	    if(document.getElementById('ubaci_rentacar_template').style.display=='block') { 
	        document.getElementById('ubaci_rentacar_template').style.display='none'; 
	        document.getElementById("rentacar_title").style.display='none';
	    }
	    
	    if(document.getElementById("aviokompanije").style.display=='none'){
		    document.getElementById("aviokompanije").style.display='block';
			document.getElementById("aviokompanije_naslov").style.display='block';
	    }
	    
	    return false;
	}
}

function showContentRent() {
	if(window.location.search.indexOf('id_rez') == -1)
	{
		if(document.getElementById('ubaci_hotele_template').style.display=='block') { 
	        document.getElementById('ubaci_hotele_template').style.display='none';
	        document.getElementById("hotel_title").style.display='none';
	    }
	    
	    if(document.getElementById('ubaci_rentacar_template').style.display=='none') { 
	        document.getElementById('ubaci_rentacar_template').style.display='block'; 
	        document.getElementById("rentacar_title").style.display='block';
	    }
	    
	    if(document.getElementById("aviokompanije").style.display=='block'){
		    document.getElementById("aviokompanije").style.display='none';
			document.getElementById("aviokompanije_naslov").style.display='none';
	    }
	    console.log("[search.js: showContentRent()]: ucitavanje svih rentacar servisa");
		
		var help = document.getElementById("ubaci_rentacar_template");
		if(help.childElementCount > 2)
		{
			for(var i=help.childElementCount; i>=3; i--)
			{
				help.removeChild(help.children[i-1]);
			}
		}
		
		$.get({
			url: "/api/rents/all",
			success: function(rents) {
	
			  if(rents == null){
					alert('There are no rentacars!');
					document.getElementById('rentacar_title').innerHTML = '';
				}
				else if(help.childElementCount == 2)
					{
					console.log('There are ' + rents.length + ' rentacars in memory.');
					document.getElementById('rentacar_title').innerHTML = 'Rent-a-car';
					document.getElementById('ubaci_rentacar_template').style.display='block';
					document.getElementById("aviokompanije").style.display='none';
					document.getElementById("aviokompanije_naslov").style.display='none';
					
					for (let rentacar of rents) 
					{
						
						addRentacar(rentacar);
					}
				}
			},
			error : function(data){
				alert('Error!');
				document.getElementById('rentacar_title').innerHTML = '';
			}
		});
	}
}

function odjava()
{
	localStorage.clear();
}


function addRentacar(rentacar) {
	console.log("naziv rentacar servisa: " + rentacar.naziv);
	
	var temp, div, a, i, text, name;
	temp = document.getElementById("template_rentacar");
	div = temp.content.querySelector("div#ubaciRentacar");
	var fil_string;
	if(rentacar.filijale.length==0)
		fil_string="No branches."
	else
		{
			fil_string= "Branches:<br/>";
				for (let fil of rentacar.filijale)
				fil_string+= fil.adresa.ulica + ' ' + fil.adresa.broj + ', ' + fil.adresa.grad.naziv + "<br/>";
		}
	
    temp.content.getElementById("name_rentacar").innerHTML = rentacar.naziv;
    temp.content.getElementById("text_rentacar").innerHTML = rentacar.adresa.ulica + ' ' + rentacar.adresa.broj + ', ' + rentacar.adresa.grad.naziv;
    temp.content.getElementById("rentacar_fil").innerHTML = fil_string;
    temp.content.getElementById("rating_rentacar").innerHTML = rentacar.prosecna_ocena;
    temp.content.getElementById("dugme_view_details_rentacar").innerHTML = '<a style="color: white;  cursor: pointer;" onclick="javascript:singleListingRentacar(' + rentacar.id + ')">view details</a>';
    
    a = document.importNode(div, true);
    document.getElementById("ubaci_rentacar_template").appendChild(a);
    
}

function singleListingRentacar(rentacar_id)
{
	var search = window.location.search;
	var splitted = search.split('&');
	
	if(splitted != "")
	{
		var check_in_fake = splitted[1].substring(13);
		var check_in_town=splitted[2].substring(14);
		var check_out_fake = splitted[3].substring(14);
		var check_out_town=splitted[4].substring(15);
		var passengers=0;
		var id_rez = 0;
		
		if(splitted[5].length > 16)
		{
			passengers = splitted[5].substring(16);
			
		}
		if(splitted.length > 6)
			id_rez=splitted[6].substring(7);
		
		var check_in = check_in_fake.substring(0, 4) + '-' + check_in_fake.substring(5, 7) + '-' + check_in_fake.substring(8, 10);
		var check_out = check_out_fake.substring(0, 4) + '-' + check_out_fake.substring(5, 7) + '-' + check_out_fake.substring(8, 10);
		
		window.location.href='single_listing_rentacar.html?id=' + rentacar_id +'&check_in=' + check_in +'&check_in_town=' + check_in_town + '&check_out=' + check_out + '&check_out_town=' + check_out_town + '&passengers=' + passengers + '&id_rez=' + id_rez;
	}
	else
	{
		window.location.href='single_listing_rentacar.html?id=' + rentacar_id +'&check_in=0001-01-01&check_in_town=prazan&check_out=0001-01-01&check_out_town=prazan&passengers=0&id_rez=0';
	}
		
}
$(document).ready(function()
{
	$.ajaxSetup({
	    headers:{
	        'Authorization': 'Bearer ' + localStorage.token
	    }
	});
	
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth()+1; //January is 0!
	var yyyy = today.getFullYear();
	 if(dd<10){
	        dd='0'+dd
	    } 
	    if(mm<10){
	        mm='0'+mm
	    } 

	today = yyyy+'-'+mm+'-'+dd;
	$('input[name=check_in_hotel]').attr("min", today);
	$('input[name=check_out_hotel]').attr("min", today);
	$('input[name=check_in_car]').attr("min", today);
	$('input[name=check_out_car]').attr("min", today);
	
	console.log('[search.js: showContentHotelSearch()]: document.ready()');
	var search = window.location.search;
	
	var splitted = search.split('&');
	
	if(search.indexOf('id_rez') !== -1)
	{
		if(search.indexOf('hotel') !== -1){
			$('.search_tab').removeClass('active');
			//$(this).addClass('active');
			//var clickedIndex = $('.search_tab').index(this);

			var panels = $('.search_panel');
			panels.removeClass('active');
			$(panels[1]).addClass('active');
		
		}
		else if(search.indexOf('rentacar') !== -1)
		{
			$('.search_tab').removeClass('active');
			//$(this).addClass('active');
			//var clickedIndex = $('.search_tab').index(this);

			var panels = $('.search_panel');
			panels.removeClass('active');
			$(panels[2]).addClass('active');
		}
		else
		{
			$('.search_tab').removeClass('active');
			//$(this).addClass('active');
			//var clickedIndex = $('.search_tab').index(this);

			var panels = $('.search_panel');
			panels.removeClass('active');
			$(panels[0]).addClass('active');
		}
	}
	
	$('.search_tab').removeClass('active');
	
	if(search.indexOf('hotel') !== -1){
		$('div[onclick="javascript:showContentHotel()"]').addClass('active');

		var panels = $('.search_panel');
		panels.removeClass('active');
		$(panels[1]).addClass('active');
		
		var name_location = splitted[0].substring(21);
		var check_in_fake = splitted[1].substring(15);
		var check_out_fake = splitted[2].substring(16);
		var adults = 0;
		var id_rez = 0;
		
		if(splitted[3].length > 13)
		{
			adults = splitted[3].substring(13);
		}
		
		if(splitted.length > 4)
		{
			id_rez=splitted[4].substring(7);
			$('input[name="name_location_hotel"]').prop('readonly', true);
			$('input[name="check_in_hotel"]').prop('readonly', true);
			$('input[name="check_out_hotel"]').prop('readonly', true);
			$('input[name="adults_hotel"]').prop('readonly', true);
			
			$('#hotel_search_button').hide();

		}
			
		
		var date_check_in = new Date(check_in_fake);
		var date_check_out = new Date(check_out_fake);
		var date_now = new Date();
		date_now.setHours(0);
		date_now.setMinutes(0);
		date_now.setSeconds(0);
		
		console.log('Date now: ' + date_now + '; date_check_in: ' + date_check_in + '; date_check_out: ' + date_check_out);
		
		if(date_check_in > date_check_out)
		{
			//alert('datum dolaska je nakon datuma polaska');
			document.getElementById("error_date").style.display='block';
			document.getElementById("error_date").innerHTML = 'Check in date is after check out date.';
			
			name_location = name_location.split('+').join(' ');
			var check_in = check_in_fake.substring(0, 4) + '-' + check_in_fake.substring(5, 7) + '-' + check_in_fake.substring(8, 10);
			var check_out = check_out_fake.substring(0, 4) + '-' + check_out_fake.substring(5, 7) + '-' + check_out_fake.substring(8, 10);
			
			$('input[name="name_location_hotel"]').val(name_location);
			$('input[name="check_in_hotel"]').val(check_in_fake);
			$('input[name="check_out_hotel"]').val(check_out_fake);
			$('input[name="adults_hotel"]').val(adults);
		}
		else if(date_check_in < date_now || date_check_out < date_now)
		{
			document.getElementById("error_date").style.display='block';
			document.getElementById("error_date").innerHTML = 'Check in and check out date must be in the future.';
			
			name_location = name_location.split('+').join(' ');
			var check_in = check_in_fake.substring(0, 4) + '-' + check_in_fake.substring(5, 7) + '-' + check_in_fake.substring(8, 10);
			var check_out = check_out_fake.substring(0, 4) + '-' + check_out_fake.substring(5, 7) + '-' + check_out_fake.substring(8, 10);
			
			$('input[name="name_location_hotel"]').val(name_location);
			$('input[name="check_in_hotel"]').val(check_in_fake);
			$('input[name="check_out_hotel"]').val(check_out_fake);
			$('input[name="adults_hotel"]').val(adults);
		}
		else
		{
			document.getElementById("error_date").style.display='none';
			
			name_location = name_location.split('+').join(' ');
			var check_in = check_in_fake.substring(0, 4) + '-' + check_in_fake.substring(5, 7) + '-' + check_in_fake.substring(8, 10);
			var check_out = check_out_fake.substring(0, 4) + '-' + check_out_fake.substring(5, 7) + '-' + check_out_fake.substring(8, 10);
			
			$('input[name="name_location_hotel"]').val(name_location);
			$('input[name="check_in_hotel"]').val(check_in_fake);
			$('input[name="check_out_hotel"]').val(check_out_fake);
			$('input[name="adults_hotel"]').val(adults);
			
			if(check_in == "--")
				check_in = "0001-01-01";
			if(check_out == "--")
				check_out = "0001-01-01";
			
			$.get({
				url: '/api/hotels/search/' + name_location + '/' + check_in + '/' + check_out + '/' + adults,
				success: function(hoteli) {
					
					if(hoteli == null || hoteli.length == 0){
						document.getElementById('hotel_title').innerHTML = 'There are no hotels for this search.';
						document.getElementById('ubaci_hotele_template').style.display='block';
						document.getElementById("aviokompanije").style.display='none';
						document.getElementById("aviokompanije_naslov").style.display='none';
					}
					else {
						console.log('There are ' + hoteli.length + ' hotels for this search.');
						document.getElementById('hotel_title').innerHTML = 'Results of search:';
						document.getElementById('ubaci_hotele_template').style.display='block';
						document.getElementById("aviokompanije").style.display='none';
						document.getElementById("aviokompanije_naslov").style.display='none';
						for (let hotel of hoteli) 
						{
							addHotelLi(hotel);
						}
					}

				},
				error : function(data){
					alert('Error!');
					document.getElementById('hotel_title').innerHTML = '';
				}
			});
			
			
		}
	}
	else if(search.indexOf('rentacar') !== -1)
	{
		console.log('pretrazi rentacar');
		$('div[onclick="javascript:showContentRent()"]').addClass('active');

		var panels = $('.search_panel');
		panels.removeClass('active');
		$(panels[2]).addClass('active');
		
		var name_location = splitted[0].substring(24);
		var check_in_fake = splitted[1].substring(13);
		var check_in_town=splitted[2].substring(14);
		var check_out_fake = splitted[3].substring(14);
		var check_out_town=splitted[4].substring(15);
		var passengers=0;
		var id_rez=0;
		if(splitted[5].length > 16)
		{
			passengers = splitted[5].substring(16);
			
		}
		if(splitted.length > 6)
			id_rez=splitted[6].substring(7);
		
		
		var date_check_in = new Date(check_in_fake);
		var date_check_out = new Date(check_out_fake);
		var date_now = new Date();
		date_now.setHours(0);
		date_now.setMinutes(0);
		date_now.setSeconds(0);
		
		if(date_check_in > date_check_out)
		{
			document.getElementById("error_date").style.display='block';
			document.getElementById("error_date").innerHTML = 'Check in date is after check out date.';
			
			name_location = name_location.split('+').join(' ');
			var check_in = check_in_fake.substring(0, 4) + '-' + check_in_fake.substring(5, 7) + '-' + check_in_fake.substring(8, 10);
			var check_out = check_out_fake.substring(0, 4) + '-' + check_out_fake.substring(5, 7) + '-' + check_out_fake.substring(8, 10);
			check_in_town=check_in_town.split('+').join(' ');
			check_out_town=check_out_town.split('+').join(' ');
				
			$('input[name="name_location_rentacar"]').val(name_location);
			$('input[name="check_in_car"]').val(check_in_fake);
			$('input[name="check_out_car"]').val(check_out_fake);
			$('input[name="check_in_town"]').val(check_in_town);
			$('input[name="check_out_town"]').val(check_out_town);
			$('input[name="passengers_rent"]').val(passengers);
			
		}
		else if(date_check_in < date_now || date_check_out < date_now)
		{
			document.getElementById("error_date").style.display='block';
			document.getElementById("error_date").innerHTML = 'Check in and check out date must be in the future.';
			
			name_location = name_location.split('+').join(' ');
			var check_in = check_in_fake.substring(0, 4) + '-' + check_in_fake.substring(5, 7) + '-' + check_in_fake.substring(8, 10);
			var check_out = check_out_fake.substring(0, 4) + '-' + check_out_fake.substring(5, 7) + '-' + check_out_fake.substring(8, 10);
			check_in_town=check_in_town.split('+').join(' ');
			check_out_town=check_out_town.split('+').join(' ');
			
			$('input[name="name_location_rentacar"]').val(name_location);
			$('input[name="check_in_car"]').val(check_in_fake);
			$('input[name="check_out_car"]').val(check_out_fake);
			$('input[name="check_in_town"]').val(check_in_town);
			$('input[name="check_out_town"]').val(check_out_town);
			$('input[name="passengers_rent"]').val(passengers);
		}
		else
		{
			document.getElementById("error_date_rentacar").style.display='none';
			
			name_location = name_location.split('+').join(' ');
			var check_in = check_in_fake.substring(0, 4) + '-' + check_in_fake.substring(5, 7) + '-' + check_in_fake.substring(8, 10);
			var check_out = check_out_fake.substring(0, 4) + '-' + check_out_fake.substring(5, 7) + '-' + check_out_fake.substring(8, 10);
			check_in_town=check_in_town.split('+').join(' ');
			check_out_town=check_out_town.split('+').join(' ');
			
			$('input[name="name_location_rentacar"]').val(name_location);
			$('input[name="check_in_car"]').val(check_in_fake);
			$('input[name="check_out_car"]').val(check_out_fake);
			$('input[name="check_in_town"]').val(check_in_town);
			$('input[name="check_out_town"]').val(check_out_town);
			$('input[name="passengers_rent"]').val(passengers);
			
			if(id_rez!=0)
			{
				$('input[name="name_location_rentacar"]').prop('readonly', true);
				$('input[name="check_in_car"]').prop('readonly', true);
				$('input[name="check_out_car"]').prop('readonly', true);
				$('input[name="check_in_town"]').prop('readonly', true);
				$('input[name="check_out_town"]').prop('readonly', true);
				$('input[name="passengers_rent"]').prop('readonly', true);
				
				$('#rent_search_butt').hide();
			}
			
			if(check_in == "--")
				check_in = "0001-01-01";
			if(check_out == "--")
				check_out = "0001-01-01";
			
			$.get({
				url: '/api/rents/search/' + name_location + '/' + check_in + '/' + check_out + '/' + check_in_town + '/' + check_out_town  + '/' + passengers,
				success: function(rents) {
					
					if(rents == null || rents.length == 0){
						document.getElementById('rentacar_title').innerHTML = 'There are no rentacar services for this search.';
						document.getElementById('ubaci_rentacar_template').style.display='block';
						document.getElementById("aviokompanije").style.display='none';
						document.getElementById("aviokompanije_naslov").style.display='none';
					}
					else {
						console.log('There are ' + rents.length + ' rentacar services for this search.');
						document.getElementById('rentacar_title').innerHTML = 'Results of search:';
						document.getElementById('ubaci_rentacar_template').style.display='block';
						document.getElementById("aviokompanije").style.display='none';
						document.getElementById("aviokompanije_naslov").style.display='none';
						for (let rentacar of rents) 
						{
							addRentacar(rentacar);
						}
					}

				},
				error : function(data){
					alert('Error!');
					document.getElementById('rentacar_title').innerHTML = '';
				}
			});
		}
	}
	
});
