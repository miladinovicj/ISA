$(document).ready(function()	
{
    var id_presented = window.location.search.substring(4);


    $.ajax({
        type: 'GET',
        url: 'rest/airline/' + id_presented,
        contentType: 'application/json',
        dataType: "json",
        complete: function (data)
		{
            var aviokompanija = data.responseJSON;
            console.log(aviokompanija);
            setUpForAirline(aviokompanija)
		}
    });
	
});

function setUpForAirline(airline)
{

    //naslovi aviokompanije
    var $title = $("#titl_aviokompanija");
    var $naslov = $("#aviokompanija_ime");
    var $listaDestinacija = $("#destination_list");
    var $listaAkcija = $("#action_list");


    $title.text(airline.naziv);
    $naslov.text(airline.naziv);

    //template destinacija - generisanje svih
    var destinationTemplate = $('#destination_template').html();
    var actionTemplate = $('#action_template').html();

    
    $.each(airline.letovi,function(i, dest) {
    	console.log("let")
        var item = Mustache.render(destinationTemplate, dest);
    	var $item_element = $(item);
    	$listaDestinacija.append($item_element.prop('outerHTML'));
    });
    
    $.each(airline.brziLetovi,function(i, action) 
    {
    	console.log("let")
        var item = Mustache.render(actionTemplate, action);
    	var $item_element = $(item);
    	
    	var $poletanje = $item_element.find("#vremePoletanja");
    	var $sletanje = $item_element.find("#vremeSletanja");
    	
    	//parsiranje teksta vremena
    	$poletanje.text(action.vremePoletanja.substring(0,16).replace("T","   "));
    	$sletanje.text(action.vremeSletanja.substring(0,16).replace("T","   "));
    	
    	
    	$listaAkcija.append($item_element.prop('outerHTML'));
    });

}
