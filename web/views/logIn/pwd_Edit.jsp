<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<% if(request.getAttribute("message") != null){ %>
	<script>
	 var message = '<%=request.getAttribute("message")%>';
	 alert(message);
	location.href="/food/views/logIn/logIn.jsp";
	 </script>
	
<%request.removeAttribute("message");
}else{%>

<%} %>
<!DOCTYPE html>
<html lang="en">
<head>
<title>푸딩 비밀번호 변경</title>
<meta charset="utf-8">
<link rel="stylesheet" href="/food/resources/css/top-menu.css">
<link rel="stylesheet" href="/food/resources/css/foot.css">
<link rel="stylesheet" href="/food/resources/css/location.css">
<link rel="stylesheet" href="/food/resources/css/bootstrap.min.css">

<script type="text/javascript"
	src="/food/resources/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript">
	
	//var vcode = 0;
	//var vcodeCheck=0;
	
<%-- 	function sendPwd() {
		
 	$.ajax({
			url : "/food/editpwd.pwenc",
			data : {
				userid : "<%=request.getParameter("userId")%>",
				userpwd : $('#pwd1').val()
			},
			type : "post",
			success : function(data) {
				location.href="/food/views/logIn/logIn.jsp";
				}
			}
	);
	
		
	} --%>

	function checkPwd() {
		//비밀번호 확인
		if ($('#pwd1').val() != $('#pwd2').val() && $('#pwd1').val() != null) {
			alert('암호와 암호확인 값이 일치하지 않습니다.\n' + '다시 입력하십시오.');
			$('#pwd2').val("");
		}
		return false;
	}
	
	
</script>

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
						<li class="active"><a href="/food/views/logIn/logIn.jsp"><span
								class="glyphicon glyphicon-log-in"></span> Login</a></li>
						<li><a href="/food/views/signUp/select_signUp.jsp"><span
								class="glyphicon glyphicon-edit"></span> 회원가입</a></li>
					</ul>

				</div>
		</nav>
	</div>
	<!-- 비밀번호 찾기  -->
	<div class="container" style="margin-top: 50px;">
		<h3 style="text-align: center;">새 비밀번호 설정</h3>
		<br> <br>
		<center>
			<form style="width: 700px" action="/food/editpwd.pwenc" method="post">
			<input type="hidden" name="userid" value="<%=request.getParameter("userId")%>">
				<!-- 패스워드 입력란 -->
				<div class="input-group">
					<span class="input-group-addon"><i
						class="glyphicon glyphicon-lock"></i></span> <input
						style="width: 570px; height: 25px;" type="password" id="pwd1"
						class="form-control" name="password" placeholder="새 비밀번호" required>
				</div>
				<br>
				<!-- 패스워드 확인 입력란 -->
				<div class="input-group">
					<span class="input-group-addon"><i
						class="glyphicon glyphicon-lock"></i></span> <input
						style="width: 570px; height: 25px;" id="pwd2" type="password" name="userPwd"
						class="form-control"  onblur="checkPwd();" placeholder="새 비밀번호 확인" required>
				</div>
				<br>
				<!-- 확인 버튼 -->
				<input type="submit" value="확인">
				<br> <br> <br> <br>
			</form>
	</div>
	</center>
	<br>
	<div class="foot" style="bottom: 0; position: absolute;">
		<p>FoodTruck &nbsp;Copyright @ iei.or.kr &nbsp; All Rights
			Reserved.</p>
	</div>
</body>
</html>
