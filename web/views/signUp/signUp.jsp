<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	if (request.getAttribute("message") != null) {
%>
<script>
	var message = '<%=request.getAttribute("message")%>' + " ";
	alert(message);
</script>

<%
	request.removeAttribute("message");
	} else {
%>
<%
	}
%>

<%
	Boolean checkVCode = true; //default false/ 이메일 인증시 true전환 //테스트용으로 default true
%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>푸딩 일반회원가입</title>
<meta charset="utf-8">
<link rel="stylesheet" href="/food/resources/css/top-menu.css">
<link rel="stylesheet" href="/food/resources/css/foot.css">
<link rel="stylesheet" href="/food/resources/css/location.css">
<link rel="stylesheet" href="/food/resources/css/bootstrap.min.css">

<style>
.text_line {
	width: 400px;
}
</style>

<script type="text/javascript"
	src="/food/resources/js/jquery-3.3.1.min.js"></script>

<script type="text/javascript">
	var idCheck = Number(1);
	var vcodeCheck = Number(1);
	var vcode = null;
	var vmail;

	function checkId() {

		var inputCheck = $('#userId').val();
		var pat = /[A-Za-z0-9]{4,10}/;

		if ($('#userId').val().length > 0) {

			if (!(pat.test(inputCheck))) {
				alert("4~10자리 영문 또는 숫자만 입력하십시오");
				$('#userId').val("");
				return false;

			} else {
				$.ajax({
					url : "/food/midcheck",
					data : {
						"userId" : $('#userId').val()
					},
					type : "post",
					success : function(data) {
						idCheck = Number(data);
						if (idCheck == 1) {
							alert("아이디가 중복되었습니다. 다시 입력해주세요.");
							$('#userId').val("");
						} else {
							alert('사용가능한 아이디입니다 .');
						}
					}
				});
			}
		}

	}

	function dupliMail() {

		$.ajax({

			url : "/food/memmaildupli",
			type : "post",
			data : {
				cusMail : $('#email').val()
			},
			success : function(data) {
					if(data==1)
						{
						alert("메일이 중복되었습니다.");
						$('#email').val("");
						}
					else
						{
						sendMail();
						}
			}

		});

	}

	function sendMail() {//이메일 인증코드 발송  

		$.ajax({
			url : "/food/sendmail",
			data : {
				email : $('#email').val()
			},
			type : "post",
			success : function(data) {
				vcode = Number(data);
				alert("인증번호를 전송하였습니다. 메일을 확인해주세요.");
				vmail = $('#email').val();
			},
			error : function() {
				alert("인증번호 전송에 실패했습니다. 메일주소를 확인해주세요.");
				$('#email').val("");
				vmail = "";
			}
		});

	}

	function vCodeCheck() {

		if (vmail == $('#email').val()) {

			if (vcode == $('#vCode').val()) {
				alert('인증완료');
				vcodeCheck = 0;
				$('[name=email]').val($('#email').val());
				$('#email').attr('disabled', 'disabled');
				
			} else {
				alert('인증번호를 확인해주세요.');
				vcodeCheck = 1;
			}
		} else {
			alert("이메일이 변경되었습니다. 인증번호를 다시 발송하세요.");
			$('#vCode').val("");
		}

	}

	function checkPwd() {
		//비밀번호 확인
		if ($('#upwd1').val() != $('#upwd2').val() && $('#upwd1').val() != null) {
			alert('암호와 암호확인 값이 일치하지 않습니다.\n' + '다시 입력하십시오.');
			$('#upwd2').val("");
		}
		return false;
	}
</script>

