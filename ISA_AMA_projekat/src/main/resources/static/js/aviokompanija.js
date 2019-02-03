var flightArray = [];
var uniqueDests = [];

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
 


    $.each(airline.letovi,function(i, dest) 
    {
    	//ubacivanje svakog leta u listu letova
    	if(jQuery.inArray(dest, flightArray) == -1)
    	{
    		flightArray.push(dest);
    	}
    	
    	//ukoliko se u listi jedinstvenih destinacije ne nalazi ova, bice ubacena i prikazana
    	if(jQuery.inArray(dest.dokle, uniqueDests) == -1 )
    	{
    		
    		uniqueDests.push(dest.dokle);
    		
        	console.log("let");
            var item = Mustache.render(destinationTemplate, dest);
        	var $item_element = $(item);

            var $dugmeZaSelektDestinacije= $item_element.find(".selektovanjeDestinacije");
            
       
            $dugmeZaSelektDestinacije.attr('id', "showdest" + jQuery.inArray(dest.dokle,uniqueDests));
            
            $listaDestinacija.append($item_element.prop('outerHTML'));

            
            $("#showdest" + jQuery.inArray(dest.dokle,uniqueDests)).click(function ()
            		{
            	  		var pretragaTemplate = $('#pretragaTemplate').html();
            	  		var $selektovaniLetovi = $("#selektovaniLetovi");
            	  		
            			console.log("klik show flights");
            		    var searchValue  = dest.dokle;

            		    $('#defaultAirlineView').hide();

            		    
            		    //PRIKAZ SVIH LETOVA KOJI ODGOVARAJU DESTINACIJI
            		    var $naslovListing = $('#naslovListing');
            		    $naslovListing.show();
            		    $naslovListing.text("Destinations for:  " + searchValue);
            		    $('#searchListing').show();
            		    
            		    //ubacivanje u listu 
            		    $.each(flightArray,function(i, flight) 
            		    	    {
            		    	    	    	if(flight.dokle == searchValue)
            		    	    	    	{
            		    	    	    		var item = Mustache.render(pretragaTemplate, flight);
                        		    	    	var $item_element = $(item);
                        		    	    	
                        		    	    	var $poletanje = $item_element.find("#vremePoletanja");
                        		    	    	var $sletanje = $item_element.find("#vremeSletanja");
                        		    	    	var $cenaPopust = $item_element.find("#cenaPopust");
                        		    	    	var $rezButton = $item_element.find("#rezButton");


                        		    	    	//parsiranje teksta vremena
                        		    	    	$poletanje.text(flight.vremePoletanja.substring(0,16).replace("T","   "));
                        		    	    	$sletanje.text(flight.vremeSletanja.substring(0,16).replace("T","   "));            		    	    	
                        		    	    	$rezButton.attr("href", "rezervacija.html?id=" + flight.id);
                        		    	    	$selektovaniLetovi.append($item_element.prop('outerHTML'));
            		    	    	    	}
            		    	    });
            		    
            		    
            		});
            
        
    		
    	}
    	
    });



    $.each(airline.brziLetovi,function(i, action) 
    {
    	    	
    	console.log("let")
        var item = Mustache.render(actionTemplate, action);
    	var $item_element = $(item);
    	
    	var $poletanje = $item_element.find("#vremePoletanja");
    	var $sletanje = $item_element.find("#vremeSletanja");
    	var $cenaPopust = $item_element.find("#cenaPopust");

    	//parsiranje teksta vremena
    	$poletanje.text(action.vremePoletanja.substring(0,16).replace("T","   "));
    	$sletanje.text(action.vremeSletanja.substring(0,16).replace("T","   "));

        $cenaPopust.text( action.cena - (action.cena*(action.popust/100)));
    	
    	$listaAkcija.append($item_element.prop('outerHTML'));
    });

}

//klik na dugme SHOW FLIGHTS
