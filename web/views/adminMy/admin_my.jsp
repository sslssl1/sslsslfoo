<%@ page import="javax.security.auth.callback.ConfirmationCallback" 
  language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="member.model.vo.*,review.model.vo.*,java.util.*,reply.model.vo.*,java.text.*,total.model.vo.*"%>


<%

ArrayList<Total> tlist = new ArrayList<Total>();
if (request.getAttribute("tlist") != null) {
	tlist = (ArrayList<Total>) request.getAttribute("tlist");
}

int count =1;


String business_email[] = null; 
	 if(request.getAttribute("business_email")!=null)
		{
		 business_email= (String[])request.getAttribute("business_email");
		}
 
	//로그인 정보 가져오기
	Manager loginMember=null;
	
	if(session.getAttribute("loginMember")!=null)
	{
		loginMember = (Manager)session.getAttribute("loginMember");

	}
	else 
	{ //로그인 아니라면 index로 강제 소환%>
	<script>
		location.href ="/food/index.jsp";
	</script>
	
	<% }
	
//tab 정보 가져오기
	if (request.getAttribute("tab") != null) {
	
%>
<script> 
var tab = '<%=request.getAttribute("tab")%>';
console.log(tab);

</script>
<% } else { %>


<script>
	var tab = "tab-1";
	console.log(tab);
</script>
<%
	}
%>


<!DOCTYPE html>
<html>
<head>
<title>푸딩 관리자</title>
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

<style>
canvas {
	-moz-user-select: none;
	-webkit-user-select: none;
	-ms-user-select: none;
}
</style>
<script rc="/food/resources/js/analytics.js"></script>
<script src="/food/resources/js/Chart.bundle.js"></script>
<style type="text/css">
@
-webkit-keyframes chartjs-render-animation {
	from {opacity: 0.99
}

to {
	opacity: 1
}

}
@
keyframes chartjs-render-animation {
	from {opacity: 0.99
}

to {
	opacity: 1
}

}
.chartjs-render-monitor {
	-webkit-animation: chartjs-render-animation 0.001s;
	animation: chartjs-render-animation 0.001s;
}
</style>
<script src="/food/resources/js/utils.js"></script>

<script src="/food/resources/js/jquery.min.js"></script>
<script src="/food/resources/js/jquery.scrolly.min.js"></script>
<script src="/food/resources/js/jquery.scrollzer.min.js"></script>
<script src="/food/resources/js/skel.min.js"></script>
<script src="/food/resources/js/util.js"></script>
<script src="/food/resources/js/main.js"></script>
<script type="text/javascript" src="/food/resources/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript">
var startPage =1;
var endPage = 1;
var maxPage = 1;
var currentPage = 1;



