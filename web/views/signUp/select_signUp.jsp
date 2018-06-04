<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>푸딩 회원가입 선택</title>
<meta charset="utf-8">
<link rel="stylesheet" href="/food/resources/css/top-menu.css">
<link rel="stylesheet" href="/food/resources/css/foot.css">
<link rel="stylesheet" href="/food/resources/css/location.css">
<link rel="stylesheet" href="/food/resources/css/bootstrap.min.css">


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
						<li><a href="/food/views/logIn/logIn.jsp"><span
								class="glyphicon glyphicon-log-in"></span> Login</a></li>
						<li class="active"><a href="/food/views/signUp/select_signUp.jsp"><span
								class="glyphicon glyphicon-edit"></span> 회원가입</a></li>
					</ul>

				</div>
		</nav>
	</div>
	
	
	
	<div align="center" style="width: 510px; margin-left: 34%; margin-top: 60px; text-align: center;">
		<a href=""><img alt="(로고)" style="width: 40%; height: 40%;" src="/food/resources/images/logo (1).png"> </a>
		<hr style="border: 0.5px solid #DCDCDC;">
		<br>
		<h2 style="text-align: left; margin-bottom: 30px;">
			<font style="vertical-align: bottom; margin-right: 28%;">회원가입</font> <br>
		</h2>
		<h5 style="text-align: left;">가입 유형을 선택해 주세요.</h5>
		<h5 style="text-align: left; margin-bottom: 50px;">회원 가입 후 푸딩에서 제공하는 다양한 서비스를 제공 받으세요.</h5>
		<p style="float: left; border: 1px solid #DCDCDC; width: 240px; height: 223px; margin-right: 1%;">
			<a href="/food/views/signUp/signUp.jsp"><img style="width: 237px; height: 219px; padding: 0" alt="" src="/food/resources/images/normal.png"></a>
		</p>
		<p style="float: right; border: 1px solid #DCDCDC; width: 240px; height: 223px;">
			<a href="/food/views/signUp/business_Signup.jsp"><img style="width: 237px; height: 219px; padding-top: 0" alt="" src="/food/resources/images/business.png"></a>
		</p>
	</div>
</body>
</html>
