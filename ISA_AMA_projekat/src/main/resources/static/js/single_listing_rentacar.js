$(document).ready(function()	
{
    var id_presented = window.location.search.substring(4);
    console.log('[single_listing_rentacar: document.ready()]: id rentacara: ' + id_presented);

    $.ajax({
        type: 'GET',
        url: 'api/rents/' + id_presented,
        contentType: 'application/json',
        dataType: "json",
        complete: function (data)
		{
            var rentacar = data.responseJSON;
            console.log(rentacar);
            showRentacar(rentacar);
		}
    });
	
});

function showRentacar(rentacar)
{

    $("#title_rentacar").text(rentacar.naziv);
    $("#rentacar_name").text(rentacar.naziv);
    $('#rentacar_info_text').text(rentacar.promotivni_opis);
    $('#adresa_rentacar').text(rentacar.adresa);
    
    if(rentacar.filijale == 0)
	{
    	$("#text_no_filijale").text("There are no branches in this rentacar service.");
	}
    else
	{
    	$("#text_no_filijale").text("");
    	for (let filijala of rentacar.filijale) 
    	{
    		addFilijala(filijala);
    	}
	}
    
    if(rentacar.usluge.length == 0)
	{
    	$("#text_no_services").text("There are no additional services this hotel.");
	}
    else
	{
    	$("#text_no_services").text("Additional services: ");
    	for (let usluga of rentacar.usluge) 
    	{
    		let li = $('<li>' + usluga.naziv + ' - $' + usluga.cena + '/per day</li>');
    		$('#usluge_rentacar').append(li);
    	}
	}

}

function addFilijala(filijala)
{
	var temp, div, a;
	temp = document.getElementById("template_filijala");
	div = temp.content.querySelector("div#ubaci_filijalu");
	
	
	temp.content.getElementById("adresa_filijale").innerHTML = filijala.adresa;
	
	a = document.importNode(div, true);
    document.getElementById("ubaci_filijale_template").appendChild(a);
}
