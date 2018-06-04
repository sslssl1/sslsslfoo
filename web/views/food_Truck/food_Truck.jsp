<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ page import="member.model.vo.*,java.util.*,foodtruck.model.vo.*"%>

<%
   ArrayList<Foodtruck> list = null;
   if (request.getAttribute("list") != null) {
      list = (ArrayList<Foodtruck>) request.getAttribute("list");
   }
   ArrayList<FoodTop3> top3List = null;
   if( request.getAttribute("top3List")!=null)
   {
      top3List = (ArrayList<FoodTop3>)request.getAttribute("top3List");
   }
   Member loginMember = null;
   if (session.getAttribute("loginMember") instanceof Business) {
      loginMember = (Business) session.getAttribute("loginMember");
   } else if (session.getAttribute("loginMember") instanceof Customer) {
      loginMember = (Customer) session.getAttribute("loginMember");
   } else if (session.getAttribute("loginMember") instanceof Manager) {
      loginMember = (Manager) session.getAttribute("loginMember");
   }
   
   
   ArrayList<FoodTop3> riceTop3List = null;
   ArrayList<FoodTop3> snackTop3List= null;
   ArrayList<FoodTop3> desertTop3List =null;
   if(request.getAttribute("riceTop3List")!=null)
   { 
      System.out.println("riceTop3List 오닝");
      riceTop3List=(ArrayList<FoodTop3>)request.getAttribute("riceTop3List");
      snackTop3List=(ArrayList<FoodTop3>)request.getAttribute("snackTop3List");
      desertTop3List=(ArrayList<FoodTop3>)request.getAttribute("desertTop3List");
   }
   
%>


<!DOCTYPE html>
<html lang="en">
<head>
<title>푸딩 푸드트럭 정보</title>
<meta charset="utf-8">
<link rel="stylesheet" href="/food/resources/css/top-menu.css">
<link rel="stylesheet" href="/food/resources/css/foot.css">
<link rel="stylesheet" href="/food/resources/css/location.css">
<link rel="stylesheet" href="/food/resources/css/bootstrap.min.css">
<script type="text/javascript"
   src="/food/resources/js/jquery-3.3.1.min.js"></script>
   
<script type="text/javascript">
var topcount=0;

var riceTop3List=["","",""];
var riceTop3NameList=["","",""];
var riceTop3BsIdList=["","",""];

var snackTop3List=["","",""];
var snackTop3NameList=["","",""];
var snackTop3BsIdList=["","",""];


var desertTop3List=["","",""];
var desertTop3NameList=["","",""];
var desertTop3BsIdList=["","",""];

<%for(FoodTop3 ft3 : riceTop3List){%>
   riceTop3List[topcount%3]="/food/resources/images/foodtruck/<%=ft3.getTruckImg()%>";
   riceTop3NameList[topcount%3]="<%=ft3.getTruckName()%>"
   riceTop3BsIdList[topcount%3]="<%=ft3.getBusinessId()%>"
   topcount++;
<%}%>

<%for(FoodTop3 ft3 : snackTop3List){%>
   snackTop3List[topcount%3]="/food/resources/images/foodtruck/<%=ft3.getTruckImg()%>";
   snackTop3NameList[topcount%3]="<%=ft3.getTruckName()%>"
   snackTop3BsIdList[topcount%3]="<%=ft3.getBusinessId()%>"

   topcount++;
<%}%>

<%for(FoodTop3 ft3 : desertTop3List){%>
   desertTop3List[topcount%3]="/food/resources/images/foodtruck/<%=ft3.getTruckImg()%>";
   desertTop3NameList[topcount%3]="<%=ft3.getTruckName()%>"
   desertTop3BsIdList[topcount%3]="<%=ft3.getBusinessId()%>"
   topcount++;
<%}%>

$(function(){
   
   $("#sidebox").css("top",290);
    var currentPosition = parseInt($("#sidebox").css("top")); 
    $(window).scroll(function() { 
       var position = $(window).scrollTop(); 
       $("#sidebox").stop().animate({"top":position+currentPosition+"px"},500); 
       if ($(this).scrollTop() > 300) {

            $("#sidebox").css("margin-top", 0);
            //$("#side_1").css("margin-top", "10px");
            //$("#side_1").css("bottom", "600px");

            }else {
               $("#sidebox").css("top", "462px");

            }    
    
    });
    
   
//onload closed   
});

var jj=0;

