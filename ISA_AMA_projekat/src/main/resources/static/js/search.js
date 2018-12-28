function showContentHotel() {
	
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

function addHotelLi(hotel) {
	console.log("naziv hotela: " + hotel.naziv);
	var temp, div, a, i, text, name;
	temp = document.getElementById("template_hotel");
	div = temp.content.querySelector("div#ubaci");
	
    temp.content.getElementById("name_hotel").innerHTML = hotel.naziv;
    temp.content.getElementById("text_hotel").innerHTML = hotel.adresa;
    temp.content.getElementById("rating_hotel").innerHTML = hotel.prosecna_ocena;
    temp.content.getElementById("dugme_view_details").innerHTML = '<a href="single_listing_hotel.html?id=' + hotel.id +'">view details</a>';
    
    a = document.importNode(div, true);
    document.getElementById("ubaci_hotele_template").appendChild(a);
    
}

function showContentAvio() {
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

function showContentRent() {
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
				/*document.getElementById("aviokompanije").style.display='none';
				document.getElementById("aviokompanije_naslov").style.display='none';
				document.getElementById("ubaci_hotel_template").style.display='none';
				document.getElementById("hotel_title").style.display='none';*/
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

function addRentacar(rentacar) {
	console.log("naziv rentacar servisa: " + rentacar.naziv);
	for (let fil of rentacar.filijale)
	console.log("adresa filijale rentacar servisa: " + fil.adresa);
	var temp, div, a, i, text, name;
	temp = document.getElementById("template_rentacar");
	div = temp.content.querySelector("div#ubaciRentacar");
	
    temp.content.getElementById("name_rentacar").innerHTML = rentacar.naziv;
    temp.content.getElementById("text_rentacar").innerHTML = rentacar.adresa;
    temp.content.getElementById("rating_rentacar").innerHTML = rentacar.prosecna_ocena;
    temp.content.getElementById("dugme_view_details_rentacar").innerHTML = '<a href="single_listing_rentacar.html?id=' + rentacar.id +'">view details</a>';
    
    a = document.importNode(div, true);
    document.getElementById("ubaci_rentacar_template").appendChild(a);
    
}

$(document).ready(function()
{
	console.log('[search.js: showContentHotelSearch()]: document.ready()');
	var search = window.location.search;
	
	var splitted = search.split('&');
	
	$('.search_tab').removeClass('active');
	if(search.indexOf('hotel') !== -1){
		$('div[onclick="javascript:showContentHotel()"]').addClass('active');

		var panels = $('.search_panel');
		panels.removeClass('active');
		$(panels[1]).addClass('active');
		
		var name_location = splitted[0].substring(21);
		var check_in_fake = splitted[1].substring(15);
		var check_out_fake = splitted[2].substring(16);
		
		var date_check_in = new Date(check_in_fake);
		var date_check_out = new Date(check_out_fake);
		
		if(date_check_in > date_check_out)
		{
			//alert('datum dolaska je nakon datuma polaska');
			document.getElementById("error_date").style.display='block';
			
			name_location = name_location.split('+').join(' ');
			var check_in = check_in_fake.substring(0, 4) + '-' + check_in_fake.substring(5, 7) + '-' + check_in_fake.substring(8, 10);
			var check_out = check_out_fake.substring(0, 4) + '-' + check_out_fake.substring(5, 7) + '-' + check_out_fake.substring(8, 10);
			
			$('input[name="name_location_hotel"]').val(name_location);
			$('input[name="check_in_hotel"]').val(check_in_fake);
			$('input[name="check_out_hotel"]').val(check_out_fake);
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
			
			if(check_in == "--")
				check_in = "0001-01-01";
			if(check_out == "--")
				check_out = "0001-01-01";
			
			$.get({
				url: '/api/hotels/search/' + name_location + '/' + check_in + '/' + check_out,
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
	
});
