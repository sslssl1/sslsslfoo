<%@ page import="javax.security.auth.callback.ConfirmationCallback" 
  language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="member.model.vo.*,review.model.vo.*,java.util.*,reply.model.vo.*, festival.model.vo.*"%>

<%
		Festival festival=(Festival)request.getAttribute("festival");
		ArrayList<String> imgList=(ArrayList<String>)request.getAttribute("img_list");
%>

<%
	//로그인 정보 가져오기
	Manager loginMember=null;
	
	if(session.getAttribute("loginMember")!=null)
	{
		loginMember = (Manager)session.getAttribute("loginMember");

	}
	else 
	{ //로그인 아니라면 index로 강제 소환%>
	<script>
	window.onload=function(){
		location.href ="/food/index.jsp";
	}
	</script>
	
	<% }
	
//tab 정보 가져오기
	if (request.getAttribute("tab") != null) {
		
%>
<script> 
var tab = '<%=request.getAttribute("tab")%>';
</script>
<% } else { %>


<script>
	var tab = "tab-1";
</script>
<%
	}
%>


<!DOCTYPE html>
<html>
<head>
<title>푸딩 푸드트럭 축제</title>
<meta charset="utf-8">
<link rel="stylesheet" href="/food/resources/css/top-menu.css">
<link rel="stylesheet" href="/food/resources/css/foot.css">
<link rel="stylesheet" href="/food/resources/css/left-menu.css">
<link rel="stylesheet" href="/food/resources/css/left-menu-tab.css">
<link rel="stylesheet" href="/food/resources/css/right-menu.css">
<link rel="stylesheet" href="/food/resources/css/modInfo.css">
<link rel="stylesheet" href="/food/resources/css/review.css">
<link rel="stylesheet" href="/food/resources/css/replay.css">
<link rel="stylesheet" href="/food/resources/css/bootstrap.min.css">


<script src="/food/resources/js/jquery.min.js"></script>
<script src="/food/resources/js/jquery.scrolly.min.js"></script>
<script src="/food/resources/js/jquery.scrollzer.min.js"></script>
<script src="/food/resources/js/skel.min.js"></script>

<script src="/food/resources/js/util.js"></script>
<script src="/food/resources/js/main.js"></script>
<script type="text/javascript" src="/food/resources/js/jquery-3.3.1.min.js"></script>

<script type="text/javascript">

function deltImg(num)
{	

	$('#imgSpan'+num).html("");
	imgCount--;
}

$(function() {
	
	if (tab != null) {
		$('.tab-content').removeClass('current');
		$('.tabs li').removeClass('current');
		$('.tabs li a').removeClass('active');
		$('#' + tab).addClass(' current');
		$('[data-tab='+tab+'] a').addClass('active');
		console.log('[data-tab='+tab+']');
	}

});

var imgNum = 2;
var imgCount = 0;
function addImage()
{

	var newCode = '<span id="imgSpan'+imgNum+'" ><input type="hidden" name="oriImg" value=""><br><br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;<input type="file" accept="image/*" onchange="imgChange('+imgNum+');" name="imgFile'+imgNum+'" >&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;<input type="button" onclick="deltImg('+imgNum+');" value="삭제하기" >&nbsp;<input type="hidden" name="fileTagName" value="imgFile'+imgNum+'" ><br><br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;<img id="img'+imgNum+'" style="width:400px; height:250px;" alt="이미지를 등록하세요" src="#"></span>';
	
	$('#addImageDiv').append(newCode);
	imgNum++;
	imgCount++;
}




