<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="member.model.vo.*,foodtruck.model.vo.*,java.util.*,review.model.vo.*,review.controller.*"%>

<%
	Foodtruck truck = null;
	ArrayList<Menu> mlist = new ArrayList<Menu>();
	if (request.getAttribute("truck") != null) {
		truck = (Foodtruck) request.getAttribute("truck");
	}
	if (request.getAttribute("mlist") != null) {
		mlist = (ArrayList<Menu>) request.getAttribute("mlist");
	}
	double tgrade = 0.0;
	if (request.getAttribute("tgrade") != null) {
		tgrade = Math.round(Double.parseDouble(String.valueOf(request.getAttribute("tgrade"))) * 10.0) / 10.0;
	}
	Member loginMember = null;
	if (session.getAttribute("loginMember") instanceof Business) {
		loginMember = (Business) session.getAttribute("loginMember");
	} else if (session.getAttribute("loginMember") instanceof Customer) {
		loginMember = (Customer) session.getAttribute("loginMember");
	} else if (session.getAttribute("loginMember") instanceof Manager) {
		loginMember = (Manager) session.getAttribute("loginMember");
	}
	
	
	
%>
<!DOCTYPE html>
<html>
<head>
<title>푸딩 푸드트럭 상세정보</title>
<meta charset="utf-8">
<link rel="stylesheet" href="/food/resources/css/top-menu.css">
<link rel="stylesheet" href="/food/resources/css/foot.css">
<link rel="stylesheet" href="/food/resources/css/business-tab.css">
<link rel="stylesheet" href="/food/resources/css/review.css">
<link rel="stylesheet" href="/food/resources/css/replay.css">
<link rel="stylesheet" href="/food/resources/css/modInfo.css">
<link rel="stylesheet" href="/food/resources/css/star.css">
<link rel="stylesheet" href="/food/resources/css/bootstrap.min.css">


<script type="text/javascript" src="/food/resources/js/star.js"></script>
<script type="text/javascript"
	src="/food/resources/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="/food/editor/js/HuskyEZCreator.js"
	charset="utf-8"></script>
<script type="text/javascript"
	src="//cdnjs.cloudflare.com/ajax/libs/jquery/1.9.0/jquery.js"></script>
	
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
	               'userid' : "<%=((Business)loginMember).getBusiness_Id()%>"
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
	            location.reload();
	            
	            
	         }else if(data == 2){
	            alert("장사를 종료합니다");
	            onoffState='N';
	            $("#graybutton").attr("src", "/food/resources/images/graybutton.png");
	            
	            $(".rb").hide();
	            $(".gb").show();
	            location.reload();
	          	
	         }
	      }
	   });
	}

</script>
	
	
<script type="text/javascript">

