$(document).ready(function(){
	
	console.log("aleksa");
	
    $.ajax({
        type: 'GET',
        url: 'rest/airline',
        contentType: 'application/json',
        dataType: "json",
        complete: function (data)
		{
        	
        	console.log("aleksa");
            var aviokompanije = data.responseJSON;
            $.each(aviokompanije,function(i,aviokompanija)
            {
            	$aviokompanijaList.append('<li> <a style="margin-top:30%;margin-left:0%;" href="aviokompanija.html?id=' + aviokompanija.id +'">' + aviokompanija.naziv + '</a> </li');
            });
		}
    });
	
	
	var $aviokompanijaList = $("#aviokompanije");
	
	
	
});