function imgChange(num)
{
	var preview = document.querySelector('#img'+num);
	var file = document.querySelector('[name=imgFile'+num+']').files[0];
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

function check() {
	var start = $('[name=upfes_start]').val();
	var end = $('[name=upfes_end]').val();
	var file = $('[name=upfes_image]').val();
	//title이 비어있으면
	if(upfes_form.upfes_title.value==""){
		alert("제목을 입력하세요");
		upfes_form.upfes_title.focus();
		return false;	
	//주소가 비어있으면	
	}else if(upfes_form.upfes_addr.value==""){
		alert("주소를 입력하세요");
		upfes_form.upfes_addr.focus();
		return false;
	//시작날짜가 끝 날짜보다 클 경우	
	}else if(Date.parse(start) > Date.parse(end)){
		alert("기간을 정확하게 입력하세요");
		upfes_form.upfes_end.focus();
		return false;
	//날짜가 비어있으면	
	}else if(!Date.parse(start) || !Date.parse(end)){
		alert("기간을 입력하세요");
		upfes_form.upfes_start.focus();
		return false;
	//주최측이 비어있으면	
	}else if(upfes_form.upfes_host.value==""){
		alert("주최측 정보를 입력하세요");
		upfes_form.upfes_host.focus();
		return false;
		
	}else if(upfes_form.upfes_phone.value==""){
		alert("연락처를 입력하세요");
		upfes_form.upfes_phone.focus();
		return false;
	}else if(!file){
		var ok = confirm("등록된 사진파일이 없습니다. 그대로 진행하시겠습니까?");
		
		if(ok){
			return true;
		}else{
			upfes_form.upfes_image.focus();
			return false;
		}
		
	}else {
		return true;
	}
}


</script>

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
					<li class="active"><a href="/food/fes_show">축제검색</a></li>
					</ul>
				<% if(loginMember==null){ %>
					<ul class="nav navbar-nav navbar-right">
						<li><a href="/food/views/logIn/logIn.jsp"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
						<li><a href="/food/views/signUp/select_signUp.jsp"><span class="glyphicon glyphicon-edit"></span> 회원가입</a></li>
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
				<li class="current" data-tab="tab-1"><a href="#" class="active"><span class="icon fa-edit">축제정보수정</span></a></li>
			</ul>
		</nav>
	</div>
	<div id="tab-1" class="tab-content tab-1">
  	
	<h2>&emsp;축제 정보 수정</h2>
			<hr style="height: 1px; background: black; width: 850px; float: left">
			<br> <br>
			
			<form action="/food/fes_update" name="upfes_form" onsubmit="return check()" method="post" enctype="multipart/form-data">
			<input type="hidden" name="fNum" value="<%= festival.getFestival_No()%>">
					<!-- 축제 이름 -->
			<strong>축제 이름 </strong>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; <input
				name="upfes_title" value="<%= festival.getFestival_Name() %>"
				type="text" style="width: 200px; color: gray;"><font
				style="color: gray; font-size: 10pt">&emsp;한글, 영문, 숫자
				혼용가능(한글기준 10글자)</font> <br> <br>
			<hr style="width: 850px; float: left">

			<!-- 축제 장소 -->
			<br> <br> <strong>축제 장소</strong>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&nbsp;&nbsp;
			<input name="upfes_addr" value="<%=festival.getFestival_Addr() %>"
				type="text" style="width: 300px; color: gray; margin-left: -4px;"><font
				style="color: gray; font-size: 10pt">&emsp;한글, 영문, 숫자
				혼용가능(한글기준 20글자)</font> <br> <br>
			<hr style="width: 850px; float: left">

			<!-- 축제 기간 -->



			<br> <br> <strong>축제 기간 </strong>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
			<input type="date" name="upfes_start"
				value="<%=festival.getFestival_Date_Start() %>"> ~ <input
				type="date" name="upfes_end"
				value="<%=festival.getFestival_Date_End() %>"> <font
				style="color: gray; font-size: 10pt">달력을 잘보시고 체크해 주세요</font> <br>
			<br>
			<hr style="width: 850px; float: left">

			<!-- 주최측 -->
			<br> <br> <strong>주최자(기관)명</strong>&emsp;&emsp;&emsp;&emsp;
			<input name="upfes_host" type="text"
				style="width: 200px; color: gray; margin-left: -5px;"
				value="<%= festival.getFestival_Host()%>">&emsp;<font
				style="color: gray; font-size: 10pt">" - " 생략가능</font> <br> <br>
			<hr style="width: 850px; float: left">
			<br> <br>

			<!-- 축제 연락처 -->
			<strong>축제 고객센터</strong>&emsp;&emsp;&emsp;&emsp; <input name="fes_phone" type="text"
				style="width: 200px; color: gray; margin-left: 2px;"
				placeholder="ex) 02-123-4567" value="<%=festival.getFestival_Phone()%>">&emsp;<font
				style="color: gray; font-size: 10pt">" - " 생략가능</font> <br> <br>
			<hr style="width: 850px; float: left">
			<br> <br>

			<!-- 썸네일 사진 등록 -->

			<strong style="float: left;">썸네일 사진 </strong>&emsp;&emsp;&emsp;&emsp;&emsp;

			<input type="file" accept="image/*" name="imgFile0"
				onchange="imgChange(0);"> <input type="hidden"
				name="upfes_image_ori"
				value="<%=festival.getFestival_Image_File() %>"> <br>
			<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; <img
				id="img0" style="width: 400px; height: 250px;" alt="이미지를 등록하세요"
				src="/food/resources/images/festival/<%=festival.getFestival_Image_File() %>">
			<br><br><hr style="width: 850px; float: left"><br><br>

			<!-- 메인사진 등록 -->
	<strong style="float: left;">메인 사진 </strong>&emsp;&emsp;&emsp;&emsp;&emsp;
			<input type="file" accept="image/*" name="imgFile1" onchange="imgChange(1);"> 
			<input type="hidden" name="upfes_image_main" value="<%=festival.getFestival_Image_Main() %>"> <br>
			<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; 
			<img id="img1" style="width: 400px; height: 250px;" alt="이미지를 등록하세요"
				src="/food/resources/images/festival/<%=festival.getFestival_Image_Main() %>">
			<br><br><hr style="width: 850px; float: left"><br><br>


			<!-- 축제사진 등록 -->

			<strong style="float: left;">축제 사진 </strong> <br> <br>
			<input type="button" value="사진 추가" onclick="addImage() ">
			&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;

			<div id="addImageDiv">
				<%for(String s : imgList) {%>
				<script>
			console.log("<%=s%>");
			var newCode = '<span id="imgSpan'+imgNum+'" ><input type="hidden" name="oriImg" value="<%=s%>"><br><br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;<input type="file" accept="image/*" onchange="imgChange('+imgNum+');" name="imgFile'+imgNum+'" >&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;<input type="button" onclick="deltImg('+imgNum+');" value="삭제하기" >&nbsp;<input type="hidden" name="fileTagName" value="imgFile'+imgNum+'" ><br><br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;<img id="img'+imgNum+'" style="width:400px; height:250px;" alt="이미지를 등록하세요" src="/food/resources/images/festival/<%=s%>"></span>';
					document.write(newCode);
					imgNum++;
					imgCount++;
				</script>
				<%
					}
				%>

			</div>
			<!-- 등록하기 버튼 -->
			<br>
			<br>
			<br>
			<br>
			<hr style="width: 850px; float: left">
			<br>
			<br>

			<div style="margin-left: 30px">
				<input type="button" value="수정하기" onclick="trySubmitBtn()">
				<input hidden="hidden" id="modSubmit" type="submit" value="수정하기">
			</div>
		</form>
		<script type="text/javascript">
			function trySubmitBtn() {
				if (imgCount > 0)
					$('#modSubmit').click();
				else
					alert("사진을 최소 한 장 이상 등록하세요.");

			}
		</script>
	</div>
 
	<div class="foot" style="bottom: 0; position: absolute;">FoodTruck &nbsp;Copyright @ iei.or.kr &nbsp; All Rights Reserved.</div>
</body>
</html>