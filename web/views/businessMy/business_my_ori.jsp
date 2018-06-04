<%@page import="javax.servlet.jsp.tagext.PageData"%>
<%@page import="javax.swing.text.AbstractDocument.Content"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="member.model.vo.*,review.model.vo.*,java.util.*,reply.model.vo.*,coupon.model.vo.*,foodtruck.model.vo.*"%>


<%
	//페이지관리 수정하기눌렀을때

	//로그인 정보 가져오기
	if(request.getAttribute("businessPageFail")!=null)
	{
		%>
		<script>  alert('<%=request.getAttribute("businessPageFail")%>'); </script>
		<%
	}
	Member loginMember = null;
	if (session.getAttribute("loginMember") instanceof Business) {
		loginMember = (Business) session.getAttribute("loginMember");
	} else { //로그인 아니라면 index로 강제 소환
%>
<script>
	location.href ="/food/index.jsp";
	</script>
<%
	}
	//tab 정보 가져오기
	if (request.getAttribute("tab") != null) {
%>
<script> 
var tab = '<%=request.getAttribute("tab")%>';
</script>
<%
	} else {
%>
<script>
	var tab = "tab-1";
</script>
<%
	}
	if (request.getAttribute("couponMessage") != null) {
%>
<script>
alert("<%=request.getAttribute("couponMessage")%>");
</script>
<%
	}
%>






<!DOCTYPE html>
<html>
<head>
<title>푸딩 사업자</title>
<meta charset="utf-8">
<script src="/food/resources/js/jquery-3.3.1.min.js"></script>
<script src="/food/resources/js/jquery.min.js"></script>
<script src="/food/resources/js/jquery.scrolly.min.js"></script>
<script src="/food/resources/js/jquery.scrollzer.min.js"></script>
<script src="/food/resources/js/skel.min.js"></script>
<script src="/food/resources/js/util.js"></script>
<script src="/food/resources/js/main.js"></script>


<link rel="stylesheet" href="/food/resources/css/left-menu.css">
<link rel="stylesheet" href="/food/resources/css/top-menu.css">
<link rel="stylesheet" href="/food/resources/css/foot.css">
<link rel="stylesheet" href="/food/resources/css/left-menu-tab.css">
<link rel="stylesheet" href="/food/resources/css/right-menu.css">
<link rel="stylesheet" href="/food/resources/css/modInfo.css">
<link rel="stylesheet" href="/food/resources/css/review.css">
<link rel="stylesheet" href="/food/resources/css/replay.css">
<link rel="stylesheet" href="/food/resources/css/business-my.css">
<link rel="stylesheet" href="/food/resources/css/map.css">
<link rel="stylesheet" href="/food/resources/css/bootstrap.min.css">

<script>
<%int cnt = 0;%>
$(function(){
	$.ajax({
		url : "/food/mtadd",
		type : "post",
		success : function(data){	
		},
		error: function(jqXHR, textStatus, errorThrown) { 
	        console.log(jqXHR.responseText); 
	    } 	
	});
	
	<%if(loginMember instanceof Business){%>
	$.ajax({
	      url : "/food/NY",
	      data : {
	    	 
               userid : "<%=((Business)loginMember).getBusiness_Id()%>"
            
	      },
	      type : 'post',
	      success : function(data) {
	    	 
	    	  onoffState=data;
	         if(data == 'Y'){//장사시작인 상태
	        	 
		            $(".gb").hide();
		            $(".rb").show();
	        	
	         }else {//장사 종료인 상태
	        	
	        	 $(".rb").hide();
		         $(".gb").show();
	         }
	      }
	   });
	<%}%>
});

function onoff(){
	
   	$.ajax({
	      	url : "/food/onoff",
	      	data : {
	        state: onoffState,
	            <%if(loginMember instanceof Business){%>
	               userid : "<%=((Business)loginMember).getBusiness_Id()%>"
	            <%}%>
	         
	         },
	      type : 'post',
	      success : function(data) {
	    	
	         if(data == 1){
	            alert("장사를 시작합니다");
	            onoffState='Y';
	            $("#graybutton").attr("src", "/food/resources/images/redbutton.png");
	            
	            $(".gb").hide();
	            $(".rb").show();
	            
	            
	            
	         }else if(data == 2){
	            alert("장사를 종료합니다");
	            onoffState='N';
	            $("#graybutton").attr("src", "/food/resources/images/graybutton.png");
	            
	            $(".rb").hide();
	            $(".gb").show();
	          	
	         }
	      }
	   });
	}

</script>



<script type="text/javascript">
var addmenucount = 0;  //실제 추가메뉴 개수
var addmenunum =0;  //메뉴태그넘버링

function couponcreate(){
	

		
		var con = confirm('쿠폰을 생성하시겠습니까?');
		
		if(con ==true){
	
	 var date = new Date();
	 date = getFormatDate(date);
	 var coupondate = $('#coupondate').val(); 
    var coupondaten = coupondate.replace(/[^0-9\.]+/g,"");
    
	 if($('#couponcontent').val() == ""){
		 alert("내용을 입력해주세요.");
	 }
    else if($('#coupondate').val() == ""){
   	 alert("날짜를 선택해주세요.");
    }
    else if(date > coupondaten){
		 alert("현재 날짜보다 작습니다.");
	 }else{
		 $.ajax({
			url : "/food/cpcreate",
			type : "post",
			data : {
			bid : "<%=((Business) loginMember).getBusiness_Id()%>", 
			createcontent : $('#couponcontent').val(), 
			createdate : $('#coupondate').val()
			},
			success : function(data){
				if(data == 0){
					alert("동일한 제목의 쿠폰이 존재합니다.");
					$('#couponcontent').val("");
				}else if(data ==1){
					location.reload();
					 $('#couponcontent').val("");
					 $('#coupondate').val("");
						alert('쿠폰 생성 완료!');
				}
			}	
	 	});
	 	}
	 

	
		}
	 
	}
function getFormatDate(date){

	var year = date.getFullYear();
	var month = (1 + date.getMonth());
	month = month >= 10 ? month : '0' + month;
	var day = date.getDate();
	day = day >= 10 ? day : '0' + day;
	return  year + '' + month + '' + day
}

