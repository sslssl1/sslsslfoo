<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="review.model.vo.*,foodtruck.model.vo.*, member.model.vo.*, reply.model.vo.*, java.util.*"%>
<%
	Foodtruck truck = null;
	ArrayList<Menu> mlist = new ArrayList<Menu>();
	if (request.getAttribute("truck") != null) {
		truck = (Foodtruck) request.getAttribute("truck");
	}
	//로그인 정보 가져오기
	Member loginMember = null;
	if (session.getAttribute("loginMember") instanceof Business) {
		loginMember = (Business) session.getAttribute("loginMember");
	} else if (session.getAttribute("loginMember") instanceof Customer) {
		loginMember = (Customer) session.getAttribute("loginMember");
	} else if (session.getAttribute("loginMember") instanceof Manager) {
		loginMember = (Manager) session.getAttribute("loginMember");
	}
%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<title>푸딩 리뷰</title>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta charset="utf-8">
<!-- meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0"/ -->
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<!-- Bootstrap -->
<link href="/food/resources/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css" />
<!-- jQuery (부트스트랩의 자바스크립트 플러그인을 위해 필요한) -->
<link rel="stylesheet" href="/food/resources/css/foot.css">
<link rel="stylesheet" href="/food/resources/css/modInfo.css">
<link rel="stylesheet" href="/food/resources/css/review.css">
<link rel="stylesheet" href="/food/resources/css/replay.css">
<link rel="stylesheet" href="/food/resources/css/bootstrap.min.css">
<link rel="stylesheet" href="/food/resources/css/top-menu.css">
<link rel="stylesheet" href="/food/resources/css/foot.css">
<link rel="stylesheet" href="/food/resources/css/star.css">
<script type="text/javascript" src="/food/editor/js/HuskyEZCreator.js" charset="utf-8"></script>
<script type="text/javascript" src="/food/resources/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="/food/resources/js/star.js"></script>
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





<style type="text/css">

/* 메인사진 */
#mainPhoto {
	background-image: url("/food/resources/images/taco.jpg");
	background-size: cover;
	background-position: center;
	width: 100%;
	height: 230px;
}
#mt-h3 {
	text-align:center;
	background: black;
	width: 1200px;
	color: white;;
	margin-top: 10px;
	font-size: 15pt;
}

.col-sm-4 {
	left-padding: 0px;
	right-padding: 0px;
}
</style>
<%
	DetailReview review = null;
	if (request.getAttribute("review") != null) {
		review = (DetailReview) request.getAttribute("review");
	} else {
%>
<script>
	alert("잘못된 접근입니다.");
	location.href = "/food/index.jsp";
</script>
<%
	}
%>
<script type="text/javascript">


//대댓글 삭제하기

function deleteUnderComment(replyuNo) {
	console.log("replyuNo : " + replyuNo);
	
	$.ajax({
		
		url : "/food/underreplydel",
		data : {
		underReplyNo : replyuNo
		},
		type : "get",
		success : function(data){
			location.reload();
		},
		error : function(jqXHR, textStatus, errorThroen) {
	        console.log(jqXHR.responseText);
	    }
	});
}

//댓글 삭제하기

function deleteComment(replyNumber) {
	$.ajax({
		
		url : "/food/replydel",
		data : {
		replyNo : replyNumber
		},
		type : "get",
		success : function(data){
			location.reload();
		},
		error : function(jqXHR, textStatus, errorThroen) {
	        console.log(jqXHR.responseText);
	    }
	});
}

