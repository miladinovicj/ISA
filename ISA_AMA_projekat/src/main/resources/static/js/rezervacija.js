var niz_osoba = [];
var korisnik = null;
var flight = null;
var personList = null;
var personCount = 0;
var friend_list = []
var token = "";

var rezervacijaOsobe = []

$(document).ready(function()
{
	
	
    personList = $('#personsList');

	getKorisnik()
	getFlightById()

	//dodavanje odmah osobe koja je kreirala rezervaciju:
	

	$("#inserNewPerson").click( function ()
	{
		insertNewPerson(function(){
			fillAllFields(personCount.toString());
		});
		price = personCount * flight.cena
		$("#price").text(price.toFixed(2));
		
	});
	
	$("#makeReservation").click(function(){
		
		var greska = true;
		greska = validateFields();
		greska = validateSemantic();
		
		if(greska == true)
		{
			makeRezervation();
		}
		else
		{
			console.log("asde")
		}
	});
	
});



function PersonInReservation(name, last, email, passNum, baggage, seat)
{
	  this.name = name;
	  this.last = last;
	  this.email = email;
	  this.passNum = passNum;
	  this.baggage = baggage;
	  this.seat = seat;
}

function getKorisnik()
{
	token = localStorage.getItem('jwtToken');
	$.post
	({
		url: "/auth/userprofile",
		headers: 'Authorization',
		contentType: 'application/json',
		data : token,
		  
		success: function(user) 
		{
			if(user == null)
			{
				window.location.replace("index.html");
			}
			
			korisnik = user;
			//ako je null idi na pocetnu
			getFriendListForUser(korisnik.id, function() {
				setPageInfo();
			})
			
		},
		error: function() 
		{
			window.location.replace("index.html");
		}
	});
}

function getFlightById()
{
	
	var url = window.location.toString();
	var flightID = url[url.length-1]
	
	$.get({
		url: "/api/let/" + flightID,
		success: function(data) 
		{
						
			if(data == null)
			{
				console.log("let je nullcina");
			}
			else
			{		
				flight = data;
				text1 = data.odakle;
				text2 = data.dokle;
				$("#flightLocationTittle1").text(text1);
				$("#flightLocationTittle2").text(text2);
			}
						
		},
		error : function(data)
		{
			console.log("errorcina prilikom prihvatanja leta");
		}
		});
}


function setPageInfo()
{
	// ime leta nalepi na naziv
	// koristi 
	
	//dodaj korisnika koji je izabrao opciju rezervisi kao prvog u listi
	insertUser(korisnik, function(){
		
		fillAllFields("");
	});
	$("#friends").val("1");
	$("#friends").attr("disabled", true); 
	$("#friends").change(function() 
	{
		index = $("#friends").prop('selectedIndex');
		if(index != 0)
		{
		    $("#imePersone").val(friend_list[index-1].ime)
		    $("#przPersone").val(friend_list[index-1].prezime)
		    $("#emailPersone").val(friend_list[index-1].email)
		}
		else
		{
			$("#imePersone").val("");
		    $("#przPersone").val("");
		    $("#emailPersone").val("");
		}
	});
}


function insertUser(user, callback)
{
    var $personTemplate = $('#personTemplate');
    
    var $item = $($personTemplate.html())
    
    $item.find("#imePersone").val(user.ime)
    $item.find("#przPersone").val(user.prezime)
    $item.find("#emailPersone").val(user.email)
    
    
    $item.find("#imePersone").prop("readonly", true);
    $item.find("#przPersone").prop("readonly", true);
    $item.find("#emailPersone").prop("readonly", true);
    
    
	$("#price").text(flight.cena.toFixed(2));

    
    personList.append($item);
    callback();
    personCount++;

}


function insertNewPerson(callback)
{
	var personTemplate = $('#personTemplate').html();	
	var $personTemplate = $(personTemplate);
	
 
	var $removeButton = $personTemplate.find("#removeButton");
	$removeButton.attr("id","removeButton" + personCount);
	
	var $imePersone = $personTemplate.find("#imePersone");
	$imePersone.attr("id","imePersone" + personCount);
	
	var $przPersone = $personTemplate.find("#przPersone");
	$przPersone.attr("id","przPersone" + personCount);
	
	var $emailPersone = $personTemplate.find("#emailPersone");
	$emailPersone.attr("id","emailPersone" + personCount);
	
	var $passPersone = $personTemplate.find("#passPersone");
	$passPersone.attr("id","passPersone" + personCount);
	
	var $selectFriend = $personTemplate.find("#friends");
	$selectFriend.attr("id","friends" + personCount);
	
	
	$personTemplate.find('input[name=radioButton]').prop("name","radioButton"+personCount);
	
	
	
	var $blah = $personTemplate.find("#errorName");
	$blah.attr("id","errorName" + personCount);
	var $blah = $personTemplate.find("#errorLastname");
	$blah.attr("id","errorLastname" + personCount);
	var $blah = $personTemplate.find("#errorEmail");
	$blah.attr("id","errorEmail" + personCount);
	var $blah = $personTemplate.find("#errorPass");
	$blah.attr("id","errorPass" + personCount);
	
	
	
	
	
            
    personList.append($personTemplate.prop('outerHTML'));
		
	callback();
	$(".selectFriendList").change(function() 
			{
				temp = $(this).attr("id").substring(7);
				index = $("#friends"+temp).prop('selectedIndex');
				if(index != 0)
				{
				    $("#imePersone"+temp).val(friend_list[index-1].ime)
				    $("#przPersone"+temp).val(friend_list[index-1].prezime)
				    $("#emailPersone"+temp).val(friend_list[index-1].email)
				    
				    $("#imePersone"+temp).prop("readonly", true);
				    $("#przPersone"+temp).prop("readonly", true);
				    $("#emailPersone"+temp).prop("readonly", true);
				}
				else
				{
					$("#imePersone"+temp).val("");
				    $("#przPersone"+temp).val("");
				    $("#emailPersone"+temp).val("");
				    
				    $("#imePersone"+temp).prop("readonly", false);
				    $("#przPersone"+temp).prop("readonly", false);
				    $("#emailPersone"+temp).prop("readonly", false);
				}
			});

	
	personCount++;
	

	
 }