</head>
<body>
	<!-- 상단 네비게이션 바 -->
	<div class="navcl">
		<nav class="navbar navbar-inverse">

			<div class="collapse navbar-collapse" id="myNavbar"
				style="padding-left: 0px;">
				<ul class="nav navbar-nav">
					<li style="margin-right: 10px;"><a class="navbar-brand"
						href="/food/index.jsp "> <img
							src="/food/resources/images/LOGO_fooding.png"
							style="width: 65px; height: 30px;">
					</a></li>
					<li><a href="/food/index.jsp">Home</a></li>
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

	<!-- 회원가입 화면구성 -->
	<h3
		style="text-align: center; margin-right: 50px; margin-bottom: 50px; margin-top: 50px;">회원가입</h3>
	<form action="/food/csenroll.pwenc" method="post">
		<table align="center" cellspacing="20px">
			<input type="submit" id="sub1" hidden="hidden">
			<!-- 아이디 입력란 -->
			<tr>
				<td><i class="glyphicon glyphicon-user"></i></td>
				<td><input id="userId" name="userId" class="text_line"
					type="text" placeholder="아이디 : 4~10자리 영문 또는 숫자로 작성하십시오"
					required="required" onblur="checkId();" maxlength="10"
					pattern="[A-Za-z0-9]{4,10}" title="4~10자리 영문 또는 숫자로 작성하십시오"></td>

			</tr>
			<!-- 패스워드 입력란 -->
			<tr>
				<td><i class="glyphicon glyphicon-lock"></i></td>
				<td><input id="upwd1" name="userPwd" class="text_line"
					type="password" placeholder="비밀번호" required="required"
					maxlength="20" pattern="^(?=.*[a-zA-Z])((?=.*\d)|(?=.*\W)).{6,20}$"
					title="6~20자리의 최소 1개이상의 숫자 또는 특수문자를 사용하세요(대소문자 구별)"
					placeholder="비밀번호 : 6~20자리의 최소 1개이상의 숫자 또는 특수문자를 사용하세요(대소문자 구별)"></td>
				<td></td>
			</tr>
			<!-- 패스워드 확인 입력란 -->
			<tr>
				<td><i class="glyphicon glyphicon-lock"></i></td>
				<td><input id="upwd2" onblur="checkPwd();" class="text_line"
					type="password" placeholder="비밀번호 확인" required="required"
					maxlength="20"></td>
				<td></td>
			</tr>
			<!-- 이름 입력란 -->
			<tr>
				<td><i class="glyphicon glyphicon-user"></i></td>
				<td><input id="userName" class="text_line" type="text"
					placeholder="이름" name="userName" required="required" maxlength="10"
					pattern="^[\uac00-\ud7af\s]{1,4}[\uac00-\ud7af]$" title="10자리 이하 한글만 입력가능합니다"></td>
				<td></td>
			</tr>
			<!-- 이메일 입력란 -->
			<tr>
				<td><i class="glyphicon glyphicon-user"></i></td>
				<td><input id="email" class="text_line" type="email"
					placeholder="이메일"  required="required"><input type="hidden" name="email" value=""></td>
				<td><input type="button" value="인증번호 발송" onclick="dupliMail();"
					required="required"></td>
				
			</tr>
			<!-- 인증번호 입력란 -->
			<tr>
				<td><i class="glyphicon glyphicon-user"></i></td>
				<td><input id="vCode" class="text_line" type="text"
					placeholder="인증번호" required="required"></td>
				<td><input type="button" value="인증" onclick="vCodeCheck();"
					required="required"></td>
			</tr>

			<!-- 가입하기 버튼 -->
			<!-- 이메일 인증시 checkVCode->true 시키고 -->
			<tr align="center">
				<td></td>
				<td><input type="button" value="가입하기" onclick="checkForm();">
					<script type="text/javascript">
						function checkForm() {
							if (vcodeCheck != 1 && idCheck != 1) {

								$('#sub1').click();
							} else if (vcodeCheck == 1) {
								alert("이메일 미인증 상태입니다.");
							} else if (idCheck == 1) {
								alert("아이디 중복검사를 해주세요.");
							}
						}
					</script></td>
				<td></td>
			</tr>
		</table>
	</form>

	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<div class="foot" style="bottom: 0; position: absolute">
		<p>FoodTruck &nbsp;Copyright @ iei.or.kr &nbsp; All Rights
			Reserved.</p>
	</div>
</body>
</html>
