<!-- class import(s) -->
<%@ page import="edu.ucla.cs.cs144.Bid" %>

<!doctype html>
<html>
	<head>
		<title>Results for item <%= request.getAttribute("id") %> | Cho's Auction House</title>
		<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" />
		<link href='https://fonts.googleapis.com/css?family=Varela' rel='stylesheet' type='text/css'>
		<style type="text/css">
			body {
				font-family: 'Varela', sans-serif;
				background-color:#f9f9f9;
			}
			.btn {
				background-color: #228B22;
				color:white;
			}
			.btn:hover, .btn:active {
				background-color: #145314;
				color:white;
			}
			/* Styles for Google Map */
			html { height: 100% } 
			body { height: 100%; margin: 0px; padding: 0px } 
			#map_canvas { height: 100% } 
		</style>
	</head>
	<body onload="initialize()">
		<div class="container-fluid">
			<form class="navbar-form" role="search" action="item">
				<div class="form-group">
					<input type="text" class="form-control" name="id" placeholder="item id..." required="required" />
				</div>
				<button type="submit" class="btn btn-default">Search for item</button>
			</form>
		</div>
		
		<p> item id:
			<%= request.getAttribute("id") %>
		</p>
		<p>
			Name: <%= request.getAttribute("name") %>
		</p>
		<p>
			Categories:

				<% for(int i = 0; i < ((String[])request.getAttribute("categories")).length; i++) { %>

				<%=	
				((String[])request.getAttribute("categories"))[i]
				%>
				<br /> 
				<% } %>
		</p>
		<p>
			Currently: <%= request.getAttribute("currently") %>
		</p>
		<p>
			First Bid: <%= request.getAttribute("firstBid") %>
		</p>
		<p>
			Num Bids: <%= request.getAttribute("numBids") %>
		</p>
		<p>
		  	<% 
		  		// Location
		  		String loc = (String) request.getAttribute("item-location");
				String lat = (String) request.getAttribute("item-location-lat");
				String lon = (String) request.getAttribute("item-location-lon");
				String country = (String) request.getAttribute("country");
				String escapedCountry = country.replaceAll("\"","\\\"").replaceAll("\'","\\\'");
				String escapedLoc = loc.replaceAll("\"","\\\"").replaceAll("\'","\\\'");
				String jsLocation = "";
		  	%>

			Location: <%= escapedLoc %>
			<br>
			<% 	if (lat != null && lon != null) { 
				jsLocation = lat + ", " + lon;
			%>

				Location-lat: <%= lat %>
				<br>
				Location-long: <%= lon %>
			<% } else {
				jsLocation = escapedLoc + ", " + escapedCountry;
			} %>
		</p>
		<p>
			Country: <%= country %>
		</p>
		<p>
			Started: <%= request.getAttribute("started") %>
		</p>
		<p>
			Ends: <%= request.getAttribute("ends") %>
		</p>
		<p>
			Seller Rating: <%= request.getAttribute("sellerRating") %>
		</p>
		<p>
			Seller User: <%= request.getAttribute("sellerUserId") %>
		</p>
		<p>
			Desc: <%= request.getAttribute("desc") %>
		</p>

		<% Bid[] bids =  ((Bid[])request.getAttribute("bids")); %>

		<% for(int i = 0; i < bids.length; i++) { %>
			<ul>
				<li> 
					Bidder ID:
					<%= bids[i].getBidder().getBidderId() %>
				</li>
				<li>
					Bidder Rating:
					<%= bids[i].getBidder().getBidderRating() %>
				</li>
				<li>
					Bidder Location: <%= bids[i].getBidder().getLocation() %>
				</li>
				<li>
					Bidder Country: <%= bids[i].getBidder().getCountry() %>
				</li>
				<li>
					Bid Time: <%= bids[i].getTime() %>
				</li>
				<li>
					Bid Amount: <%= bids[i].getAmount() %>
				</li>
			</ul>
		<% } %>
	<div id="map_canvas" style="width:500px; height:200px"></div> 
	</body>

	<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAf8b6hpR00H3gNbdDtlCeCmyv6X9C21sQ&callback=initMap"
	  type="text/javascript"></script>
	<script type="text/javascript">
	function initMap(){}
	  function initialize() {
	    var address = "<%= jsLocation %>"; 

	    var geocoder = new google.maps.Geocoder();
	    geocoder.geocode({'address': address}, function(results, status) {
	      if (status === google.maps.GeocoderStatus.OK) {
	        var latlng = results[0].geometry.location;
	        var myOptions = { 
	          zoom: 12, // default is 8  
	          center: latlng, 
	          mapTypeId: google.maps.MapTypeId.ROADMAP 
	        }; 
	        var map = new google.maps.Map(document.getElementById("map_canvas"), 
	            myOptions); 
	        var marker = new google.maps.Marker({
	            map: map,
	            position: results[0].geometry.location,
	            title: "<%= escapedLoc %>"
	        });

	        // Info window that pops up when the marker is clicked.
	        // Can include some info about the item in this if we want.
	        var infowindow = new google.maps.InfoWindow({
	            content: "<%= escapedLoc %>"
	        });

	        marker.addListener('click', function() {
	          infowindow.open(map, marker);
	        });


	      } else {
	        // Geocoding failed for some reason (status), just display generic world map.
	        var genericMapOptions = {
	          zoom: 1,
	          center: new google.maps.LatLng(30,0),
	          mapTypeId: google.maps.MapTypeId.ROADMAP
	        };
	        var map = new google.maps.Map(document.getElementById("map_canvas"), genericMapOptions);
	      }
	    });


	  } 

	</script> 
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
	<script type="text/javascript" src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</html>