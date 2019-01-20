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