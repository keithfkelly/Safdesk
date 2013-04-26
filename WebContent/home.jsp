<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Sign in &middot; Safdesk</title>
<link rel="shortcut icon" href="img/favicon.png">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Cookie Check -->
<script type="text/javascript">
		function readCookie() {
			if(document.cookie.match("Status=failed")){
				document.getElementById('msg').setAttribute("class", "alert alert-error");
				document.getElementById('msg').innerHTML="<i class=\"icon-warning-sign\">  </i><strong>Error:</strong><br />Incorrect Username or Password";	
			}
		}
	</script>
<link href="css/bootstrap.css" rel="stylesheet">
<link href="css/bootstrap-responsive.css" rel="stylesheet">

<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
      <script src="js/html5shiv.js"></script>
    <![endif]-->
</head>

<body class="login" onload="readCookie()">

	<div class="container">

		<form class="form-signin" method="post" action="doLogin">
			<img src="img/logo.png" alt="" /> <br /> <br /> <input type="text"
				name="user" class="input-block-level" placeholder="Email address">
			<input type="password" name="pass" class="input-block-level"
				placeholder="Password">
			<div id="msg"></div>
			<button class="btn btn-large btn-primary" type="submit">Sign
				in</button>
		</form>
		<div class="login-other">
			<a class="btn btn-primary btn-block" href="#">Register</a><a
				class="btn btn-block btn-primary" href="#">Log a Ticket</a>
		</div>


	</div>
	<!-- /container -->

	<!--javascript
    ==================================================
    Placed at the end of the document so the pages load faster-->
	<script src="http://code.jquery.com/jquery.js" type="text/javascript"></script>
	<script src="js/bootstrap/bootstrap-transition.js" type="text/javascript"></script>


</body>
</html>
