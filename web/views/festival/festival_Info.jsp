<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="member.model.vo.*, festival.model.vo.*, java.util.*" %>

<%

Festival festival=(Festival)request.getAttribute("festival");
ArrayList<String> fi=(ArrayList<String>)request.getAttribute("fImage");
%>

<% 
Member loginMember= null;
//Festival_Truck ft=(Festival_Truck)request.getAttribute("fTruck");

if(session.getAttribute("loginMember") instanceof Business)
 {
	loginMember = (Business)session.getAttribute("loginMember");
 }
else if (session.getAttribute("loginMember") instanceof Customer)
{
	loginMember = (Customer)session.getAttribute("loginMember");
}else if (session.getAttribute("loginMember") instanceof Manager)
{
	loginMember = (Manager)session.getAttribute("loginMember");
}
%> 



<!DOCTYPE html>
<html>
<head>
<title>푸딩 푸드트럭 축제</title>
<meta charset="utf-8">
<link rel="stylesheet" href="/food/resources/css/top-menu.css">
<link rel="stylesheet" href="/food/resources/css/foot.css">
<link rel="stylesheet" href="/food/resources/css/festival-info.css">
<script src="/food/resources/js/jquery-3.3.1.min.js"></script>
<script src="/food/resources/js/jquery.min.js"></script>
<script src="/food/resources/js/jquery.scrolly.min.js"></script>
<script src="/food/resources/js/jquery.scrollzer.min.js"></script>
<link rel="stylesheet" href="/food/resources/css/bootstrap.min.css">
<script src="/food/resources/js/slick.js"></script>
<script src="/food/resources/js/slick.min.js"></script>
<link rel="stylesheet" href="/food/resources/css/slick.css">
<link rel="stylesheet" href="/food/resources/css/slick-theme.css">
<script src="/food/resources/slick-1.8.0/slick/slick.js"></script>
<script src="/food/resources/slick-1.8.0/slick/slick.min.js"></script>
<link rel="stylesheet" href="/food/resources/slick-1.8.0/slick/slick.css">
<link rel="stylesheet" href="/food/resources/slick-1.8.0/slick/slick-theme.css">


<script type="text/javascript">
		$(function(){
			$('.fvimages').slick({
				  dots: true,
				  infinite: true,
				  autoplay: true,
				  autoplaySpeed: 2000,
				  speed: 1000,
				  slidesToShow: 1,
				  adaptiveHeight: true,
			});
				
		});
	</script>


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

		function joinTruck() {
			
			$.ajax({
				url: '/food/trucklist.do',
				type: 'post',
				data: { fNum : '<%= festival.getFestival_No()%>' },
				dataType: 'json',
				success: function(data){
					console.log(data);
					//json 객체 하나(data)를 문자열 형태로 바꿈
					var jsonStr = JSON.stringify(data);
					//문자열을 다시 자바스크립트가 사용할 수 있는
					//json 객체로 파싱함
					var json = JSON.parse(jsonStr);
					var values = "";
					for(var i in json.truckName){
						values += "<a class='tnames' href='/food/foodtruckdetail?bId="+ json.truckName[i].businessid +" '>" +json.truckName[i].truckName+"</a><br>";
					}
					
					$('#jtList').html(values);
				}
			});
			
		}	
		
	$(function(){
		
		joinTruck();
		//참여트럭 리스트 보여주기
	
	<% if(loginMember instanceof Business) { %>	
		//참여시키기
		$('#addTruck').on('click', function(){
		
				$.ajax({
					url: '/food/join.do',
					type: 'post',
					data: { 
							fesno: '<%= festival.getFestival_No()%>',
							truckid: '<%= ((Business)loginMember).getBusiness_Id()%>'},
					success: function(title){
						joinTruck();
					}
				});	//ajax
				
		});	//click
		
		//삭제시키기
		$('#outTruck').on('click', function(){
			
			$.ajax({
				url: '/food/out.do',
				type: 'post',
				data: { 
						fesno: '<%= festival.getFestival_No()%>',
						truckid: '<%= ((Business)loginMember).getBusiness_Id()%>'},
				success: function(title){
					joinTruck();
				}
			});	//ajax
			
			
		
	});	//click
			
	<% } %>
			
	}); //onload
	
	//참여하기 마우스 올렸을 때
	function addMouseOver() {
		addTruck.style.color="blue";
		addTruck.style.cursor="pointer";
	}
	//삭제하기 마우스 올렸을 때
	function outMouseOver() {
		outTruck.style.color="blue";
		outTruck.style.cursor="pointer";
	}
	//참여하기 마우스 떼었을 때
	function addMouseOut() {
		addTruck.style.color="white";
	}
	//삭제하기 마우스 떼었을 때
	function outMouseOut() {
		outTruck.style.color="white";
	}
	
