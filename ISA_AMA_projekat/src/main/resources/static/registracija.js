$(document).ready(function() {
	$('#forma').submit(function(event) {
		event.preventDefault();
		let email = $('input[name="email"]').val();
		let lozinka = $('input[name="lozinka"]').val();
		let password2 = $('input[name="lozinkaPonovo"]').val();
		let ime = $('input[name="ime"]').val();
		let prezime = $('input[name="prezime"]').val();
		let grad = $('input[name="grad"]').val();
		let telefon = $('input[name="telefon"]').val();
		
		
		if(!email || !lozinka || !password2 || !ime || !prezime || !telefon || !grad){
			alert('All fields must be filled!');
			
			return;
		}
			
		if(!(lozinka == password2)){
			alert('The passwords are not the same!');
			
			return;
		}
	
		$.post({
			url: "/api/users/registruj",
			data: JSON.stringify({email: email, lozinka: lozinka, ime: ime, prezime: prezime, grad:grad, telefon: telefon}),
			contentType: 'application/json',
			success: function(data) {
				if(data==null){
					alert('User with this email already exists!');
				}
				else {
					//sessionStorage.setItem('ulogovan',JSON.stringify(data));
					alert('User successfully registred!');
					window.location.href="index.html";
				}
			}
		});
	});
});
