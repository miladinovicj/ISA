function showContentHotel() {
	console.log("milicaaa");
	$.get({
		url: "/api/hotels/all",
		success: function(hoteli) {
			if(hoteli == null){
				alert('There are no hotels!');
				$('#hotel_title').text = '';
			}
			else {
				console.log('There are ' + hoteli.length + ' hotels in memory.');
				$('#hotel_title').text = 'Hotels';
				document.getElementById('ubaci_hotele_template').style.display='block';
				for (let hotel of hoteli) 
				{
					addHotelLi(hotel);
				}
			}
		},
		error : function(data){
			alert('Error!');
			$('#hotel_title').text = '';
		}
	});
}


function addHotelLi(hotel) {
	console.log("naziv hotela: " + hotel.naziv);
	var temp, div, a, i, text, name;
	temp = document.getElementById("template_hotel");
	div = temp.content.querySelector("div#ubaci");
	
    temp.content.getElementById("name_hotel").innerHTML = hotel.naziv;
    temp.content.getElementById("text_hotel").innerHTML = hotel.adresa;
    temp.content.getElementById("rating_hotel").innerHTML = hotel.prosecna_ocena;
    temp.content.getElementById("dugme_view_details").innerHTML = '<a href="single_listing_hotel.html?id=' + hotel.id +'">view details</a>';
    
    a = document.importNode(div, true);
    document.getElementById("ubaci_hotele_template").appendChild(a);
	
    /*
    text = temp.content.getElementById("text_hotel");
    text.innerHtml = hotel.adresa;
    name = temp.content.getElementById("name_hotel");
    name.innerHtml = hotel.naziv;
    //append the new node wherever you like:

    a = document.importNode(div, true);
    document.body.appendChild(a);
	*/
}

function showContentAvio() {
    if(document.getElementById('ubaci_hotele_template').style.display=='block') { 
        document.getElementById('ubaci_hotele_template').style.display='none'; 
    }
    
    return false;
}

function showContentRent() {
    if(document.getElementById('ubaci_hotele_template').style.display=='block') { 
        document.getElementById('ubaci_hotele_template').style.display='none'; 
    } 
    return false;
}

$(document).ready(function()
{
	console.log('milica');
	
	
});
