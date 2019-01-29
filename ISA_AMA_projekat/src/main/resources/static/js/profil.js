$(document).ready(function(){
	var token=localStorage.getItem('jwtToken');
	$.ajaxSetup({
	    headers:{
	        'Authorization': 'Bearer ' + token
	    }
	});
	
	$.post({
		url: "/auth/userprofile",
		headers: 'Authorization',
		contentType: 'application/json',
		data : token,
		  
		success: function(user) {
			document.getElementById("naslov").innerHTML = 'My profile <br/>' + user.ime + ' ' + user.prezime;
			document.getElementById("name_profile").value  = user.ime;
			document.getElementById("lastname_profile").value  = user.prezime;
			document.getElementById("username_profile").value  = user.ime;
			document.getElementById("password_profile").value  = user.lozinka;
			document.getElementById("email_profile").value  = user.email;
			document.getElementById("phone_profile").value  = user.telefon;
			document.getElementById("city_profile").value  = user.grad.naziv;
			document.getElementById("address_profile").value  = user.grad.naziv;
		},
		
		error: function() {
			alert('Error');
		}
	
	});
});

function newAirline()
{
	console.log('[profil.js: newAirline()]');
	document.getElementById("div_new").style.display  = 'block';
	document.getElementById("div_buttons").style.display  = 'none';
	document.getElementById('button_add').innerHTML = 'Add airline';
}

function newHotel()
{
	console.log('[profil.js: newHotel()]');
	document.getElementById("div_new").style.display  = 'block';
	document.getElementById("div_buttons").style.display  = 'none';
	document.getElementById('button_add').innerHTML = 'Add hotel';
}

function newRental()
{
	console.log('[profil.js: newRental()]');
	document.getElementById("div_new").style.display  = 'block';
	document.getElementById("div_buttons").style.display  = 'none';
	document.getElementById('button_add').innerHTML = 'Add car rental';
}

function add()
{
	console.log('[profil.js: add()]');
	
	name = $('input[name="new_name"]').val();
	city = $('input[name="new_city"]').val();
	street = $('input[name="new_street"]').val();
	number = $('input[name="new_street_number"]').val();
	latitude = $('input[name="new_latitude"]').val();
	longitude = $('input[name="new_longitude"]').val();
	description = $('input[name="new_description"]').val();
	
	$.get({
		url: "/api/address/checkCity/" + city,
		  
		success: function(data) {
			if(data == null || data == "")
			{
				console.log('nije pronadjen grad');
				newCity(city);
			}
			else
			{
				console.log('pronadjen grad: ' + data.naziv);
				checkAddress(data);
			}
			window.location.search='';
		},
		
		error: function() {
			alert('Error with loading city');
		}
	
	});
	
}

function newCity(city)
{
	console.log('[profil.js: newCity()]');
	
	$.post({
		url: "/api/address/newCity/" + city,
		  
		success: function(data) {
			if(data == null || data == "")
			{
				console.log('nije sacuvan grad');
			}
			else
			{
				console.log('sacuvan grad: ' + data.naziv);
				checkAddress(data);
			}
		},
		
		error: function() {
			alert('Error with saving city');
		}
	
	});
}

function checkAddress(newCity)
{
	console.log('[profil.js: checkAddress()]');
	
	$.get({
		url: "/api/address/checkAddress/" + street + '/' + number + '/' + longitude + '/' + latitude + '/' + newCity.id,
		success: function(data) {
			if(data == null || data == "")
			{
				console.log('nije pronadjena adresa');
				finalAdd(data);
			}
			else
			{
				console.log('pronadjena adresa sa id: ' + data.id);
				finalAdd(data);
			}
		},
		
		error: function() {
			alert('Error with saving address');
		}
	
	});
}

function finalAdd(address)
{
	console.log('[profil.js: finalAdd()]');
	
	if(document.getElementById('button_add').innerHTML == 'Add airline')
	{
		$.post({
			url: "/rest/airline/save",
			data: JSON.stringify({naziv: name, opis: description, adresa: address}),
			contentType: 'application/json',
			  
			success: function(airline) {
				console.log('aviokompanija uspesno uneta');
				var url = window.location.search='';
			},
			
			error: function() {
				alert('Error');
			}
		
		});
	}
	else if(document.getElementById('button_add').innerHTML == 'Add hotel')
	{
		$.post({
			url: "/api/hotels/save",
			data: JSON.stringify({naziv: name, promotivni_opis: description, adresa: address}),
			contentType: 'application/json',
			  
			success: function(airline) {
				console.log('hotel uspesno unet');
				var url = window.location.search='';
			},
			
			error: function() {
				alert('Error');
			}
		
		});
	}
	else
	{
		$.post({
			url: "/api/rents/save",
			data: JSON.stringify({naziv: name, promotivni_opis: description, adresa: address}),
			contentType: 'application/json',
			  
			success: function(airline) {
				console.log('rental uspesno unet');
				var url = window.location.search='';
			},
			
			error: function() {
				alert('Error');
			}
		
		});
	}
}