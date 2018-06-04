<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>푸딩 아이디찾기</title>
<meta charset="utf-8">
<link rel="stylesheet" href="/food/resources/css/top-menu.css">
<link rel="stylesheet" href="/food/resources/css/foot.css">
<link rel="stylesheet" href="/food/resources/css/location.css">
<link rel="stylesheet" href="/food/resources/css/bootstrap.min.css">
<script type="text/javascript"
	src="/food/resources/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript">
	
	var vcode = 0;
	var vcodeCheck=0;
	
	function sendMail() {//이메일 인증코드 발송  
		
	$.ajax({
			url : "/food/findid",
			data : {
				email : $('#email').val(),
				username : $('#userName').val()
			},
			type : "post",
			success : function(data) {
				
				if(data=="회원정보가 없습니다.")
				{
					alert("회원정보가 없습니다.");
				}else{
				vcode = Number(data);
				alert("인증번호를 전송하였습니다.");
				}
				
			}
	});
	
	}


	function vCodeCheck() {
		//이메일 인증버튼 클릭하면 실행되는 함수
		//이메일 인증번호와 서버의 인증번호를 비교하여
		//맞으면 checkVCode=true
		//틀리면 checkVCode=false

			if ($('#vCode').val() != "" && vcode == $('#vCode').val()) {
			alert('인증완료');
			vcodeCheck = 1;
			
		} else {
			alert('인증번호를 확인해주세요.');
		}
		
	}
	
	 function findbt(){
		if (vcodeCheck == 0){
			alert('입력창을 다시 확인하여 주세요.');
		}else{
			$('#idf').submit();
		}
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




	<!-- 아이디 찾기  -->
	<div class="container" style="margin-top: 50px;">
		<h3 style="text-align: center;">아이디 찾기</h3>
		<br> <br>

		<center>
			<form action="/food/searchId" method="post" id="idf" style="width: 700px">

				<!-- 이름 입력란 -->
				<div class="input-group">
					<span class="input-group-addon"><i
						class="glyphicon glyphicon-user"></i></span> <input
						style="width: 475px; height: 25px; margin-right: 95px;"
						id="userName" type="text" class="form-control" name="userName"
						placeholder="이름" >
				</div>
				<br>


				<!-- 이메일 입력란 -->
				<!-- 인증하기 버튼 누르면  이름과 아이디 값으로 해당 유저가 있는지 검사 있으면
				입력받은 메일로  인증번호 발송   인증번호 보내고 다시 현재 페이지로 와야함 AJAX?-->
				<div class="input-group">
					<span class="input-group-addon"><i
						class="glyphicon glyphicon-user"></i></span> <input
						style="width: 475px; height: 25px;" type="email"
						class="form-control" name="email" id="email" placeholder="이메일">
					<span style="float: center; margin-left: 20px;"><input
						style="height: 33px;" type="button" value="인증하기" 
						onclick="sendMail();"></span>
				</div>
				<br>
				<!-- 인증번호 입력란 -->
				<div class="input-group">
					<span class="input-group-addon"><i
						class="glyphicon glyphicon-lock"></i></span> <input
						style="width: 475px; height: 25px;" id="vCode" type="text"
						class="form-control" placeholder="인증번호"> <span
						style="float: center; margin-left: 20px;"><input
						style="height: 33px;" type="button" value="확인하기" onclick="vCodeCheck();" ></span>
				</div>
				<br> <br>
				<!-- 찾기 버튼 -->
				
				<input type="button" style="height: 33px;" onclick="findbt();" value="찾기" > <br>
				
				
				<br> <br> <br>

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
