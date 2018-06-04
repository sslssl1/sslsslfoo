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

<!DOCTYPE html>
<html lang="en">
<head>
<title>푸딩 사업자 회원가입</title>
<meta charset="utf-8">
<link rel="stylesheet" href="/food/resources/css/top-menu.css">
<link rel="stylesheet" href="/food/resources/css/foot.css">
<link rel="stylesheet" href="/food/resources/css/location.css">
<link rel="stylesheet" href="/food/resources/css/bootstrap.min.css">
<script type="text/javascript"
	src="/food/resources/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript">
	var idCheck = Number(1);
	var vcodeCheck = Number(1);
	var vcode = null;
	var vmail;

	$(function() {
		$('#userId').blur(function() {

			if ($('#userId').val().length > 0) {

				var inputCheck = $('#userId').val();
				var pat = /[A-Za-z0-9]{4,10}/;

				if (!(pat.test(inputCheck))) {
					alert("4~10자리 영문 또는 숫자만 입력하십시오");
					$('#userId').val("");
					return false;

				} else {

					$.ajax({
						url : "/food/MemberIdDupliCheckServlet2",
						data : {
							userid : $('#userId').val()
						},
						type : "post",
						success : function(data) {
							idCheck = Number(data);
							if (data == 1) {
								alert("아이디가 중복되었습니다. 다시 입력해주세요.");
								$('#userId').val("");

							} else {
								alert("사용가능한 아이디입니다.");
							}
						},
						error : function(jqXHR, textStatus, errorThrown) {
							console.log(jqXHR.responseText);
						}
					});
				}

			}

		});
	});

	function dupliMail() {

		$.ajax({

			url : "/food/memmaildupli",
			type : "post",
			data : {
				"busMail" : $('#email').val()
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
				alert("인증번호를 전송하였습니다.")
				vmail = $('#email').val();
			console.log(vcode);
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
		if ($('#upwd1').val() != $('#upwd2').val()) {
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
	<div class="container" style="margin-top: 50px;">
		<h3 style="text-align: center;">회원가입(사업자)</h3>
		<br> <br>
		<center>
			<form style="width: 750px;" method="post"
				action="/food/bsenroll.pwenc">
				<!-- 아이디 입력란 -->
				<div class="input-group">
					<span class="input-group-addon"><i
						class="glyphicon glyphicon-user"></i></span> <input id="userId"style="width: 475px; height: 25px; margin-right: 95px;"	id="userId" type="text" class="form-control" name="userId"
						placeholder="아이디" required maxlength="10" pattern="[A-Za-z0-9]{4,10}"  title="4~10자리 영문 또는 숫자로 작성하십시오"  placeholder="아이디 : 4~10자리 영문 또는 숫자로 작성하십시오" >
				</div>
				<br>

				<!-- 패스워드 입력란 -->
				<div class="input-group">
					<span class="input-group-addon"><i
						class="glyphicon glyphicon-lock"></i></span> <input id="upwd1"
						style="width: 475px; height: 25px; margin-right: 95px;"
						type="password" class="form-control" name="userPwd"
						placeholder="비밀번호" required="required" maxlength="20"
						pattern="^(?=.*[a-zA-Z])((?=.*\d)|(?=.*\W)).{6,20}$" title="6~20자리의 최소 1개이상의 숫자 또는 특수문자를 사용하세요(대소문자 구별)" 
						 placeholder="비밀번호 : 6~20자리의 최소 1개이상의 숫자 또는 특수문자를 사용하세요(대소문자 구별)"   >
				</div>
				<br>


				<!-- 패스워드 확인 입력란 -->
				<div class="input-group">
					<span class="input-group-addon"><i
						class="glyphicon glyphicon-lock"></i></span> <input id="upwd2"
						onblur="checkPwd(); return false;"
						style="width: 475px; height: 25px; margin-right: 95px;"
						type="password" class="form-control" placeholder="비밀번호 확인"
						required="required" maxlength="20">
				</div>
				<br>

				<!-- 이름 입력란 -->
				<div class="input-group">
					<span class="input-group-addon"><i
						class="glyphicon glyphicon-user"></i></span> <input
						style="width: 475px; height: 25px; margin-right: 95px;"
						id="userName" type="text" class="form-control" name="userName"
						placeholder="이 름" required="required" maxlength="10" 
						pattern="^[\uac00-\ud7af\s]{1,4}[\uac00-\ud7af]$" title="10자리 이하 한글만 입력가능합니다" >
				</div>
				<br>

				<!-- 전화번호 입력란 -->
				<div class="input-group">
					<span class="input-group-addon"><i
						class="glyphicon glyphicon-user"></i></span> <input
						style="width: 475px; height: 25px; margin-right: 95px;"
						id="userPhone" type="tel" class="form-control" name="userPhone"
						placeholder="전화번호" required="required" >
				</div>
				<br>

				<!-- 이메일 입력란 -->
				<div class="input-group">
					<span class="input-group-addon"><i
						class="glyphicon glyphicon-user"></i></span> <input
						style="width: 475px; height: 25px;" type="email"
						class="form-control"  id="email" placeholder="이메일"
						required="required"> <input type="hidden" name="email" value=""><span
						style="float: center; margin-right: 0px"><input
						style="height: 33px;" type="button" value="인증번호 발송"
						onclick="dupliMail();" pattern="([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$"></span>
				</div>
				<br>

				<!-- 인증번호 입력란 -->
				<div class="input-group">
					<span class="input-group-addon"><i
						class="glyphicon glyphicon-lock"></i></span> <input
						style="width: 475px; height: 25px;" id="vCode" type="text"
						class="form-control" placeholder="인증번호" required="required">
					<span style="float: center; margin-left: 20px;"><input
						style="height: 33px;" type="button" value="확인하기"
						onclick="vCodeCheck();"></span>
				</div>
				<br>

				<!-- FAX -->
				<div style="width: 570px; margin-left: -30px"
					class="panel panel-default">
					<div class="panel-heading">
						<h4>FAX : 01-2345-6789</h4>
					</div>
					<div class="panel-body">
						사업자등록증을 위 FAX번호로 보내주시 바랍니다.<br> *관리자 승인 후 해당 전호번호로 문자를
						보내드립니다.
					</div>
				</div>
				<br>
				<!-- 가입하기 버튼 -->
				<input type="submit" id="sub1" hidden="hidden">
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
			</form>
	</div>
	</center>
	<br>
	<br>
	<br>
	<div class="foot" style="bottom: 0;">
		<p>FoodTruck &nbsp;Copyright @ iei.or.kr &nbsp; All Rights
			Reserved.</p>
	</div>
</body>
</html>
