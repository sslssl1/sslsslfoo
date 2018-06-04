<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%  if(request.getAttribute("message") !=null ){%>
	<script>
	var message = '<%= request.getAttribute("message")%>' +  " ";
	alert(message);
	</script>

<% request.removeAttribute("message");
}else{ %>
 <%} %>

<!DOCTYPE html>
<html lang="en">
<head>
<title>푸딩 로그인</title>
<meta charset="utf-8">
<link rel="stylesheet" href="/food/resources/css/top-menu.css">
<link rel="stylesheet" href="/food/resources/css/foot.css">
<link rel="stylesheet" href="/food/resources/css/location.css">
<link rel="stylesheet" href="/food/resources/css/bootstrap.min.css">
<script type="text/javascript"  src="/food/resources/js/jquery-3.3.1.min.js"></script>
<style>
	.log_text_line { width:200px; }
</style>

</head>
<body>
	<!-- 상단 네비게이션 바 -->
	<div class="navcl">
		<nav class="navbar navbar-inverse">
			
				<div class="collapse navbar-collapse" id="myNavbar" style="padding-left: 0px;">
					<ul class="nav navbar-nav" >
					<li style="margin-right:10px;"><a class="navbar-brand" href="/food/index.jsp ">
						<img src="/food/resources/images/LOGO_fooding.png" style="width:65px; height:30px; ">
						</a></li>
					<li ><a href="/food/index.jsp">Home</a></li>
					<li><a href="/food/slocation">지역검색</a></li>
					<li><a href="/food/flist">푸드트럭</a></li>
					<li><a href="/food/fes_show">축제검색</a></li>
					</ul>

					<ul class="nav navbar-nav navbar-right">
						<li class="active"><a href="/food/views/logIn/logIn.jsp"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
					<li><a href="/food/views/signUp/select_signUp.jsp"><span class="glyphicon glyphicon-edit"></span> 회원가입</a></li>
					</ul>
				</div>
		</nav>
	</div>

<br><br>
<!-- 로그인 화면구성 -->
<div class="container" style="margin-top:100px;">
  <h3 style="text-align:center; margin-right:20px;">로그인</h3>
</div>
  
 
  <form method="post" action="/food/memberlogin.pwenc">
  <table align="center" cellspacing="10px" style="text-align:center">
  <!-- 테이블 아이디 -->
  	<tr>
		<td><i class="glyphicon glyphicon-user"></i></td>
		<td><input class="log_text_line"type="text" placeholder="아이디" id="userId" name="userId" ></td>
		<td><input type="submit" value="로그인"></td>
	</tr>
	
	<tr>
			<td><i class="glyphicon glyphicon-lock"></i></td>
			<td><input class="log_text_line" id="password" name="userPwd" type="password" placeholder="비밀번호"></td>
	</tr>
	
	<tr>
			<td></td>
			<td>
				<button onclick='location.href="/food/views/logIn/id_Find.jsp"; return false; '>아이디찾기</button>
				<button onclick='location.href="/food/views/logIn/pwd_Find.jsp"; return false; '>비밀번호찾기</button>
			</td>
		</tr> 
	
  </table>
  </form>



<div class="foot" style="bottom:0; position: absolute;">
		<p>FoodTruck &nbsp;Copyright @ iei.or.kr &nbsp; All Rights Reserved.</p>
	</div>

</body>
</html>