////////////////


	function delOriMenu(menuNum)
	{	

		$('#oriMenu'+menuNum).html(" ");
		$('[name=oriMenuCount]').val( $('[name=oriMenuCount]').val() - 1 );
	
	}

	function addMenu(){
		addmenucount++;
		addmenunum++;
		var htmlCode = "";
			htmlCode = '<br><br><tr><td id="addMenu'+addmenunum+'"> &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&nbsp;&nbsp;<input type="button" onclick="delAddMenu('+addmenunum+')" value="메뉴 삭제">&nbsp;<input name="addMenuFile'+addmenunum+'" onchange="addMenuChangeImg('+addmenunum+');"  id="addMenuFile'+addmenunum+'" type="file" accept="image/*"><input type="hidden" name="mFile" value="addMenuFile'+addmenunum+'"><br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&nbsp;&nbsp;<img width="300" height="300" id="addMenuImg'+addmenunum+'" src="" alt="사진을 등록해주세요."><br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&nbsp;&nbsp;메뉴 이름&nbsp;&nbsp;<input name="mName" type="text" required="required"><br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&nbsp;&nbsp;가&nbsp;격&nbsp;&nbsp;<input name="mPrice" type="number" required="required"> 원 </td></tr> '; 	
		$('#addMenuTable').append(htmlCode);
		$('[name=menuCount]').val(addmenucount);
		
	}
	function delAddMenu(menuNum)
	{	

		$('#addMenu'+menuNum).html(" ");
		addmenucount--;
		$('[name=menuCount]').val(addmenucount);
	}

	function addMenuChangeImg(num)
	{
		console.log('addMenuFile'+num);
		var preview = document.querySelector('#addMenuImg'+num);
		var file = document.querySelector('#addMenuFile'+num).files[0];
		var reader  = new FileReader(); 
        reader.onloadend = function () { 
               preview.src = reader.result; 
        } 
        if (file) { 
               reader.readAsDataURL(file); 
           } else { 
               preview.src = ""; 
        }
	}
	
	function oriMenuChange(num)
	{
		var preview = document.querySelector('#oriMenuImg'+num);
		var file = document.querySelector('[name=oriMenuFile'+num+']').files[0];
		var reader  = new FileReader(); 
        reader.onloadend = function () { 
               preview.src = reader.result; 
        } 
        if (file) { 
               reader.readAsDataURL(file); 
           } else { 
               preview.src = ""; 
        }
	}
	

	$(function() {
		
		$('[name=truckImg]').change(function(){
					
			 var preview = document.querySelector('#truckImg'); 
            var file = document.querySelector('[name=truckImg]').files[0]; 
            var reader  = new FileReader(); 
            reader.onloadend = function () { 
                   preview.src = reader.result; 
            } 
            if (file) { 
                   reader.readAsDataURL(file); 
               } else { 
                   preview.src = ""; 
            } 
		});
		
		if (tab != null) {
			$('.tab-content').removeClass('current');
			$('.tabs li').removeClass('current');
			$('.tabs li a').removeClass('active');
			$('#' + tab).addClass(' current');
			$('[data-tab=' + tab + '] a').addClass('active');
			
			tab = null;
		}

		$('.tabs li').click(function() {
			var tab_cl = $(this).attr('data-tab');

			$('.tabs li').removeClass('current');
			$('.tab-content').removeClass('current');
			$(this).addClass('current');
			$("." + tab_cl).addClass('current');
		});

		$('#delCheck').change(function() {
			//리뷰 전체선택 체크박스 선택
			if ($('#delCheck').is(":checked") == true) {
				$('[name=delCheck]').attr("checked", true);
			} else if ($('#delCheck').is(":checked") == false) {
				$('[name=delCheck]').attr("checked", false);
			}

		});

		$('#reply_all_check').change(function() {
			//댓글 전체선택 체크박스 선택
			if ($('#reply_all_check').is(":checked") == true) {

				$('[name=reply_check]').attr("checked", true);
			} else if ($('#reply_add_check').is(":checked") == false) {
				$('[name=reply_check]').attr("checked", false);
			}
		});
		
		var add= "bstruckinfo?bsId=<%=((Business)loginMember).getBusiness_Id()%>";
		var link = location.href;
		var linka = link.split('/').pop();
			if(add == linka){
				map.relayout();
			}
	});

	var vcodeCheck = 1;
	var opwCheck = 1;
	var cpwCheck = 1;
	var vcode = null;
	function checkMail() {
		if ($('#email2').val() != null) {
			if ($('#codeinput').css('display') == 'block'
					|| $('#numbersend').text() == '인증번호 재발송') {
				$('#codeinput').css('display', 'none');
				$('#numbersend').text('인증번호 발송');
			}
			var email = $("#email2").val();

			var exptext = /^[A-Za-z0-9_\.\-]+@[A-Za-zS0-9\-]+\.[A-Za-z0-9\-]+/;
			if ($('#email2').val() == "") {
				alert("입력하신 이메일이 없습니다.");
			} else if (exptext.test(email) == false) {
				if ($('#codeinput').css('display') == 'block'
						|| $('#numbersend').text() == '인증번호 재발송') {
					$('#codeinput').css('display', 'none');
					$('#numbersend').text('인증번호 발송');
				}
				//이메일 형식이 알파벳+숫자@알파벳+숫자.알파벳+숫자 형식이 아닐경우			
				alert("이메일형식이 올바르지 않습니다.");
				$('#email2').focus();
				return false;
			} else {
				$.ajax({
					url : "/food/checkmail",
					data : {
						userId : $('#ID').val(),
						email : $('#email2').val()
					},
					type : "post",
					success : function(data) {
						if (data == 0) {
							if ($('#codeinput').css('display') == 'block'
									|| $('#numbersend').text() == '인증번호 재발송') {
								$('#codeinput').css('display', 'none');
								$('#numbersend').text('인증번호 발송');
							}
							alert("중복되는 이메일 입니다.");
						} else if (data == 1) {
							$('#numbersend').text("인증번호 재발송");
							$('#codeinput').css('display', 'block');
							$.ajax({
								url : "/food/sendmail",
								data : {
									email : $('#email2').val()
								},
								type : "post",
								success : function(data) {
									vcode = Number(data);
									console.log(vcode);
									alert("인증번호를 전송하였습니다.");
								}
							});
						}
					}
				});
			}
		}
	}

	function vCodeCheck() {
		//이메일 인증버튼 클릭하면 실행되는 함수
		//이메일 인증번호와 서버의 인증번호를 비교하여
		//맞으면 checkVCode=true
		//틀리면 checkVCode=false

		if (vcode == $('#vCode').val()) {
			alert('인증완료');
			vcodeCheck = 0;
		} else {
			alert('인증번호를 확인해주세요~ ^~!');
			vcodeCheck = 1;
		}

	}
	function checkPwd() {
		//비밀번호 확인
		if ($('#newPassword').val() != $('#newPasswordRepeat').val()) {
			alert('암호와 암호확인 값이 일치하지 않습니다.\n' + '다시 입력하십시오.');
			$('#newPasswordRepeat').val("");
			cpwCheck = 1;
		} else {
			cpwCheck = 0;
		}
		return false;
	}

	function checkPwd2() {
		//비밀번호 확인
		var regExp = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"\s]/gi;
		if ($('#oldPassword').val() != null && $('#newPassword').val()) {
			if ($('#oldPassword').val() == $('#newPassword').val()) {
				alert('현재 비밀번호와 새 비밀번호가 같습니다.\n' + '새 비밀번호를 다시 입력하십시오.');
				$('#newPassword').val("");
			}
		}
		if (regExp.test($('#newPassword').val())) {
			alert("특수문자를 쓰실수없습니다.");
			var m = $('#newPassword').val().replace(regExp, "");
			$('#newPassword').val("");
			$('#newPassword').focus();
		}
		return false;
	}

	function checkOldPwd() {
		//현재 비밀번호 확인
		if ($('#oldPassword').val() != null
				&& $('#oldPassword').val().length > 0) {
			$.ajax({
				url : "/food/checkbspwd.pwenc",
				type : "post",
				data : {
					"userPwd" : $('#oldPassword').val(),
					"userId" : $('#ID').val()
				},
				success : function(data) {
					if (data == 0) {
						alert('현재 비밀번호가 일치하지 않습니다.');
						$('#oldPassword').val('');
						opwCheck = 1;
					} else if (data == 1) {
						opwCheck = 0;
					}
				}
			});
		}
	}
	
	function bsmodify() {
		if (opwCheck == 0) {
			if (cpwCheck == 0 && vcodeCheck == 0) {
				$.ajax({
					url : "/food/bupdate.pwenc",
					data : {
						userId : $('#ID').val(),
						userPwd : $('#newPasswordRepeat').val(),
						email : $('#email2').val()
					},
					type : "post",
					success : function(data) {
						$('#newPassword').text("");
						$('#newPasswordRepeat').text("");
						$('#codeinput').css('display', 'none');
						$('#numbersend').text('인증번호 발송');
						$('#email2').text("");
						location.href = "/food/views/businessMy/business_my.jsp";
						alert('회원정보 수정 완료!');
					}
				});
			} else if (cpwCheck == 0 && vcodeCheck != 0
					&& $('#newPasswordRepeat').val() != "") {
				$.ajax({
					url : "/food/bupdate.pwenc",
					data : {
						userId : $('#ID').val(),
						userPwd : $('#newPasswordRepeat').val(),
						email : $('#email').val()
					},
					type : "post",
					success : function(data) {
						$('#newPassword').text("");
						$('#newPasswordRepeat').text("");
						$('#email2').text("");
						location.href = "/food/views/businessMy/business_my.jsp";
						alert('회원정보 수정 완료!');
					}
				});
			} else if (cpwCheck != 0 && vcodeCheck == 0) {

				$.ajax({
					url : "/food/bupdate.pwenc",
					data : {
						userId : $('#ID').val(),
						userPwd : $('#oldPassword').val(),
						email : $('#email2').val()
					},
					type : "post",
					success : function(data) {
						$('#newPassword').text("");
						$('#newPasswordRepeat').text("");
						$('#codeinput').css('display', 'none');
						$('#numbersend').text('인증번호 발송');
						$('#email2').text("");
						location.href = "/food/views/businessMy/business_my.jsp";
						alert('회원정보 수정 완료!');
					}
				});
			} else {
				alert("입력하신 정보가 없습니다.");
			}
		} else {
			alert("현재 비밀번호를 입력해주세요.");
		}
	}

	function withdraw() {
		if (opwCheck == 0) {
			var answer = confirm("정말 탈퇴 하시겠습니까 ? ");

			if (answer) {
				var writer = prompt("\"탈퇴\"라고 적어주세요.");
				if (writer == "탈퇴") {
					$.ajax({
						url : "/food/withdraw",
						data : {
							bId : $('#ID').val(),
						},
						type : "post",
						success : function(data) {
							if (data == 1) {
								alert('탈퇴 성공!');
								location.reload();
							} else {
								alert('탈퇴 실패!');
								location.reload();
							}
						}
					});
				}else{
					alert("\"탈퇴\" 라고 정확히 입력해 주세요");
				
				}
			} else {
			}
		} else {
			alert("현재 비밀번호를 입력해주세요.");
		}

	}
	
	
	
