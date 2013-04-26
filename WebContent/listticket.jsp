<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Tickets &middot; Safdesk</title>
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
<script>
	var client;
	var problem;
	var daysOpen;
	var id;
	function popData(i, c, p, dO){
			id = i;
			client = c;
			problem = p;
			daysOpen = dO;
			document.getElementById("ticketData").innerHTML=
			"<ul class=\"unstyled\"><li><strong>Client:\t</strong>"+client+"</li>"
            +"<hr />"
            +"<li><strong>Problem:<\/strong><br />"+problem+"</li>"
            +"<hr />"
            +"<li><strong>Days Open:\t</strong>"+daysOpen+"</li>"
            +"<hr />"
         +"</ul>"
         +"<button type=\"button\" class=\"btn btn-primary\" onclick=\"window.location.href='ticket?action=edit&idtickets="+id+"'\">Edit Ticket</button>"
		 +"<button type=\"button\" class=\"btn btn-danger offset1\" onclick=\"window.location.href='ticket?action=close&idtickets="+id+"'\">Close Ticket</button>";
		}

</script>
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
		$('#tableDiv').enscroll();
		$('#tableDiv').floatHeader();

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
				<h1>Open Tickets</h1>
				<div class="row-fluid" id="tableDiv" style="height:390px">
				<div class="span12 well">
					<c:forEach var="ticket" items="${tickets}">
						
						<div class="well well-hover" onClick="popData('<c:out value="${ticket.getId()}" />','<c:out value="${ticket.getClient()}" />','<c:out value="${ticket.getData()}" />','<c:out value="${ticket.getDaysOpen()}" />')">
							<i class="icon-tag icon-white"></i>
							<strong>ID: </strong><c:out value="${ticket.getId()}"/><br />
							<strong>Description: </strong><c:out value="${ticket.getDesc()}"/>
						</div>
						<hr />
		        	</c:forEach>
				</div>
				</div>
				<br />
				<h1>Ticket Information</h1>
				<div class="row-fluid">
					<div class="span11 well" id="ticketData"></div>
				
				</div>
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