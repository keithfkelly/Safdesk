<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Clients &middot; Safdesk</title>
<link rel="shortcut icon" href="img/favicon.png">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="css/bootstrap.css" rel="stylesheet" media="screen">
<link type="text/css" rel="stylesheet" href="css/tsc_rssfeed.css" />
<script src="http://code.jquery.com/jquery.js" type="text/javascript"></script>
<script src="js/bootstrap/bootstrap.min.js" type="text/javascript"></script>
<script src="js/enscroll.js" type="text/javascript" ></script>
<script src="js/jquery.floatheader.js" type="text/javascript" ></script>
<script src="js/jExpand.js" type="text/javascript" ></script>
<script src="amcharts/amcharts.js" type="text/javascript"></script>
<script type="text/javascript">
var userName;
var userLevel;
function getUser(){
	var userResults = document.cookie.match("User"+ '=(.*?)(;|$)');
	var statusResults = document.cookie.match("Status"+ '=(.*?)(;|$)');
	userName = userResults[1].toString();
	userLevel = statusResults[1].toString();
	document.getElementById("uName").innerHTML=userName;
	document.getElementById("userLink").setAttribute("href", "users?action=edit&username="+userName+"");	
}
</script>
	<script type="text/javascript">
	$(document).ready(function(){
		$('#dataDiv').enscroll();
	});
	</script>
</head>
<body onload="getUser()">
	<!--NAVBAR-->
	<div class="navbar navbar-inverse navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container-fluid">
				<a class="brand" href="#">SafDesk</a>
				<p class="navbar-text pull-right">
					Logged in as <a href="#" class="navbar-link" id="userLink"><strong><span
							id="uName"></span></strong></a>
				</p>
				<ul class="nav" id="nav">
				</ul>
			</div>
		</div>
	</div>

	<hr />
	<br />
	<!--/NAVBAR-->

	<!-- Sidebar Menu -->
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span2">
				<div class="well sidebar-nav" id="sideBar"></div>
			</div>
			
			<!-- /Sidebar Menu -->


			<!--Main Body-->
			<div class="span10"  >
				<!--Body content-->
				<h1>Clients</h1>
				<div class="row-fluid" id="dataDiv" style="height:400px">
				<div class="span12">
					<c:forEach var="client" items="${clients}">
						<div class="well">
							<h4><c:out value="${client.getClientname()}"/></h4><hr />
							<strong>Email Address: </strong><c:out value="${client.getEmailaddress()}"/><br />
							<strong>Phone Number: </strong><c:out value="${client.getPhonenumber()}"/>
							<button type="button" class="pull-right btn btn-warning" onclick="window.location.href='client?action=edit&idclient=<c:out value="${client.getId()}"/>'">Edit Client</button>
						</div>
						<hr />
		        	</c:forEach>
				</div>
				</div>
				<br />
			</div>
		</div>
	</div>
	<script type="text/javascript">
		getUser();
		if(userLevel==1){
			$("#nav").load("sideLoad/adminNav.html");
			$("#sideBar").load("sideLoad/adminSideBar.html");
		}else{
			$("#nav").load("sideLoad/userNav.html");
			$("#sideBar").load("sideLoad/userSideBar.html");
		}
	</script>
</body>
</html>