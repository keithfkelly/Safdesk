<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Renewals &middot; Safdesk</title>
<link rel="shortcut icon" href="img/favicon.png">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="css/bootstrap.css" rel="stylesheet" media="screen">
<link type="text/css" rel="stylesheet" href="js/fullcalendar/fullcalendar.css" />
<script src="http://code.jquery.com/jquery.js" type="text/javascript"></script>
<script src="js/bootstrap/bootstrap.min.js" type="text/javascript"></script>
<script src="js/fullcalendar/fullcalendar.js" type="text/javascript" ></script>
<script src="js/enscroll.js" type="text/javascript" ></script>
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
$(document).ready(function() {
	
	var date = new Date();
	var d = date.getDate();
	var m = date.getMonth();
	var y = date.getFullYear();
	
	$('#renewCal').fullCalendar({
		contentHeight: 600,
		weekends: false,
		firstDay: 1,
		header: {
			left: 'prev,next today',
			center: 'title',
			right: 'month,basicWeek,basicDay'
		},
		editable: true,
		events: [
			<c:forEach var="renewal" items="${renewals}">
				{
					title: '<c:out value="${renewal.getCatagory()}"/> Renewal - <c:out value="${renewal.getClient()}"/>',
					start: '<c:out value="${renewal.getDateDue()}"/>',
					url: 'renewal?action=edit&idrenewal=<c:out value="${renewal.getId()}"/>'
				},
			</c:forEach>
		]
	});
	
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
			<div class="well span10"  >
				<div id="renewCal">
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