setInterval(function()  {
   console.log(topcount);
   
   $("#top3img1").prop("src",riceTop3List[topcount%3]);
   $("#a_tag_top1").prop("href",'foodtruckdetail?bId='+riceTop3BsIdList[topcount%3]);
   $("#nametag_top1").html("식사류 | <strong>"+riceTop3NameList[topcount%3])+"</strong>";
   
   $("#top3img2").prop("src",snackTop3List[topcount%3]);
   $("#a_tag_top2").prop("href",'foodtruckdetail?bId='+snackTop3BsIdList[topcount%3]);
   $("#nametag_top2").html("간식류 | <strong>"+snackTop3NameList[topcount%3])+"</strong>";
   
   
   $("#top3img3").prop("src",desertTop3List[topcount%3]);
   $("#a_tag_top3").prop("href",'foodtruckdetail?bId='+desertTop3BsIdList[topcount%3]);
   $("#nametag_top3").html("디저트류 | <strong>"+desertTop3NameList[topcount++%3])+"</strong>";
   
   
   console.log(topcount);
   
}, 2000 );


</script>   
   
   
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
   
<script type="text/javascript">

function searchAutoComplete()
{
   $.ajax({
      url : "/food/searchautocomplete",
      type : "post",
      dataType : "json",
      data : {
         seType : $('#seType').val(),
         seValue : $('#seValue').val()
      },
      success : function(data){
         var jsonStr = JSON.stringify(data);
         var json = JSON.parse(jsonStr);
         
         var autoCode ="";
         for(var i in json.list)
            {
            autoCode += " <option value='"+json.list[i].values+"'>"+json.list[i].values+"</option>  ";
            }
         $('#autoCompleteSelect').html(autoCode);
         
      },
      error : function(){
         console.log("자동완성 ajax 에러");
      }
   });
}

$(function(){
   //검색어 자동완성~!^~%
$('#seValue').keyup(function(){
   searchAutoComplete();
   
});
   

$('#autoCompleteSelect').click(function(){
   $('#seValue').val($('#autoCompleteSelect').val());
});   
   
$('#ctType').change( getTruckList );

   
   
   $("input[name=sevalue]").keydown(function (key) {
       
        if(key.keyCode == 13){//키가 13이면 실행 (엔터는 13)
           getTruckList();
        }
   });
});

function getTruckList()
{
   
    $.ajax({
      url : "/food/trucksearchlist",
      type : "post",
      data : {ctType : $('#ctType').val(),
            seType : $('#seType').val(),
            seValue : $('#seValue').val()
         },
   dataType: "json",
   success : function(data){ 
   
      var jsonStr = JSON.stringify(data);
      var json = JSON.parse(jsonStr);
      
      var htmlCode = "";
      for(var i in json.list)
         {   if(i%3==0){
            htmlCode += '<tr style="border: 1px solid #BDBDBD">';}
            htmlCode += '<td style="border: 1px solid #BDBDBD; width: 33%;">';
            htmlCode += '<a href="foodtruckdetail?bId='+json.list[i].bsId+'">';
            htmlCode += '<img src="/food/resources/images/foodtruck/'+json.list[i].tImg+'" style="width: 100%; padding: 0px;"></img></a>';
           if(json.list[i].tLive== "Y"){
            htmlCode += '<h3 style="text-align: center"><img id="blink_on" width="20px"height="auto" src="/food/resources/images/btn_on.png">'+decodeURIComponent((json.list[i].tName).replace(/\+/g, '%20'))+'</h3>';
            
           }else{
              htmlCode += ' <h3 style="text-align: center">'+decodeURIComponent((json.list[i].tName).replace(/\+/g, '%20'))+'</b></span> </center>'; 
           }
            htmlCode += '<h5 style="text-align: center">'+decodeURIComponent((json.list[i].tMainMenu).replace(/\+/g, '%20'))+'</h5></td>';
            
            
           
            
            if ( i % 3 == 2 )
               {
               htmlCode += "</tr>";
               }
            if( i==(json.list.length -1) )
               {
               for(j=i%3;j<3;j++)
                  {htmlCode+="<td></td>";}
               htmlCode+="</tr>";
               }
         }
   $('#truck_table').html(htmlCode);
   console.log(htmlCode);
   },error: function(jqXHR, textStatus, errorThrown) { 
        console.log(jqXHR.responseText); 
    }    
   
   });
    
    
   }
   
</script>

<style>


#blink_on {
   animation:blink 1.0s ease-in-out infinite alternate;
   animation-duration: 1000ms;
    animation-iteration-count: infinite;
}

@-webkit-keyframes blink{
    0% {opacity:0.3;}
    100% {opacity:1;}
    
}

#sidebox {background-color:white; position:absolute; width:150%; top:470px; right:50px; padding: 0px; }

.main {
    width: 100%;
    height: 2000px;
}
.main .content {
    float: left;
    width: 70%;
    height: 2000px;
    line-height: 30px;
    font-size: 18px;
    padding: 15px;
    background-color: #EEEEEE;
}
.main .left {
    float: right;
    width: 25%;
    height: 2000px;
}
.main .left .menu {
    width: 150px;
    height: 165px;
    background-color: #424242;
    padding: 15px;
}
.main .left .menu ul {
    list-style: none;
    color: #EEEEEE;
    padding-left: 0px;
}
.main .left .menu ul li {margin:10px;}

