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
		},
		
		error: function() {
			alert('Error');
	}
	
			});
});