//댓글 수정하기 누르면 댓글 수정하는 창 뜨기
function modifiedComment(replyNumber,content) {
     //댓글달기 창을 없앤다.
     $("#commentEditor").remove();
	 var parentElement = $('#addBtn'+replyNumber).parent().parent();
     //부모의 하단에 댓글달기 창을 삽입
     /* var parentElementValue = $('#d'+replyNumber).text(); */
    /* var parentElementValue = document.getElementById('d'+replyNumber).innerHTML;
     console.log(parentElementValue); */
         var commentEditor = '<tr id="commentEditor">'+
                                 '<td style="width:1%"> </td>'+
                                 '<td>'+
                                     '<span class="form-inline" role="form">'+
                                             '<textarea id="ModifiedcommentChildText" name="commentChildText" class="form-control" style="width:98%" rows="4">'+content+'</textarea>'+
                                             '<button id="commentParentSubmit" class="btn btn-default" align="left" onclick="modifiedReply(\''+replyNumber+'\');">댓글수정하기</button>'+
                                             '</span>'+
                                 '</td>'+
                             '</tr>';
         parentElement.after(commentEditor); 
        /*  $("#ModifiedcommentChildText").val(parentElementValue); */
}


//대댓글 수정하기 누르면 대댓글 수정하는 창 뜨기
function modifiedUnderComment(replyNumber,content) {
//	alert(replyNumber); 
   //댓글달기 창을 없앤다.
   $("#commentEditor").remove();
	 var parentElement = $('#undermodifiedBtn'+replyNumber).parent().parent();
	//alert(parentElement.text());
   //부모의 하단에 댓글달기 창을 삽입
   /* var parentElementValue = $('#d'+replyNumber).text(); */
  /* var parentElementValue = document.getElementById('d'+replyNumber).innerHTML;
   console.log(parentElementValue); */
       var commentEditor = '<tr id="commentEditor">'+
                               '<td style="width:1%"> </td>'+
                               '<td>'+
                                   '<span class="form-inline" role="form">'+
                                           '<textarea id="ModifiedUndercommentChildText" name="commentChildText" class="form-control" style="width:98%" rows="4">'+content+'</textarea>'+
                                           '<button id="commentParentSubmit" class="btn btn-default" align="left" onclick="modifiedUnderReply(\''+replyNumber+'\');">댓글수정하기</button>'+
                                           '</span>'+
                               '</td>'+
                           '</tr>';
       parentElement.after(commentEditor); 
      /*  $("#ModifiedcommentChildText").val(parentElementValue); */
}




//댓글 수정하기

function modifiedReply(replyNumber) {
	
	$.ajax({
	
		url : "/food//replymod",
		data : {
			content : $('#ModifiedcommentChildText').val(),
			replyNo : replyNumber 
			},
		type : "get",
		success : function(data){
			location.reload();
		},
		error : function(jqXHR, textStatus, errorThroen) {
	        console.log(jqXHR.responseText);
	    }
	});
	
}


//대댓글 수정하기

function modifiedUnderReply(underreplyNumber) {
	
	$.ajax({
	
		url : "/food/underreplymod",
		data : {
			content : $('#ModifiedUndercommentChildText').val(),
			replyNo : underreplyNumber 
			},
		type : "get",
		success : function(data){
			location.reload();
		},
		error : function(jqXHR, textStatus, errorThroen) {
	        console.log(jqXHR.responseText);
	    }
	});
	
}

//댓글밑에  댓글창 만들기
function addComment(replyNumber) {
       //댓글달기 창을 없앤다.
       $("#commentEditor").remove();
	 var parentElement = $('#addBtn'+replyNumber).parent().parent();
       //부모의 하단에 댓글달기 창을 삽입
           var commentEditor = '<tr id="commentEditor">'+
                                   '<td style="width:1%"> </td>'+
                                   '<td>'+
                                       '<span class="form-inline" role="form">'+
                                               '<textarea id="commentChildText" name="commentChildText" class="form-control" style="width:98%" rows="4"></textarea>'+
                                               '<button id="commentParentSubmit" class="btn btn-default commentclass" align="left" onclick="InsertUnderReply(\''+replyNumber+'\');">댓글입력하기</button>'+
                                               '</span>'+
                                   '</td>'+
                               '</tr>';
           parentElement.after(commentEditor); 
}


