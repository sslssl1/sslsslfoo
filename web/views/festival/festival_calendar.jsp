<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="member.model.vo.*, festival.model.vo.*, java.util.*"%>
    
<% 
	ArrayList<Festival> show=(ArrayList<Festival>)request.getAttribute("show");
	Festival fes=(Festival)request.getAttribute("festival");
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
    
    
<!DOCTYPE>
<html>
<head>
<meta charset="UTF-8">
<title>푸딩 푸드트럭 축제</title>

	<script type="text/javascript" src="/food/resources/js/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="/food/resources/fullcalendar-3.9.0/lib/moment.min.js"></script>
	<script type="text/javascript" src="/food/resources/fullcalendar-3.9.0/fullcalendar.js"></script>
	<link rel="stylesheet" href="/food/resources/fullcalendar-3.9.0/fullcalendar.css">
	<link rel="stylesheet" href="/food/resources/fullcalendar-3.9.0/fullcalendar.min.css">
	
<link rel="stylesheet" href="/food/resources/css/top-menu.css">
<link rel="stylesheet" href="/food/resources/css/foot.css">
<link rel="stylesheet" href="/food/resources/css/festival.css">
<link rel="stylesheet" href="/food/resources/css/location.css">
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

<script type="text/javascript">
$(function(){
	
	//캘린더 데이터 보여주기
	/* function setCalendar(title,start,end){ */
	<% for(Festival f : show) { %>
		console.log("<%= f.getFestival_No()%>");
		console.log("<%= f.getFestival_Name()%>");
	<% } %>
		
		$('#calendar').fullCalendar({
			header: {
	            left: 'prev,next today',
	            center: 'title',
	            right: 'month'
	        },
			
	        defaultdate: '2018-04-20',
	        editable: false,
	        eventLimit: true,
	   		displayEventTime: false,
	        events: [
	        	
	       	<% for(Festival f : show) { %>	
	        
	        	{
	        		id: '<%= f.getFestival_No()%>',
		       		title: '<%= f.getFestival_Name()%>',
		       		start: '<%= f.getFestival_Date_Start()%>',
		       		end: '<%= f.getFestival_Date_End()%>T23:59:00',
		       		borderColor: 'white',
		       		color: '#'+Math.round(Math.random()*0xffffff).toString(16)
	        		
	        	},
	        
	        <% } %>	
	        
	        ],
	        
	        eventClick: function(event) {
	        	var id=event.id;
		        	<% for(Festival f : show) { %>
			        	if(id==<%= f.getFestival_No()%>){
				        	location.href="/food/fes_info?fesNo=<%= f.getFestival_No()%>";
			        	}
		        	<% } %>
		        	
	        },eventMouseover : function(event) {
				$(this).css("cursor", "pointer");
				$(this).css("border-color", "black");
			},
			eventMouseout : function() {
				$(this).css("border-color", "white");
			}
	        
		});	
		//fullcalendar
	/* }	//setCalendar */
	
	
});	//ready

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
	<hr><br>
	
		<h3 style="text-align:center" id="fesList"></h3>
	
	<div align="center">
		<div id="calendar" style="width:800px; height:500px;"></div>
	</div>
	
	
	
</body>
</html>