<html>

<head>
    <link href='https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.1.0/fullcalendar.print.min.css' rel='stylesheet'
        media='print' />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.2.7/semantic.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.17.1/moment.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.1.0/fullcalendar.min.js"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.1.0/fullcalendar.min.css" rel="stylesheet" />
    <link href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.2.7/semantic.min.css" rel="stylesheet" />
   <script>

   
   		var queryArr = [];
		var arrDate;

		
		function fetchData(){
		$.ajax({ //starts here
			url : "http://localhost:1235/EventBasedCalendar/viewAllEvents",
	  		success : function(data) {
	  		$.each(data, function(index, obj) {
	     		queryStr = { "title" : obj.title,"start":new Date(obj.start),"end":new Date(obj.end)};
	     		queryArr.push(queryStr);
	  		});
	  		
	  		arrDate=data[0].cli.arrivalDate;
	  		
	  		setData();
	  	 	}
			
		});//end starts
		}
		
			function setData(){
            $('#calendar').fullCalendar({
            header: {
            	left: 'prev,next today',
                center: 'title',
                right: 'month,basicWeek,basicDay'
            },
            defaultDate: arrDate,
            navLinks: true, // can click day/week names to navigate views
            editable: true,
            eventLimit: true, // allow "more" link when too many events
            events: queryArr
            	
            

    });
          
            
            
			}
</script>
   
<body onload="fetchData()">

    <br />
    <div class="ui container">
        <div class="ui grid">
            <div class="ui sixteen column">
                <div id="calendar"></div>
            </div>
        </div>
    </div>

    </div>
</body>


</html>