</script>
<style type="text/css">
		.tnames:link {color: white;}
		.tnames:visited {color: white;}
		.tnames:link {color: white;}
		.tnames:visited {color: white;}
		.tnames{text-decoration:none;}
		.tnames:hover{
			color:blue;
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
	
	<%if(festival.getFestival_Image_Main()!=null){ %>
		<div style="background-image: url('/food/resources/images/festival/<%=festival.getFestival_Image_Main() %>');
	background-size: cover;
	background-position: center;
	width: 100%;
	height: 350px;">
		<div style="font-size: 100px;
	text-align: center;
	color: white;
	opacity: 0.8;">
			<br> F E S T I V A L
		</div>
	</div>
	<% }else{ %>
	<div style="background-image: url('/food/resources/images/FESTIVAL.jpg');
	background-size: cover;
	background-position: center;
	width: 100%;
	height: 350px;">
		<div style="font-size: 100px;
	text-align: center;
	color: white;
	opacity: 0.8;">
	
		</div>
	</div>
	<% }%>
	<!-- 축제 이름 -->
	<div id="festival_subtitle">
		<b><i><%= festival.getFestival_Name() %></i></b>
	</div>
	<!-- 축제 사진 -->
	<div id="festival_info" align="center">

		<div class="fvimages" style="width: 800px;">
		<% for(String s : fi){ %>
			<img  style="margin-top: 5px; width:800px; height:800px;" 
				src="/food/resources/images/festival/<%=s %>">
				<%} %>
		</div>
	</div>
	<!-- 축제 일정 -->
				
	<div id="festival_text">
	
		<div align="center">
			<% if(loginMember instanceof Business) { %>
						<div style="padding-top:10px; font-size:20px;">
							<span id="addTruck" onmouseover="addMouseOver();" onmouseout="addMouseOut();">참가하기</span>&nbsp; / 
							<span id="outTruck" onmouseover="outMouseOver();" onmouseout="outMouseOut();">취소하기</span><hr>
						</div>
			<% } %>			
			<table>
				<tr>
					<td width="160px;"><b>[일시]</b></td>
					<td><%= festival.getFestival_Date_Start() %> ~ <%= festival.getFestival_Date_End() %></td>
				</tr>
				<tr>
					<td width="160px;"><b>[장소]</b></td>
					<td><%= festival.getFestival_Addr() %></td>
				</tr>
				<tr>
					<td width="160px;"><b>[주최]</b></td>
					<td><%= festival.getFestival_Host() %></td>
				</tr>
				<tr>
					<td width="160px;"><b>[연락처]</b></td>
					<% if(festival.getFestival_Phone()!=null){ %>
					<td><%= festival.getFestival_Phone() %></td>
					<% } else { %>
					<td></td>
					<% } %>
					
				</tr>
				<tr>
					<td width="160px;"><b>[참여트럭]</b></td>
					<td id="jtList"></td>
				</tr>
				
			</table>
		</div>
	</div>
	
	<div class="foot">
		<p>FoodTruck &nbsp;Copyright @ iei.or.kr &nbsp; All Rights Reserved.</p>
	</div>
</body>
</html>