function wantcoupon(content,cdate){
	<%if(loginMember instanceof Customer){%>
	 $.ajax({
			url : "/food/wcoupon",
			type : "post",
			data : {
				
					bid : "<%=truck.getBusiness_Id()%>",
					con : content,
					cid : "<%=((Customer)loginMember).getCustomer_Id()%>",
					date : cdate
				
			},
			success : function(data){
				 if(data==1){
					 alert("발급되셨습니다.")
				}else{
					 alert("이미 발급하신 쿠폰입니다.");						
				} 
			}
			
		});
	 <%}else{%>
	 	alert("발급 하실수 없는 사용자입니다.")
	 <%}%>
};


	$(function() {
	
		
		<%if(request.getAttribute("tab")!=null){ %>
		var tab = '<%=request.getAttribute("tab")%>';
		<%}else{%>
		var tab = 'tab-1';
		<%}%>
	
		$('li[data-tab=tab-4]').click(function(){
			<%if(loginMember instanceof Customer){%>
				$('.wcbth').show();
			<%}else{%>
				$('.wcbth').hide();
			<%}%>
		});	
		
		
		$('#searchBtn').click(function(){
			reviewPaging(1);
		
		});
		
		
		
		  if (tab != null) {
		         $('.tab-content').removeClass('current');
		         $('.tabs li').removeClass('current');
		         $('.tabs li a').removeClass('active');
		         $('#' + tab).addClass(' current');
		         $('[data-tab='+tab+']').addClass(' current');
		         $('[data-tab='+tab+'] a').addClass('active');
		    
		         
		         tab=null;
		      }
		      
		      $('.tabs li').click(function() {
		         var tab_cl = $(this).attr('data-tab');

		         $('.tabs li').removeClass('current');
		         $('.tab-content').removeClass('current');
		         
		        
					if(tab!=null){
					$('[data-tab='+tab+']').addClass(' current');
					$('#'+tab).addClass(' current');
					}else{
					$(this).addClass('current');
					$("." + tab_cl).addClass('current');
					}
					
		      });
		      
		      
		   <%--    <%if (request.getAttribute("tab") != null) {%>
				
				$('[data-tab=<%=request.getAttribute("tab")%>]').addClass(' current');
				$('#<%=request.getAttribute("tab")%>').addClass(' current');
				<%} else {%>
				$(this).addClass('current');
				$("." + tab_cl).addClass('current');
				<%}%> --%>
		      
		     <%--  $('.tabs li').click(function() {
					var tab_cl = $(this).attr('data-tab');

					$('.tabs li').removeClass('current');
					$('.tab-content').removeClass('current');

					<%if (request.getAttribute("tab") != null) {%>
				
					$('[data-tab=<%=request.getAttribute("tab")%>]').addClass('current');
					$('#<%=request.getAttribute("tab")%>').addClass('current');
					<%} else {%>
					$(this).addClass('current');
					$("." + tab_cl).addClass('current');
					<%}%>
				}); --%>
		      
		
		
		<%-- $('.tabs li').click(function() {
			var tab_cl = $(this).attr('data-tab');

			$('.tabs li').removeClass('current');
			$('.tab-content').removeClass('current');

			<%if (request.getAttribute("tab") != null) {%>
		
			$('[data-tab=<%=request.getAttribute("tab")%>]').addClass('current');
			$('#<%=request.getAttribute("tab")%>').addClass('current');
			<%} else {%>
			$(this).addClass('current');
			$("." + tab_cl).addClass('current');
			<%}%>
		});
		 --%>
		
		
		
		
		$.ajax({
			url : "/food/fcview",
			type : "post",
			dataType : "json",
			data : {
				bid : "<%=truck.getBusiness_Id()%>"	
			},
			success : function(data){
				var jsonStr = JSON.stringify(data);
				var json = JSON.parse(jsonStr);
				var cnt=0;
				var main=null;
				for(var i in json.list){
					main += '<tr><th>' + decodeURIComponent((json.list[i].fname).replace(/\+/g, '%20')) + '</th>';
					main += '<th>'+decodeURIComponent((json.list[i].ccontent).replace(/\+/g, '%20'))+'</th>';
					main += '<th>' + json.list[i].cdate + '</th>';
					main += '<th class="wcbth" hidden style="width:10px"><button type="button" onclick="wantcoupon(\''+decodeURIComponent((json.list[i].ccontent).replace(/\+/g, '%20'))+'\',\''+json.list[i].cdate+'\');" class="btn btn-outline-primary">발급</button></th></tr>';
				}
				$('#fcouponlist').html(main);
			}		
		});
		
		
		$('#searchBtn').click(function(){
			reviewPaging(1);
		
		});
		
		
		
	});

	
	var startPage =1;
	var endPage = 1;
	var maxPage = 1;
	var currentPage = 1;
	
	function reviewPaging(pageNum){
		$.ajax({
			url : "/food/truckdetailreviewlist",
			data : {page : pageNum,
					bsid : "<%=truck.getBusiness_Id()%>",
						seType : $('[name=seType]').val(),
						seValue : $('[name=seValue]').val()
					},
					type : "post",
					dataType : "json",
					success : function(data) {

						startPage = data.startPage;
						endPage = data.endPage;
						maxPage = data.maxPage;
						currentPage = data.currentPage;

						var jsonStr = JSON.stringify(data);
						var json = JSON.parse(jsonStr);
						//리뷰

						var main = null;
						for ( var i in json.list) {
							
							main += '<tr><th>' + json.list[i].rno + '</th>';
							main += '<th><a href="/food/reviewdetail?reviewNo='+json.list[i].rno+'">'
									+ decodeURIComponent((json.list[i].rtitle)
											.replace(/\+/g, '%20'))
									+ '</a></th>';
							main += '<th>' + json.list[i].rwriter + '</th>';
							main += '<th>' + json.list[i].rdate + '</th>';
							main += '<th>' + json.list[i].rcount + '</th></tr>';
						}

						$('#t_main').html(main);
						//페이징 처리
						var paging = '<th colspan="5">';
						if (currentPage <= 1) {
							paging += '[맨처음]&nbsp;';
						} else {

							paging += '<a href="javascript:reviewPaging(1);">[맨처음]</a>';
						}
						if ((currentPage - 10) < startPage
								&& (currentPage - 10) >= 1) {
							paging += '<a href="javascript:reviewPaging('
									+ (startPage - 1) + ');">[prev]</a>';
						} else {
							paging += '[prev]&nbsp;';
						}
						for (var p = startPage; p <= endPage; p++) {
							if (p == currentPage) {
								paging += '<font color="red" size="4"><b>[' + p
										+ ']</b></font>';
							} else {
								paging += '<a href="javascript:reviewPaging('
										+ p + ');">' + p + '</a>';
							}
						}

						if ((currentPage + 10) > endPage
								&& (currentPage + 10) <= maxPage) {
							paging += '<a href="javascript:reviewPaging('
									+ (endPage + 1) + ')">[next]</a>';

						} else {
							paging += '[next]&nbsp';
						}
						if (currentPage >= maxPage) {
							paging += '[맨끝]&nbsp';
						} else {
							paging += '<a href="javascript:reviewPaging('
									+ maxPage + ')">[맨끝]</a>';
						}
						paging += '</th>';

						$('#t_page').html(paging);

					},
					error : function(jqXHR, textStatus, errorThrown) {
						console.log(jqXHR.responseText);
					}
				});

	}
