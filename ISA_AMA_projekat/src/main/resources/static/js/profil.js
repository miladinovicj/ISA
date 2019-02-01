$(document).ready(function(){
	token=localStorage.getItem('jwtToken');
	
	
	
	$.post({
		url: "/auth/userprofile",
		headers: {"Authorization": "Bearer " + token},
		contentType: 'application/json',
		data : token,
		  
		success: function(user) {
			
			if(user != "")
			{
				korisnik = user;
				
				if(korisnik.authority.authority!="ROLE_SYSADMIN")
				{
					$("#div_buttons").hide();
					$("#div_buttons_admin").hide();
					$("#div_buttons_system_admin").hide();
					
				}
				else
				{
					$("#div_buttons").show();
					$("#div_buttons_admin").show();
					$("#div_buttons_system_admin").show();
					
				}
				
				document.getElementById("naslov").innerHTML = 'My profile <br/>' + user.ime + ' ' + user.prezime;
				document.getElementById("name_profile").value  = user.ime;
				document.getElementById("lastname_profile").value  = user.prezime;
				document.getElementById("email_profile").value  = user.email;
				document.getElementById("phone_profile").value  = user.telefon;
				document.getElementById("city_profile").value  = user.grad.naziv;
				document.getElementById("bonus_profile").value  = user.bonuspoeni;
			}
			else
			{
				localStorage.clear();
				window.location.href = 'index.html';
			}
			
		},
		
		error: function() {
			//alert('Error');
			console.log('istekao je token');
			localStorage.clear();
			window.location.href = 'index.html';
			
		}
	
	});
	
	$('#add_form').submit(function(event) {
		console.log('add_form submit');
		event.preventDefault();
		
		name = $('input[name="new_name"]').val();
		city = $('input[name="new_city"]').val();
		street = $('input[name="new_street"]').val();
		number = $('input[name="new_street_number"]').val();
		latitude = $('input[name="new_latitude"]').val();
		longitude = $('input[name="new_longitude"]').val();
		description = $('input[name="new_description"]').val();
		
		let ispravno = true;
		
		if(!(/[A-Z]/.test(name[0])))
		{
			document.getElementById("error_new_name").innerHTML = "First letter of name must be capital.";
			document.getElementById("error_new_name").style.display  = 'block';
			ispravno=false;
		}
		else
		{
			document.getElementById("error_new_name").innerHTML = "";
			document.getElementById("error_new_name").style.display  = 'none';
		}
			
	
		if(!(/^[a-zA-ZćĆčČšŠđĐžŽ ]+$/.test(city)))
		{
			document.getElementById("error_new_city").innerHTML = "Field City must contains only the letters.";
			document.getElementById("error_new_city").style.display  = 'block';
			ispravno=false;
		}
		else if(!(/[A-Z]/.test( city[0])))
		{
			document.getElementById("error_new_city").innerHTML = "First letter of the city's name must be capital.";
			document.getElementById("error_new_city").style.display  = 'block';
			ispravno=false;
		}
		else
		{
			document.getElementById('error_new_city').innerHTML = "";
			document.getElementById("error_new_city").style.display  = 'none';
		}
		
		
		if(!(/[A-Z]/.test( street[0])))
		{
			document.getElementById("error_new_street").innerHTML = "First letter of the street's name must be capital.";
			document.getElementById("error_new_street").style.display  = 'block';
			ispravno=false;
		}
		else
		{
			document.getElementById('error_new_street').innerHTML = "";
			document.getElementById("error_new_street").style.display  = 'none';
		}
			
		
		if(!(/^[0-9.]+$/.test(latitude)))
		{
			document.getElementById("error_new_latitude").innerHTML = "Field Latitude number must contains only numbers.";
			document.getElementById("error_new_latitude").style.display  = 'block';
			ispravno=false;
		}
		else
		{
			document.getElementById("error_new_latitude").innerHTML = "";
			document.getElementById("error_new_latitude").style.display  = 'none';
		}
		
		
		if(!(/^[0-9.]+$/.test(longitude)))
		{
			document.getElementById("error_new_longitude").innerHTML = "Field Latitude number must contains only numbers.";
			document.getElementById("error_new_longitude").style.display  = 'block';
			ispravno=false;
		}
		else
		{
			document.getElementById("error_new_longitude").innerHTML = "";
			document.getElementById("error_new_longitude").style.display  = 'none';
		}
		
		if(ispravno == true)
		{
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
			
	});
	
	$('#add_form_admin').submit(function(event) {
		console.log('add_form_admin submit');
		event.preventDefault();
		
		let name_admin = $('input[name="first_name_admin"]').val();
		let last_name_admin = $('input[name="last_name_admin"]').val();
		let email_admin = $('input[name="email_admin"]').val();
		let pass_admin = $('input[name="pass_admin"]').val();
		let city_admin = $('input[name="city_admin"]').val();
		let phone_admin = $('input[name="phone_admin"]').val();
		let option = $( "select[name=admin_id] option:selected" ).val();
		option = parseInt(option, 10);
		
		let ispravno = true;
		document.getElementById("error_email").innerHTML = "";
		document.getElementById("error_email").style.display  = 'none';
		
		if(!(/^[a-zA-ZćĆčČšŠđĐžŽ ]+$/.test(name_admin)))
		{
			document.getElementById("error_name").innerHTML = "First name must contains only the letters.";
			document.getElementById("error_name").style.display = 'block';
			ispravno=false;
		}
		else if(!(/[A-Z]/.test( name_admin[0])))
		{
			document.getElementById("error_name").innerHTML = "First letter of name must be capital.";
			document.getElementById("error_name").style.display  = 'block';
			ispravno=false;
		}
		else
		{
			document.getElementById("error_name").innerHTML = "";
			document.getElementById("error_name").style.display  = 'none';
		}
		
	
		if(!(/^[a-zA-ZćĆčČšŠđĐžŽ ]+$/.test(last_name_admin)))
		{
			document.getElementById("error_last_name").innerHTML = "Last name must contains only the letters.";
			document.getElementById("error_last_name").style.display  = 'block';
			ispravno=false;
		}
		else if(!(/[A-Z]/.test( last_name_admin[0])))
		{
			document.getElementById("error_last_name").innerHTML = "First letter of last name must be capital.";
			document.getElementById("error_last_name").style.display  = 'block';
			ispravno=false;
		}
		else
		{
			document.getElementById("error_last_name").innerHTML = ("");
			document.getElementById("error_last_name").style.display  = 'none';
		}
			
		
	
		if(!(/^[a-zA-ZćĆčČšŠđĐžŽ ]+$/.test(city_admin)))
		{
			document.getElementById("error_city").innerHTML = "Field City must contains only the letters.";
			document.getElementById("error_city").style.display  = 'block';
			ispravno=false;
		}
		else if(!(/[A-Z]/.test( city_admin[0])))
		{
			document.getElementById("error_city").innerHTML = "First letter of the city's name must be capital.";
			document.getElementById("error_city").style.display  = 'block';
			ispravno=false;
		}
		else
		{
			document.getElementById('error_city').innerHTML = "";
			document.getElementById("error_city").style.display  = 'none';
		}
			
		
		if(!(/^[0-9]+$/.test(phone_admin)))
		{
			document.getElementById("error_phone").innerHTML = "Field Phone number must contains only numbers.";
			document.getElementById("error_phone").style.display  = 'block';
			ispravno=false;
		}
		else
		{
			document.getElementById("error_phone").innerHTML = "";
			document.getElementById("error_phone").style.display  = 'none';
		}
		
		//atribut aktiviran ce se ovde koristiti kao identifikator uloge: 0-system admin; 1-avio admin; 2-hotel admin; 3-rental admin
		let uloga;
		
		if(document.getElementById('admin_id').innerHTML == 'Car rentals: ')
		{
			uloga = 3;
		}
		else if(document.getElementById('admin_id').innerHTML == 'Hotels: ')
		{
			uloga = 2;
		}
		else if(document.getElementById('admin_id').innerHTML == 'Airlines:')
		{
			uloga = 1;
		}
		else
		{
			uloga = 0;
		}
		
		if(ispravno == true)
		{
			$.post({
				url: "/api/users/registruj_admina/" + uloga,
				data: JSON.stringify({email: email_admin, lozinka: pass_admin, ime: name_admin, prezime: last_name_admin, grad: city_admin, telefon: phone_admin, admin_id: option}),
				headers: {"Authorization": "Bearer " + token},
				contentType: 'application/json',
				success: function(data) {
					if(data == null || data == ""){
						document.getElementById("error_email").innerHTML = 'User with this email already exists!';
						document.getElementById("error_email").style.display = 'block';
					}
					else {
						console.log('Uspesno ste dodali novog administratora');
						window.location.href="profil.html";
					
					}
				}
			
				
			});
		}
			
	});
	
	$('#search_form_1').submit(function(event) {
		console.log('search_form_1 submit');
		event.preventDefault();
		
		var attr = $('#passChange').attr('pomoc');
		let email_profile = $("#email_profile").val();
		
		if($('input[value=edit]').val() == 'edit' && (typeof attr == typeof undefined || attr == false))
		{
			$("#name_profile").prop('readonly', false);
			$("#lastname_profile").prop('readonly', false);
			$("#phone_profile").prop('readonly', false);
			$("#city_profile").prop('readonly', false);
			
			$('input[value=edit]').val('save changes');
			$('button[onclick="javascript:changePassword()"]').hide();
		}
		else if($('input[value="save changes"]').val() == 'save changes' && (typeof attr == typeof undefined || attr == false))
		{
			let name_profile = $("#name_profile").val();
			let lastname_profile = $("#lastname_profile").val();
			let email_profile = $("#email_profile").val();
			let phone_profile = $("#phone_profile").val();
			let city_profile = $("#city_profile").val();
			
			$.post({
				url: "/api/users/changeData/" + korisnik.email,
				data: JSON.stringify({email: email_profile, ime: name_profile, prezime: lastname_profile, grad: city_profile, telefon: phone_profile}),
				contentType: 'application/json',
				success: function() {
					console.log('uspesna promena podataka korisnika');
					window.location.href = 'profil.html';
				}
			});
		}
		else
		{
			console.log('password is changing');
			
			let ispravno = true;
			
			$('#error_old_pass_profile').hide();
			let old_password = $("#old_password_profile").val();
			let new_password = $("#new_password_profile").val();
			let confirm_new_password = $("#confirm_new_password_profile").val();
			
			if(!(new_password == confirm_new_password))
			{
				$('#error_pass_profile').show();
				ispravno=false;
			}
			else
				$('#error_pass_profile').hide();
			
			if(ispravno == true)
			{
				$.post({
					url: "/auth/change_password/",
					headers: {"Authorization": "Bearer " + token},
					data: JSON.stringify({oldPassword: old_password, newPassword: new_password}),
					contentType: 'application/json',
					success: function(data) {
						
						if(data.result == 'success')
						{
							console.log('uspesna promena lozinke korisnika');
							
							$('#passChange').removeAttr('pomoc');
							$("#old_password_profile").prop('required', false);
					        $("#new_password_profile").prop('required', false);
					        $("#confirm_new_password_profile").prop('required', false);
					        $('#error_old_pass_profile').hide();
					        
							loginAgain(email_profile, new_password);
						}
						else
						{
							$('#error_old_pass_profile').show();
						}
						
					},
					error: function(data)
					{
						console.log('greska prilikom menjanja lozinke');
					}
				});
				
					
				
			}
			
			
			
			
		}
		
	});
});
/*
function refresh()
{
	$.post({
		url: "/auth/refresh",
		headers: 'Authorization',
		contentType: 'application/json',
		  
		success: function(res) {
			if(res!="" && res.accessToken != null)
			{
				localStorage.setItem('jwtToken', res.accessToken);
			}
			else
			{
				localStorage.removeItem('jwtToken');
			}
		},
		
		error: function() {
			console.log('greska prilikom refresh-ovanja tokena');
		}
	
	});
}
*/
function loginAgain(email, pass)
{
	$.post({
		url: "/auth/login",
		data: JSON.stringify({email: email, lozinka: pass}),
		contentType: 'application/json',
		headers: 'Authorization',
		  
		success: function(res) {
			if(res!="" && res.accessToken != null)
			{
				localStorage.setItem('jwtToken', res.accessToken);
				window.location.href="profil.html";
			}
			else
			{
				console.log('greska prilikom logovanja sa novom lozinkom');
			}
				
		}
	});
}

function newAirline()
{
	console.log('[profil.js: newAirline()]');
	document.getElementById("div_new").style.display  = 'block';
	document.getElementById("div_buttons").style.display  = 'none';
	document.getElementById("div_new_admin").style.display  = 'none';
	document.getElementById("div_buttons_admin").style.display  = 'block';
	document.getElementById("div_buttons_system_admin").style.display  = 'block';
	document.getElementById('button_add').value = 'Add airline';
}

function newHotel()
{
	console.log('[profil.js: newHotel()]');
	document.getElementById("div_new").style.display  = 'block';
	document.getElementById("div_buttons").style.display  = 'none';
	document.getElementById("div_new_admin").style.display  = 'none';
	document.getElementById("div_buttons_admin").style.display  = 'block';
	document.getElementById("div_buttons_system_admin").style.display  = 'block';
	document.getElementById('button_add').value = 'Add hotel';
}

function newRental()
{
	console.log('[profil.js: newRental()]');
	document.getElementById("div_new").style.display  = 'block';
	document.getElementById("div_buttons").style.display  = 'none';
	document.getElementById("div_new_admin").style.display  = 'none';
	document.getElementById("div_buttons_admin").style.display  = 'block';
	document.getElementById("div_buttons_system_admin").style.display  = 'block';
	document.getElementById('button_add').value = 'Add car rental';
}

function back()
{
	console.log('[profil.js: back()]');
	document.getElementById("div_new").style.display  = 'none';
	document.getElementById("div_buttons").style.display  = 'block';
}

/*
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
*/

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
	
	if(document.getElementById('button_add').value == 'Add airline')
	{
		$.post({
			url: "/rest/airline/save",
			data: JSON.stringify({naziv: name, opis: description, adresa: address}),
			contentType: 'application/json',
			headers: {"Authorization": "Bearer " + token},
			success: function(airline) {
				console.log('aviokompanija uspesno uneta');
				var url = window.location.search='';
			},
			
			error: function() {
				alert('Error');
			}
		
		});
	}
	else if(document.getElementById('button_add').value == 'Add hotel')
	{
		$.post({
			url: "/api/hotels/save",
			data: JSON.stringify({naziv: name, promotivni_opis: description, adresa: address}),
			contentType: 'application/json',
			headers: {"Authorization": "Bearer " + token},
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
			headers: {"Authorization": "Bearer " + token},
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

function newAirlineAdmin()
{
	console.log('[profil.js: newAirlineAdmin()]');
	document.getElementById("div_new_admin").style.display  = 'block';
	document.getElementById("div_buttons_admin").style.display  = 'none';
	document.getElementById("div_buttons_system_admin").style.display  = 'none';
	document.getElementById("div_new").style.display  = 'none';
	document.getElementById("div_buttons").style.display  = 'block';
	document.getElementById('button_add_admin').value = 'Add airline administrator';
	document.getElementById('admin_sys_close').style.display = 'block';
	document.getElementById('admin_id').innerHTML = 'Airlines:';
	
	$.get({
		url: "/rest/airline/all_admin",
		headers: {"Authorization": "Bearer " + token},
		contentType: 'application/json',
		success: function(airlines) {

			if(airlines == null || airlines.length == 0)
			{
				console.log('There are no airlines');
				document.getElementById("div_new_admin").style.display  = 'none';
				document.getElementById('no_one').innerHTML = 'There are no airlines to add their administrators.';
				document.getElementById('no_one').style.display = 'block';
				document.getElementById('button_back_admin2').style.display = 'block';
			}
			else
			{
				console.log('There are ' + airlines.length + ' airlines in memory.');
				document.getElementById('no_one').style.display = 'none';
				
				var select = $('select[name=admin_id]');
				select.children().remove();
				for (let airline of airlines) 
				{
					var option = document.createElement("option");
					option.text = airline.naziv;
					option.value = airline.id;
					select.append(option);
				}
			}
		},
		error : function(data){
			alert('Error with loading airlines');
		}
	});
}

function newHotelAdmin()
{
	console.log('[profil.js: newHotelAdmin()]');
	document.getElementById("div_new_admin").style.display  = 'block';
	document.getElementById("div_buttons_admin").style.display  = 'none';
	document.getElementById("div_buttons_system_admin").style.display  = 'none';
	document.getElementById("div_new").style.display  = 'none';
	document.getElementById("div_buttons").style.display  = 'block';
	document.getElementById('button_add_admin').value = 'Add hotel administrator';
	document.getElementById('admin_sys_close').style.display = 'block';
	document.getElementById('admin_id').innerHTML = 'Hotels: ';
	
	$.get({
		url: "/api/hotels/all_admin",
		headers: {"Authorization": "Bearer " + token},
		contentType: 'application/json',
		success: function(hoteli) {

			if(hoteli == null || hoteli.length == 0)
			{
				console.log('There are no hotels');
				document.getElementById("div_new_admin").style.display  = 'none';
				document.getElementById('no_one').innerHTML = 'There are no hotels to add their administrators.';
				document.getElementById('no_one').style.display = 'block';
				document.getElementById('button_back_admin2').style.display = 'block';
			}
			else
			{
				console.log('There are ' + hoteli.length + ' hotels in memory.');
				document.getElementById('no_one').style.display = 'none';
				
				var select = $('select[name=admin_id]');
				select.children().remove();
				for (let hotel of hoteli) 
				{
					var option = document.createElement("option");
					option.text = hotel.naziv;
					option.value = hotel.id;
					select.append(option);
				}
			}
		},
		error : function(data){
			alert('Error with loading hotels');
		}
	});
}

function newRentalAdmin()
{
	console.log('[profil.js: newRentalAdmin()]');
	document.getElementById("div_new_admin").style.display  = 'block';
	document.getElementById("div_buttons_admin").style.display  = 'none';
	document.getElementById("div_buttons_system_admin").style.display  = 'none';
	document.getElementById("div_new").style.display  = 'none';
	document.getElementById("div_buttons").style.display  = 'block';
	document.getElementById('button_add_admin').value = 'Add car rental administrator';
	document.getElementById('admin_sys_close').style.display = 'block';
	document.getElementById('admin_id').innerHTML = 'Car rentals: ';
	
	$.get({
		url: "/api/rents/all_admin",
		headers: {"Authorization": "Bearer " + token},
		contentType: 'application/json',
		success: function(rentals) {

			if(rentals == null || rentals.length == 0)
			{
				console.log('There are no rentals');
				document.getElementById("div_new_admin").style.display  = 'none';
				document.getElementById('no_one').innerHTML = 'There are no rentals to add their administrators.';
				document.getElementById('no_one').style.display = 'block';
				document.getElementById('button_back_admin2').style.display = 'block';
			}
			else
			{
				console.log('There are ' + rentals.length + ' rentals in memory.');
				document.getElementById('no_one').style.display = 'none';
				
				var select = $('select[name=admin_id]');
				select.children().remove();
				for (let rental of rentals) 
				{
					var option = document.createElement("option");
					option.text = rental.naziv;
					option.value = rental.id;
					select.append(option);
				}
			}
		},
		error : function(data){
			alert('Error with loading rentals');
		}
	});
}

function newSystemAdmin()
{
	console.log('[profil.js: newSystemAdmin()]');
	document.getElementById("div_new_admin").style.display  = 'block';
	document.getElementById("div_buttons_system_admin").style.display  = 'none';
	document.getElementById("div_buttons_admin").style.display  = 'none';
	document.getElementById("div_new").style.display  = 'none';
	document.getElementById("div_buttons").style.display  = 'block';
	document.getElementById('button_add_admin').value = 'Add system administrator';
	document.getElementById('admin_sys_close').style.display = 'none';
}

function backAdmin()
{
	console.log('[profil.js: backAdmin()]');
	document.getElementById("div_new_admin").style.display  = 'none';
	document.getElementById("div_buttons_admin").style.display  = 'block';
	document.getElementById("no_one").style.display  = 'none';
	document.getElementById("button_back_admin2").style.display  = 'none';
}

function changePassword()
{
	console.log('changing password');
	
	$('#passChange').show();
	//$('#passChange').attr('pomoc', 'pomoc');
	$('input[value=edit]').hide();
	
	var $passChange = $('#passChange');
	/*
    if ($passChange.attr('pomoc')) {

        $("#old_password_profile").prop('required', false);
        $("#new_password_profile").prop('required', false);
        $("#confirm_new_password_profile").prop('required', false);
        
    } else {*/
	if(!$passChange.attr('pomoc'))
	{
    	$passChange.attr('pomoc', 'pomoc');

        $("#old_password_profile").prop('required', true);
        $("#new_password_profile").prop('required', true);
        $("#confirm_new_password_profile").prop('required', true);
    }
    
}