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

			/*page specific*/
			.checkout-form{
				margin-top:25%;
				border:3px solid gray;
				-webkit-box-shadow: 4px 5px 5px 0px rgba(140,136,140,1);
-moz-box-shadow: 4px 5px 5px 0px rgba(140,136,140,1);
box-shadow: 4px 5px 5px 0px rgba(140,136,140,1);
padding-bottom: 15px;
			}
		</style>
	</head>
	
	<body>
		<div class="container text-center">
			<div class="checkout-form">
				<form class="form" action="confirm" method="POST">
					<h3>Buy This Item Now!</h3>
					<p>
						ItemID: <%= request.getAttribute("id") %> <br/>
						Item Name: <%= request.getAttribute("name") %> <br/>
						Buy Price: <%= request.getAttribute("buyPrice") %>
					</p>
					<label for="ccnum">Credit Card: </label>
					<input id="ccnum" type="text" name="creditCardNum" placeholder="e.g 1234..." />
					<input type="submit" value="Buy!"/>
				</form>
			</div>

		</div>
	</body>

	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
	<script type="text/javascript" src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</html>