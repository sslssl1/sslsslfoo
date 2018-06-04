<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page
	import="javax.swing.text.AbstractDocument.Content,member.model.vo.*,review.model.vo.*,java.util.*,reply.model.vo.*,coupon.model.vo.*"%>

<%
	int reviewPage = 1;
	//로그인 정보 가져오기
	Customer loginMember = null;
	if (session.getAttribute("loginMember") != null) {
		loginMember = (Customer) session.getAttribute("loginMember");

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
%>
<!DOCTYPE html>
<html>
<head>
<title>푸딩 일반사용자</title>
<meta charset="utf-8">
<link rel="stylesheet" href="/food/resources/css/top-menu.css">
<link rel="stylesheet" href="/food/resources/css/foot.css">
<link rel="stylesheet" href="/food/resources/css/left-menu.css">
<link rel="stylesheet" href="/food/resources/css/left-menu-tab.css">
<link rel="stylesheet" href="/food/resources/css/right-menu.css">
<link rel="stylesheet" href="/food/resources/css/modInfo.css">
<link rel="stylesheet" href="/food/resources/css/review.css">
<link rel="stylesheet" href="/food/resources/css/replay.css">
<link rel="stylesheet" href="/food/resources/css/user-my.css">
<link rel="stylesheet" href="/food/resources/css/bootstrap.min.css">

<script type="text/javascript"
	src="/food/resources/js/jquery-3.3.1.min.js"></script>
<script src="/food/resources/js/jquery.min.js"></script>
<script src="/food/resources/js/jquery.scrolly.min.js"></script>
<script src="/food/resources/js/jquery.scrollzer.min.js"></script>
<script src="/food/resources/js/skel.min.js"></script>
<script src="/food/resources/js/util.js"></script>
<script src="/food/resources/js/main.js"></script>
<script type="text/javascript">
var sum= 0;
var reviewPage=1;


function myReviewAjax(page)
{
   reviewPage=page;
   $.ajax({
      url: "/food/cusmyreviewlist",
      type : "post",
      data : {
         "userId" : '<%=((Customer) loginMember).getCustomer_Id()%>',
         "page" : reviewPage ,
         "seType" : $('#reviewSeType').val(),
         "seValue" : $('#reviewSeValue').val()
      },
      dataType : "json",
      success : function(data){
         
         var jsonStr = JSON.stringify(data);
         var json = JSON.parse(jsonStr);
         
         var tableCode = "";
         
         
         for(var i in json.list)
         {
            tableCode+='<tr><th style="width: 15%;"><input type="checkbox" name="delCheck" value="'+json.list[i].no+'"></th>';
            tableCode+='<th>'+json.list[i].no+'</th>';
            tableCode+='<th><a href="/food/reviewdetail?reviewNo='+json.list[i].no+'">'+json.list[i].title+'</a></th>';
            tableCode+='<th>'+json.list[i].date+'</th>';
            tableCode+='<th>'+json.list[i].count+'</th></tr>';
         }
         $('#reviewTable').html(tableCode);
/////////////////////////////////////////////////////////
         var maxPage = json.maxPage;
         var startPage = json.startPage;
         var endPage = json.endPage;
         reviewPage = json.currentPage;
         
         var pageCode = "";
         
         if (reviewPage <= 1){
            pageCode+= "[맨처음]&nbsp";
         }else{
            pageCode+= "<a href='javascript: myReviewAjax(1);'>[맨처음]</a>"
         }
         if ((reviewPage - 10) < startPage
               && (reviewPage - 10) >= 1) {
            pageCode += '<a href="javascript:myReviewAjax('
                  + (startPage - 1) + ');">[prev]</a>';
         } else {
            pageCode += '[prev]&nbsp;';
         }
         
         for (var p = startPage; p <= endPage; p++) {
            if (p == reviewPage) {
               pageCode += '<font color="red" size="4"><b>[' + p
                     + ']</b></font>';
            } else {
               pageCode += '<a href="javascript:myReviewAjax('
                     + p + ');">' + p + '</a>';
            }
         }
         if ((reviewPage + 10) > endPage
               && (reviewPage + 10) <= maxPage) {
            pageCode += '<a href="javascript:myReviewAjax('
                  + (endPage + 1) + ')">[next]</a>';

         } else {
            pageCode += '[next]&nbsp';
         }
         if (reviewPage >= maxPage) {
            pageCode += '[맨끝]&nbsp';
         } else {
            pageCode += '<a href="javascript:myReviewAjax('
                  + maxPage + ')">[맨끝]</a>';
         }
         console.log(pageCode);
         console.log("reviewPage: "+reviewPage);
         console.log("maxPage: "+maxPage);
         console.log("endPage: "+endPage);
         console.log("startPage: "+startPage);
         $('#reviewPaging').html(pageCode);
      },
      error : function(){
         alert("myReviewAjax 에러");
      }
      
      
   });
      
}


function checkedCheck(name)
{
   var arr=[];
   var i=0;
   sum = 0;
   $('[name='+name+']').each(function(){
      if( $(this).is(":checked")==true )
         {
         arr[i]=1;
         }
      else{
         arr[i]=0;
      }
      i++;
      
   });
   
   for(var j=0; j< arr.length; j++)
      {
      sum+=arr[j];
      }
   return sum;
}

   $(function() {

      $('#delBtn').click(function(){
         if( checkedCheck('delCheck') >0  ){
            $('#delSubmit').click();
         }else
            {
            alert("선택된 항이 없습니다");
            }
      });
      
      $('#delBtn2').click(function(){
         if(checkedCheck('reply_check') >0){
            $('#delSubmit2').click();
         }else{
            alert("선택된 항이 없습니다.");
         }
         
      });
      
      if (tab != null) {
         $('.tab-content').removeClass('current');
         $('.tabs li').removeClass('current');
         $('.tabs li a').removeClass('active');
         $('#' + tab).addClass(' current');
         $('[data-tab='+tab+'] a').addClass('active');
         console.log('[data-tab='+tab+']');
         tab=null;
      }
      
      $('.tabs li').click(function() {
         var tab_cl = $(this).attr('data-tab');

         $('.tabs li').removeClass('current');
         $('.tab-content').removeClass('current');
         $(this).addClass('current');
         $("." + tab_cl).addClass('current');
      });
      
      $('#delCheck').change(function(){
         //리뷰 전체선택 체크박스 선택
      if(   $('#delCheck').is(":checked")==true )
         {
            $('[name=delCheck]').attr("checked",true);
         }
      else if ( $('#delCheck').is(":checked")==false )
         {
         $('[name=delCheck]').attr("checked",false);
         }
         
      });
      
      $('#reply_all_check').change(function(){
      //댓글 전체선택 체크박스 선택
         if( $('#reply_all_check').is(":checked")==true ){
            
            $('[name=reply_check]').attr("checked",true);
            }
         else if ($('#reply_add_check').is(":checked")==false)
         {
            $('[name=reply_check]').attr("checked",false);
         }
         
      });
      
   });
   
   //한결이오빠코드
   function csmodify() {
      if (opwCheck == 0) {
         if (cpwCheck == 0 && vcodeCheck == 0) {
            $.ajax({
               url : "/food/mupdate.pwenc",
               data : {
                  userName :$('#userName').val() ,
                  withdraw : $('#withdraw').val() ,
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
                  location.href = "/food/views/userMy/user_my.jsp";
                  alert('회원정보 수정 완료!');
               }
            });
         } else if (cpwCheck == 0 && vcodeCheck != 0
               && $('#newPasswordRepeat').val() != "") {
            $.ajax({
               url : "/food/mupdate.pwenc",
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
                  location.href = "/food/views/userMy/user_my.jsp";
                  alert('회원정보 수정 완료!');
               }
               });
         } else if (cpwCheck != 0 && vcodeCheck == 0) {

            $.ajax({
               url : "/food/mupdate.pwenc",
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
                  location.href = "/food/views/userMy/user_my.jsp";
                  alert('회원정보 수정완료!');
               }
            });
         } else {
            alert("입력하신 정보가 없습니다.");
         }
      } else {
         alert("현재 비밀번호를 입력해주세요.");
      }
   }
   

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
   
   function withdraw() {
      if (opwCheck == 0) {
         var answer = confirm("정말 탈퇴 하시겠습니까 ? ");

         if (answer) {
            var writer = prompt("\"탈퇴\"라고 적어주세요.");
            if (writer == "탈퇴") {
               $.ajax({
                  url : "/food/withdraw",
                  data : {
                     ID : $('#ID').val(),
                  },
                  type : "post",
                  success : function(data) {
                     if (data == 1) {
                        alert('탈퇴 성공!');
                        location.reload();
                     } else {
                     }
                  }
               });
            }
         } else {
         }
      } else {
         alert("현재 비밀번호를 입력해주세요.");
      }

   }
   

