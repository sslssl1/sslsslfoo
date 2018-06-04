<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="member.model.vo.*,java.util.*,foodtruck.model.vo.Foodtruck" %>

<% 
Member loginMember= null;
ArrayList<Foodtruck> list=(ArrayList<Foodtruck>)request.getAttribute("list");
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
<title>푸딩 푸드트럭 찾기</title>
<meta charset="utf-8">
<link rel="stylesheet" href="/food/resources/css/top-menu.css">
<link rel="stylesheet" href="/food/resources/css/foot.css">
<link rel="stylesheet" href="/food/resources/css/location.css">
<link rel="stylesheet" href="/food/resources/css/bootstrap.min.css">
<link rel="stylesheet" href="/food/resources/css/map.css">
<script type="text/javascript" src="/food/resources/js/jquery-3.3.1.min.js"></script>
<script src="/food/resources/js/jquery.min.js"></script>


<script>
<%int cnt = 0;%>
$(function(){

	
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
					<li class="active"><a href="/food/slocation">지역검색</a></li>
					<li ><a href="/food/flist">푸드트럭</a></li>
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
		<h3 id="mt-h3">S e a r c h &nbsp;&nbsp;&nbsp;L o c a t i o n</h3>
	</div>
	<br>
	<div align="center">
				<div class="map_wrap">
			<div id="map" style="width: 1000px; height: 100%; position: relative; overflow: hidden;"></div>
			<div id="menu_wrap" class="bg_white">
				<div class="option">
					<div>
						<form onsubmit="searchPlaces(); return false;">
							검색어 : <input type="text" value="서울" id="keyword" size="15">
							<button type="submit">검색하기</button>
						</form>
					</div>
				</div>
			</div>
		</div>
		<font style="font-size:9pt; color:gray; font:bold;"> * 지도가 깨질시 지도를 클릭하세요. </font>

	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=15e2354024ec8ffe859e608291415c04&libraries=services"></script>

		<script>
var mapContainer = document.getElementById('map'), 
    mapOption = {
        center: new daum.maps.LatLng(37.566826, 126.9786567),
        level: 6
    };  

var map = new daum.maps.Map(mapContainer, mapOption); 

var ps = new daum.maps.services.Places(); 

function searchPlaces() {

    var keyword = document.getElementById('keyword').value;

    if (!keyword.replace(/^\s+|\s+$/g, '')) {
        return false;
    }

    ps.keywordSearch( keyword, placesSearchCB);
} 



<%
//db에서 불러온 데이터를 mdata에 넣어준다.
int size=list.size();
double mdata[][] = new double[size][size-1];
for(int i=0; i<size; i++){
	mdata[i][0]=list.get(i).getTruck_Loc_X();
	mdata[i][1]=list.get(i).getTruck_Loc_Y();
	
}

//db에서 불러온 마크의 정보를 넣어준다.

for(int i=0; i<size; i++){  %>
	
var text_<%=i%> = " <img src='/food/resources/images/foodtruck/<%=list.get(i).getTruck_Img()%>' style='width:228px; height:110px;'><div><%=list.get(i).getTruck_Category()%>  | <%=list.get(i).getTruck_Name()%></div><div style='text-align:center;''><%=list.get(i).getTruck_MainMenu()%></div> "; 

 <% } %>



 


<%for (int j = 0; j < size; j++) {%>

var marker = new daum.maps.Marker({
position: new daum.maps.LatLng(<%=mdata[j][0]%>,<%=mdata[j][1]%>) , 
map: map

});
var infowindow = new daum.maps.InfoWindow({
position : new daum.maps.LatLng(<%=mdata[j][0]%>,<%=mdata[j][1]%>), 
content : text_<%=j%>
});

daum.maps.event.addListener(marker, 'mouseover', makeOver(map, marker, infowindow));
daum.maps.event.addListener(marker, 'mouseout', makeOut(infowindow));
daum.maps.event.addListener(marker, 'click', function() {
	   location.href="foodtruckdetail?bId=<%=list.get(j).getBusiness_Id()%>"
	});
<%}%>

function makeOver(map, marker, infowindow) {
return function() {
    infowindow.open(map, marker);
};
}

function makeOut(infowindow) {
return function() {
    infowindow.close();
};
}


function placesSearchCB (data, status, pagination) {
if (status === daum.maps.services.Status.OK) {

 var bounds = new daum.maps.LatLngBounds();

 for (var i=0; i<data.length; i++) {  
     bounds.extend(new daum.maps.LatLng(data[i].y, data[i].x));
 }       

 map.setBounds(bounds);
} 
}

var mapTypeControl = new daum.maps.MapTypeControl();

map.addControl(mapTypeControl, daum.maps.ControlPosition.TOPRIGHT);	
</script>
	</div>
	<br>
	<br>
		<div class="foot" style="bottom: 0;">
			<p>FoodTruck &nbsp;Copyright @ iei.or.kr &nbsp; All Rights Reserved.</p>
		</div>
</body>
</html>
