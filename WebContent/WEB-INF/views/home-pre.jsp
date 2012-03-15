<%-- Landing page
     Copyright (C) 2012  Artur Ventura

Author: Artur Ventura

This file is part of Zebrinho.

Zebrinho is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Zebrinho is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with GNU Emacs.  If not, see <http://www.gnu.org/licenses/>. --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<html>
<head>
<title>Zebrinho</title>
<link rel="stylesheet" href="/zebrinho/resources/css/bootstrap.css" />

<link href='http://fonts.googleapis.com/css?family=Yanone+Kaffeesatz' rel='stylesheet' type='text/css'>
<script type="text/javascript" src="/zebrinho/resources/js/jquery.js"></script>
<script type="text/javascript" src="/zebrinho/resources/js/bootstrap.js"></script>
<script type="text/javascript" src="/zebrinho/resources/js/bootstrap-dropdown.js"></script>
<link href='http://fonts.googleapis.com/css?family=Lustria' rel='stylesheet' type='text/css'>
<style>

body {
	font-family: 'Yanone Kaffeesatz', sans-serif;
	background-color: hsl(34, 53%, 82%);
background-image: -webkit-repeating-linear-gradient(45deg, transparent 5px, hsla(197, 62%, 11%, 0.5) 5px, hsla(197, 62%, 11%, 0.5) 10px,                  
      hsla(5, 53%, 63%, 0) 10px, hsla(5, 53%, 63%, 0) 35px, hsla(5, 53%, 63%, 0.5) 35px, hsla(5, 53%, 63%, 0.5) 40px,
      hsla(197, 62%, 11%, 0.5) 40px, hsla(197, 62%, 11%, 0.5) 50px, hsla(197, 62%, 11%, 0) 50px, hsla(197, 62%, 11%, 0) 60px,                
      hsla(5, 53%, 63%, 0.5) 60px, hsla(5, 53%, 63%, 0.5) 70px, hsla(35, 91%, 65%, 0.5) 70px, hsla(35, 91%, 65%, 0.5) 80px,
      hsla(35, 91%, 65%, 0) 80px, hsla(35, 91%, 65%, 0) 90px, hsla(5, 53%, 63%, 0.5) 90px, hsla(5, 53%, 63%, 0.5) 110px,
      hsla(5, 53%, 63%, 0) 110px, hsla(5, 53%, 63%, 0) 120px, hsla(197, 62%, 11%, 0.5) 120px, hsla(197, 62%, 11%, 0.5) 140px       
      ),
  -webkit-repeating-linear-gradient(135deg, transparent 5px, hsla(197, 62%, 11%, 0.5) 5px, hsla(197, 62%, 11%, 0.5) 10px, 
      hsla(5, 53%, 63%, 0) 10px, hsla(5, 53%, 63%, 0) 35px, hsla(5, 53%, 63%, 0.5) 35px, hsla(5, 53%, 63%, 0.5) 40px,
      hsla(197, 62%, 11%, 0.5) 40px, hsla(197, 62%, 11%, 0.5) 50px, hsla(197, 62%, 11%, 0) 50px, hsla(197, 62%, 11%, 0) 60px,                
      hsla(5, 53%, 63%, 0.5) 60px, hsla(5, 53%, 63%, 0.5) 70px, hsla(35, 91%, 65%, 0.5) 70px, hsla(35, 91%, 65%, 0.5) 80px,
      hsla(35, 91%, 65%, 0) 80px, hsla(35, 91%, 65%, 0) 90px, hsla(5, 53%, 63%, 0.5) 90px, hsla(5, 53%, 63%, 0.5) 110px,
      hsla(5, 53%, 63%, 0) 110px, hsla(5, 53%, 63%, 0) 140px, hsla(197, 62%, 11%, 0.5) 140px, hsla(197, 62%, 11%, 0.5) 160px       
  );
}

.navbar{
	margin-bottom:0px;
	
}

.navbar-search .search-query{
	font-family: 'Yanone Kaffeesatz', sans-serif;
	height:25px;
	line-height:17px;
}

.holder{
	background:rgba(0,0,0,0.3);
	padding:14px;
	margin-top:250px;
	border-radius: 10px;
	box-shadow: 0 5px 10px rgba(0,0,0,0.12);

}

#content{
	margin-bottom:0px;
}
#inner{
	margin-top:10px;
}
.brand{
	font-family:'Lustria';
}
.container-inner{
	padding:5px;
	background: white;
	border-radius: 10px;
	padding-bottom:40px;
}
</style>

<script>
	$(document).ready(function(){
		$(".container").delay(400).fadeIn(1000);
	});
</script>

</head>
<body>
  
	<div class="container holder" style="display:none">
	<div class="container-inner">
	<div style="margin-top:40px; line-height:80px; font-size:80px; height:100px; font-family:Lustria,serif;">
		<center>
			Zebrinho
		</center>
	</div>
	<div style="font-size:22px; margin-bottom:15px;">
		<center>
			A twitter clone.
		</center>
		
		</div>
	<c:if test="${username == null}">
	<center>
		<a class="btn btn-large btn-primary" href="/zebrinho/login">Login</a> <a class="btn btn-large" href="/zebrinho/register">Register</a>
		</center>
	</c:if>
	</div>
	</div>
</body>
</html>
