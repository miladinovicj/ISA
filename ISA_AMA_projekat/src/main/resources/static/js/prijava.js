$(document).ready(function() {	
$('#formaPrijava').submit(function(event) {
		event.preventDefault();
		let ispravno=true;
		let email = $('input[name="email"]').val();
		let lozinka = $('input[name="lozinka"]').val();
		$('#uspesno').text("");
		$('#neuspesno').text("");
		
		
		
		if(!email || !lozinka){
			$('#neuspesno').text('All fields must be filled!');
			ispravno=false;
		}
		
			
		if(ispravno==true)
			{
		$.post({
			url: "/api/users/login",
			data: JSON.stringify({email: email, lozinka: lozinka}),
			contentType: 'application/json',
			success: function(data) {
				if(data==null || data==""){
					$('#neuspesno').text("Incorrect email or password or you did not activate your profile with link you received on email!");
				}
				else {
					//sessionStorage.setItem('ulogovan',JSON.stringify(data));
					$('#neuspesno').text("");
					$('#uspesno').text('User successfully logged in! Enjoy!');
					$('#formaPrijava').hide();
					$("#uspesno").show().delay(2000).fadeOut(function(){
						window.location.href="index.html";
					});
					
								//window.location.href="index.html";
								
								
					
					
				}
			}
		
			
	});
			}
	
});
});