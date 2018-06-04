<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="member.model.vo.*, festival.model.vo.*, java.util.*" %>

<%ArrayList<Festival> list=null;
if(request.getAttribute("show")!=null){
	 list=(ArrayList<Festival>)request.getAttribute("show");}
%>

<% 
Member loginMember= null;

if(session.getAttribute("loginMember") instanceof Business)
 {
	loginMember = (Business)session.getAttribute("loginMember");
 }
else if (session.getAttribute("loginMember") instanceof Customer)
{
	loginMember = (Customer)session.getAttribute("loginMember");
}
else if (session.getAttribute("loginMember") instanceof Manager)
{
	loginMember = (Manager)session.getAttribute("loginMember");
}
%> 


<!DOCTYPE html>
<html lang="en">
<head>
<title>푸딩 푸드트럭 축제</title>
<meta charset="utf-8">
<link rel="stylesheet" href="/food/resources/css/top-menu.css">
<link rel="stylesheet" href="/food/resources/css/foot.css">
<link rel="stylesheet" href="/food/resources/css/festival.css">
<link rel="stylesheet" href="/food/resources/css/location.css">
<link rel="stylesheet" href="/food/resources/css/bootstrap.min.css">
<script src="/food/resources/js/jquery-3.3.1.min.js"></script>
<script src="/food/resources/js/jquery.min.js"></script>
<script src="/food/resources/js/jquery.scrolly.min.js"></script>
<script src="/food/resources/js/jquery.scrollzer.min.js"></script>
	<script src="/food/resources/js/jquery.scrollfollow.js"></script>

 
<script type="text/javascript">

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
<%int cnt = 0;%>
	//축제리스트 검색 ajax
	$(function(){
		$('#fsearch').on('click', function(){
			$.ajax({
				url: 'fsearch.do',
				type: 'post',
				data: { fSearch : $('input[name=fesSearch]').val() },
				dataType: 'json',
				success: function(data) {
					var jsonStr = JSON.stringify(data);
					var json = JSON.parse(jsonStr);
					var searchList = "";
					
					for(var i in json.list){
						searchList += 
							
							"<div class='media-left'>"+
								"<img src='/food/resources/images/festival/"+json.list[i].festival_image_file+"'"+	
								"class='media-object'"+
								"align='left' width='300px' height='300px'>"+
							"</div>"+
							"<div class='media-body'>"+
								"<h4><a href='/food/fes_info?fesNo="+json.list[i].festival_no+"'>" +
							"<strong>"+json.list[i].festival_name+"</strong>" +
							"</a></h4>"+
							"<p>장소 : "+json.list[i].festival_addr+"</p>" +
							"<p>날짜 : "+json.list[i].festival_date_start+" ~ "+json.list[i].festival_date_end+"</p>";
							
						<% if(loginMember instanceof Manager) { %>
						searchList += "<a href='/food/fes_delete?fesNo="+json.list[i].festival_no+"'>삭제</a>"+ " / " +
							"<a href='/food/fes_upview?fNo="+json.list[i].festival_no+"'>수정</a>";
						<% } %>	
						
						searchList += "<br><br><br><hr style='width: 800px'></div><br><br>";
					}
						$('#festival_list').html(searchList);
				}
				
			});//ajax
			
		});	//click
		
		$("#calendars").scrollFollow({
            speed : 450,    // 꿈지럭 거리는 속도
            offset : 400     // 웹페이지 상단에서 부터의 거리(바꿔보면 뭔지 안다)
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
	}); //onload
	
	
	
	function deleteCheck() {
		var ok = confirm("해당 축제정보를 삭제하시겠습니까?");
		
		if(ok) {
			return true;
		}else{
			return false;
		}
		
	}
	
</script>

<style>

/* 메인사진 */
#mainPhoto {
	background-image: url("/food/resources/images/FOODING2.jpg");
	background-size: cover;
	background-position: center;
	width: 100%;
	height: 230px;
}

.col-sm-4 {
	left-padding: 0px;
	right-padding: 0px;
}

</style>

<style type="text/css">


#main-t a #calendars:hover{
		border: 5px solid #f00;
}
	
#calendars{
           position: absolute;
            border: 3px solid #f00;
            right : 5px;
            top : 30px;
            width: 150px;
            height: 300px;
}

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
					<li ><a href="/food/flist">푸드트럭</a></li>
					<li class="active"><a href="/food/fes_show">축제검색</a></li>
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
						<li ><a href="/food/views/businessMy/business_my.jsp"><span class="glyphicon glyphicon-th-list"></span>내 정보 보기</a></li>
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
	<!-- 메인 사진 -->

	<div id="mainPhoto" align="center">
		
	</div>
	<div id="main-t" align="center">
	
		<br>
			<a href="/food/cal_show"><img id="calendars" src="/food/resources/images/calendar2018s.png"
			style="width: 80px; height: 80px; float: right"></a>
		
		<h3 id="mt-h3">F e s t i v a l &nbsp;&nbsp;&nbsp;I n f o m a t i o n</h3>
		<form onsubmit="return false">
			<input name="fesSearch" type="text" placeholder="Search.."> 
			<button id="fsearch" class="glyphicon glyphicon-search"></button>
		</form>	
	<br>
	<br>
	</div>
		<br>
	<div id="festival_list" class="container" align="center">
		<!-- festival -->
		<% for(Festival f : list) { %>
		<div>
			 <div class="media-left">
				<img src="/food/resources/images/festival/<%= f.getFestival_Image_File() %>"	
					class="media-object"
					align="left" width="200px" height="200px">
			</div>
			<div class="media-body">
					<h4><a href="/food/fes_info?fesNo=<%= f.getFestival_No()%>">
					<strong><%=f.getFestival_Name()%></strong>
					</a></h4>
					<p>장소 : <%=f.getFestival_Addr() %></p>
					<p>날짜 : <%=f.getFestival_Date_Start()%> ~ <%=f.getFestival_Date_End()%></p>
				<% if(loginMember instanceof Manager) { %>	
					<p><a id="deleteFes" onclick="return deleteCheck()" href="/food/fes_delete?fesNo=<%= f.getFestival_No()%>">삭제</a> / 
					<a href="/food/fes_upview?fNo=<%= f.getFestival_No()%>">수정</a>
				<% } %>	
			</div>
		</div>
		<br>
		<hr style="width: 800px">
		<br>
			<% } %>	
	</div>
	
	<div class="foot" style="bottom: 0; position: relative;">FoodTruck
		&nbsp;Copyright @ iei.or.kr &nbsp; All Rights Reserved.</div>
</body>
</html>
