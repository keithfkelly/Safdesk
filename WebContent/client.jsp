<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Client Details &middot; Safdesk</title>
<link rel="shortcut icon" href="img/favicon.png">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="css/bootstrap.css" rel="stylesheet" media="screen" />
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery.js" type="text/javascript"></script>
<script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"
	type="text/javascript"></script>
<script src="js/bootstrap/bootstrap.min.js" type="text/javascript"></script>
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
			<div class="span10">
				<!--Body content-->
				<div class="row-fluid">
					<div id="msg"></div>
					<div class="span8 offset2 well">
						<form class="form-horizontal" method="POST" action="client">
							<fieldset>
								<legend>
									<h1>Client Details</h1>
								</legend>
								<input type="hidden" name="idclient" id="idclient" value="${client.getId()}">
								<div class="control-group">
									<label class="control-label">Name</label>
									<div class="controls">
										<c:choose>
											<c:when test="${edit=='edit'}">
												<input type="text" class="warning" readonly="readonly"
													name="name" id="name"
													value='<c:out value="${client.getClientname()}" />' />
											</c:when>
											<c:otherwise>
												<input type="text" name="name" id="name" />
											</c:otherwise>
										</c:choose>
									</div>
								</div>

								<div class="control-group">
									<label class="control-label">Email Address</label>
									<div class="controls">
										<input type="text" name="email" id="email"
											value='<c:out value="${client.getEmailaddress()}" />' />
									</div>
								</div>

								<div class="control-group">
									<label class="control-label">Phone Number</label>
									<div class="controls">
										<input type="text" name="phone" id="phone"
											value='<c:out value="${client.getPhonenumber()}" />' /> <br />
										<br />
										<button type="submit" class="btn btn-primary">Submit</button>
										<button type="button" class="btn btn-warning offset2"
											onclick="window.location.href='client?action=allClients'">Back</button>
									</div>
								</div>
							</fieldset>
						</form>
					</div>
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