function checkOldPwd() {
   //현재 비밀번호 확인
   if ($('#oldPassword').val() != null
         && $('#oldPassword').val().length > 0) {
      $.ajax({
         url : "/food/checkcspwd.pwenc",
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

<%-- $(function() {
   <%if(request.getAttribute("reply_list")!=null &&request.getAttribute("reply_list").toString().length()>0){%>
   
      $('#delBtn2').css('display','none');
   <%}%>
}); --%>

</script>
<style>
.mod_table {
	background-color: #DCDCDC;
	border-color: black;
	width: 170px;
	padding-left: 10px;
}
</style>
</head>
<body>
	<div id="header" style="top: 50px">
		<nav id="nav">
			<ul id="ul" class="tabs">
				<li class="current" data-tab="tab-1"><a href="#edit"><span
						class="icon fa-edit">회원정보수정</span></a></li>
				<li data-tab="tab-2"><a href="/food/csreview"><span
						class="icon fa-user">내후기보기</span></a></li>
				<li data-tab="tab-3"><a href="/food/csreplylist"><span
						class="icon fa-user">내댓글보기</span></a></li>
				<li data-tab="tab-4"><a href="/food/clist"><span
						class="icon fa-envelope">쿠폰확인</span></a></li>
				<li data-tab="tab-5"><a href="/food/cusedlist"><span
						class="icon fa-envelope-o">쿠폰사용내역</span></a></li>
			</ul>
		</nav>
	</div>
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
				<%
					if (loginMember == null) {
				%>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="/food/views/logIn/logIn.jsp"><span
							class="glyphicon glyphicon-log-in"></span> Login</a></li>
					<li><a href="/food/views/signUp/select_signUp.jsp"><span
							class="glyphicon glyphicon-edit"></span> 회원가입</a></li>
				</ul>
				<%
					} else if (loginMember instanceof Customer) {
				%>
				<ul class="nav navbar-nav navbar-right">
					<li><a><span class="glyphicon glyphicon-user"></span> <%=((Customer) loginMember).getCustomer_Id()%>님</a></li>
					<li class="active"><a href="/food/views/userMy/user_my.jsp"><span
							class="glyphicon glyphicon-th-list"></span>내 정보 보기</a></li>
					<li><a href="/food/memberlogout"><span
							class="glyphicon glyphicon-share"></span>로그아웃</a></li>
				</ul>

				<%
					}
				%>

			</div>

		</nav>
	</div>
	<!-- 회원정보 수정 코드 부분~~~ -->
	<div id="tab-1" class="tab-content tab-1 " style="margin-bottom: 0px">
		<div id="tab-1div">
			<!-- <form id="frm" method="post" action="/food/bupdate.pwenc"> -->
			<div class="input-group"
				style="margin-left: 250px; margin-top: 45px;">
				<h3>회원정보 수정</h3>
				<div style="border-bottom: 1px solid #bdbdbd; width: 500px;"></div>
				<br>
			</div>

			<div style="width: 500px; margin-left: 250px;">
				<div class="input-group" align="center">

					<input type="hidden"
						value="<%=((Customer) loginMember).getCustomer_Name()%>"
						id="userName"> <input type="hidden"
						value="<%=((Customer) loginMember).getWithdraw()%>" id="withdraw">
					<input id="ID" type="text" class="form-control" name="ID"
						placeholder="아이디"
						value="<%=((Customer) loginMember).getCustomer_Id()%>" readonly>
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
						value="<%=((Customer) loginMember).getCustomer_Email()%>" readonly>
				</div>
				<div>
					<input id="email2" type="email" class="form-control" name="email2"
						placeholder="new Email" style="margin-top: 10px;" required>
					<br>
					<button id="numbersend" class="btn btn-outline-primary"
						style="margin-top: 10px;" onclick="checkMail();">인증번호 발송</button>
				</div>

				<div id="codeinput" class="input-group" style="display: none">
					<input id="vCode" type="text" class="form-control" name="vCode"
						placeholder="인증번호 입력" style="margin-top: 10px;">
					<button class="btn btn-outline-primary" style="margin-top: 10px;"
						onclick="vCodeCheck();">확인</button>
				</div>

				<div class="input-group" style="margin-top: 30px;">
					<span><button class="btn btn-outline-primary"
							style="margin-right: 200px" onclick="csmodify();">수정하기</button>
						<button class="btn btn-outline-primary"
							style="position: absolute; margin-left: -80px"
							onclick="withdraw();">탈퇴하기</button> </span>
				</div>
			</div>

			<br> <br> <br>
			<!-- </form> -->
		</div>
	</div>




	<br>
	<br>
	<br>

	<!-- 내 후기보기 코드 부분~~~~~ -->


	<div id="tab-2" class="tab-content tab-2"
		style="margin: 0px; margin-left: 250px">
		<div class="container"
			style="margin-left: 15%; margin-right: 15%; margin-top: 3%;">
			<h3>내후기 보기</h3>
			<div style="border-bottom: 1px solid #bdbdbd;"></div>
			<br> <select name="seType" id="reviewSeType">
				<option value="REVIEW_TITLE" selected>제목만
				<option value="REVIEW_CONTENT">내용만
				<option value="REVIEW_NO">글번호
			</select> <input type="text" name="seValue" id="reviewSeValue">&nbsp;
			<input type="button" onclick="myReviewAjax(1);" value="검색"
				style="background: green; border: green; color: white;">


			<form method="post" action="csreviewdel">
				<table class="table table-striped">
					<thead>
						<tr>
							<th style="width: 15%;"><input id="delCheck" type="checkbox"
								name="delCheckAll" value=null>전체선택</th>
							<th>글번호</th>
							<th>제목</th>
							<th>작성일</th>
							<th>조회수</th>
						</tr>
					</thead>
					<tbody id="reviewTable">
						<%
							int listCount = 0;
							int startPage = 0;
							int endPage = 0;
							int maxPage = 0;
							int currentPage = 0;
							if (request.getAttribute("review_list") != null) {
								ArrayList<Review> reviewList = (ArrayList<Review>) request.getAttribute("review_list");
								listCount = ((Integer) request.getAttribute("listCount")).intValue();
								startPage = ((Integer) request.getAttribute("startPage")).intValue();
								endPage = ((Integer) request.getAttribute("endPage")).intValue();
								maxPage = ((Integer) request.getAttribute("maxPage")).intValue();
								currentPage = ((Integer) request.getAttribute("currentPage")).intValue();

								for (Review review : reviewList) {
						%>
						<tr>
							<th style="width: 15%;"><input type="checkbox"
								name="delCheck" value="<%=review.getNumber()%>"></th>
							<th><%=review.getNumber()%></th>
							<th><a
								href="/food/reviewdetail?reviewNo=<%=review.getNumber()%>"><%=review.getReview_title()%></a></th>
							<th><%=review.getReview_Date()%></th>
							<th><%=review.getReview_Count()%></th>
						</tr>
						<%
							}
							} else {
						%>

						<tr>
							<th></th>
							<th></th>
							<th><font color="gray">후기 없음</font></th>
							<th></th>
							<th></th>

						</tr>

						<%
							}
						%>
					</tbody>
				</table>

				<hr style="margin-top: 30px;">
				<input type="submit" hidden id="delSubmit"> <input
					type="button" value="삭제하기" id="delBtn">
			</form>
			<div style="text-align: center;" id="reviewPaging">
				<%
					if (currentPage <= 1) {
				%>
				[맨처음]&nbsp;
				<%
					} else {
				%>
				<a href="javascript:myReviewAjax(1);">[맨처음]</a>
				<%
					}
				%>
				<%
					if ((currentPage - 10) < startPage && (currentPage - 10) >= 1) {
				%>
				<a href="javascript:myReviewAjax(<%=startPage - 1%>);">[prev]</a>
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
				<a href="javascript:myReviewAjax(<%=p%>);"><%=p%></a>
				<%
					}
					}
				%>

				<%
					if ((currentPage + 10) > endPage && (currentPage + 10) <= maxPage) {
				%>
				<a href="javascript:myReviewAjax(<%=endPage + 1%>);">[next]</a>
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
				<a href="javascript:myReviewAjax(<%=maxPage%>);">[맨끝]</a>
				<%
					}
				%>
				<!-- <input type="button" value="테스트" onclick="myReviewAjax(1);"> -->
				<br style="margin-top: 20px;">
				<form style="float: right;" action="/food/cssearchreview"
					method="post"></form>
			</div>

		</div>
		<br> <br> <br>
	</div>


	<!-- 내 댓글보기 코드 부분~~~~ -->
	<div id="tab-3" class="tab-content tab-3"
		style="margin: 0px; margin-left: 250px">
		<div class="container"
			style="margin-left: 15%; margin-right: 15%; margin-top: 3%;">
			<h3>내댓글 보기</h3>
			<div style="border-bottom: 1px solid #bdbdbd;"></div>
			<br>

			<form method="post" action="csreplydel">
				<table class="table table-striped">
					<thead>
						<tr>
							<th style="width: 10%; min-width: 100px;"><input
								type="checkbox" id="reply_all_check">전체선택</th>
							<th>내용</th>
							<th></th>
							<th>작성일</th>
						</tr>
					</thead>
					<tbody>

						<%
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
						</tr>
						<%
							}
							} else {
						%>
						<tr>

							<th></th>
							<th></th>
							<th><font color="gray">댓글 없음</font></th>
							<th></th>
						</tr>

						<%
							}
						%>


					</tbody>
				</table>
				<hr style="margin-top: 30px;">
				<input type="button" value="삭제하기" id="delBtn2"> <input
					type="submit" hidden id="delSubmit2">
			</form>

			<div style="text-align: center;">
				<%
					if (currentPage <= 1) {
				%>
				[맨처음]&nbsp;
				<%
					} else {
				%>
				<a href="/food/csreplylist?page">[맨처음]</a>
				<%
					}
				%>
				<%
					if ((currentPage - 10) < startPage && (currentPage - 10) >= 1) {
				%>
				<a href="/food/csreplylist?page=<%=startPage - 1%>">[prev]</a>
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
				<a href="/food/csreplylist?page=<%=p%>"><%=p%></a>
				<%
					}
					}
				%>

				<%
					if ((currentPage + 10) > endPage && (currentPage + 10) <= maxPage) {
				%>
				<a href="/food/csreplylist?page=<%=endPage + 1%>">[next]</a>
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
				<a href="/food/csreplylist?page=<%=maxPage%>">[맨끝]</a>
				<%
					}
				%>
			</div>
		</div>

		<br> <br> <br>
	</div>
	<div id="tab-4" class="tab-content tab-4"
		style="margin: 0px; margin-left: 250px">
		<div class="container"
			style="margin-left: 15%; margin-right: 15%; margin-top: 2%;">
			<div>
				<form action="/food/cserachFoodtruck" method="post">
					<ul class="pagination">
						<li class="page-item"><h3>쿠폰 확인</h3>
							<div style="border-bottom: 1px solid #bdbdbd; width: 1145px;"></div>

						</li>

						<li class="page-item"
							style="margin-top: 5px; position: absolute; right: 20%;"><input
							type="text" class="form-control" name="cSearchFoodtrcuk"
							placeholder="가게명 검색" style="width-min: 130px;"></li>
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
						<th style="width: 10%; min-width: 100px;">가게명</th>
						<th>쿠폰</th>
						<th style="width: 12%; min-width: 100px;">사용기한</th>
					</tr>
				</thead>
				<tbody style="text-align: center; vertical-align: middle;">
					<%
						listCount = 1;
						startPage = 1;
						endPage = 1;
						maxPage = 1;
						currentPage = 1;
						if (request.getAttribute("coupon_list") != null) { //쿠폰리스트 요청이 널이 아니라면
							ArrayList<Coupon_l> couponList = (ArrayList<Coupon_l>) request.getAttribute("coupon_list");
							//쿠폰리스트를 어레이리스트에 담고
							listCount = ((Integer) request.getAttribute("listCount")).intValue();
							startPage = ((Integer) request.getAttribute("startPage")).intValue();
							endPage = ((Integer) request.getAttribute("endPage")).intValue();
							maxPage = ((Integer) request.getAttribute("maxPage")).intValue();
							currentPage = ((Integer) request.getAttribute("currentPage")).intValue();

							for (Coupon_l c : couponList) {
					%>
					<tr>
						<th><%=c.getTruck_name()%></th>
						<th><%=c.getCoupon_content()%></th>
						<th><%=c.getCoupon_date()%></th>
					</tr>
					<%
						}
						} else {
					%>

					<tr>
						<td></td>
						<td><font color="gray"><%=request.getAttribute("couponMessage")%></font></td>
						<td></td>
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
				<a href="/food/clist?currentPage=1">[맨처음]</a>
				<%
					}
				%>
				<%
					if ((currentPage - 10) < startPage && (currentPage - 10) >= 1) {
				%>
				<a href="/food/clist?currentPage=<%=startPage - 1%>">[prev]</a>
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
				<a href="/food/clist?currentPage=<%=p%>"><%=p%></a>
				<%
					}
					}
				%>

				<%
					if ((currentPage + 10) > endPage && (currentPage + 10) <= maxPage) {
				%>
				<a href="/food/clist?currentPage=<%=endPage + 1%>">[next]</a>
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
				<a href="/food/clist?currentPage=<%=maxPage%>">[맨끝]</a>
				<%
					}
				%>
			</div>
		</div>
		<br> <br> <br>
		<!-- tab-4 끝나는 div -->
	</div>
	<div id="tab-5" class="tab-content tab-5"
		style="margin: 0px; margin-left: 250px">
		<div class="container"
			style="margin-left: 8.5%; margin-right: 15%; margin-top: 3.2%;">
			<div class="container"
				style="margin-left: 8.5%; margin-right: 15%; margin-top: 3.2%;">
				<h3>쿠폰 사용 내역</h3>
				<div style="border-bottom: 1px solid #bdbdbd; width: 1145px"></div>
				<br>

				<table class="table table-striped" style="width: 1145px;">
					<thead>
						<tr>
							<th style="width: 10%; min-width: 100px;">가게명</th>
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
							if (request.getAttribute("coupon_list") != null) { //쿠폰리스트 요청이 널이 아니라면
								ArrayList<Coupon_l> couponList = (ArrayList<Coupon_l>) request.getAttribute("coupon_list");
								//쿠폰리스트를 어레이리스트에 담고
								listCount = ((Integer) request.getAttribute("listCount")).intValue();
								startPage = ((Integer) request.getAttribute("startPage")).intValue();
								endPage = ((Integer) request.getAttribute("endPage")).intValue();
								maxPage = ((Integer) request.getAttribute("maxPage")).intValue();
								currentPage = ((Integer) request.getAttribute("currentPage")).intValue();

								for (Coupon_l c : couponList) {
						%>
						<tr>
							<th><%=c.getTruck_name()%></th>
							<th><%=c.getCoupon_content()%></th>
							<th><%=c.getCoupon_date()%></th>
						</tr>
						<%
							}
							} else {
						%>
						<tr>
							<td></td>
							<td><font color="gray"><%=request.getAttribute("couponMessage")%></font></td>
							<td></td>
						</tr>
						<%
							}
						%>
					</tbody>
				</table>

				<hr style="margin-top: 30px; width: 1145px;">
				<div style="text-align: center; margin-left: 130px;">
					<%
						if (currentPage <= 1) {
					%>
					[맨처음]&nbsp;
					<%
						} else {
					%>
					<a href="/food/cusedlist?currentPage=1">[맨처음]</a>
					<%
						}
					%>
					<%
						if ((currentPage - 10) < startPage && (currentPage - 10) >= 1) {
					%>
					<a href="/food/cusedlist?currentPage=<%=startPage - 1%>">[prev]</a>
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
					<a href="/food/cusedlist?currentPage=<%=p%>"><%=p%></a>
					<%
						}
						}
					%>

					<%
						if ((currentPage + 10) > endPage && (currentPage + 10) <= maxPage) {
					%>
					<a href="/food/cusedlist?currentPage=<%=endPage + 1%>">[next]</a>
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
					<a href="/food/cusedlist?currentPage=<%=maxPage%>">[맨끝]</a>
					<%
						}
					%>
				</div>
			</div>
		</div>
		<br> <br> <br>
		<!-- tab-5끝나는 div -->
	</div>
	<div class="foot" style="bottom: 0; position: fixed;">FoodTruck
		&nbsp;Copyright @ iei.or.kr &nbsp; All Rights Reserved.</div>
</body>
</html>