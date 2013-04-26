<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Dashboard &middot; Safdesk</title>
<link rel="shortcut icon" href="img/favicon.png">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="css/bootstrap.css" rel="stylesheet" media="screen">
<link type="text/css" rel="stylesheet" href="css/tsc_rssfeed.css" />
<script src="http://code.jquery.com/jquery.js" type="text/javascript"></script>
<script src="js/bootstrap/bootstrap.min.js" type="text/javascript"></script>
<script src="js/tsc_rssfeed.js" type="text/javascript"></script>
<script src="js/tsc_vticker.js" type="text/javascript"></script>
<script src="js/amcharts/amcharts.js" type="text/javascript"></script>
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
<script>
	$(document).ready(function () {
	    $('#silRep').rssfeed('http://www.siliconrepublic.com/feeds/',{}, function(e) {
	        $(e).find('div.rssBody').vTicker({ showItems: 3});
	    });
	    $('#theReg').rssfeed('http://www.theregister.co.uk/headlines.atom',{}, function(e) {
	        $(e).find('div.rssBody').vTicker({ showItems: 3});
	    });
	});
</script>
<script>
var chart;
var ticksToday = 0;
var ticksWeek = 0;
var ticksLonger = 0;
var dueToday = 0;
var dueWeek = 0;
var dueMonth = 0;

<c:forEach var="ticket" items="${tickets}">
	if(<c:out value="${ticket.getDaysOpen()}" /><=1){
		ticksToday++;
	}else if(<c:out value="${ticket.getDaysOpen()}" /> >=1 && <c:out value="${ticket.getDaysOpen()}" /> <7){
		ticksWeek++;
	}else{
		ticksLonger++;
	}
</c:forEach>

<c:forEach var="renewal" items="${renewals}">
	if(<c:out value="${renewal.getDaysUntilDue()}" />==0){
		dueToday++;
	}else if(<c:out value="${renewal.getDaysUntilDue()}" /> >=1 && <c:out value="${renewal.getDaysUntilDue()}" /> < 8){
		dueWeek++;
	}else if( <c:out value="${renewal.getDaysUntilDue()}" /> >=8 &&  <c:out value="${renewal.getDaysUntilDue()}" /> < 31){
		dueMonth++;
	}
</c:forEach>
	


var chartData = [{Label: "Tickets Opened Today",value: ticksToday, color: "#04D215"},{Label: "Tickets Open More Then 1 day",value: ticksWeek, color: "#F8FF01"},{Label: "Tickets open More then 1 Week",value: ticksLonger, color: "#D15858"}, 
                 {Label:"Renewals Due Today",value:dueToday, color: "#D15858"},{Label:"Renewals Due this week",value:dueWeek, color: "#F8FF01"},{Label:"Renewals Due this month",value:dueMonth, color: "#04D215"}
];


AmCharts.ready(function() {
    // SERIAL CHART
    chart = new AmCharts.AmSerialChart();
    chart.dataProvider = chartData;
    chart.categoryField = "Label";
    chart.marginRight = 0;
    chart.marginTop = 0;    
    chart.autoMarginOffset = 0;
    // the following two lines makes chart 3D
    chart.depth3D = 20;
    chart.angle = 30;

    // AXES
    // category
    var categoryAxis = chart.categoryAxis;
    categoryAxis.labelRotation = 45;
    categoryAxis.dashLength = 1;
    categoryAxis.color = "#33b5e5";
    categoryAxis.gridPosition = "start";

    // value
    var valueAxis = new AmCharts.ValueAxis();
    valueAxis.title = "value";
    valueAxis.dashLength = 5;
    valueAxis.color = "#33b5e5";
    chart.addValueAxis(valueAxis);

    //Balloon
    var balloon = chart.balloon;
    balloon.color = "#33b5e5";
    balloon.fillColor = "#121417";
    balloon.adjustBorderColor = true;
    balloon.textShadowColor = "#FFFFFF";

    // GRAPH            
    var graph = new AmCharts.AmGraph();
    graph.valueField = "value";
    graph.colorField = "color";
    graph.balloonText = "[[category]]: [[value]]";
    graph.type = "column";
    graph.lineAlpha = 0;
    graph.fillAlphas = 1;
    chart.addGraph(graph);

    // WRITE
    chart.write("ticketChart");
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
				<button type="button" class="btn btn-primary" data-toggle="collapse" data-target="#stats">Statistics Toggle</button>
				<br />
				<br />
				<button type="button" class="btn btn-primary" data-toggle="collapse" data-target="#feeds">Tech News Toggle</button>
			</div>
			
			<!-- /Sidebar Menu -->


			<!--Main Body-->
			<div class="span10"  >
				<!--Body content-->
				<h1>Statistics</h1>
				<div class="row-fluid" id="stats">
					<div class="span12 well">
						<div class="span8 offset2" id="ticketChart" style="height: 400px"></div>
			 		</div>
			 	</div>
			 	<h1>Tech News</h1>
			 	<div class="row-fluid" id="feeds">			 		
			 		<div class="span5 well" id="silRep"></div>
			 		<div class="span5 offset1 well" id="theReg"></div>
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