<%-- User profile
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
	border-bottom-left-radius: 7px;
	border-bottom-right-radius: 7px;
	box-shadow: 0 5px 10px rgba(0,0,0,0.12);
	padding-top:0px;
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
	padding:20px;
	background: white;
	border-bottom-left-radius: 7px;
	border-bottom-right-radius: 7px;
	padding-bottom:40px;
	padding-bottom:40px;
}
</style>

</head>
<body>

<div class="navbar">
    <div class="navbar-inner">
      <div class="container">
        <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </a>
        <a class="brand" href="/zebrinho">Zebrinho</a>
        <div class="nav-collapse">
          <form class="navbar-search pull-left" action="/zebrinho/search" method="post">
            <input name="username" type="text" class="search-query span2" placeholder="Search for users">
          </form>
          <ul class="nav pull-right">
            <li><a href="/zebrinho/user/${ username }">${ username }</a></li>
            <li class="divider-vertical"></li>
            <li><a href="/zebrinho/logout">Logout</a></li>
          </ul>
        </div><!-- /.nav-collapse -->
      </div>
    </div><!-- /navbar-inner -->
  </div>
  
	<div class="container holder" >
	<div class="container-inner">
		<legend>${ user.username }</legend>
		<div style="margin-top:-10px; margin-bottom:20px;">
		Following <span class="label label-success">${ user.following }</span> person and followed by  <span class="label label-info">${ user.followers }</span> person
		</div>
		<c:choose>
			<c:when test="${ isOwn == true }">
			<span style="font-size:20px; color:#696969">Its you!</span>
			 
		</c:when>

			<c:when test="${ follows == true }">
				<a href="/zebrinho/unfollow/${ user.username }" class="btn"> Unfollow </a>
			</c:when>

			<c:otherwise>
				<a href="/zebrinho/follow/${ user.username }" class="btn btn-danger">
					Follow </a>
			</c:otherwise>
		</c:choose>

		<div id="inner">
			<c:forEach var="tweet" items="${user.myTweets}">
			<div>
				<div style="font-size:20px;line-height:36px;"><c:out value="${ tweet.content }"></c:out></div>
				<span style="color:#666; font-size:13px;"> on ${ tweet.formatedDate } by <a href="/zebrinho/user/${ tweet.user.username }">${ tweet.user.username }</a> </span>
			</div>
			</c:forEach>
		</div>
	</div>
	</div>
</body>
</html>