function getFriendListForUser(korisnikId, callback)
{
	$.get({
	url: "/api/users/friendsOf/" + korisnikId,
	success: function(data) 
	{
		if(data == null)
		{
			console.log("nullcina");
		}
		
		friend_list.push(korisnik)
		
		for(let friend of data)
		{
			friend_list.push(friend);
		}
		
		callback();
		
	},
	error : function(data)
	{
		console.log("errorcina prilikom prihvatanja prijatelja");
	}
	});
}


function fillAllFields(number)
{
	
	var friendListElement = document.getElementById("friends" + number)
	var $friendListElement = $(friendListElement);
		
	$friendListElement.append('<option value="null"></option>')
	for (i = 0; i < friend_list.length; i++)
	{
		
		$friendListElement.append('<option value="' + friend_list[i].id +'">'  + friend_list[i].ime + ' ' + friend_list[i].prezime + '</option>')
	}
}

function validateFields()
{
	var correct = true;
	
	var nameText = $("#imePersone").val();
	var lastnameText = $("#przPersone").val();
	var emailText = $("#emailPersone").val();
	var passText = $("#passPersone").val();

	var baggageValue = $('input[name=radioButton]:checked').val();
	
	
	
	var emailRegex = new RegExp('^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$');
	var nameRegex = new RegExp('^[a-zA-Z]{1,}');
	var passRegex = new RegExp('^[0-9]{2,}');
	if(passText=="" || 	passRegex.test(passText)==false)
	{
		correct = false;
		$("#errorPass").prop("hidden",false);
	}
	
	
	//PREUZIMANJE SEDISTA TODO
	var putnik = new osobaRezervacije(null,nameText,lastnameText,emailText,passText,baggageValue,2);
	rezervacijaOsobe.push(putnik);
	
	
	for(var i = 1 ; i < personCount ; i++)
	{
		var nameText = $("#imePersone" + i).val();
		var lastnameText = $("#przPersone" + i).val();
		var emailText = $("#emailPersone" + i).val();
		var passText = $("#passPersone" + i).val();
		
		if(nameText=="" || 	nameRegex.test(nameText)==false)
		{
			correct = false;
			$("#errorName"+i).prop("hidden",false);
		}
		
		if(lastnameText=="" || 	nameRegex.test(lastnameText)==false)
		{
			correct = false;
			$("#errorLastname"+i).prop("hidden",false);
		}
		
		if(emailText=="" || emailRegex.test(emailText)==false)
		{
			correct = false;
			$("#errorEmail"+i).prop("hidden",false);
		}
		
		if(passText=="" || 	passRegex.test(passText)==false)
		{
			correct = false;
			$("#errorPass" + i).prop("hidden",false);
		}
		
		var baggageValue = $('input[name=radioButton]:checked').val();
		
		//PREUZIMANJE SEDISTA TODO
		
		var putnik = new osobaRezervacije(null,nameText,lastnameText,emailText,passText,baggageValue,2);
		rezervacijaOsobe.push(putnik);
		
	}

	return correct;
}


function validateSemantic()
{
	correct = true;
	
	temp_list = [];
	var passText = $("#passPersone").val();
	temp_list.push(passText);
	
	for(var i = 1 ; i < personCount ; i++)
	{
		passText = $("#passPersone" + i).val();
		if($.inArray(passText, temp_list) != -1)
		{
			alert("Error: There are two passengers with the same passport number.");
			correct = false;	
		}
		temp_list.push(passText);
	}
	
	return correct;
	
}

function makeRezervation()
{
	$.post({
		url: "/api/rezervacija/create/" + flight.id + "/" + token,
		data: JSON.stringify(rezervacijaOsobe),
		contentType: 'application/json',
		success: function(data) {
			if(data==null || data=="")
			{
				alert("An error occured while processing information.")
			}
			else 
			{
				alert("Your flight is succesfully booked.")
				window.location.replace("rezervacijaPreview.html?id=" + data);
			}
		}
	
		
	});
}


function osobaRezervacije(id, ime,prezime,email,broj_pasosa,prtljag,sediste)
{
	this.id = id; //u ovom IDu se nalazi id korisnika koji je prijatelj, u bekendu se menja generickim IDjem za ovaj tip
	this.ime = ime;
	this.prezime = prezime;
	this.email = email;
	this.brojPasosa = broj_pasosa;
	this.prtljag = prtljag;
	this.sediste = sediste;
	
}

