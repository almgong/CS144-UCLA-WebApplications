<!DOCTYPE html>
<html>
	<head>
		<title>Search Results for: <%= request.getParameter("q") %> | Cho's Auction House</title>
		<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" />
		<link href='https://fonts.googleapis.com/css?family=Varela' rel='stylesheet' type='text/css'>
		<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-XdYbMnZ/QjLh6iI4ogqCTaIjrFk87ip+ekIjefZch0Y+PvJ8CDYtEs1ipDmPorQ+" crossorigin="anonymous">
		<link href='css/suggestion.css' rel='stylesheet' type='text/css'>
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
			.list-group-item {
				cursor:pointer;
			}
			.list-group-item:hover, .list-group-item.active {
				background-color: #f3f3f3;
			}
			.navigation-links i {
				cursor: pointer;
			}
			.navigation-links i:hover, .navigation-links i:active {
				color: #D3D3D3;
			}
		</style>
	</head>
	<body>
		<!-- top navigation -->
		<div class="container-fluid">
			<form class="navbar-form navbar-left" role="search" action="search">
				<div class="form-group">
					<input type="text" class="form-control" id="search" name="q" placeholder="I am looking for..." />
					<input type="hidden" name="numResultsToSkip" value="0" />
					<input type="hidden" name="numResultsToReturn" value="20" />
				</div>
				<button type="submit" class="btn btn-default">Search</button>
			</form>
		</div>

		<!-- body -->
		<div class="container-fluid">
			<h2>Search results for "<%= request.getParameter("q") %>":</h2>

			<% int numSkip = Integer.parseInt(request.getParameter("numResultsToSkip")); 

			   int numRet = Integer.parseInt(request.getParameter("numResultsToReturn"));

			   int numResults = (Integer)request.getAttribute("numResults"); //upper bound on search result count

			   int prev = 0;
			   int next = 0;
			   int numResultsReturned = numResults;
			   boolean showPrev;
			%>
			<% if(numSkip < numRet) {  //prevent any potential errors
					next = numRet;
					prev = 0;
					showPrev = false; //this means numSkip = 0, can't go back
				} 
				else {
					prev = numSkip-numRet;
					next = numSkip+numRet;
					showPrev=true;
				} 

				numResults+=numSkip;
			%>
			<p>
				<i>
					<% if(numResults > 0) { %>
						Showing results <%=numSkip + 1%> to <%=numResults%>
					<% } %>
					<% if(numResults <= 0) { %>
						No results
					<% } %>
				</i>
			</p>
			<div class="row text-center navigation-links">
				<div class="col-sm-6">
					<% if(showPrev) { %>
					<i class="fa fa-chevron-circle-left fa-2x" onclick="window.location.href='/eBay/search?q=<%= request.getParameter("q") %>&numResultsToSkip=<%=prev%>&numResultsToReturn=<%=numRet%>'"></i>
					<% } %>
				</div>
				<div class="col-sm-6">
					<% if(numResultsReturned==20) { %>
					<i class="fa fa-chevron-circle-right fa-2x" onclick="window.location.href='/eBay/search?q=<%= request.getParameter("q") %>&numResultsToSkip=<%=next%>&numResultsToReturn=<%=numRet%>'"></i>
					<% } %>
				</div>
			</div>
			<hr />
			<ul id="" class="list-group">
				<% String[] idsCasted = (String[])request.getAttribute("ids"); %>
				<% String[] namesCasted = (String[])request.getAttribute("names"); %>
				<% for(int i=0; i < idsCasted.length; i++) {  %>
					<li class="list-group-item" onclick="window.location.href='/eBay/item?id=<%= idsCasted[i] %>'">
						ID: <%= idsCasted[i] %> <br />
						Name: <%= namesCasted[i] %>
					</li>
				<% } %>
			</ul>

			<hr/>
			<div class="row text-center navigation-links">
				<div class="col-sm-6">
					<% if(showPrev) { %>
					<i class="fa fa-chevron-circle-left fa-2x" onclick="window.location.href='/eBay/search?q=<%= request.getParameter("q") %>&numResultsToSkip=<%=prev%>&numResultsToReturn=<%=numRet%>'"></i>
					<% } %>
				</div>
				<div class="col-sm-6">
					<% if(numResultsReturned==20) { %>
					<i class="fa fa-chevron-circle-right fa-2x" onclick="window.location.href='/eBay/search?q=<%= request.getParameter("q") %>&numResultsToSkip=<%=next%>&numResultsToReturn=<%=numRet%>'"></i>
					<% } %>
				</div>
			</div>
			<br/><br/>
		</div>
	</body>

	<script type="text/javascript" src="js/autosuggest.js"></script>
    <script type="text/javascript" src="js/suggestions.js"></script>
	<script type="text/javascript">
        window.onload = function () {
            var oTextbox = new AutoSuggestControl(document.getElementById("search"), new SuggestionProvider()); 
        }
    </script>

	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
	<script type="text/javascript" src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</html>