</script>
<style type="text/css">
#map_wrap:active {
	clear: both;
	width: 300px;
}

#map:active {
	width: 300px;
}
</style>
<style>
#dialog-background {
	display: none;
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background: rgba(255, 255, 255, 0.5);
	z-index: 10;
}

#my-dialog {
	display: none;
	position: fixed;
	left: calc(50% - 160px);
	top: calc(50% - 70px);
	width: 320px;
	height: 140px;
	background: #fff;
	z-index: 11;
	padding: 10px;
}

.cover{
display: flex;
justify-content: space-between;
}

.cover2{
display: flex;
justify-content: space-between;
}
</style>
</head>
<body>
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
					<% if(loginMember==null){ %>
					<ul class="nav navbar-nav navbar-right">
						<li><a href="/food/views/logIn/logIn.jsp"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
						<li><a href="/food/views/signUp/select_signUp.jsp"><span class="glyphicon glyphicon-edit"></span> 회원가입</a></li>
					</ul>
					<% } else if(loginMember instanceof Customer){%>
					<ul class="nav navbar-nav navbar-right">
					<li><a><span class="glyphicon glyphicon-user"></span> <%=((Customer)loginMember).getCustomer_Id() %>님</a></li>
					<li><a href="/food/views/userMy/user_my.jsp"><span class="glyphicon glyphicon-th-list"></span>내 정보 보기</a></li>
					<li><a href="/food/memberlogout"><span class="glyphicon glyphicon-share"></span>로그아웃</a></li>
					</ul>
					<% } else if(loginMember instanceof Business){%>
						<ul class="nav navbar-nav navbar-right">

						<li class="gb" ><a class="gb"  href="javascript: onoff();"><img class="gb" id="graybutton"  alt="회색버튼" src="/food/resources/images/graybutton.png" width="15px" height="15px"><span class="glyphicon gb"></span>장사 시작</a></li>						
						<li class="rb" ><a class="rb"  href="javascript: onoff();" ><img class="rb" id="redbutton"  alt="빨간버튼" src="/food/resources/images/redbutton.png" width="15px" height="15px"><span class="glyphicon rb"></span>장사 종료</a></li>
						<li><a href="/food/foodtruckdetail?bId=<%=((Business)loginMember).getBusiness_Id() %>"><span class="glyphicon glyphicon-cutlery"></span>내 트럭</a></li>
						<li><a><span class="glyphicon glyphicon-user"></span> <%=((Business)loginMember).getBusiness_Id() %>님 </a></li>
						<li class="active"><a href="/food/views/businessMy/business_my.jsp"><span class="glyphicon glyphicon-th-list"></span>내 정보 보기</a></li>
						<li><a href="/food/memberlogout"><span class="glyphicon glyphicon-share"></span>로그아웃</a></li>
						</ul>
					<% } else if(loginMember instanceof Manager){%>
					<ul class="nav navbar-nav navbar-right">
					<li><a><span class="glyphicon glyphicon-user"></span> <%=((Manager)loginMember).getManager_Id() %>님</a></li>
					<li><a href="/food/views/adminMy/admin_my.jsp"><img alt="회원관리" src="/food/resources/images/people.png" width="15px" height="15px">회원관리</a></li>
					<li><a href="/food/memberlogout"><span class="glyphicon glyphicon-share"></span>로그아웃</a></li>
					
					</ul>
					<% } %>
				</div>
			
		</nav>
	</div>
	<div id="header" style="top: 50px">
		<nav id="nav">
			<ul id="ul" class="tabs">
				<li class="current" data-tab="tab-1"><a href="#edit"><span
						class="icon fa-edit">회원정보수정</span></a></li>
				<!--  <li data-tab="tab-2"><a href="#view"><span class="icon fa-user">내후기보기</span></a></li>-->
				<li data-tab="tab-3"><a href="/food/bsreplylist"><span
						class="icon fa-user">내댓글보기</span></a></li>
			<li data-tab="tab-0"><a href="/food/createclist"><span class="icon fa-user">쿠폰생성</span></a></li>
				<li data-tab="tab-4"><a href="/food/bclist"><span
						class="icon fa-envelope">쿠폰확인</span></a></li>
				<li data-tab="tab-5"><a href="/food/busedlist"><span
						class="icon fa-envelope-o">쿠폰사용내역</span></a></li>
				<li data-tab="tab-6"><a
					href="/food/bstruckinfo?bsId=<%=((Business) loginMember).getBusiness_Id()%>"><span
						class="icon fa-th">페이지관리</span></a></li>
			</ul>
		</nav>
	</div>

	<!-- 회원정보 수정 코드 부분~~~ -->
	<div id="tab-1" class="tab-content tab-1 " style="margin-bottom: 0px">
		<div id="tab-1div">
			<!-- <form id="frm" method="post" action="/food/bupdate.pwenc"> -->
			<div class="input-group">
				<h3>회원정보 수정</h3>
				<div style="border-bottom: 1px solid #bdbdbd;"></div>
				<br>
			</div>
			<div class="input-group" align="center">
				<input id="ID" type="text" class="form-control" name="ID"
					placeholder="아이디"
					value="<%=((Business) loginMember).getBusiness_Id()%>" readonly>
			</div>
			<div class="input-group">
				<input id="oldPassword" type="password" class="form-control"
					name="oldPassword" placeholder="현재 비밀번호" style="margin-top: 10px;"
					onblur="checkOldPwd();">
			</div>
			<div class="input-group">
				<input id="newPassword" type="password" class="form-control"
					name="newPassword" placeholder="새 비밀번호" style="margin-top: 10px;"
					onblur="checkPwd2();">
			</div>
			<div class="input-group">
				<input id="newPasswordRepeat" type="password" class="form-control"
					name="newPasswordRepeat" placeholder="새 비밀번호 확인"
					style="margin-top: 10px;" onblur="checkPwd();">
			</div>
			<div class="input-group">
				<input id="email" type="text" class="form-control" name="email"
					placeholder="Email" style="margin-top: 10px;"
					value="<%=((Business) loginMember).getBusiness_Email()%>" readonly>
			</div>
		
			<div>
				<input id="email2" type="email" class="form-control" name="email2"
					placeholder="new Email" style="margin-top: 10px;" required>
				<button id="numbersend" class="btn btn-outline-primary"
					style="margin-top: 10px;" onclick="checkMail();">인증번호 발송</button>
			</div>
			
			<div id="codeinput" class="input-group" style="display: none">
				<input id="vCode" type="text" class="form-control" name="vCode"
					placeholder="인증번호 입력" style="margin-top: 10px;">
				<button class="btn btn-outline-primary" style="margin-top: 10px;"
					onclick="vCodeCheck();">확인</button>
			</div>
			<br>
			<span><button class="btn btn-outline-primary"
					style="margin-right: 200px" onclick="bsmodify();">수정하기</button>
				<button class="btn btn-outline-primary"
					style="position: absolute; margin-left: -80px"
					onclick="withdraw();">탈퇴하기</button> </span>
		</div>
		<br> <br> <br>
		<!-- </form> -->
	</div>
	</div>
	<!-- 내 후기보기 코드 부분~~~~~ -->
	<!--  
		<div id="tab-2" class="tab-content tab-2">
			<div class="container">
				<h1>내후기 보기</h1>
				<table class="table table-striped">
					<thead>
						<tr>
							<th style="width: 10%; min-width: 100px;"><input type="checkbox">전체선택</th>
							<th>제목</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><input type="checkbox"></td>
							<td><a class="alert-link" href="">제목1</a></td>
						</tr>
						<tr>
							<td><input type="checkbox"></td>
							<td><a class="alert-link" href="">제목2</a></td>
						</tr>
						<tr>
							<td><input type="checkbox"></td>
							<td><a class="alert-link" href="">제목3</a></td>
						</tr>
						<tr>
							<td><input type="checkbox"></td>
							<td><a class="alert-link" href="">제목4</a></td>
						</tr>
						<tr>
							<td><input type="checkbox"></td>
							<td><a class="alert-link" href="">제목5</a></td>
						</tr>
						<tr>
							<td><input type="checkbox"></td>
							<td><a class="alert-link" href="">제목6</a></td>
						</tr>
						<tr>
							<td><input type="checkbox"></td>
							<td><a class="alert-link" href="">제목7</a></td>
						</tr>
						<tr>
							<td><input type="checkbox"></td>
							<td><a class="alert-link" href="">제목8</a></td>
						</tr>
						<tr>
							<td><input type="checkbox"></td>
							<td><a class="alert-link" href="">제목9</a></td>
						</tr>
					</tbody>
				</table>
				<hr style="margin-top: 30px;">
				<div style="margin-left: 35%">
					<ul class="pagination">
						<li class="page-item"><a class="page-link" href="#"><</a></li>
						<li class="page-item"><a class="page-link" href="#">1</a></li>
						<li class="page-item"><a class="page-link" href="#">2</a></li>
						<li class="page-item"><a class="page-link" href="#">3</a></li>
						<li class="page-item"><a class="page-link" href="#">></a></li>
						<li class="end-page-item"><button type="button" class="btn btn-outline-primary">삭제</button></li>
					</ul>
				</div>
			</div>
			<br><br><br>
		</div>
		-->
	<!-- 내 댓글보기 코드 부분~~~~ -->
	<div id="tab-3" class="tab-content tab-3">
		<div class="container">
			<h3>내댓글 보기</h3>
			<div style="border-bottom: 1px solid #bdbdbd;"></div>
				<br>
			<form method="post" action="bsreplydel">
				<table class="table table-striped">
					<thead>
						<tr>
							<th style="width: 10%; min-width: 100px;"><input
								type="checkbox" id="reply_all_check">전체선택</th>
							<th>내용</th>
							<th>&nbsp;</th>
							<th>작성일</th>
						</tr>
					</thead>
					<tbody>

						<%
							int listCount = 0;
							int startPage = 0;
							int endPage = 0;
							int maxPage = 0;
							int currentPage = 0;
							if (request.getAttribute("reply_list") != null) {
								ArrayList<Reply> replyList = (ArrayList<Reply>) request.getAttribute("reply_list");
								listCount = ((Integer) request.getAttribute("listCount")).intValue();
								startPage = ((Integer) request.getAttribute("startPage")).intValue();
								endPage = ((Integer) request.getAttribute("endPage")).intValue();
								maxPage = ((Integer) request.getAttribute("maxPage")).intValue();
								currentPage = ((Integer) request.getAttribute("currentPage")).intValue();

								for (Reply reply : replyList) {
						%>

						<tr>
							<th><input type="checkbox" name="reply_check"
								value="<%=reply.getReply_No()%>"></th>
							<th><%=reply.getReply_Content()%></th>
							<th><a
								href="reviewdetail?reviewNo=<%=reply.getReview_No()%>">원문보기</a></th>
							<th><%=reply.getReply_Date()%></th>
							<%
								}
								} else {
							%>
							<tr>
							<th></th>
							<th><font color="gray">댓글없음</font></th>
							<th></th>
							<th></th>
							</tr>
							<%
								}
							%>

						
						

					</tbody>
				</table>
				<hr style="margin-top: 30px;">
				<input type="submit" value="삭제하기">
			</form>
			
			<div style="text-align: center;">
				<%
					if (currentPage <= 1) {
				%>
				[맨처음]&nbsp;
				<%
					} else {
				%>
				<a href="/food/bsreplylist?page">[맨처음]</a>
				<%
					}
				%>
				<%
					if ((currentPage - 10) < startPage && (currentPage - 10) >= 1) {
				%>
				<a href="/food/bsreplylist?page=<%=startPage - 1%>">[prev]</a>
				<%
					} else {
				%>
				[prev]&nbsp;
				<%
					}
				%>
				<!-- 현재 페이지가 포함된 그룹의 페이지 숫자 출력 -->
				<%
					for (int p = startPage; p <= endPage; p++) {
						if (p == currentPage) {
				%>
				<font color="red" size="4"><b>[<%=p%>]
				</b></font>
				<%
					} else {
				%>
				<a href="/food/bsreplylist?page=<%=p%>"><%=p%></a>
				<%
					}
					}
				%>

				<%
					if ((currentPage + 10) > endPage && (currentPage + 10) <= maxPage) {
				%>
				<a href="/food/bsreplylist?page=<%=endPage + 1%>">[next]</a>
				<%
					} else {
				%>
				[next]&nbsp;
				<%
					}
				%>

				<%
					if (currentPage >= maxPage) {
				%>
				[맨끝]&nbsp;
				<%
					} else {
				%>
				<a href="/food/bsreplylist?page=<%=maxPage%>">[맨끝]</a>
				<%
					}
				%>
			</div>
		</div>
		<br> <br> <br>
	</div>


	<!-- tab-0 쿠폰 생성 -->


	<div id="tab-0" class="tab-content tab-0">
		<div id="tab-1div">
			<!-- <form id="frm" method="post" action="/food/bupdate.pwenc"> -->
			<div class="input-group">
				<h3>쿠폰생성</h3>
				<div style="border-bottom: 1px solid #bdbdbd;"></div>
				<br>
			</div>
			<div class="cover">
				<div style="width: 100px; margin-top: 16px; margin-right: 10px;">쿠폰내용</div>
				<input id="couponcontent" type="text" class="form-control"
					name="couponcontent" placeholder="ex) 10% 할인"
					style="vertical-align: top;">
			</div>

			<div class="cover2">
				<div style="width: 100px; margin-top: 15px; margin-right: 10px;">사용기한</div>
				<input id="coupondate" type="date" class="form-control"
					style="margin-top: 10px;">
			</div>
			<div class="input-group" style="margin-top: 30px;">
				<span><button class="btn btn-outline-primary"
						style="margin-right: 200px" onclick="couponcreate();">쿠폰등록</button></span>
			</div>
			<br> <br> <br> <br>
			
			
			<h3>쿠폰 생성 목록</h3>
			<div style="border-bottom: 1px solid #bdbdbd;"></div>
				<br>
	<table class="table table-striped">
    		<thead>
    		
    		
      		<tr>
      	<th style="width:10%; min-width:100px;" >쿠폰번호</th>
        <th style="width:10%; min-width:100px;" >쿠폰내용</th>
      	<th style="width:10%; min-width:100px;">사용기한</th>
      	
      	</tr>
      	</thead>
      	<tbody style="text-align:center;">
      	<%if(request.getAttribute("clist") != null) {
      		ArrayList<Coupon> clist = (ArrayList<Coupon>)request.getAttribute("clist");  
      		
			for(Coupon coupon: clist){%>
			<tr>
			<th><%=coupon.getCoupon_No() %></th>
			<th><%=coupon.getCoupon_Content() %></th>
			<th><%=coupon.getCoupon_Date() %></th>
			</tr>
			<%}}else{ %>
			<tr>
			<th></th>
			<th><font color="gray">쿠폰없음</font></th>
			<th></th>
			</tr>
			<%} %>
		</tbody>
		</table>
			<!-- </form> -->
		</div>
	</div>

	<!--  쿠폰생성 끝 -->











	<div id="tab-4" class="tab-content tab-4">
		<div class="container">
			<div>
				<form action="/food/bsearchid" method="post">
					<ul class="pagination">
						<li class="page-item"><h3>쿠폰 확인</h3>
						<div style="border-bottom: 1px solid #bdbdbd; width:1145px;"></div>
				<br></li>
						<li class="page-item"
							style="margin-top: 5px; position: absolute; right: 20%;"><input
							type="text" class="form-control" name="bSearchcusId"
							placeholder="아이디 검색" style="width-min: 130px;"></li>
						<li class="page-item"
							style="margin-top: 15px; position: absolute; right: 16%;"><input
							type="submit" class="btn btn-outline-primary;" value="검색">
						</li>
					</ul>
				</form>
			</div>
			<table class="table table-striped">
				<thead>
					<tr>
						<th style="width: 10%; min-width: 100px;">아이디</th>
						<th>쿠폰</th>
						<th style="width: 12%; min-width: 100px;">사용기한</th>
						<th style="width: 8%"></th>
					</tr>
				</thead>
				<tbody style="text-align: center; vertical-align: middle;">
					<%
						listCount = 0;
						startPage = 0;
						endPage = 0;
						maxPage = 0;
						currentPage = 0;
						if (request.getAttribute("coupon_blist") != null) { //쿠폰리스트 요청이 널이 아니라면
							ArrayList<Coupon_bl> couponList = (ArrayList<Coupon_bl>) request.getAttribute("coupon_blist");
							//쿠폰리스트를 어레이리스트에 담고
							listCount = ((Integer) request.getAttribute("listCount")).intValue();
							startPage = ((Integer) request.getAttribute("startPage")).intValue();
							endPage = ((Integer) request.getAttribute("endPage")).intValue();
							maxPage = ((Integer) request.getAttribute("maxPage")).intValue();
							currentPage = ((Integer) request.getAttribute("currentPage")).intValue();

							for (Coupon_bl bc : couponList) {
					%>
					
						
						<form action="/food/usecouponbtn" method="post">
							<input type="hidden" value="<%=bc.getCoupon_No()%>" name="coupon_no" id="coupon_no"> <input type="hidden"
								value="<%=bc.getCustomer_id()%>" name="cuid" id="cuid">
							<tr>
							<th><%=bc.getCustomer_id()%></th>
							<th><%=bc.getCoupon_content()%></th>
							<th><%=bc.getCoupon_date()%></th>
							<th style="float: right;"><input type="submit" value="사용"
								class="btn btn-outline-primary"></th>
							</tr>
						</form>
						<%
							}} else {
						%>
						<tr>
						<th>&emsp;&emsp;&emsp;</th>
						<th><font color="gray">쿠폰없음</font></th>
						<th>&emsp;&emsp;&emsp;</th>
						<th>&emsp;&emsp;&emsp;</th>
						</tr>
					
					
					<%
						}						
					%>

				</tbody>
			</table>
			<hr style="margin-top: 30px;">
			<div style="text-align: center;">
				<%
					if (currentPage <= 1) {
				%>
				[맨처음]&nbsp;
				<%
					} else {
				%>
				<a href="/food/bclist?currentPage=1">[맨처음]</a>
				<%
					}
				%>
				<%
					if ((currentPage - 10) < startPage && (currentPage - 10) >= 1) {
				%>
				<a href="/food/bclist?currentPage=<%=startPage - 1%>">[prev]</a>
				<%
					} else {
				%>
				[prev]&nbsp;
				<%
					}
				%>
				<!-- 현재 페이지가 포함된 그룹의 페이지 숫자 출력 -->
				<%
					for (int p = startPage; p <= endPage; p++) {
						if (p == currentPage) {
				%>
				<font color="red" size="4"><b>[<%=p%>]
				</b></font>
				<%
					} else {
				%>
				<a href="/food/bclist?currentPage=<%=p%>"><%=p%></a>
				<%
					}
					}
				%>

				<%
					if ((currentPage + 10) > endPage && (currentPage + 10) <= maxPage) {
				%>
				<a href="/food/bclist?currentPage=<%=endPage + 1%>">[next]</a>
				<%
					} else {
				%>
				[next]&nbsp;
				<%
					}
				%>

				<%
					if (currentPage >= maxPage) {
				%>
				[맨끝]&nbsp;
				<%
					} else {
				%>
				<a href="/food/bclist?currentPage=<%=maxPage%>">[맨끝]</a>
				<%
					}
				%>
			</div>
		</div>
		<!-- tab-4 끝나는 div -->
	</div>
	<div id="tab-5" class="tab-content tab-5">
		<div class="container">
			<h3>쿠폰 사용 내역</h3>
			<div style="border-bottom: 1px solid #bdbdbd;"></div>
				<br>

			<table class="table table-striped">
				<thead>
					<tr>
						<th style="width: 10%; min-width: 100px;">아이디</th>
						<th>쿠폰</th>
						<th style="width: 15%; min-width: 100px;">사용날짜</th>
					</tr>
				</thead>
				<tbody style="text-align: center; vertical-align: middle;">
					<%
						listCount = 0;
						startPage = 0;
						endPage = 0;
						maxPage = 0;
						currentPage = 0;
						if (request.getAttribute("coupon_bulist") != null) { //쿠폰리스트 요청이 널이 아니라면
							ArrayList<Coupon_bl> couponList = (ArrayList<Coupon_bl>) request.getAttribute("coupon_bulist");
							//쿠폰리스트를 어레이리스트에 담고
							listCount = ((Integer) request.getAttribute("listCount")).intValue();
							startPage = ((Integer) request.getAttribute("startPage")).intValue();
							endPage = ((Integer) request.getAttribute("endPage")).intValue();
							maxPage = ((Integer) request.getAttribute("maxPage")).intValue();
							currentPage = ((Integer) request.getAttribute("currentPage")).intValue();

							for (Coupon_bl bc : couponList) {
					%>
					<tr>
						
						<th><%=bc.getCustomer_id()%></th>
						<th><%=bc.getCoupon_content()%></th>
						<th><%=bc.getCoupon_date()%></th>
						</tr>
						<%
							}} else {
						%>
						<tr>
						<th></th>
						<th><font color="gray">사용 쿠폰 없음</font></th>
						<th></th>
						</tr>
						<%
							} 
						%>
					
					

				</tbody>
			</table>

			<hr style="margin-top: 30px;">
			<div style="text-align: center;">
				<%
					if (currentPage <= 1) {
				%>
				[맨처음]&nbsp;
				<%
					} else {
				%>
				<a href="/food/busedlist?currentPage=1">[맨처음]</a>
				<%
					}
				%>
				<%
					if ((currentPage - 10) < startPage && (currentPage - 10) >= 1) {
				%>
				<a href="/food/busedlist?currentPage=<%=startPage - 1%>">[prev]</a>
				<%
					} else {
				%>
				[prev]&nbsp;
				<%
					}
				%>
				<!-- 현재 페이지가 포함된 그룹의 페이지 숫자 출력 -->
				<%
					for (int p = startPage; p <= endPage; p++) {
						if (p == currentPage) {
				%>
				<font color="red" size="4"><b>[<%=p%>]
				</b></font>
				<%
					} else {
				%>
				<a href="/food/busedlist?currentPage=<%=p%>"><%=p%></a>
				<%
					}
					}
				%>

				<%
					if ((currentPage + 10) > endPage && (currentPage + 10) <= maxPage) {
				%>
				<a href="/food/busedlist?currentPage=<%=endPage + 1%>">[next]</a>
				<%
					} else {
				%>
				[next]&nbsp;
				<%
					}
				%>

				<%
					if (currentPage >= maxPage) {
				%>
				[맨끝]&nbsp;
				<%
					} else {
				%>
				<a href="/food/busedlist?currentPage=<%=maxPage%>">[맨끝]</a>
				<%
					}
				%>
			</div>
			<br> <br> <br>
		</div>
		<!-- tab-5끝나는 div -->
	</div>
	<div id="tab-6" class="tab-content tab-6" style="margin-bottom: 0px;">
		<!-- tab-6 시작 -->

		<%
			Foodtruck truck = null;
			ArrayList<Menu> mlist = null;
			if (request.getAttribute("truck") != null) {
				truck = (Foodtruck) request.getAttribute("truck");
				mlist = (ArrayList<Menu>) request.getAttribute("mlist");
			}
		%>

		<form id="truckPageModForm" method="post"
			action="/food/businessmy_test" enctype="multipart/form-data">
			<h2>&emsp;페이지관리</h2>
			<hr style="height: 1px; background: black; width: 850px; float: left">
			<br> <br>

			<div class="pagemain">
				<strong>푸드트럭 이름 </strong>&emsp;&emsp;&emsp;&emsp; <input
					type="hidden" name="bsId"
					value="<%=((Business) loginMember).getBusiness_Id()%>">

				<%
					if (truck != null) {
				%>

				<input required="required" type="text" style="width: 200px;"
					name="truckName" value="<%=truck.getTruck_Name()%>">
				<%
					} else {
				%>
				<input required="required" type="text" style="width: 200px;"
					name="truckName">
				<%
					}
				%>

				<font style="color: gray; font-size: 10pt">&emsp;한글, 영문, 숫자
					혼용가능(한글기준 10글자)</font> <br> <br>
				<hr style="width: 850px; float: left">
				<br> <br> <strong>공지사항</strong>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&nbsp;&nbsp;

				<%
					if (truck != null && truck.getTruck_Notice() != null) {
				%>
				<input type="text" value="<%=truck.getTruck_Notice()%>"
					style="width: 300px;" name="truckNotice" placeholder="한줄 공지사항">
				<%
					} else {
				%>
				<input type="text" placeholder="한줄 공지사항" style="width: 300px;"
					name="truckNotice">
				<%
					}
				%>

				<font style="color: gray; font-size: 10pt">&emsp;한글, 영문, 숫자
					혼용가능(한글기준 20글자)</font> <br> <br>

				<!-- 휴무 일 -->
				<hr style="width: 850px; float: left">
				<br> <br> <strong>휴무일 </strong>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&nbsp;&nbsp;
				<%
					if (truck != null && truck.getTruck_Dayoff() != null) {
				%>
				<input type="text" name="truckDayoff"
					value="<%=truck.getTruck_Dayoff()%>" placeholder="ex) 월요일">
				<%
					} else {
				%>
				<input type="text" name="truckDayoff" placeholder="ex) 월요일">
				<%
					}
				%>
				<font style="color: gray; font-size: 10pt">&emsp;</font> <br> <br>


				<!-- 영업시간 -->
				<hr style="width: 850px; float: left">
				<br> <br> <strong>영업 시간 </strong>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
				<select name="startHour">
					<%
						for (int i = 0; i < 24; i++) {
					%>
					<option><%=i%>
					</option>
					<%
						}
					%>
				</select>시 <select name="startMin">
					<%
						for (int j = 0; j <= 59; j++) {
					%>
					<option><%=j%>
					</option>
					<%
						}
					%>
				</select>분&nbsp;~&nbsp; <select name="endHour">
					<%
						for (int i = 0; i < 24; i++) {
					%>
					<option><%=i%>
					</option>
					<%
						}
					%>
				</select>시 <select name="endMin">
					<%
						for (int j = 0; j <= 59; j++) {
					%>
					<option><%=j%>
					</option>
					<%
						}
					%>
				</select>분 까지&emsp;<font style="color: gray; font-size: 10pt">시간 타입 -
					24시</font> <br> <br>
				<hr style="width: 850px; float: left">
				<br> <br>
				<div>
					<strong style="float: left;">트럭 사진 </strong>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
					<%
						if (truck != null) {
					%>
					사진 변경&nbsp;&nbsp;<input type="file" name="truckImg"
						accept="image/*"><br>
					<br>
					<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; <img id="truckImg"
						src="/food/resources/images/foodtruck/<%=truck.getTruck_Img()%>">
					<input type="hidden" value="<%=truck.getTruck_Img()%>"
						name="truckImgOld">

					<%
						} else {
					%>
					<input type="file" value="등록" name="truckImg" accept="image/*">
					<img id="truckImg" alt="사진을 등록해주세요." src="">
					<%
						}
					%>
				</div>
			</div>
			<br> <br> <br> <br> <br>
			<hr style="width: 850px; float: left">
			<br> <br> <strong>연락처</strong>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
			<%
				if (truck != null && truck.getTruck_Phone() != null) {
			%>
			<input name="truckTel" type="text" style="width: 200px;"
				value="<%=truck.getTruck_Phone()%>">
			<%
				} else {
			%>
			<input name="truckTel" type="text" style="width: 200px;"
				placeholder="ex) 010-1234-4567">
			<%
				}
			%>
			&emsp;<font style="color: gray; font-size: 10pt">" - " 생략가능</font> <br>
			<br>

			<!-- 카테고리 -->
			<br>
			<br>
			<hr style="width: 850px; float: left">
			<br> <br> <strong>카테고리</strong>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
			<select name="category">
				<option value="식사류">식사류</option>
				<option value="간식류">간식류</option>
				<option value="디저트류">디저트류</option>
			</select> <font style="color: gray; font-size: 10pt">&emsp;</font> <br> <br>
			<hr style="width: 850px; float: left">
			<br> <br>
			<div>
				<strong style="float: left;">메뉴 </strong>&emsp;&emsp;&emsp;
				<!-- 메뉴 추가 -->
				<input name="menuCount" type="text" hidden="hidden" value="0">
				&emsp;&emsp;&emsp;<strong>처음 등록한 메뉴가 대표메뉴로 등록됩니다.</strong><br>
				<br>
				<br>
				<input type="button" onclick="addMenu();" value="메뉴 추가"> <br>
				<br>
				<div style="clear: both;">
					<%int i = 0;
						if (mlist != null && mlist.size() > 0) {
					%>



					<table id="menu_table">
						<%
							
								for (Menu m : mlist) {
									if (i % 3 == 0) {
						%>
						<tr>
							<%
								}
							%>
							<td align="center" id="oriMenu<%=i%>"><input type="button"
								value="메뉴 삭제" onclick="delOriMenu(<%=i%>);"> <input
								type="file" accept="image/*" onchange="oriMenuChange(<%=i%>)"
								name="oriMenuFile<%=i%>" /> <input type="hidden" name="mFile"
								value="oriMenuFile<%=i%>"> <input type="hidden"
								value="<%=m.getMenu_Img()%>" name="oriMenuFile<%=i%>Img" /> <img
								id="oriMenuImg<%=i%>" width="300" height="300"
								src="/food/resources/images/menu/<%=m.getMenu_Img()%>"><br>
								<input type="text" required="required"
								value="<%=m.getMenu_Name()%>" name="mName"><br> <input
								type="number" required="required"
								value="<%=m.getMenu_price()%>" name="mPrice">원<br>

							</td>
							<%
								if (i == (mlist.size() - 1) || i % 3 == 2) {
							%>
						</tr>
						<%
							}
									i++;
								}
						%>
					</table>
		

					<%
						}
					%>
								<input type="hidden" name="oriMenuCount" value="<%=i%>">
					<br>
					<br>
					<table id="addMenuTable">
					</table>

				</div>
			</div>

			<!-- 장사 ONOFF -->
			<br>
			<br>
			<br>
			<br>
			<br>
			<hr style="width: 850px; float: left">
			<br> <br> <strong>영업중 ON/OFF</strong>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;

			<%
				if (truck != null) {
			%>
			<%
				if (truck.getTruck_Live_OnOff().equals("Y")) {
			%>
			<input type="radio" name="truckOnOff" value="Y" checked="checked">영업
			ON <input type="radio" name="truckOnOff" value="N">영업 OFF
			<%
				} else if (truck.getTruck_Live_OnOff().equals("N")) {
			%>
			<input type="radio" name="truckOnOff" value="Y">영업 ON <input
				type="radio" name="truckOnOff" value="N" checked="checked">영업
			OFF
			<%
				}
				} else {
			%>
			<input type="radio" name="truckOnOff" value="Y" checked="checked">영업
			ON <input type="radio" name="truckOnOff" value="N">영업 OFF
			<%
				}
			%>



			<font style="color: gray; font-size: 10pt">&emsp;</font> <br> <br>
			<!-- 장사 위치 -->
			<br> <br> <br>
			<hr style="width: 850px; float: left">
			<br> <br> <strong style="float: left;">영업 위치 </strong>
			&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
			<div class="map_wrap"
				style="width: 700px; height: 400px; margin-left: 100px; position: relative; border: 1px solid black; background: gray; display: table">
				<div id="map"
					style="width: 700px; height: 400px; position: relative; overflow: hidden;"></div>
				<div id="menu_wrap" class="bg_white">
					<div class="option">
						<div>
							&nbsp;위치 : <input type="text" value="서울" id="keyword" size="15">
							<input type="button" id="mfind" onclick="searchPlaces();"
								value="검색하기">
						</div>
					</div>
				</div>
				<!-- <font id="mwait" style="display:table-cell; text-align:center; vertical-align:middle;">검색해주세요.</font> -->
			</div>
			<script type="text/javascript"
				src="//dapi.kakao.com/v2/maps/sdk.js?appkey=15e2354024ec8ffe859e608291415c04&libraries=services"></script>
			<script>
				var mapContainer = document.getElementById('map'), mapOption = {
					center : new daum.maps.LatLng(37.566826, 126.9786567),
					level : 6
				};

				var map = new daum.maps.Map(mapContainer, mapOption);

				var ps = new daum.maps.services.Places();

				function searchPlaces() {

					var keyword = document.getElementById('keyword').value;

					if (!keyword.replace(/^\s+|\s+$/g, '')) {
						return false;
					}

					ps.keywordSearch(keyword, placesSearchCB);
				
				}

				function placesSearchCB(data, status, pagination) {
					if (status === daum.maps.services.Status.OK) {

						var bounds = new daum.maps.LatLngBounds();

						for (var i = 0; i < data.length; i++) {
							bounds.extend(new daum.maps.LatLng(data[i].y,
									data[i].x));
						}

						map.setBounds(bounds);
					}
				}
				var marker = new daum.maps.Marker({
					position : map.getCenter()
				});

				marker.setMap(map);

				daum.maps.event.addListener(map, 'click', function(mouseEvent) {

					var latlng = mouseEvent.latLng;

					marker.setPosition(latlng);

					var x = latlng.getLat();
					var y = latlng.getLng();

					document.getElementById('xloc').value = x;
					document.getElementById('yloc').value = y;
				});

			
			</script>
			<br> <br> <br> <br> <br>
			<hr style="width: 850px; float: left">
			<br> <br>

			<div style="text-align: center; width: 850px">
				<!--xloc = x좌표,  yloc = y좌표  (value속성안에 값이 들어가있음)-->
				<%
					if (truck != null) {
				%>
				<input name="xloc" id="xloc" type="text"
					value="<%=truck.getTruck_Loc_X()%>"> <input name="yloc"
					id="yloc" type="text" value="<%=truck.getTruck_Loc_Y()%>">
				<%
					} else {
				%>
				<input name="xloc" id="xloc" type="text" value="0"> <input
					name="yloc" id="yloc" type="text" value="0">
				<%
					}
				%>
				<input type="button" onclick="truckPageModBtn()" value="수정하기">
				<input type="submit" id="truckPageModSubmit" hidden="hidden">
			</div>
			<div id="clickLatlng"></div>
		</form>
		<br> <br> <br>
	</div>
	<script type="text/javascript">

function truckPageModBtn(){
	console.log(  Number(addmenucount) +Number( $('[name=oriMenuCount]').val()) );
	if( (addmenucount + $('[name=oriMenuCount]').val() ) >0 ){
	$('#truckPageModSubmit').click();
	}else{
		alert("메뉴를 하나 이상 등록해주세요.");
	}
}
</script>

	<!-- tab-6끝나는 div -->

	<div>
		<div class="foot" style="bottom: 0; position: absolute;">FoodTruck
			&nbsp;Copyright @ iei.or.kr &nbsp; All Rights Reserved.</div>
	</div>
</body>
</html>