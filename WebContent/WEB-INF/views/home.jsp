<%-- Logged homepage
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
	background: #36c;
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

<script>

function escapeHTML(x) {                                        
    return(                                                                 
        x.replace(/&/g,'&amp;').                                         
            replace(/>/g,'&gt;').                                           
            replace(/</g,'&lt;').                                           
            replace(/"/g,'&quot;')                                         
    );                                                                      
};

function update () {
	$.get("/zebrinho/getTweetsAsync", function (c) { 
		c = eval(c);
		$('#inner').html("");
		for (var i=0; i < c.length; i++) {
			var o = c[i];

			$("#inner").append('<div ><div style="font-size:20px;line-height:36px;">' + escapeHTML(o['content']) + '</div><span style="color:#666; font-size:13px;"> on ' + o['date'] + ' by <a href="/zebrinho/user/' + o['user'] + '">' + o['user'] + '</a> </span></div>');
		}
	});
}
function post() {
		$.post("/zebrinho/tweetAsync", { tweet : $("#content").val() }, function() {
			$('#content').val("");
			update();
		});
		
}

$(document).ready(function(){
	$("#tweet-btn").click(post);	
	$('#content').on('keypress', function(e) {
		var code = (e.keyCode ? e.keyCode : e.which);
		 if(code == 13) { 
		   post()
		 }
	});
	update();
	setInterval(function(){ update() }, 10000 );
});
	
</script>

<div class="navbar">
    <div class="navbar-inner">
      <div class="container">
        <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </a>
        <a class="brand" href="/zebrinho/">Zebrinho</a>
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
	<c:if test="${username != null}">
		<center>
			<input id="content" type="text" placeholder="What are you thinking?" style="height:26px;"class="input-xlarge span8" name="tweet" />
			<button class="btn" id="tweet-btn" >Tweet!</button>
		</center> 
		<div style="border-bottom: 1px solid #ddd;">
			<span style="font-size:16px; color:#888">Your wall</span>
		</div>
		<div id="inner">

		</div>
	</c:if>
	</div>
	</div>
</body>
</html>
