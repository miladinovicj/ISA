$(document).ready(function()	
{
	let token = localStorage.getItem('jwtToken');
	
	var search = window.location.search;
    id_presented = search.substring(4);
    $('.new_additional_service').hide();
    
    console.log('[hotelAdmin.js: document.ready()]: id hotela: ' + id_presented);

    $.ajax({
        type: 'GET',
        url: 'api/hotels/admin/' + id_presented,
        headers: {"Authorization": "Bearer " + token},
        contentType: 'application/json',
        success: function (hotel)
		{
        	moj_hotel = hotel;
        	longitudeMap = hotel.adresa.longitude;
        	latitudeMap = hotel.adresa.latitude;
            console.log(hotel);
            showHotel(hotel);
            initMap();
		},
		error: function(data){
			console.log('greska prilikom ucitavanja stranice za admina hotela');
		}
    });
    
    $('textarea').on('keyup', function(){
    	  $(this).val($(this).val().replace(/[\r\n\v]+/g, ''));
    });

	
	$('#edit_hotel_form').submit(function(event) {
		console.log('edit_hotel_form submit');
		event.preventDefault();
		
		var attr = $('#button_hotel_back').attr('pritisnuto');
		
		if (typeof attr !== typeof undefined && attr !== false)
		{
			$('#button_hotel_back').removeAttr('pritisnuto');
		}
		else
		{
			console.log('promena hotela');
			
			name = $('input[name="hotel_name"]').val();
			city = $('input[name="hotel_city"]').val();
			street = $('input[name="hotel_street"]').val();
			number = $('input[name="hotel_street_number"]').val();
			latitude = $('input[name="hotel_latitude"]').val();
			longitude = $('input[name="hotel_longitude"]').val();
			description = $('textarea[name="hotel_description"]').val();
			
			let ispravno = true;
			
			if(!(/[A-Z]/.test(name[0])))
			{
				document.getElementById("error_hotel_name").innerHTML = "First letter of name must be capital.";
				document.getElementById("error_hotel_name").style.display  = 'block';
				ispravno=false;
			}
			else
			{
				document.getElementById("error_hotel_name").innerHTML = "";
				document.getElementById("error_hotel_name").style.display  = 'none';
			}
				
		
			if(!(/^[a-zA-ZćĆčČšŠđĐžŽ ]+$/.test(city)))
			{
				document.getElementById("error_hotel_city").innerHTML = "Field City must contains only the letters.";
				document.getElementById("error_hotel_city").style.display  = 'block';
				ispravno=false;
			}
			else if(!(/[A-Z]/.test( city[0])))
			{
				document.getElementById("error_hotel_city").innerHTML = "First letter of the city's name must be capital.";
				document.getElementById("error_hotel_city").style.display  = 'block';
				ispravno=false;
			}
			else
			{
				document.getElementById('error_hotel_city').innerHTML = "";
				document.getElementById("error_hotel_city").style.display  = 'none';
			}
			
			
			if(!(/[A-Z]/.test( street[0])))
			{
				document.getElementById("error_hotel_street").innerHTML = "First letter of the street's name must be capital.";
				document.getElementById("error_hotel_street").style.display  = 'block';
				ispravno=false;
			}
			else
			{
				document.getElementById('error_hotel_street').innerHTML = "";
				document.getElementById("error_hotel_street").style.display  = 'none';
			}
				
			
			if(!(/^[0-9.]+$/.test(latitude)))
			{
				document.getElementById("error_hotel_latitude").innerHTML = "Field Latitude must contains only numbers.";
				document.getElementById("error_hotel_latitude").style.display  = 'block';
				ispravno=false;
			}
			else
			{
				document.getElementById("error_hotel_latitude").innerHTML = "";
				document.getElementById("error_hotel_latitude").style.display  = 'none';
			}
			
			
			if(!(/^[0-9.]+$/.test(longitude)))
			{
				document.getElementById("error_hotel_longitude").innerHTML = "Field Latitude must contains only numbers.";
				document.getElementById("error_hotel_longitude").style.display  = 'block';
				ispravno=false;
			}
			else
			{
				document.getElementById("error_hotel_longitude").innerHTML = "";
				document.getElementById("error_hotel_longitude").style.display  = 'none';
			}
			
			if(ispravno == true)
			{
				lat = parseFloat(latitude);
				long = parseFloat(longitude)
				
				$.get({
					url: "/api/address/check/" + city +'/' + street + '/' + number + '/' + latitude + '/' + longitude,
					success: function(data) {
						if(data == null || data == "")
						{
							console.log('nije pronadjena adresa');
						}
						else
						{
							console.log('pronadjena adresa sa id: ' + data.id);
							finalEdit(data);
						}
						
					},
					
					error: function(data) {
						alert('Error with loading address');
					}
				
				});
				
			}
		}
	});
	

	$('#change_price_form').submit(function(event) {
		console.log('add_form_admin submit');
		event.preventDefault();
		
		var attr = $('#button_hotel_back').attr('pritisnuto');
		var attr2 = $('#button_add_service').attr('pritisnuto');
		
		if (typeof attr !== typeof undefined && attr !== false)
		{
			$('#button_hotel_back').removeAttr('pritisnuto');
		}
		else if (typeof attr2 !== typeof undefined && attr2 !== false)
		{
			$('#button_add_service').removeAttr('pritisnuto');
			if($('#button_add_service').text() == 'Add service')
			{
				
				
				let service = $('input[name="new_service"]').val();
				let price_service = $('input[name="new_price_service"]').val();
				
				let ispravno = true;
				
				if(!(/^[0-9]+$/.test(price_service)))
				{
					document.getElementById("error_new_price_service").style.display  = 'block';
					ispravno=false;
				}
				else
				{
					document.getElementById("error_new_price_service").style.display  = 'none';
				}
				
				if(ispravno == true)
				{
					console.log('dodaj novu uslugu zahtev');
					
					$.post({
						url: '/api/usluge/dodaj_uslugu/' + service + '/'  + price_service + '/' + id_presented,
						headers: {"Authorization": "Bearer " + token},
						contentType: 'application/json',
						success: function(data) {
							if(data==null || data==""){
								console.log('Nije dodata usluga');
							}
							else {
								
								console.log('Usluga dodata!');
								window.location.href="hotelAdmin.html?id=" + id_presented;
											
							}
						}
					});
				}
			}
		}
		else
		{
			console.log('promena cene');
		
			let price = $('input[name=price_service]').val();
			let id = $( "select[name=select_services] option:selected" ).val();
			
			let ispravno = true;
			
			if(!(/^[0-9]+$/.test(price)))
			{
				document.getElementById("error_price_service").style.display  = 'block';
				ispravno=false;
			}
			else
			{
				document.getElementById("error_price_service").style.display  = 'none';
			}
			
			if(ispravno == true)
			{
				$.post({
					url: '/api/usluge/admin/izmenaUsluge/' + id + '/'  + price,
					headers: {"Authorization": "Bearer " + token},
					contentType: 'application/json',
					success: function(data) {
						if(data==null || data==""){
							console.log('Nije izmenjena cena usluge!');
						}
						else {
							
							console.log('Cost of additional service successfully changed!');
							window.location.href="hotelAdmin.html?id=" + id_presented;
										
						}
					}
				});
			}
			
		}
	});
	
});