function deltImg(num)
{	

	$('#imgSpan'+num).html("");
	imgCount--;
}
var imgNum = 2;
var imgCount = 0;
function addImage()
{

	var newCode = '<span id="imgSpan'+imgNum+'" ><input type="hidden" name="oriImg" value=""><br><br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;<input type="file" accept="image/*" onchange="imgChange('+imgNum+');" name="imgFile'+imgNum+'" >&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;<input type="button" onclick="deltImg('+imgNum+');" value="삭제하기" >&nbsp;<input type="hidden" name="fileTagName" value="imgFile'+imgNum+'" ><br><br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;<img id="img'+imgNum+'" style="width:400px; height:250px;" alt="이미지를 등록하세요" src=""></span>';
	
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


$(function() {
	
	reviewPaging(1);
	
	
	$('[name=delCheck]').change(function(){
		
		if($(this).is(":checked")==true)
			{
			$('#'+$(this).attr('id')+'email').prop("checked",true);
			}
		else if($(this).is(":checked")==false)
			{
			$('#'+$(this).attr('id')+'email').prop("checked",false);
			}
		});
		
		
		
		$('#delCheck').change(function(){
			//사업자 미승인 회원 전체 체크박스 선택
		if(	$('#delCheck').is(":checked")==true )
			{
			
			$('[name=delCheck]').each(function(){
				$(this).prop("checked",true);
			});
			
			$('[name=business_email]').each(function(){
				$(this).prop("checked",true);
			});
			}
			
		else if ( $('#delCheck').is(":checked")==false )
			{
			$('[name=delCheck]').each(function(){
				$(this).prop("checked",false);
			});
			
			$('[name=business_email]').each(function(){
				$(this).prop("checked",false);
			});
			
			}

			
		});
	
	
	<%
	if(business_email !=null){
	for(int i=0;i<business_email.length;i++){%>
	$.ajax({
	type : "post",
	url : "/food/bapprovsendemail",
	data:{"business_email" : '<%=business_email[i]%>'
	},
	success: function(data){
		console.log('email발송 되닝');				
		}				
	});
<%}%>alert('<%=request.getAttribute("mailMessage")%>');

<%}%>
	
	
	if (tab != null) {
		$('.tab-content').removeClass('current');
		$('.tabs li').removeClass('current');
		$('.tabs li a').removeClass('active');
		$('#' + tab).addClass(' current');
		$('[data-tab='+tab+'] a').addClass('active');
		console.log('[data-tab='+tab+']');
		
	}
	
	$('.tabs li').click(function() {
		var tab_cl = $(this).attr('data-tab');

		$('.tabs li').removeClass('current');
		$('.tab-content').removeClass('current');
		$(this).addClass('current');
		$("." + tab_cl).addClass('current');
	});
	

});




function againApprovalbutton(bsid,addr){
	
	var con = confirm ("선택한 회원님을 재승인하시겠습니까?");
	
	if(con==true){
		location.href ="/food/approvbtn?bsid="+bsid+"&addr="+addr;	
		alert("승인완료!");
	}else{
		
	}
	
	
}
 
 


function rejectbutton(bsid){
 
// 여기까지 하다감
   //#
var doublebtn1 ='<input type ="text" name = "rejectReason" id="rejectReason" placeholder="거절 사유를 입력하세요" style="padding-left:0px; font-color:red">';
var doublebtn2 ='<input type="button" onclick ="rejectOKbutton(\''+bsid+'\')" hidden="hidden" value="확인" class="btn btn-outline-primary">&nbsp;'; 
var doublebtn3 ='<input type="button" onclick ="rejectCancelbutton(\''+bsid+'\');"  value="취소" class="btn btn-outline-primary">'; 

$("#rejectbutton" + bsid).html(doublebtn1+doublebtn2+doublebtn3);
//$('#approvalbutton'+bsid).html();



}

function rejectOKbutton(bsid){
	
	
	if($('#rejectReason').val()==""){
		alert("거절사유를 입력해주세요!");
	}else{
		alert("사업자 승인 거절 완료!");
		 location.href = "/food/rejectbtn?bsid="+bsid+"&rejectReason="+$('#rejectReason').val();
	}


}

function rejectCancelbutton(bsid){	
	var btn4= '<input type="button" onclick = "approvalbutton(\'' +bsid+'\',\'bapprov\');" class="btn btn-outline-primary" value="승인">&nbsp;' ;
	var btn5= '<input type="button" onclick = "rejectbutton(\''+bsid+'\');" class="btn btn-outline-primary" value="거절">' ;

	
	$("#rejectbutton" + bsid).html(btn5);
	$('#approvalbutton'+bsid).html(btn4);
	
}
function deletebutton(bsid){
	 var con = confirm("정말 삭제하시겠습니까?                                                        삭제된 회원은 탈퇴처리됩니다.");
	
	 if(con==true){
		 location.href = "/food/deletebtn?bsid="+bsid;
	 }else{
		
	 }
	 
}
function compulWithDrawButton(bsid){
	 var con2 =confirm("정말 강제 탈퇴 시키겠습니까?");
	 if(con2==true){
		 location.href = "/food/comwithbtn?bsid="+bsid;
	 }else{
		
	 }
}

function withdraw(customerId, pageNum)
{
	 var con3 =confirm("정말 강제 탈퇴 시키겠습니까?");
	 if(con3==true){
			
		 $.ajax({
		 	url : "/food/cWithdraw",
		 	data : {page : pageNum,
		 		cId : customerId},
		 	type : "get",
		 	dataType: "json",
		 	  success: function(data){
		 console.log("dd");
		 	startPage =data.startPage;
		 		endPage =data.endPage;
		 		maxPage = data.maxPage;
		 		currentPage = data.currentPage;
		 		
		 		  var jsonStr = JSON.stringify(data);
		 		var json = JSON.parse(jsonStr);
		 		//관리자 모든 회원보기
		 		
		 		var main = null;
		 		for(var i in json.list){
		 			console.log(json.list[i].customerId);
		 		main+='<tr><th>'+json.list[i].customerId+'</th>';
		 		main+='<th>'+decodeURIComponent(json.list[i].customerName)+'</th>';
		 		main+='<th>'+json.list[i].customerEmail+'</th>';
		 		main+='<th style="float:right;"><button type="button" onclick="withdraw(\''+json.list[i].customerId+'\');" class="btn btn-outline-primary" >강퇴</button></th></tr>';
		 		}
		 		
		 		$('#tab1_main').html(main);
		 		//페이징 처리
		 		var paging ='<th colspan="5">';
		 		if(currentPage<=1)
		 			{
		 			paging+='[맨처음]&nbsp;';
		 			}
		 		else {
		 			
		 			paging+='<a href="javascript:reviewPaging(1);">[맨처음]</a>';
		 		}
		 		if ((currentPage - 10) < startPage && (currentPage - 10) >= 1) {
		 			paging+='<a href="javascript:reviewPaging('+(startPage-1)+');">[prev]</a>';
		 		} else {
		 			paging+='[prev]&nbsp;';
		 		}
		 		for (var p = startPage; p <= endPage; p++) {
		 			if (p == currentPage) {
		 				paging+='<font color="red" size="4"><b>['+p+']</b></font>';
		 		} else {
		 			paging+='<a href="javascript:reviewPaging('+p+');">'+p+'</a>';
		 		}}

		 		if ((currentPage + 10) > endPage && (currentPage + 10) <= maxPage) {
		 			paging+=
		 	'<a href="javascript:reviewPaging('+(endPage + 1)+')">[next]</a>';
		 	
		 		} else {paging+='[next]&nbsp';}
		 		if (currentPage >= maxPage) {
		 			paging+='[맨끝]&nbsp';
		 		} else {
		 			paging+='<a href="javascript:reviewPaging('+maxPage+')">[맨끝]</a>';
		 		}
		 		paging+='</th>';
		 	
		 		
		 	
		 		$('#t_page').html(paging);

		 	},
		 	error: function(jqXHR, textStatus, errorThrown) { 
		         console.log(jqXHR.responseText); 
		     } 	
		 });
	 }else{
		 
		 location.href = "/food/views/adminMy/admin_my.jsp";
	
	

	 }
}
function reviewPaging(pageNum){
	$.ajax({
		url : "/food/adminAllSearchList",
		data : {page : pageNum},
		type : "get",
		dataType: "json",
		  success: function(data){
	
			
	
			  
			  
		startPage =data.startPage;
			endPage =data.endPage;
			maxPage = data.maxPage;
			currentPage = data.currentPage;
		
			  var jsonStr = JSON.stringify(data);
			var json = JSON.parse(jsonStr);
			//관리자 모든 회원보기
			
			var main = null;
			for(var i in json.list){
				console.log(json.list[i].customerId);
			main+='<tr><th>'+json.list[i].customerId+'</th>';
			main+='<th>'+decodeURIComponent(json.list[i].customerName)+'</th>';
			main+='<th>'+json.list[i].customerEmail+'</th>';
			main+='<th style="float:right;"><button type="button" onclick="withdraw(\''+json.list[i].customerId+'\');" class="btn btn-outline-primary" >강퇴</button></th></tr>';
			}
			
			$('#tab1_main').html(main);
			//페이징 처리
			var paging ='<th colspan="5">';
			if(currentPage<=1)
				{
				paging+='[맨처음]&nbsp;';
				}
			else {
				
				paging+='<a href="javascript:reviewPaging(1);">[맨처음]</a>';
				
			}
			if ((currentPage - 10) < startPage && (currentPage - 10) >= 1) {
				paging+='<a href="javascript:reviewPaging('+(startPage-1)+');">[prev]</a>';
			} else {
				paging+='[prev]&nbsp;';
			}
			for (var p = startPage; p <= endPage; p++) {
				if (p == currentPage) {
					paging+='<font color="red" size="4"><b>['+p+']</b></font>';
			} else {
				paging+='<a href="javascript:reviewPaging('+p+');">'+p+'</a>';
			}}
	
			if ((currentPage + 10) > endPage && (currentPage + 10) <= maxPage) {
				paging+=
		'<a href="javascript:reviewPaging('+(endPage + 1)+')">[next]</a>';
		
			} else {paging+='[next]&nbsp';}
			if (currentPage >= maxPage) {
				paging+='[맨끝]&nbsp';
			} else {
				paging+='<a href="javascript:reviewPaging('+maxPage+')">[맨끝]</a>';
			}
			paging+='</th>';
		
			
		
			$('#t_page').html(paging);

		},
		error: function(jqXHR, textStatus, errorThrown) { 
            console.log(jqXHR.responseText); 
        } 	
	});
}

function adminSearchName(customer_seType, customer_seValue) {
	console.log($('#customer_seValue').val());
	console.log( $('[name=customer_seType]').val() );
	$.ajax({
		url : "/food/aSearchName",
		data : {cSeValue : $('#customer_seValue').val(),
			cSeType :$('[name=customer_seType]').val() },
		type : "get",
		dataType: "json",
		  success: function(data){
		startPage =data.startPage;
			endPage =data.endPage;
			maxPage = data.maxPage;
			currentPage = data.currentPage;
			
			  var jsonStr = JSON.stringify(data);
			var json = JSON.parse(jsonStr);
			//관리자 모든 회원보기
			
			var main = null;
			for(var i in json.list){
				console.log(json.list[i].customerId);
			main+='<tr><th>'+json.list[i].customerId+'</th>';
			main+='<th>'+decodeURIComponent(json.list[i].customerName)+'</th>';
			main+='<th>'+json.list[i].customerEmail+'</th>';
			main+='<th style="float:right;"><button type="button" onclick="withdraw(\''+json.list[i].customerId+'\');" class="btn btn-outline-primary" >강퇴</button></th></tr>';
			}
			
			$('#tab1_main').html(main);
			//페이징 처리
			var paging ='<th colspan="5">';
			if(currentPage<=1)
				{
				paging+='[맨처음]&nbsp;';
				}
			else {
				
				paging+='<a href="javascript:reviewPaging(1);">[맨처음]</a>';
			}
			if ((currentPage - 10) < startPage && (currentPage - 10) >= 1) {
				paging+='<a href="javascript:reviewPaging('+(startPage-1)+');">[prev]</a>';
			} else {
				paging+='[prev]&nbsp;';
			}
			for (var p = startPage; p <= endPage; p++) {
				if (p == currentPage) {
					paging+='<font color="red" size="4"><b>['+p+']</b></font>';
			} else {
				paging+='<a href="javascript:reviewPaging('+p+');">'+p+'</a>';
			}}
	
			if ((currentPage + 10) > endPage && (currentPage + 10) <= maxPage) {
				paging+=
		'<a href="javascript:reviewPaging('+(endPage + 1)+')">[next]</a>';
		
			} else {paging+='[next]&nbsp';}
			if (currentPage >= maxPage) {
				paging+='[맨끝]&nbsp';
			} else {
				paging+='<a href="javascript:reviewPaging('+maxPage+')">[맨끝]</a>';
			}
			paging+='</th>';
		
			
		
			$('#t_page').html(paging);

		},
		error: function(jqXHR, textStatus, errorThrown) { 
            console.log(jqXHR.responseText); 
        } 	
	});
}

function check() {
	var start = $('[name=fes_start]').val();
	var end = $('[name=fes_end]').val();
	var file = $('[name=fes_image]').val();
	//title이 비어있으면
	if(fes_form.fes_title.value==""){
		alert("제목을 입력하세요");
		fes_form.fes_title.focus();
		return false;	
	//주소가 비어있으면	
	}else if(fes_form.fes_addr.value==""){
		alert("주소를 입력하세요");
		fes_form.fes_addr.focus();
		return false;
	//시작날짜가 끝 날짜보다 클 경우	
	}else if(Date.parse(start) > Date.parse(end)){
		alert("기간을 정확하게 입력하세요");
		fes_form.fes_end.focus();
		return false;
	//날짜가 비어있으면	
	}else if(!Date.parse(start) || !Date.parse(end)){
		alert("기간을 입력하세요");
		fes_form.fes_start.focus();
		return false;
	//주최측이 비어있으면	
	}else if(fes_form.fes_host.value==""){
		alert("주최측 정보를 입력하세요");
		fes_form.fes_host.focus();
		return false;
	//연락처가 비어있으면	
	}else if(fes_form.fes_phone.value==""){
		alert("연락처를 입력하세요");
		fes_form.fes_phone.focus();
		return false;
	}else if(!file){
		var ok = confirm("등록된 사진파일이 없습니다. 그대로 진행하시겠습니까?");
		
		if(오케이){
			return true;
		}else{
			fes_form.fes_image.focus();
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
					<li><a href="/food/fes_show">축제검색</a></li>
					</ul>
				<% if(loginMember==null){ %>
					<ul class="nav navbar-nav navbar-right">
						<li><a href="/food/views/logIn/logIn.jsp"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
						<li><a href="/food/views/signUp/select_signUp.jsp"><span class="glyphicon glyphicon-edit"></span> 회원가입</a></li>
					</ul>
					<% } else if(loginMember instanceof Manager){%>
					<ul class="nav navbar-nav navbar-right">
					<li><a><span class="glyphicon glyphicon-user"></span> <%=((Manager)loginMember).getManager_Id() %>님</a></li>
					<li class="active"><a href="/food/views/adminMy/admin_my.jsp"><img alt="회원관리" src="/food/resources/images/people.png" width="15px" height="15px">회원관리</a></li>
					<li><a href="/food/memberlogout"><span class="glyphicon glyphicon-share"></span>로그아웃</a></li>
					
					</ul>
					<% } %>

				</div>
			
		</nav>
	</div>
	<div id="header" style="top: 50px">
		<nav id="nav">
			<ul id="ul" class="tabs">
				<li class="current" data-tab="tab-1"><a href="/food/views/adminMy/admin_my.jsp" class="active"><span class="icon fa-edit">일반회원</span></a></li>
				<li data-tab="tab-2"><a href="/food/blist"><span class="icon fa-edit">사업자 회원 </span></a></li>
				<li data-tab="tab-3"><a href="/food/bapprov"><span class="icon fa-edit">사업자 미승인회원</span></a></li>
				<li data-tab="tab-5"><a href="/food/breject"><span class="icon fa-edit">사업자 거절회원</span></a></li>
				<li data-tab="tab-4"><a href="#festival"><span class="icon fa-edit">축제정보등록</span></a></li>
				<li data-tab="tab-6"><a href="/food/mtotal"><span class="icon fa-edit">통계</span></a></li>
			</ul>
		</nav>
	</div>
	
	
	
	<div id="tab-1" class="tab-content tab-1">
		<div  style="margin-left:15%; margin-right:15%; margin-top:3%; min-width:300px;">
				<!-- tab-1 시작 -->
  	
	<div>
	<ul class="pagination" >
	<li class="page-item"><h2>일반 회원 관리</h2>
	<div style="border-bottom: 1px solid #bdbdbd; width: 1145px;"></div>
				<br>
	</li>
	</ul></div>
	<form  style="float:right;" action="" method="post">
	<select name="customer_seType" >
			<option value="customer_id" selected> 아이디</option>
			<option value="customer_name" > 이름</option>
			<option value="customer_email"> 이메일</option>
		</select>

<input type="text" id="customer_seValue" name="customer_seValue">&nbsp;<input type="button" value="검색" style="background:green; border:green; color:white;" onclick ="javascript: adminSearchName(customer_seType, customer_seValue);"></form>
	
		 <table class="table table-striped">
    <thead>
      <tr>
        <th style="width:10%; min-width:100px;" >아이디</th>
      	<th style="width:10%; min-width:100px;">이름</th>
      	<th >이메일</th>
       	<th style="width:8%"> </th>
      </tr>
    </thead>
    
    <tbody id="tab1_main">
	
					</tbody>
  <tbody id="t_page">
  
				</tbody>
  </table>
		


</div>
 
 <!-- tab-1끝 -->
</div>

<div id="tab-2" class="tab-content tab-2">
		<div  style="margin-left:15%; margin-right:15%; margin-top:3%; min-width:300px;">
			<!-- tab-2 시작 -->
<div>
	<ul class="pagination" >
	<li class="page-item"><h2>사업자 회원 관리</h2>
<div style="border-bottom: 1px solid #bdbdbd; width: 1145px;"></div>
				<br>
	</li>
	
	</ul></div>
	<form  style="float:right;" action="/food/searchblist" method="post">
	<select name="seType" >
			<option value="business_id" selected> 아이디</option>
			<option value="business_name" > 이름</option>
			<option value="business_email"> 이메일</option>
		</select>

<input type="text" name="seValue">&nbsp;<input type="submit" value="검색" style="background:green; border:green; color:white;"></form>
	
	
	
	
		 <table class="table table-striped">
    <thead>
      <tr>
        <th style="width:10%; min-width:100px;" >아이디</th>
      	<th style="width:10%; min-width:100px;">이름</th>
      	<th >이메일</th>
       	<th style="width:8%"> </th>

      </tr>
    </thead>
    
    <tbody style="text-align:center;">
    <%
     int listCount=1;
	 int startPage=1;
	 int endPage=1;
	 int maxPage=1;
	 int currentPage=1;
	
	if(request.getAttribute("list") != null){
		ArrayList<Business> list =(ArrayList<Business>)request.getAttribute("list");  
		 listCount = ((Integer)request.getAttribute("listCount")).intValue();
		startPage = ((Integer)request.getAttribute("startPage")).intValue();
		 endPage = ((Integer)request.getAttribute("endPage")).intValue();
		 maxPage = ((Integer)request.getAttribute("maxPage")).intValue();			
		currentPage = ((Integer)request.getAttribute("currentPage")).intValue();	
		
		for(Business business:list) {%>
		<tr>
			<th><%=business.getBusiness_Id()%></th>
			<th><%=business.getBusiness_Name()%></th>
			<th><%=business.getBusiness_Email()%></th>
			<th style="float:right;"><input type="button" onclick = "compulWithDrawButton('<%=business.getBusiness_Id() %>');" class="btn btn-outline-primary" value="강퇴"></th>
			
		</tr>
		
		<% }}else{%>
		<tr style = "background-color: white;">
		<td align="center" colspan="4" style="color:gray"><%=request.getAttribute("noSearchArpproval") %></td>
		</tr>
		<% } %>


    </tbody>
  </table>
 
		<hr style="margin-top:30px;">


			<div style="text-align:center; ">
<% if(currentPage <= 1){ %>
	[맨처음]&nbsp;
<% }else{ %>
	<a href="/food/blist?currentPage=1">[맨처음]</a>
<% } %>
<% if((currentPage - 10) < startPage 
		&& (currentPage - 10) >= 1){ %>
	<a href="/food/blist?currentPage=<%= startPage - 1 %>">[prev]</a>	
<% }else{ %>
	[prev]&nbsp;
<% } %>
<!-- 현재 페이지가 포함된 그룹의 페이지 숫자 출력 -->
<% for(int p = startPage; p <= endPage; p++){ 
		if(p == currentPage){
%>
	<font color="red" size="4"><b>[<%= p %>]</b></font>
	<% }else{ %>
	<a href="/food/blist?currentPage=<%= p %>"><%= p %></a>
<% }} %>

<% if((currentPage + 10) > endPage 
		&& (currentPage + 10) <= maxPage){ %>
	<a href="/food/blist?currentPage=<%= endPage + 1 %>">[next]</a>	
<% }else{ %>
	[next]&nbsp;
<% } %>

<% if(currentPage >= maxPage){ %>
[맨끝]&nbsp;
<% }else{ %>
	<a  href="/food/blist?currentPage=<%= maxPage %>">[맨끝]</a>
<% } %>

<br style="margin-top:20px;">

</div>

</div>
  
 <!-- tab-2끝 -->
</div>



<div id="tab-3" class="tab-content tab-3">
		<div  style="margin-left:15%; margin-right:15%; margin-top:3%; min-width:300px;">
			<!-- tab-3 시작 -->


<div>
	<ul class="pagination" >
	<li class="page-item"><h2>사업자 미승인 회원</h2>
	<div style="border-bottom: 1px solid #bdbdbd; width: 1145px;"></div>
				<br>
	</li>
	
	</ul></div>
	<form  style="float:right;" action="/food/searchapproval" method="post">
	<select name="seType" style="height:21.5px;">
			<option value="business_id" selected> 아이디</option>
			<option value="business_name" > 이름</option>
			<option value="business_email"> 이메일</option>
		</select>

<input type="text" name="seValue" style="vertical-align:top; ">&nbsp;<input type="submit" value="검색" style="background:green; vertical-align:top; border:green; color:white; height:21.5px; text-align:center "></form>
	
	
	
	<form method="post" action="/food/approvbtn" id ="app">
		 <table class="table table-striped">
    <thead>
      <tr>
      <th style="width: 15%;"><input id="delCheck" type="checkbox"
						name="delCheckAll" value=null  >전체선택</th>
        <th style="width:10%; min-width:100px;" >아이디</th>
      	<th style="width:10%; min-width:100px;">이름</th>
      	<th >이메일</th>
      	<th id="th_reject" style="width:5%;"></th>
      </tr>
    </thead>
    <tbody style="text-align:center;">
    <%
 
	
	if(request.getAttribute("list") != null){
		ArrayList<Business> list =(ArrayList<Business>)request.getAttribute("list");  
		 listCount = ((Integer)request.getAttribute("listCount")).intValue();
		startPage = ((Integer)request.getAttribute("startPage")).intValue();
		 endPage = ((Integer)request.getAttribute("endPage")).intValue();
		 maxPage = ((Integer)request.getAttribute("maxPage")).intValue();			
		currentPage = ((Integer)request.getAttribute("currentPage")).intValue();	
		
		for(Business business:list) {
			
		%>
		
		
		<tr>
		<input id="ID<%=count %>email" type="checkbox" name="business_email" value="<%=business.getBusiness_Email()%>" hidden = "hidden" >
			<th style="width: 15%;">
			<input id="ID<%=count %>" type="checkbox"	name="delCheck" value="<%=business.getBusiness_Id()%>"></th>
			<th><%=business.getBusiness_Id()%></th>
			<th><%=business.getBusiness_Name()%></th>
			<th><%=business.getBusiness_Email()%></th>
			<!-- <th><p id="approvalbutton<%=business.getBusiness_Id()%>"><input type="button"  onclick = "approvalbutton('<%=business.getBusiness_Id() %>' ,'bapprov');" class="btn btn-outline-primary" value="승인"></p></th> -->
			<th ><p id="rejectbutton<%=business.getBusiness_Id()%>"><input type="button"  onclick = "rejectbutton('<%=business.getBusiness_Id() %>');" class="btn btn-outline-primary" value="거절" ></p>
			</th>
			<%count++; %>
		</tr>		
		
		<%}%>
		<%}else{%>
		<tr style = "background-color: white; ">
		<th align="center" colspan="5" style="color:gray"><%=request.getAttribute("noSearchArpproval") %></th>
		</tr>
		<% } 

		%>

    </tbody>
  </table>
  
 <hr style="margin-top:30px;">
<% if(request.getAttribute("list") != null &&  ((ArrayList<Business>)request.getAttribute("list")).size() >0 ) {       %>
	<input type ="submit" value="승인하기" id ="approvabtn">
	<% } %>
 <%if(request.getAttribute("apvbtnMessage")!= null ){ %>
  <script> alert('<%=request.getAttribute("apvbtnMessage")%>');</script>
  <% request.removeAttribute("apvbtnMessage"); }%>
		
 </form>

<div style="text-align:center; ">
<% if(currentPage <= 1){ %>
	[맨처음]&nbsp;
<% }else{ %>
	<a href="/food/bapprov?currentPage=1">[맨처음]</a>
<% } %>
<% if((currentPage - 10) < startPage 
		&& (currentPage - 10) >= 1){ %>
	<a href="/food/bapprov?currentPage=<%= startPage - 1 %>">[prev]</a>	
<% }else{ %>
	[prev]&nbsp;
<% } %>
<!-- 현재 페이지가 포함된 그룹의 페이지 숫자 출력 -->
<% for(int p = startPage; p <= endPage; p++){ 
		if(p == currentPage){
%>
	<font color="red" size="4"><b>[<%= p %>]</b></font>
	<% }else{ %>
	<a href="/food/bapprov?currentPage=<%= p %>"><%= p %></a>
<% }} %>

<% if((currentPage + 10) > endPage 
		&& (currentPage + 10) <= maxPage){ %>
	<a href="/food/bapprov?currentPage=<%= endPage + 1 %>">[next]</a>	
<% }else{ %>
	[next]&nbsp;
<% } %>

<% if(currentPage >= maxPage){ %>
[맨끝]&nbsp;
<% }else{ %>
	<a  href="/food/bapprov?currentPage=<%= maxPage %>">[맨끝]</a>
<% } %>

<br style="margin-top:20px;">

</div>

</div>
<!--  tab-3끝 -->
</div>

<div id="tab-4" class="tab-content tab-4">
<h2>&emsp;축제 정보 등록</h2>
		<hr style="height: 1px; background: black; width: 850px; float: left">
		<br> <br>

		<form action="/food/fes_update" method="post"
			enctype="multipart/form-data">
			
			<!-- 축제 이름 -->
			<strong>축제 이름 </strong>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; <input
				name="upfes_title" value=""
				type="text" style="width: 200px; color: gray;" required="required"><font
				style="color: gray; font-size: 10pt">&emsp;한글, 영문, 숫자
				혼용가능(한글기준 10글자)</font> <br> <br>
			<hr style="width: 850px; float: left">

			<!-- 축제 장소 -->
			<br> <br> <strong>축제 장소</strong>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&nbsp;&nbsp;
			<input name="upfes_addr" value=""
				type="text" style="width: 300px; color: gray; margin-left: -4px;" required="required"><font
				style="color: gray; font-size: 10pt">&emsp;한글, 영문, 숫자
				혼용가능(한글기준 20글자)</font> <br> <br>
			<hr style="width: 850px; float: left">

			<!-- 축제 기간 -->



			<br> <br> <strong>축제 기간 </strong>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
			<input type="date" name="upfes_start"
				value="2018-01-01"> ~ <input
				type="date" name="upfes_end"
				value="2018-12-31"> <font
				style="color: gray; font-size: 10pt">달력을 잘보시고 체크해 주세요</font> <br>
			<br>
			<hr style="width: 850px; float: left">

			<!-- 주최측 -->
			<br> <br> <strong>주최자(기관)명</strong>&emsp;&emsp;&emsp;&emsp;
			<input name="upfes_host" type="text"
				style="width: 200px; color: gray; margin-left: -5px;"
				value="" required="required">&emsp;<font
				style="color: gray; font-size: 10pt"></font> <br> <br>
			<hr style="width: 850px; float: left">
			<br> <br>

			<!-- 축제 연락처 -->
			<strong>축제 고객센터</strong>&emsp;&emsp;&emsp;&emsp; <input name="fes_phone" type="text"
				style="width: 200px; color: gray; margin-left: 2px;"
				placeholder="ex) 02-123-4567">&emsp;<font
				style="color: gray; font-size: 10pt">" - " 생략가능</font> <br> <br>
			<hr style="width: 850px; float: left">
			<br> <br>

			<!-- 썸네일 사진 등록 -->

			<strong style="float: left;">썸네일 사진 </strong>&emsp;&emsp;&emsp;&emsp;&emsp;

			<input type="file" accept="image/*" name="imgFile0"
				onchange="imgChange(0);"> <input type="hidden"
				name="upfes_image_ori"
				value=""> <br>
			<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; <img
				id="img0" style="width: 400px; height: 250px;" alt="이미지를 등록하세요"
				src="/food/resources/images/festival/">
			<br><br><hr style="width: 850px; float: left"><br><br>

			<!-- 메인사진 등록 -->
	<strong style="float: left;">메인 사진 </strong>&emsp;&emsp;&emsp;&emsp;&emsp;
			<input type="file" accept="image/*" name="imgFile1" onchange="imgChange(1);"> 
			<input type="hidden" name="upfes_image_main" value=""> <br>
			<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; 
			<img id="img1" style="width: 400px; height: 250px;" alt="이미지를 등록하세요"
				src="/food/resources/images/festival/">
			<br><br><hr style="width: 850px; float: left"><br><br>


			<!-- 축제사진 등록 -->

			<strong style="float: left;">축제 사진 </strong> <br> <br>
			<input type="button" value="사진 추가" onclick="addImage() ">
			&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;

			<div id="addImageDiv">
				<%-- <%for() {%>
				<script>
			console.log("<%=s%>");
			var newCode = '<span id="imgSpan'+imgNum+'" ><input type="hidden" name="oriImg" value="<%=s%>"><br><br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;<input type="file" accept="image/*" onchange="imgChange('+imgNum+');" name="imgFile'+imgNum+'" >&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;<input type="button" onclick="deltImg('+imgNum+');" value="삭제하기" >&nbsp;<input type="hidden" name="fileTagName" value="imgFile'+imgNum+'" ><br><br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;<img id="img'+imgNum+'" style="width:400px; height:250px;" alt="이미지를 등록하세요" src="/food/resources/images/festival/"></span>';
					document.write(newCode);
					imgNum++;
					imgCount++;
				</script>
				<%
					}
				%> --%>

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
				<input type="button" value="수정하기" onclick="trySubmitBtn(); return false;">
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

<!-- tab-5시작 -->		
<div id="tab-5" class="tab-content tab-5">
<div  style="margin-left:15%; margin-right:15%; margin-top:3%; min-width:300px;">
			
<div>
	<ul class="pagination" >
	<li class="page-item"><h2>사업자 승인거절 회원</h2>
	
	</li>
	
	</ul></div>
	
	
	
	
	
		 <table class="table table-striped">
    <thead>
      <tr>
        <th style="width:10%; min-width:100px;" >아이디</th>
      	<th style="width:10%; min-width:100px;">이름</th>
      	<th >이메일</th>
      	<th></th>
       	
      </tr>
    </thead>
    
    <tbody style="text-align:center;">
    <%
   
	
	if(request.getAttribute("list") != null){
		ArrayList<Business> list =(ArrayList<Business>)request.getAttribute("list");  
		 listCount = ((Integer)request.getAttribute("listCount")).intValue();
		startPage = ((Integer)request.getAttribute("startPage")).intValue();
		 endPage = ((Integer)request.getAttribute("endPage")).intValue();
		 maxPage = ((Integer)request.getAttribute("maxPage")).intValue();			
		currentPage = ((Integer)request.getAttribute("currentPage")).intValue();	
		
		for(Business business:list) {%>
		<tr>
		
			<th><%=business.getBusiness_Id()%></th>
			<th><%=business.getBusiness_Name()%></th>
			<th><%=business.getBusiness_Email()%></th>
			<th style="float:right;"><input type="button" onclick = "againApprovalbutton('<%=business.getBusiness_Id() %>','breject')" class="btn btn-outline-primary" value="재승인"></th>
			<th style="float:right;"><input type="button" onclick = "deletebutton('<%=business.getBusiness_Id() %>')" class="btn btn-outline-primary" value="삭제"></th>
		</tr>
		<% }}else{%>
		<tr style = "background-color: white;">
		<td align="center" colspan="4"><%=request.getAttribute("noSearchArpproval") %></td>
		</tr>
		<% } %>

    


    </tbody>
  </table>
 
		<hr style="margin-top:30px;">


			<div style="text-align:center; ">
<% if(currentPage <= 1){ %>
	[맨처음]&nbsp;
<% }else{ %>
	<a href="/food/breject?currentPage=1">[맨처음]</a>
<% } %>
<% if((currentPage - 10) < startPage 
		&& (currentPage - 10) >= 1){ %>
	<a href="/food/breject?currentPage=<%= startPage - 1 %>">[prev]</a>	
<% }else{ %>
	[prev]&nbsp;
<% } %>
<!-- 현재 페이지가 포함된 그룹의 페이지 숫자 출력 -->
<% for(int p = startPage; p <= endPage; p++){ 
		if(p == currentPage){
%>
	<font color="red" size="4"><b>[<%= p %>]</b></font>
	<% }else{ %>
	<a href="/food/breject?currentPage=<%= p %>"><%= p %></a>
<% }} %>

<% if((currentPage + 10) > endPage 
		&& (currentPage + 10) <= maxPage){ %>
	<a href="/food/breject?currentPage=<%= endPage + 1 %>">[next]</a>	
<% }else{ %>
	[next]&nbsp;
<% } %>

<% if(currentPage >= maxPage){ %>
[맨끝]&nbsp;
<% }else{ %>
	<a  href="/food/breject?currentPage=<%= maxPage %>">[맨끝]</a>
<% } %>

<br style="margin-top:20px;">

</div>

</div>
</div>


<div id="tab-6" class="tab-content tab-6">
<div style="width:75%;"><div class="chartjs-size-monitor" style="position: absolute; left: 0px; top: 0px; right: 0px; bottom: 0px; overflow: hidden; pointer-events: none; visibility: hidden; z-index: -1;"><div class="chartjs-size-monitor-expand" style="position:absolute;left:0;top:0;right:0;bottom:0;overflow:hidden;pointer-events:none;visibility:hidden;z-index:-1;"><div style="position:absolute;width:1000000px;height:1000000px;left:0;top:0"></div></div><div class="chartjs-size-monitor-shrink" style="position:absolute;left:0;top:0;right:0;bottom:0;overflow:hidden;pointer-events:none;visibility:hidden;z-index:-1;"><div style="position:absolute;width:200%;height:200%;left:0; top:0"></div></div></div>
		<canvas id="canvas" width="300" height="200" class="chartjs-render-monitor" style="display: block; width: 483px; height: 241px;"></canvas>
	</div>
	<br>
	<br>
	<script>
	<%Calendar calendar = Calendar.getInstance();
	String str[]=new String[8];
	for(int i=1; i<=7; i++){
		if(i>1){
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		}else{
			calendar.add(Calendar.DAY_OF_MONTH, +0);
		}
		Date date = calendar.getTime();
		str[i]=new SimpleDateFormat("yyyy.MM.dd").format(date);
	}
	%>


		var config = {
			type: 'bar',
			data: {
				
				labels: [
					<%for(int i=7; i>=1; i--){%>
					'<%=str[i]%>',
					<%}%>
					],
				
				datasets: [{
					label: '방문자 데이터',
					backgroundColor: window.chartColors.red,
					borderColor: window.chartColors.red,
					data: [
						
						<%if(request.getAttribute("tlist") != null){
						for(int i=1; i<=7; i++){
							System.out.print("나와라"+tlist.get(i).getT_total());
						%>
							<%=tlist.get(i).getT_total()%>,
						<%}}else{}%>
						
					],
					fill: false,
				}]
			},
			options: {
				responsive: true,
				title: {
					display: true,
					text: '방문자 그래프'
				},
				tooltips: {
					mode: 'index',
					intersect: false,
				},
				hover: {
					mode: 'nearest',
					intersect: true
				},
				scales: {
					xAxes: [{
						display: true,
						scaleLabel: {
							display: true,
							labelString: 'Days'
						}
					}],
					yAxes: [{
						display: true,
						scaleLabel: {
							display: true,
							labelString: 'Value'
						},
						ticks: {
							min: 0,
							max: 1000,
							stepSize: 100
						}
					}]
				}
			}
		};

		window.onload = function() {
			var ctx = document.getElementById('canvas').getContext('2d');
			window.myLine = new Chart(ctx, config);
		};

	</script>
	</div>


	<div class="foot" style="bottom: 0; position: absolute;">FoodTruck &nbsp;Copyright @ iei.or.kr &nbsp; All Rights Reserved.</div>
</body>
</html>