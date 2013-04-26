<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.sql.*"%>
<%@ page import="com.safdesk.util.*"%>
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
<%!
    ResultSet rsCat =null;
    ResultSet rsClient = null;
    %>
<%
    new DB();
	rsCat = DB.getAll("catagories");
	new DB();
    rsClient = DB.getAll("client");
%>
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
    	function newCatagory(){
    		var selectBox = document.getElementById("ticketcat");
    		var selectedValue = selectBox.options[selectBox.selectedIndex].value;
        	if(selectedValue=="createnew"){
            	$("#myModal").load("sideLoad/newCat.html");
        		$("#myModal").modal({remote:true});
            	}
        	}
</script>
<script>
$(document).ready(function(){
	var ref = document.referrer;
	if(ref.match("catagory")){
		$('#msg').addClass("alert alert-success span4 offset4");
		$('#msg').append("<button type=\"button\" class=\"close\" data-dismiss=\"alert\">×</button><h5>New Catagory Created</h5>");
	}
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
			<div class="span10">
				<!--Body content-->
				<div class="row-fluid">
					<div id="msg"></div>					
					<div class="span8 offset2 well">
						<form class="form-horizontal" method="POST" action="ticket">
							<fieldset>
								<legend><h1>New Ticket</h1></legend>
								<div class="control-group warning">
									<label class="control-label" for="id">Ticket ID</label>
									<div class="controls">
										<input type="text" class="warning" readonly="readonly" id="id"
											name="idtickets" value='<c:out value="${ticket.id}" />' />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">Catagory</label>
									<div class="controls">
										<c:choose>
											<c:when test="${edit=='edit'}">
												<input type="text" class="warning" readonly="readonly" name="ticketcat" value='<c:out value="${ticket.cat}" />' />
											</c:when>
											<c:otherwise>
												<select name="ticketcat" id="ticketcat"
													onchange="newCatagory()">
													<% while(rsCat.next()){ %><option
														value="<%=rsCat.getString(2)%>"><%=rsCat.getString(2)%></option>
													<%}%>
													<option value="createnew">....Create New
														Catagory....</option>
												</select>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">Client</label>
									<div class="controls">
										<c:choose>
											<c:when test="${edit=='edit'}">
												<input type="text" class="warning" readonly="readonly"
													name="ticketclient"
													value='<c:out value="${ticket.client}" />' />
											</c:when>
											<c:otherwise>
												<select name="ticketclient">
													<% while(rsClient.next()){ %><option
														value="<%=rsClient.getString(2)%>"><%=rsClient.getString(2)%></option>
													<%}%>
												</select>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">Description</label>
									<div class="controls">
										<c:choose>
											<c:when test="${edit=='edit'}">
												<input type="text" name="ticketdesc" class="warning"
													readonly="readonly"
													value='<c:out value="${ticket.desc}" />' />
											</c:when>
											<c:otherwise>
												<input type="text" name="ticketdesc"
													value='<c:out value="${ticket.desc}" />' />
											</c:otherwise>
										</c:choose>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">Details</label>
									<div class="controls">
										<textarea class="span10" rows="6" name="ticketdata">
											<c:out value="${ticket.data}" />
										</textarea>
										<br />
										<br />
										<button type="submit" class="btn btn-primary">Submit</button>
										<button type="button" class="btn btn-warning offset2" onclick="window.location.href='ticket?action=allTickets'">Back</button>
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
	<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
</body>
</html>