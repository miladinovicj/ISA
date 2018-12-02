$(document).ready(function() {
	$('#forma').submit(function(event) {
		event.preventDefault();
		let email = $('input[name="email"]').val();
		let password1 = $('input[name="lozinka"]').val();
		let password2 = $('input[name="lozinkaPonovo"]').val();
		let ime = $('input[name="ime"]').val();
		let prezime = $('input[name="prezime"]').val();
		let grad = $('input[name="grad"]').val();
		let telefon = $('input[name="telefon"]').val();
		
		
		if(!email || !password1 || !password2 || !ime || !prezime || !telefon || !grad)
			{
			alert('All fields must be filled!');
			
			return;
			}
	
		$.post({
			url: "../Projekat_Andrijana/rest/users/registruj",
			data: JSON.stringify({username: username, password: password, ime: ime, prezime: prezime, telefon: telefon, email:email}),
			contentType: 'application/json',
			success: function(data) {
				if(data==null){
					$('#error').text('Korisnik sa tim korisničkim imenom već postoji!');
					$("#error").show().delay(3000).fadeOut();
				}
				else {
					sessionStorage.setItem('ulogovan',JSON.stringify(data));
					$('#success').text('Korisnik uspešno registrovan.');
					$("#success").show().delay(3000).fadeOut(function(){
						window.location.href="pocetna.html";
					});
				}
			}
		});
	});
});
