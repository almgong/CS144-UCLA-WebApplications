<!-- class import(s) -->
<%@ page import="edu.ucla.cs.cs144.Bid" %>

<!doctype html>
<html>
	<head>
		<title>Confirmation for purchase of item <%= request.getAttribute("id") %> | Cho's Auction House</title>
		<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" />
		<link href='https://fonts.googleapis.com/css?family=Varela' rel='stylesheet' type='text/css'>
		<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-XdYbMnZ/QjLh6iI4ogqCTaIjrFk87ip+ekIjefZch0Y+PvJ8CDYtEs1ipDmPorQ+" crossorigin="anonymous">
		<style type="text/css">
			body {
				font-family: 'Varela', sans-serif;
				background-color:#f9f9f9;
				background: url('img/cho.jpg') no-repeat;
				background-size: cover; 
			}
			.btn {
				background-color: #228B22;
				color:white;
			}
			.btn:hover, .btn:active {
				background-color: #145314;
				color:white;
			}

			/*page specific*/
			.super-small {
				font-size:5px;
				font-style: italic;
			}
			.checkout-form{
				background-color:#f9f9f9;
				margin-top:2%;
				border:3px solid gray;
				-webkit-box-shadow: 4px 5px 5px 0px rgba(140,136,140,1);
-moz-box-shadow: 4px 5px 5px 0px rgba(140,136,140,1);
box-shadow: 4px 5px 5px 0px rgba(140,136,140,1);
			}
		</style>
	</head>
	
	<body>
		<div class="container text-center">
			<div class="checkout-form">
				<h3>You have purchased the following item:</h3>
				<p>
					ItemID: <%= request.getAttribute("id") %> <br/>
					Item Name: <%= request.getAttribute("name") %> <br/>
					Buy Price: <%= request.getAttribute("buyPrice") %> <br/>
					Credit Card: <%= request.getAttribute("ccnum") %><br/>
					Time: <%= request.getAttribute("time") %>
				</p>
				<p class="super-small disclaimer">
					Disclaimer: Cho's Auction House is not responsible for any damages, 
					failed expectations, or carrying out refunds. Buy at your
					own risk!
				</p>
			</div>

		</div>
	</body>

	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
	<script type="text/javascript" src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</html>