<!-- class import(s) -->
<%@ page import="edu.ucla.cs.cs144.Bid" %>

<!doctype html>
<html>
	<head>
		<title>Results for item <%= request.getAttribute("id") %> | Cho's Auction House</title>
		<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" />
		<link href='https://fonts.googleapis.com/css?family=Varela' rel='stylesheet' type='text/css'>
		<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-XdYbMnZ/QjLh6iI4ogqCTaIjrFk87ip+ekIjefZch0Y+PvJ8CDYtEs1ipDmPorQ+" crossorigin="anonymous">
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

			/* page specific styles */
			.header-text {
				font-weight: bold;
				font-size: 1.2em;
			}
			.item-bidder {
				border-top: 2px solid gray;
			    margin: 15px 0;
			    padding: 15px 0;
			}
			.item-description {
				padding-left:10%;
				padding-right:10%;
			}
			.item-meta {
				border-left:4px solid gray;
			}
			.item-pic {
				min-height: 200px;
			    padding-top: 10%;
			    padding-bottom: 10%;
			}
		</style>
	</head>
	<% 
		String valid = (String)request.getAttribute("valid");
		if(valid.equals("true")) { 
	%>
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
	<body onload="initialize()">
		<div class="container-fluid">
			<form class="navbar-form" role="search" action="item">
				<div class="form-group">
					<input type="text" class="form-control" name="id" placeholder="item id..." />
				</div>
				<button type="submit" class="btn btn-default">Search for item</button>
			</form>
		</div>

		<!-- new specific html here -->
		<br />
		<div class="container-fluid">
			<div class="row">
				<div class="col-sm-6 text-center">
					<div class="item-pic">
						<i class="fa fa-file-image-o fa-4x"></i><br />
						<i>No image for this item.</i>
					</div>
					<div style="height:200px" id="map_canvas"></div>
				</div>
				<div class="col-sm-6 item-meta">
					<p>
						<span class="header-text">Item Id:</span> <%= request.getAttribute("id") %> 
					</p>
					<p>
						<span class="header-text">Name:</span> <%=request.getAttribute("name") %> 
					</p>
					<p>
						<span class="header-text">Seller:</span> <%= request.getAttribute("sellerUserId") %>
					</p>
					<p>
						<span class="header-text">Seller Rating:</span> <%= request.getAttribute("sellerRating") %>
					</p>
					<p>
						<span class="header-text">Categories:</span>
						<ul> 
							<% for(int i = 0; i < ((String[])request.getAttribute("categories")).length; i++) { %>
							<li>
								<%=	((String[])request.getAttribute("categories"))[i] %>
							</li> 
							<% } %> 
						</ul>
					</p>
					<p>
						<span class="header-text">Currently:</span> <%= request.getAttribute("currently") %>
					</p>
					<p>
						<span class="header-text">First Bid:</span> <%= request.getAttribute("firstBid") %>
					</p>
					<p>
						<span class="header-text">Number of Bids:</span> <%= request.getAttribute("numBids") %>
					</p>
					<p>
						<span class="header-text">Location:</span> <%= escapedLoc %><br/>
						<% 	if (lat != null && lon != null) { 
							jsLocation = lat + ", " + lon;
						%>

							<span class="header-text">Latitude:</span> <%= lat %>
							<br>
							<span class="header-text">Longitude:</span> <%= lon %>
						<% } else {
							jsLocation = escapedLoc + ", " + escapedCountry;
						} %>
						
					</p>
					<p>
						<span class="header-text">Country:</span> <%= request.getAttribute("country") %>
					</p>
					<p>
						<span class="header-text">Started:</span> <%= request.getAttribute("started") %>
					</p>
					<p>
						<span class="header-text">Ends:</span> <%= request.getAttribute("ends") %>
					</p>
					<% if(request.getAttribute("buyPrice")!= null) { %>
					<p>
						<span class="header-text">Buy Price:</span> <%= request.getAttribute("buyPrice") %>
					</p>
					<button class="btn">Pay Now!</button>
					<% } %>
				</div>
			</div>
			<br/>
			<div class="row item-description">
				<div class="col-sm-12">
					<%= request.getAttribute("desc") %>
				</div>
			</div>

			<hr />
			<div class="container text-center">
				<% Bid[] bids =  ((Bid[])request.getAttribute("bids")); %>
				<% if(bids.length > 0) { %>
				<h3>
					Bids
				</h3>
				<% } %>

				<!-- a bidder, repeat below for each bidder -->
				<% for(int i = 0; i < bids.length; i++) { %>
					<div class="row item-bidder">
						<div class="col-sm-6">
							<div>
								<i class="fa fa-user fa-3x"></i><br/>
								Username: <%= bids[i].getBidder().getBidderId() %> <br/>
								Rating: <%= bids[i].getBidder().getBidderRating() %> <br/>
								<% if(bids[i].getBidder().getLocation()!=null) { %>
									Location: <%= bids[i].getBidder().getLocation() %> <br/> 
								<% } %>
								<%if(!bids[i].getBidder().getCountry().equals("") && bids[i].getBidder().getCountry()!=null) { %>
								Country: <%= bids[i].getBidder().getCountry() %> <br/>
								<% } %>
							</div>
						</div>
						<div class="col-sm-6">
							Bid: <span class="alert-warning"><%= bids[i].getAmount() %></span> <br/>
							Time: <%= bids[i].getTime() %>
						</div>
					</div>
				<% } %>
			</div>
			
		</div>
		
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

	<% } else {%>
		<body>
			<div class="container-fluid">
				<div class="container-fluid">
					<form class="navbar-form" role="search" action="item">
						<div class="form-group">
							<input type="text" class="form-control" name="id" placeholder="item id..." />
						</div>
						<button type="submit" class="btn btn-default">Search for item</button>
					</form>
				</div>
				<h2>
					No item found with that ID.
				</h2>
			</div>
		</body>
	<%}%>

	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
	<script type="text/javascript" src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</html>