//맨밑 댓글등록 버튼 누르면 실행하는 ajax
function customerInserReply() {
	$.ajax({
		url : "/food/cInsertReply",
		data : {
			<%if (loginMember instanceof Business) {%>
				bId : "<%=((Business) loginMember).getBusiness_Id()%>",
			<%} else if (loginMember instanceof Customer) {%>
				cId : "<%=((Customer) loginMember).getCustomer_Id()%>",
			<%}%>
				rNo : "<%=review.getREVIEW_NO()%>",
			content : $('#commentParentText').val()
		},
		type : "get",
		success : function(data){
			location.reload();
		},
		error : function(jqXHR, textStatus, errorThroen) {
	        console.log(jqXHR.responseText);
	    }
	});
}



$(function() {
 /* 댓글 불러오는 ajax  */
	$.ajax({
		url: "/food/cDetailReplyList",
		data:{ rNo : "<%=review.getREVIEW_NO()%>"},
		type : "get",
		dataType : "json",
		success : function (data) {
				<%if (loginMember instanceof Business) {
				if (!((((Business) loginMember).getBusiness_Id()).equals(review.getBUSINESS_ID()))) {%>
					$('.commentclass').css('display','none');
				<%} else {%>
					$('.commentclass').css('display','block');
				<%}
			}%>
				
				
			var jsonStr = JSON.stringify(data);
			var json = JSON.parse(jsonStr);
			var notlogin="0";
			var cid = null;
			var bid= null;
			var main = "";
			var mycid ="0";
			var mybid ="0";

			<%if (loginMember == null) {%>
				notlogin="1";
			<%}else{%>
				notlogin="0";
			<%}%>
			
			<%if (loginMember instanceof Business) {%>
				bid="<%=((Business) loginMember).getBusiness_Id()%>";
			<%}%>
			<%if (loginMember instanceof Customer) {%>
				cid="<%=((Customer) loginMember).getCustomer_Id()%>";
			<%}%>
			
			for(var j in json.list){
				if(cid != null){
					if(json.list[j].replyCsWriter != null){
						if(json.list[j].replyCsWriter == cid){	
							mycid="1";
						}else{
							mycid="0";
						}
					}
				}
				if(bid!=null){
					if(json.list[j].replyBsWriter != null){
						if(json.list[j].replyBsWriter == bid){
							mybid="1";
						}else{
							mybid="0";
						}
					}
				}
				//
				
				if(json.list[j].replyCsWriter != null){		
				main+='<tr id="r'+json.list[j].relpyNumber+'" name="commentParentCode"><td class="replyidchc">'+json.list[j].replyCsWriter+'</td>';
				
				}else if(json.list[j].replyBsWriter != null){
					main+='<tr id="r'+json.list[j].relpyNumber+'" name="commentParentCode"><td class="replyidchb">'+json.list[j].replyBsWriter+'</td>';
				}
				main+='<td>'+decodeURIComponent((json.list[j].replyContent).replace(/\+/g, "%20"))+'</td>';
				main+='<td>' + json.list[j].relpyDate+'</td>';
				if($('.commentclass').css('display') == 'none'){
					main+='<td></td></tr>';
				}else{
					main+='<td style="float:right;" class="commentclass"><button type="button" id = "addBtn'+json.list[j].replyNumber+'" class="btn btn-outline-primary" onclick="addComment(\''+json.list[j].replyNumber+'\');" >댓글달기</button></td>';
					if(mycid =="1"){
					main+='<td style="float:right;"><button type="button" id = "modifiedBtn'+json.list[j].replyNumber+'" class="btn btn-outline-primary" onclick="modifiedComment(\''+json.list[j].replyNumber+'\',\''+decodeURIComponent((json.list[j].replyContent).replace(/\+/g, '%20'))+'\');" >수정</button></td>';
					main+='<td style="float:right;"><button type="button" id = "deleteBtn'+json.list[j].replyNumber+'" class="btn btn-outline-primary" onclick="deleteComment(\''+json.list[j].replyNumber+'\');" >삭제</button></td></tr>';
					}else if(mybid =="1"){
					main+='<td style="float:right;"><button type="button" id = "modifiedBtn'+json.list[j].replyNumber+'" class="btn btn-outline-primary" onclick="modifiedComment(\''+json.list[j].replyNumber+'\',\''+decodeURIComponent((json.list[j].replyContent).replace(/\+/g, '%20'))+'\');" >수정</button></td>';
					main+='<td style="float:right;"><button type="button" id = "deleteBtn'+json.list[j].replyNumber+'" class="btn btn-outline-primary" onclick="deleteComment(\''+json.list[j].replyNumber+'\');" >삭제</button></td></tr>';
					}else{
					main+='</tr>';
					}
				}

				main+='<input type="hidden" value="'+ json.list[j].replyNumber+'">';
				
					 for(var i in json.list){
						 var myucid ="0";
							var myubid ="0";
						 if(cid != null){
							if(json.list[i].replyuCid != null){
								if(json.list[i].replyuCid == cid){	
									myucid="1";
								}else{
									myucid="0";
								}
							}
						 }
						 if(bid!=null){
							 if(json.list[i].replyuBid != null){
								if(json.list[i].replyuBid == bid){
									myubid="1";
								}else{
									myubid="0";
								}
							}
						 }
						 
						if(json.list[j].replyNumber == json.list[i].replyuNo){
							if(json.list[i].replyuCid != null){
								main+='<tr id="r'+json.list[i].replyuNo+'" name="commentParentCode"><td><img src ="/food/resources/images/comment.png" height="20px" weight="20px">&emsp;'+json.list[i].replyuCid+'</td>';
							}else if(json.list[i].replyuBid != null){
								main+='<tr id="r'+json.list[i].replyuNo+'" name="commentParentCode"><td><img src ="/food/resources/images/comment.png" height="20px" weight="20px">&emsp;'+json.list[i].replyuBid+'</td>';
							}
						main+='<td>'+decodeURIComponent((json.list[i].replyuContent).replace(/\+/g, '%20'))+'</td>';
						main+='<td>' + json.list[i].relpyuDate+'</td>';
						 if(myucid =="1"){ 
							 
						main+='<td style="float:right;"><button type="button" id = "undermodifiedBtn'+json.list[i].replyuuNo+'" class="btn btn-outline-primary" onclick="modifiedUnderComment(\''+json.list[i].replyuuNo+'\',\''+decodeURIComponent((json.list[i].replyuContent).replace(/\+/g, '%20'))+'\');" >수정</button></td>';
						main+='<td style="float:right;"><button type="button" id = "underdeleteBtn'+json.list[i].replyuuNo+'" class="btn btn-outline-primary" onclick="deleteUnderComment(\''+json.list[i].replyuuNo+'\');" >삭제</button></td></tr>';
						 }else if(myubid =="1"){
							main+='<td style="float:right;"><button type="button" id = "undermodifiedBtn'+json.list[i].replyuuNo+'" class="btn btn-outline-primary" onclick="modifiedUnderComment(\''+json.list[i].replyuuNo+'\',\''+decodeURIComponent((json.list[i].replyuContent).replace(/\+/g, '%20'))+'\');" >수정</button></td>';
							main+='<td style="float:right;"><button type="button" id = "underdeleteBtn'+json.list[i].replyuuNo+'" class="btn btn-outline-primary" onclick="deleteUnderComment(\''+json.list[i].replyuuNo+'\');" >삭제</button></td></tr>';
						}else{
							main+='<td></td></tr>';
						} 
						
						
						}
					} 
				if(notlogin != "1"){
					$("#commentTable").html(main);	
				}else{
					$('.commentclass').css('display','none');
				}
			}
		},
		error : function(jqXHR, textStatus, errorThroen) {
	        console.log(jqXHR.responseText);
	    }
	});   //여기까지가 댓글 불러오기
	
});

 
//댓글의 댓글입력하기 ajax
function InsertUnderReply(replyNumber) {
	$.ajax({
		url: "/food/cInsertUnderReply",
		data :{
			rno : replyNumber,	
			<%if (loginMember instanceof Business) {%>
				bid : "<%=((Business) loginMember).getBusiness_Id()%>",
			<%} else if (loginMember instanceof Customer) {%>
				cid : "<%=((Customer) loginMember).getCustomer_Id()%>",
			<%}%>
				content : $('#commentChildText').val()
		},
		type : "post",
	success : function(data){
		location.reload();
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
					<li><a href="/food/slocation">지역검색</a></li>
					<li class="active"><a href="/food/flist">푸드트럭</a></li>
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
		
		<%-- <div id="mp-text" align="center"><%=review.getTRUCK_NAME()%></div> --%>
	</div>
	<center>
	<div id="main-t" align="center">
		<h3 id="mt-h3">&nbsp;R&nbsp;&nbsp; E&nbsp;&nbsp; V&nbsp;&nbsp; I&nbsp;&nbsp; E&nbsp;&nbsp; W&nbsp;</h3>
	</div>
	<br>
	
	
	
	<!-- 좌우측의 공간 확보 -->
	<div class="container" id="board" align="center" style="width: 1200px; ">
		<hr />
		<div class="row">
			<div class="col-md-10">
				<table class="table table-condensed">
					<thead>
						<tr align="center">
							<th width="10%">제목</th>
							<th width="auto"><%=review.getREVIEW_TITLE()%></th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>작성일</td>
							<td><%=review.getREVIEW_DATE()%></td>
						</tr>
						<tr>
							<td>글쓴이</td>
							<td><%=review.getREVIEW_WRITER()%> <span style='float: right'>조회 : <%=review.getREVIEW_COUNT()%></span></td>
						</tr>
						<tr>
							<td colspan="2">
								<p><%=review.getREVIEW_CONTENT()%></p>
							</td>
						</tr>
					</tbody>
				</table>
				<!-- 댓글받아와서 뿌려주는 태이블 -->
				<table class="table table-striped">
					<thead>
						<tr>
							<th style="width: 10%"></th>
							<th style="width: 55%"></th>
							<th style="width: 10%"></th>
							<th style="width: 35%"></th>
						</tr>
					</thead>
					<tbody id="commentTable">
					</tbody>
				</table>
				<table class="table table-condensed commentclass">
					<tr>
						<td><span class="form-inline" role="form">
							<% if(loginMember!=null){ %>
								<div align="right">
								
									<textarea id="commentParentText" class="form-control col-lg-12" style="width: 1200px" rows="4"></textarea>
									<button  type="button" id="commentParentSubmit" name="commentParentSubmit" class="btn btn-default" align="left" onclick="customerInserReply();">댓글입력하기</button>
								</div>
								<%} %>
						</span></td>
					</tr>
				</table>
				<table class="table table-condensed">
					<thead>
						<tr>
							<td><span style='float: right'> <%
 	if (loginMember instanceof Customer) {
 		if (review.getREVIEW_WRITER().equals(((Customer) loginMember).getCustomer_Id())) {
 %>
									<input type="button" id="list" onclick="reviewList();" class="btn btn-default" value="목록">
									<input type="button" onclick="boardreviewsh();" id="modify" class="btn btn-default" value="수정">
									<input type="button" id="delete" class="btn btn-default" value="삭제" onclick="reviewDelete()">
									<input type="button" onclick="reviewWrite()" id="write" class="btn btn-default" value="글쓰기"> <%
 	} else {
 		}
 	}
 %>
							</span></td>
						</tr>
					</thead>
				</table>
			</div>
		</div>
		<hr />
	</div>
	</center>
	
	
	<script type="text/javascript">
	function reviewDelete(){
		
		var deleteConfirm =  confirm("정말 삭제하시겠습니까?");
		if( deleteConfirm ){
		location.href = "/food/deletereview?rNo=<%=review.getREVIEW_NO()%>&bId=<%=review.getBUSINESS_ID()%>";
		}
	}
	
	function reviewWrite()
	{ 	
		
		location.href = "/food/foodtruckdetail?bId=<%=review.getBUSINESS_ID()%>&tab=tab-3&write=write";
		
	}
	function reviewList()
	{
		location.href = "/food/foodtruckdetail?bId=<%=review.getBUSINESS_ID()%>&tab=tab-3";
	}
	</script>
	<div class="container" style="margin-left: 20%; margin-right: 5%; margin-top: 0%; display: none" id="reviewedit">
		<table width="100%" id="board">
			<tr>
				<td>제목 <input type="text" id="title" value="<%=review.getREVIEW_TITLE()%>" /></td>
			</tr>
			<tr>
				<td>별점 <span class="star-input"> <span class="input"> <input type="radio" name="star-input" id="p1" value="1"><label for="p1">1</label> <input type="radio" name="star-input" id="p2" value="2"><label for="p2">2</label> <input type="radio" name="star-input" id="p3" value="3"><label for="p3">3</label> <input type="radio" name="star-input" id="p4" value="4"><label for="p4">4</label> <input type="radio" name="star-input" id="p5" value="5"><label for="p5">5</label> <input type="radio" name="star-input" id="p6" value="6"><label for="p6">6</label> <input type="radio" name="star-input" id="p7" value="7"><label for="p7">7</label> <input type="radio" name="star-input" id="p8" value="8"><label for="p8">8</label> <input type="radio" name="star-input" id="p9" value="9"><label for="p9">9</label> <input type="radio" name="star-input" id="p10" value="10"><label for="p10">10</label>
					</span> <output for="star-input">
							<b id="starpoint">0</b>점
						</output>
				</span>
				</td>
			</tr>
			<tr>
				<td><textarea class="tcontent" rows="10" cols="30" id="ir1" name="ir1" style="width: 766px; height: 412px;"></textarea></td>
			</tr>
			<tr>
				<td><input type="button" id="save" value="수정" /></td>
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
				var oEditors = [];
			function boardreviewsh() 
			{	
			
				$('#board').css('display','none');
				$('#reviewedit').css('display','block');
				$('#p<%=review.getREVIEW_GRADE()%>').focus();
				$('#starpoint').text('<%=review.getREVIEW_GRADE()%>');
				
	<%String str = review.getREVIEW_CONTENT();%>
				nhn.husky.EZCreator.createInIFrame({
					oAppRef : oEditors,
					elPlaceHolder : "ir1",
					sSkinURI : "editor/SmartEditor2Skin.html",
					htParams : { 
						bUseToolbar : true,
						bUseVerticalResizer : true,
						bUseModeChanger : false,
					},
					fOnAppLoad : function(){ 
						//기존 저장된 내용의 text 내용을 에디터상에 뿌려주고자 할때 사용
						oEditors.getById["ir1"].exec("PASTE_HTML", ['<%=str%>']); 
					}

					
				});
					
					
				}

				
				$("#save").click(function() {
					oEditors.getById["ir1"].exec("UPDATE_CONTENTS_FIELD", []);
					
					$.ajax({
						url : "reviewwrite",
						data : {
							rno : "<%=review.getREVIEW_NO()%>",
							title : $('#title').val(),
							content : $('#ir1').val(),
							bid : "<%=review.getBUSINESS_ID()%>",
							lid : "<%=review.getREVIEW_WRITER()%>",
							starpoint : $('#starpoint').text(),
							count : "<%=review.getREVIEW_COUNT()%>",
							check : "1"
						},
						success : function(data){
							location.href="/food/reviewdetail?reviewNo=<%=review.getREVIEW_NO()%>";
						}
					});
				});

</script>
	<br>
	<br>
	<br>
</body>
</html>