$(document).ready(function() {
	$('#forma').submit(function(event) {
		event.preventDefault();
		ispravno=true;
		 email = $('input[name="email"]').val();
		 lozinka = $('input[name="lozinka"]').val();
		 password2 = $('input[name="lozinkaPonovo"]').val();
		 ime = $('input[name="ime"]').val();
		 prezime = $('input[name="prezime"]').val();
		 grad = $('input[name="grad"]').val();
		 telefon = $('input[name="telefon"]').val();
		
		
		if(!email || !lozinka || !password2 || !ime || !prezime || !telefon || !grad){
			alert('All fields must be filled!');
			ispravno=false;
		}
		else
		{
			if(!(/^[a-zA-Z]+$/.test(ime)))
			{
			$('#validacijaIme').text("First name must contains the letters!");
			$('#validacijaIme').show();
			ispravno=false;
			}
			else if(!(/[A-Z]/.test( ime[0])))
				{
				$('#validacijaIme').text("First letter of name must be capital!");
				$('#validacijaIme').show();
				ispravno=false;
				
				}
			else
				$('#validacijaIme').hide();
		
			if(!(/^[a-zA-Z]+$/.test(prezime)))
			{
			$('#validacijaPrezime').text("Last name must contains the letters!");
			$('#validacijaPrezime').show();
			ispravno=false;
			}
			else if(!(/[A-Z]/.test( prezime[0])))
			{
			$('#validacijaPrezime').text("First letter of last name must be capital!");
			$('#validacijaPrezime').show();
			ispravno=false;
			
			}
		else
			$('#validacijaPrezime').hide();
		
			if(!(/^[a-zA-Z ]+$/.test(grad)))
			{
			$('#validacijaGrad').text("Field City must contains the letters!");
			$('#validacijaGrad').show();
			ispravno=false;
			}
			else if(!(/[A-Z]/.test( grad[0])))
			{
			$('#validacijaGrad').text("First letter of the city's name must be capital!");
			$('#validacijaGrad').show();
			ispravno=false;
			
			}
		else
			$('#validacijaGrad').hide();
			
			if(!(/^[0-9]+$/.test(telefon)))
			{
			$('#validacijaTelefon').text("Field Phone number must contains only numbers!");
			$('#validacijaTelefon').show();
			ispravno=false;
			}
			else
			$('#validacijaTelefon').hide();
		
			if(!(lozinka==password2))
			{
				$('#validacijaLozinka').text("The password must be the same!");
				$('#validacijaLozinka').show();
				ispravno=false;
			}
			else
				$('#validacijaLozinka').hide();
			
			
			}
		
		if(ispravno==true)
			{
		$.post({
			url: "/api/users/registruj",
			data: JSON.stringify({email: email, lozinka: lozinka, ime: ime, prezime: prezime, grad:grad, telefon: telefon}),
			contentType: 'application/json',
			success: function(data) {
				if(data==null || data==""){
					$('#validacijaEmail').text("User with this email already exists!");
					$('#validacijaEmail').show();
				}
				else {
					//sessionStorage.setItem('ulogovan',JSON.stringify(data));
					$('#validacijaEmail').hide();
					alert('User successfully registred!');
					window.location.href="index.html";
				}
			}
		
			
	});
			}
	
});
});
