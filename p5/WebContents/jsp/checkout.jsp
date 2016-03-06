<!-- class import(s) -->
<%@ page import="edu.ucla.cs.cs144.Bid" %>

<!doctype html>
<html>
	<head>
		<title>Checkout for item <%= request.getAttribute("id") %> | Cho's Auction House</title>
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
		</style>
	</head>
	
	<body>
		Hello world
		You are buying at: <%= request.getAttribute("buyPrice") %>
	</body>

	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
	<script type="text/javascript" src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</html>