function finalEdit(address)
{
	$.post({
		url: "/api/hotels/editHotel/" + id_presented,
		data: JSON.stringify({naziv: name, promotivni_opis: description, adresa: address}),
		contentType: 'application/json',
		success: function(data) {
			if(data.result == 'success')
			{
				console.log('uspesna promena podataka hotela');
				window.location.href = 'hotelAdmin.html?id=' + id_presented;
			}
			else
			{
				console.log('neuspesna promena podataka hotela');
				window.location.href = 'hotelAdmin.html?id=' + id_presented;
			}
		}
	})
}

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
    	
    	var select = $('select[name=select_services]');
		select.children().remove();
		
    	for (let usluga of hotel.usluge) 
    	{
    		let li = $('<li><span>' + usluga.naziv + ' - $' + usluga.cena + '/per day</span></li>');
    		$('#usluge_hotela').append(li);
    		
    		var option = document.createElement("option");
			option.text = usluga.naziv;
			option.value = usluga.id;
			select.append(option);
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

function editHotel()
{
	$('input[name=hotel_name').val(moj_hotel.naziv);
    $('input[name=hotel_city').val(moj_hotel.adresa.grad.naziv);
    $('input[name=hotel_street').val(moj_hotel.adresa.ulica);
    $('input[name=hotel_street_number').val(moj_hotel.adresa.broj);
    $('input[name=hotel_latitude').val(moj_hotel.adresa.latitude);
    $('input[name=hotel_longitude').val(moj_hotel.adresa.longitude);
    $('textarea[name=hotel_description').val(moj_hotel.promotivni_opis);
    
    $('#div_edit_services').hide();
	$('#div_edit_hotel').show();
	var el = document.getElementById('div_edit_hotel');
    el.scrollIntoView(true);
    window.scrollBy(0, -100);
}

function editServices()
{
	console.log('editing services');
	$('#div_edit_hotel').hide();
	
	$('#div_edit_services').show();
	var el = document.getElementById('div_edit_services');
    el.scrollIntoView(true);
    window.scrollBy(0, -350);
    changeSelection();
}

function editRooms()
{
	
}

function backEdit()
{
	$('#div_edit_hotel').hide();
	$('#div_edit_services').hide();
	$('.new_additional_service').hide();
	
	$('#button_hotel_back').attr('pritisnuto', 'true');
	
	var el = document.getElementById('div_changing_buttons');
    el.scrollIntoView(true);
    window.scrollBy(0, -500);
}

function changeSelection()
{
	let id = $( "select[name=select_services] option:selected" ).val();
	
	$.get({
		url: "/api/usluge/get/" + id,
		success: function(price) {
			$('input[name=price_service]').val(price);
		},
		
		error: function(data) {
			console.log('Error with loading service');
		}
	
	});
	
}

function addService()
{
	$('#button_add_service').attr('pritisnuto', 'true');
	$('.new_additional_service').show();
	$('#button_add_service').text('Add service');
	
	$('input[name=new_price_service]').prop('required',true);
	$('input[name=new_service]').prop('required',true);
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
          center: ol.proj.fromLonLat([longitudeMap, latitudeMap]),
          zoom: 17
        })
      });
}