</script>

<style>
#truck_mainPhoto {
	background-image:
		url("/food/resources/images/foodtruck/<%=truck.getTruck_Img()%>");
	background-position: center;
	background-size: cover;
	margin-top: 0px;
	width: 100%;
	height: 400px;
}

.page-item {
	min-width: 36px
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
					<li class="active"><a href="/food/index.jsp">Home</a></li>
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
						<li><a href="foodtruckdetail?bId=<%=((Business)loginMember).getBusiness_Id() %>"><span class="glyphicon glyphicon-cutlery"></span>내 트럭</a></li>
						<li><a><span class="glyphicon glyphicon-user"></span> <%=((Business)loginMember).getBusiness_Id() %>님 </a></li>
						<li><a href="/food/views/businessMy/business_my.jsp"><span class="glyphicon glyphicon-th-list"></span>내 정보 보기</a></li>
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
	<div id="truck_mainPhoto">
		<div
			style="font-size: 100px; text-align: center; color: white; opacity: 0.8;">
			<br><%=truck.getTruck_Name()%>
		</div>
	</div>
	<!-- 별점 구간 -->

   <div align="center" style="background: black;  padding-bottom: 0px; text-align:center;">
   	
   	<table align="center">
   	
   	<tr>
	   	<th>
	   <% if( truck.getTruck_Live_OnOff().equals("Y")){ %>
	    <img id ="openimg" style=" width:200px; margin-top:0px;" src="/food/resources/images/open_by_junga.png">
	   <% }else{%>
	    <img id="closeimg" style=" width:200px; margin-top:0px;" src="/food/resources/images/close_by_junga.png" >
	   <% } %>
	      
	       
	    
	      <!-- </span>   --> 
	    </th> 
	    
	    <th>
	      <!-- <span id="stars" style=" height: 100px;" > -->
	      <%
	         for (int i = 2; i <= tgrade; i += 2) {
	      %>
	      <img src="/food/resources/images/star_fulls.png" width="50px"
	         height="50px" style="margin-bottom:5px;">
	      <%
	         if (i + 1 <= tgrade && i + 2 > tgrade) {
	      %>
	      <img src="/food/resources/images/star_halfs.png" width="50px"
	         height="50px" style="margin-right: 0px;margin-bottom:5px;">
	      <%
	         }
	         }
	      %>
	      </th>
	      
	      <th>
			  	<font color="white" size="6px" style="text-align:center;">&nbsp; <%=tgrade%>점   </font>
		  </th>
	</tr>  	
   	</table>
   	
     <!--  </span> -->
   </div>

	<!-- 맵 구간 -->
	<div align="center" id="omapd">
		<div id="omap" style="width: 1000px; height: 400px; margin:30px;"></div>
	</div>
	<%
			if(loginMember instanceof Business){
			if( (((Business) loginMember).getBusiness_Id()).equals(truck.getBusiness_Id())){
	%>
	<div align="center">
		<input id="elbtn" type="button" style="margin-top:-30px; margin-bottom:15px" onclick="editlocationbt();" value="위치 수정" >
	</div>
	<% }} %>
	<div align="center">
		<div class="map_wrap" style="width: 1000px; height: 400px; margin-top: 30px;" id="emapd" hidden>
				<div id="emap"
					style="width: 1000px; height: 400px; position: relative; overflow: hidden;"></div>
				<div id="menu_wrap" class="bg_white"  id="emapdd" >
					<div class="option">
						<div>
							&nbsp;위치 : <input type="text" value="서울" id="keyword" size="15">
							<button id="mfind" type="submit"  onclick="searchPlaces();">검색하기</button>
						</div>
					</div>
				</div>
				<!-- <font id="mwait" style="display:table-cell; text-align:center; vertical-align:middle;">검색해주세요.</font> -->
			</div>
		</div>	
	<div id="addcldiv" style="margin:15px" align="center" hidden>
	 	<table >
	 	<tr>
	 	<td><input id="addlbtn" type="button" onclick="editlocation();" value="등록" ></td>
	 	<td>&emsp;&emsp;&emsp;</td>
	 	<td><input id="clbtn" type="button" onclick="location.href='/food/foodtruckdetail?bId=<%=truck.getBusiness_Id()%>'" value="취소" ></td>
	 	</tr>
	 	</table>	 	
	 	<input name="xloc" id="xloc" type="text" hidden> 
		<input name="yloc" id="yloc" type="text" hidden>
	</div>
	<script type="text/javascript">
		function editlocation(){
			<% if(loginMember instanceof Business){ %>
			 $.ajax({
				url : "/food/bpeditloc",
				data : {
					userId : "<%=((Business) loginMember).getBusiness_Id()%>",
					xLoc :  $('#xloc').val(),
					yLoc : $('#yloc').val()
					
				},
				type : "post",
				success : function(data){
					if(data > 0){
						location.reload();
					}
				}
				
			});
			 <%}%>
		}
	</script>
			<script type="text/javascript"
				src="//dapi.kakao.com/v2/maps/sdk.js?appkey=15e2354024ec8ffe859e608291415c04&libraries=services"></script>
			
		<script>
		
		 function editlocationbt(){
			$('#elbtn').css('display','none');
			$('#omapd').css('display','none');
			
			$('#addcldiv').css('display','block');
			$('#emapd').css('display','block');
			$('#emapdd').css('display','block');
			$('#addlbtn').css('display','block');
			$('#clbtn').css('display','block');
			
			emap.relayout();
		
		} 
			var omapContainer = document.getElementById('omap'), omapOption = {
				center : new daum.maps.LatLng(
				<%=truck.getTruck_Loc_X()%>,
				<%=truck.getTruck_Loc_Y()%>
				),
				level : 3
			};

			var omap = new daum.maps.Map(omapContainer, omapOption);

			var omarkerPosition = new daum.maps.LatLng(
			<%=truck.getTruck_Loc_X()%>
				,
			<%=truck.getTruck_Loc_Y()%>
			);

			var omarker = new daum.maps.Marker({
				position : omarkerPosition
			});

			omarker.setMap(omap);

			//지도수정
			var emapContainer = document.getElementById('emap'), emapOption = {
				center : new daum.maps.LatLng(
				<%=truck.getTruck_Loc_X()%>,
				<%=truck.getTruck_Loc_Y()%>
				),
				level : 3
			};

				var emap = new daum.maps.Map(emapContainer, emapOption);

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

						emap.setBounds(bounds);
					}
				}
				var emarker = new daum.maps.Marker({
					position : emap.getCenter()
				});

				emarker.setMap(emap);

				daum.maps.event.addListener(emap, 'click', function(mouseEvent) {

					var latlng = mouseEvent.latLng;

					emarker.setPosition(latlng);

					var x = latlng.getLat();
					var y = latlng.getLng();

					document.getElementById('xloc').value = x;
					document.getElementById('yloc').value = y;
				});

			</script>
		
		
	
	
	<!-- 중간 메뉴바 구간 -->
	<div align="center" style="magin-left: 30px; magin-right: 30px;">
		<div align="center">
			<ul
				style="background: black; width: 1100px; padding: 0; text-align: center;"
				class="tabs">
				<li class="current" data-tab="tab-1" style="width: 150px;"><strong>주요정보</strong></li>
				<li data-tab="tab-2" style="width: 150px;"><strong>메뉴</strong></li>
				<li data-tab="tab-3" style="width: 150px;"><strong>리뷰</strong></li>
				<li data-tab="tab-4" style="width: 150px;"><strong>쿠폰</strong></li>
			</ul>
		</div>
	</div>
	<br>
	<!-- 주요정보 구간 -->
	<div id="tab-1" class="tab-content tab-1 current"
		style="background: white">
		<div align="center">
			<div
				style="width: 800px; height: 150px; border: 2px solid black; background: lightgray; left: 0px; right: 0px; margin: 0px;"
				align="center">
				<h4>공지사항</h4>
				<input type="text" name="truck_info_text"
					style="width: 780px; height: 75px; font-size: 20px; text-align: center;"
					value="<%=truck.getTruck_Notice()%>">
			</div>
		</div>
		<div>
			<h3 style="text-align: center;">
				영업시간 :
				<%=truck.getTruck_Time()%></h3>
			<h3 style="text-align: center;">
				전화번호 : <% if( truck.getTruck_Phone()!=null ){ %> <%=truck.getTruck_Phone()%> <% } %> </h3>
			<h3 style="text-align: center;">
				휴무 : <%=truck.getTruck_Dayoff()%></h3>
		</div>
	</div>
	<div align="center" id="tab-2" class="tab-content tab-2"
		style="background: white">
		<!-- 메뉴 탭 -->
		<table style="border: 1px solid #BDBDBD;">
			<%
				int i = 0;
				for (Menu menu : mlist) {
					if (i % 3 == 0) {
			%>
			<tr style="border: 1px solid #BDBDBD;">
				<%
					}
				%>
				<td
					style="text-align: center; width: 33%; border: 1px solid #BDBDBD;"><img
					src="/food/resources/images/menu/<%=menu.getMenu_Img()%>"
					style="width: 300px;">
					<hr style="color: #BDBDBD;">
					<h3><%=menu.getMenu_Name()%></h3>
					<h5><%=menu.getMenu_price()%>원
					</h5></td>
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


	</div>
	<div id="tab-3" class="tab-content tab-3" style="background: white">


		<div class="container"
			style="margin-left: 25%; margin-right: 25%; margin-top: 0%;">
			<table id="review" class="table table-striped">

				<!-- 글번호 | 제목 | 글쓴이 | 작성일 | 조회수  -->
				<%
					int startPage = 1;
					int endPage = 1;
					int maxPage = 1;
					int currentPage = 1;
				%>

				<thead>
					<tr>
						<th>글번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>작성일</th>
						<th>조회수</th>
					</tr>
				</thead>
				<tbody id="t_main">
					<%
						if (request.getAttribute("review_list") != null) {
							ArrayList<Review> rlist = (ArrayList<Review>) request.getAttribute("review_list");
							startPage = ((Integer) request.getAttribute("startPage")).intValue();
							endPage = ((Integer) request.getAttribute("endPage")).intValue();
							maxPage = ((Integer) request.getAttribute("maxPage")).intValue();
							currentPage = ((Integer) request.getAttribute("currentPage")).intValue();

							for (Review r : rlist) {
					%>
					<tr>
						<th><%=r.getNumber()%></th>
						<th><a href="/food/reviewdetail?reviewNo=<%=r.getNumber()%>"><%=r.getReview_title()%></a></th>
						<th><%=r.getReview_Writer()%></th>
						<th><%=r.getReview_Date()%></th>
						<th><%=r.getReview_Count()%></th>
					</tr>
					<%
						}
						}
					%>
				</tbody>
				<tbody id="t_page">
					<tr>
						<th colspan="5">
							<%
								if (currentPage <= 1) {
							%> [맨처음]&nbsp; <%
 	} else {
 %> <a href="javascript:reviewPaging(1);">[맨처음]</a> <%
 	}
 %> <%
 	if ((currentPage - 10) < startPage && (currentPage - 10) >= 1) {
 %> <a href="javascript:reviewPaging(<%=startPage - 1%>);">[prev]</a>
							<%
								} else {
							%> [prev]&nbsp; <%
 	}
 %> <!-- 현재 페이지가 포함된 그룹의 페이지 숫자 출력 --> <%
 	for (int p = startPage; p <= endPage; p++) {
 		if (p == currentPage) {
 %> <font color="red" size="4"><b>[<%=p%>]
							</b></font> <%
 	} else {
 %> <a href="javascript:reviewPaging(<%=p%>);"><%=p%></a> <%
 	}
 	}
 %> <%
 	if ((currentPage + 10) > endPage && (currentPage + 10) <= maxPage) {
 %> <a href="javascript:reviewPaging(<%=endPage + 1%>)">[next]</a> <%
 	} else {
 %> [next]&nbsp; <%
 	}
 %> <%
 	if (currentPage >= maxPage) {
 %> [맨끝]&nbsp; <%
 	} else {
 %> <a href="javascript:reviewPaging(<%=maxPage%>)">[맨끝]</a> <%
 	}
 %>
						</th>
					</tr>
				</tbody>
				<%
					String loginid="";
					if(loginMember instanceof Customer){ 
						loginid=((Customer) loginMember).getCustomer_Id();
					%>
				<tbody id="t_search">
					<tr>
						<td colspan="4"><select name="seType">
								<option value="REVIEW_TITLE" selected>제목만</option>
								<option value="REVIEW_CONTENT">내용만</option>
								<option value="REVIEW_WRITER">작성자</option>
								<option value="REVIEW_NO">글번호</option>
						</select> <input type="text" name="seValue">&nbsp; <input
							id="searchBtn" type="button" value="검색"
							style="background: green; border: green; color: white;">
						
					</td>
					
					<td align="center"><button id="reviewbt" onclick="writeForm();">글쓰기</button></td>
					</tr>
				</tbody>
				<%}else{%>
				<tbody id="t_search">
					<tr>
						<td colspan="5"><select name="seType">
								<option value="REVIEW_TITLE" selected>제목만</option>
								<option value="REVIEW_CONTENT">내용만</option>
								<option value="REVIEW_WRITER">작성자</option>
								<option value="REVIEW_NO">글번호</option>
						</select> <input type="text" name="seValue">&nbsp; <input
							id="searchBtn" type="button" value="검색"
							style="background: green; border: green; color: white;">
					</td>
					</tr>
				</tbody>
						
				<%}%>
			</table>
			<table width="100%" id="board">
				<tr>
					<td>제목 <input type="text" id="title" /></td>
				</tr>
				<tr>
					<td>별점 <span class="star-input"> <span class="input">
								<input type="radio" name="star-input" id="p1" value="1"><label
								for="p1">1</label> <input type="radio" name="star-input" id="p2"
								value="2"><label for="p2">2</label> <input type="radio"
								name="star-input" id="p3" value="3"><label for="p3">3</label>
								<input type="radio" name="star-input" id="p4" value="4"><label
								for="p4">4</label> <input type="radio" name="star-input" id="p5"
								value="5"><label for="p5">5</label> <input type="radio"
								name="star-input" id="p6" value="6"><label for="p6">6</label>
								<input type="radio" name="star-input" id="p7" value="7"><label
								for="p7">7</label> <input type="radio" name="star-input" id="p8"
								value="8"><label for="p8">8</label> <input type="radio"
								name="star-input" id="p9" value="9"><label for="p9">9</label>
								<input type="radio" name="star-input" id="p10" value="10"><label
								for="p10">10</label>
						</span> <output for="star-input">
								<b id="starpoint">0</b>점
							</output>
					</span>


					</td>
				</tr>
				<tr>
					<td><textarea rows="10" cols="30" id="ir1"
							style="width: 766px; height: 412px;"></textarea></td>
				</tr>
				<tr>
					<td><input type="button" id="save" value="저장" /></td>
				</tr>
			</table>
		</div>
<script>
			// star rating
			var starRating = function() {
				var $star = $(".star-input"), $result = $star.find("output>b");
				$(document).on("focusin", ".star-input>.input", function() {
					$(this).addClass("focus");
				}).on("focusout", ".star-input>.input", function() {
					var $this = $(this);
					setTimeout(function() {
						if ($this.find(":focus").length === 0) {
							$this.removeClass("focus");
						}
					}, 100);
				}).on("change", ".star-input :radio", function() {
					$result.text($(this).next().text());
				}).on("mouseover", ".star-input label", function() {
					$result.text($(this).text());
				}).on("mouseleave", ".star-input>.input", function() {
					var $checked = $star.find(":checked");
					if ($checked.length === 0) {
						$result.text("0");
					} else {
						$result.text($checked.next().text());
					}
				});
			};
			starRating();
</script>


<script type="text/javascript">


</script>

<script type="text/javascript">
var oEditors = [];

function writeForm(){
	 if(dClickCheck()) return;
	 if(oEditors==""){
	nhn.husky.EZCreator.createInIFrame({
		oAppRef : oEditors,
		elPlaceHolder : "ir1",
		sSkinURI : "editor/SmartEditor2Skin.html",
		htParams : { 
			bUseToolbar : true,
			bUseVerticalResizer : true,
			bUseModeChanger : false,
		}
	});
	 }
	$('#review').hide();
	$('#board').show();
	
}

var dClick= false;
function dClickCheck(){
    if(dClick){
        return dClick;
    }else{
       dClick = true;
        return false;
    }
}
			$(function() {
				$('#review').show();
				$('#board').hide();
				
				
				
				$('li[data-tab=tab-3]').click(function() {
					$('#review').show();
					$('#board').hide();
					dClick=false;
				});
			
				
				$("#save").click(function() {
					oEditors.getById["ir1"].exec("UPDATE_CONTENTS_FIELD", []);
					$.ajax({
						url : "reviewwrite",
						data : {
							title : $('#title').val(),
							content : $('#ir1').val(),
							bid : "<%=truck.getBusiness_Id()%>",
							lid : "<%=loginid%>",
							starpoint : $('#starpoint').text()
						},
						success : function(data){
							location.href="/food/foodtruckdetail?bId=<%=truck.getBusiness_Id()%>&tab=tab-3"
						}
					});
				});
				
				<% if(request.getAttribute("write")!=null){%>
				var write= '<%= request.getAttribute("write")  %>';
				<%}else{%>
				var write=null;
				<%}%>
			
				if( write =='write')
       			{
       		
       			writeForm();
       		
       			write=null;
       			}
			
			
			});
</script>		
		
		<br> <br> <br>
	</div>
	<div id="tab-4" class="tab-content tab-4" style="background: white">
	
	<table class="table table-striped" align="center" style="width:800px">
		<thead>
			<tr>
				<th>가게명</th>
				<th>쿠폰</th>
				<th>사용기한</th>
				<th style="width:100px" class="wcbth" hidden>발급</th>
			</tr>
		</thead>
		<tbody id="fcouponlist">

		</tbody>
	</table>
	
</div>
	
	
	<br>
	<br>
	<div class="foot">
		<p>FoodTruck &nbsp;Copyright @ iei.or.kr &nbsp; All Rights
			Reserved.</p>
	</div>
</body>
</html>
