$(document).ready(function(){
	
	console.log("aleksa");
	
    $.ajax({
        type: 'GET',
        url: '/rest/airline/all',
        success: function (airlines)
		{
        	if(airlines == null)
			{
				console.log('There are no airlines');
			}
			else
			{
				console.log('There are ' + airlines.length + ' airlines in memory.');
				for (let airline of airlines) 
				{
					$aviokompanijaList.append('<li> <a style="margin-top:30%;margin-left:0%;" href="aviokompanija.html?id=' + airline.id +'">' + airline.naziv + '</a> </li');
				}
			}
        	/*
        	console.log("aleksa");
            var aviokompanije = data.responseJSON;
            $.each(aviokompanije,function(i,aviokompanija)
            {
            	$aviokompanijaList.append('<li> <a style="margin-top:30%;margin-left:0%;" href="aviokompanija.html?id=' + aviokompanija.id +'">' + aviokompanija.naziv + '</a> </li');
            });
            */
		}
    });
	
	
	var $aviokompanijaList = $("#aviokompanije");
	
	
	
});