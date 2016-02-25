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
		</style>
	</head>
	<body>
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
			Bids: TODO
		</p>
		<p>
			Location: <%= request.getAttribute("item-location") %>
			<br>
			Location-lat: <%= request.getAttribute("item-location-lat") %>
			<br>
			Location-long: <%= request.getAttribute("item-location-lon") %>
		</p>
		<p>
			Country: <%= request.getAttribute("country") %>
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
		
	</body>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
	<script type="text/javascript" src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</html>