/* 메인사진 */
#mainPhoto {
   background-image: url("/food/resources/images/FOODING2.jpg");
   background-size: cover;
   background-position: center;
   width: 100%;
   height: 230px;
}

.col-sm-4 {
   border: 2px solid #DCDCDC;
   width: 30%;
   padding: 0;
   margin-left: 2.5%;
   float: left;
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
     
   </div>
   <div id="main-t" align="center">
      <h3 id="mt-h3">F o o d &nbsp;&nbsp;&nbsp;T r u c k</h3>

      <select id="seType">
         <option value="TRUCK_NAME">푸드트럭 이름</option>
         <option value="MENU_NAME">메뉴</option>
      </select> 
<span style="padding:0">
      <input  id="seValue" name="sevalue" type="search"  placeholder="Search.." style="margin-bottom:0;"> 
      <a href="javascript: getTruckList();" class="glyphicon glyphicon-search"
         style="text-decoration: none;"></a> <Br><select style="width:162.8px; margin-left:91px; margin-top:0px;" id="autoCompleteSelect"></select>
      </span>
      
         
      <br> <br> <br>

      <div style="width: 70%; float: left;">
      
         <select id=ctType style="margin-right: 50%;">
            <option value="%" >전체보기</option>
            <option value="식사류" >식사류</option>
            <option value="간식류" >간식류</option>
            <option value="디저트류" >디저트류</option>
         </select><br>
         <br>
      </div>
      <table id="truck_table"style="width: 70%;">
         <%
            int i = 0;
            for (Foodtruck truck : list) {

               if (i % 3 == 0) {
         %>
         <tr style="border: 1px solid #BDBDBD; padding:10px;">
            <%}%>
            <td style="border: 1px solid #BDBDBD; width: 30%;  margin:50px; ">
            <a href="foodtruckdetail?bId=<%=truck.getBusiness_Id()%>"><img
                  src="/food/resources/images/foodtruck/<%=truck.getTruck_Img()%>"
                  style="width: 100%; padding: 0px;"></img></a>
                 <%if(truck.getTruck_Live_OnOff().equals("Y")){%>
               <h3 style="text-align: center"><img id="blink_on" width="20px" height="auto" src="/food/resources/images/btn_on.png"><%=truck.getTruck_Name()%></h3>
                  <%}else{ %>
                  <h3 style="text-align: center"><%=truck.getTruck_Name()%></h3>
                  <%} %>
                <h5 style="text-align: center ; margin-top: 10px;"><%=truck.getTruck_MainMenu()%></h5></td>
               
            <%
               if (i == (list.size() - 1) || i % 3 == 2) {
            %>
         </tr>

         <%
            }
               i++;
            }
         %>



      </table>
   <!--top3시작 -->
   
   <!-- top3끝 -->
   
      <!-- div 닫음 -->
   </div>

<span id="sidebox" style="width:8%; text-align: center; align:center;"  > 
 
 <span style="width:120%; align:center; color:#000; font-size:13px" >금주의 인기트럭  <br>
<span style="color:red">TOP3</span></span>
 <br>
 <div style="margin: 10px 0; border-bottom:1px solid #bdbdbd; background-color: red"></div>

<%
Foodtruck ftruck = null;
   
   int k=0;
%>

<div id="top3img_div">
   <% if(riceTop3List!=null){
       cnt =1;
       String sss[]={"식사류 | ","간식류 | ","디저트류 | "};
      for(FoodTop3 ft : riceTop3List){%>
   <br>
   
   <a id="a_tag_top<%=cnt %>" href="foodtruckdetail?bId=<%=ft.getBusinessId()%>">
   <img id="top3img<%=cnt %>" style="width:100%;  margin-bottom: 0px; "  src="/food/resources/images/foodtruck/<%=ft.getTruckImg()%>"></a>
   <br>
   <span id="nametag_top<%=cnt %>" style="width:110%; vertical-align:center; font-size:13px; text-align: center; margin-top: 0px; color: gray"><%=sss[(cnt++)-1]%><strong><%=ft.getTruckName() %></strong></span>
   <br>
   </div>
   

   <% }}else{%>
   ~~~~
   <%} %>

 </span>










   <br style="clear: both">
   <br>
   <br>
   <br>
   <br>
   <br>
   <br>
   <br>

   <div class="foot" style="bottom: 0;">
      <p>FoodTruck &nbsp;Copyright @ iei.or.kr &nbsp; All Rights
         Reserved.</p>
   </div>
</body>
</html>