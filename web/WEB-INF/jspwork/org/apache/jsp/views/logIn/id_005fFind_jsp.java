/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.0.49
 * Generated at: 2018-04-20 01:22:10 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.views.logIn;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class id_005fFind_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

final java.lang.String _jspx_method = request.getMethod();
if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET POST or HEAD");
return;
}

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html lang=\"en\">\n");
      out.write("<head>\n");
      out.write("<title>푸딩 아이디찾기</title>\n");
      out.write("<meta charset=\"utf-8\">\n");
      out.write("<link rel=\"stylesheet\" href=\"/food/resources/css/top-menu.css\">\n");
      out.write("<link rel=\"stylesheet\" href=\"/food/resources/css/foot.css\">\n");
      out.write("<link rel=\"stylesheet\" href=\"/food/resources/css/location.css\">\n");
      out.write("<link rel=\"stylesheet\" href=\"/food/resources/css/bootstrap.min.css\">\n");
      out.write("<script type=\"text/javascript\"\n");
      out.write("\tsrc=\"/food/resources/js/jquery-3.3.1.min.js\"></script>\n");
      out.write("<script type=\"text/javascript\">\n");
      out.write("\t\n");
      out.write("\tvar vcode = 0;\n");
      out.write("\tvar vcodeCheck=0;\n");
      out.write("\t\n");
      out.write("\tfunction sendMail() {//이메일 인증코드 발송  \n");
      out.write("\t\t\n");
      out.write("\t$.ajax({\n");
      out.write("\t\t\turl : \"/food/findid\",\n");
      out.write("\t\t\tdata : {\n");
      out.write("\t\t\t\temail : $('#email').val(),\n");
      out.write("\t\t\t\tusername : $('#userName').val()\n");
      out.write("\t\t\t},\n");
      out.write("\t\t\ttype : \"post\",\n");
      out.write("\t\t\tsuccess : function(data) {\n");
      out.write("\t\t\t\t\n");
      out.write("\t\t\t\tif(data==\"회원정보가 없습니다.\")\n");
      out.write("\t\t\t\t{\n");
      out.write("\t\t\t\t\talert(\"회원정보가 없습니다.\");\n");
      out.write("\t\t\t\t}else{\n");
      out.write("\t\t\t\tvcode = Number(data);\n");
      out.write("\t\t\t\talert(\"인증번호를 전송하였습니다.\");\n");
      out.write("\t\t\t\t}\n");
      out.write("\t\t\t\t\n");
      out.write("\t\t\t}\n");
      out.write("\t});\n");
      out.write("\t\n");
      out.write("\t}\n");
      out.write("\n");
      out.write("\n");
      out.write("\tfunction vCodeCheck() {\n");
      out.write("\t\t//이메일 인증버튼 클릭하면 실행되는 함수\n");
      out.write("\t\t//이메일 인증번호와 서버의 인증번호를 비교하여\n");
      out.write("\t\t//맞으면 checkVCode=true\n");
      out.write("\t\t//틀리면 checkVCode=false\n");
      out.write("\n");
      out.write("\t\t\tif ($('#vCode').val() != \"\" && vcode == $('#vCode').val()) {\n");
      out.write("\t\t\talert('인증완료');\n");
      out.write("\t\t\tvcodeCheck = 1;\n");
      out.write("\t\t\t\n");
      out.write("\t\t} else {\n");
      out.write("\t\t\talert('인증번호를 확인해주세요.');\n");
      out.write("\t\t}\n");
      out.write("\t\t\n");
      out.write("\t}\n");
      out.write("\t\n");
      out.write("\t function findbt(){\n");
      out.write("\t\tif (vcodeCheck == 0){\n");
      out.write("\t\t\talert('입력창을 다시 확인하여 주세요.');\n");
      out.write("\t\t}else{\n");
      out.write("\t\t\t$('#idf').submit();\n");
      out.write("\t\t}\n");
      out.write("\t} \n");
      out.write("\t\n");
      out.write("</script>\n");
      out.write("\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("\t<!-- 상단 네비게이션 바 -->\n");
      out.write("\t<div class=\"navcl\">\n");
      out.write("\t\t\t<nav class=\"navbar navbar-inverse\">\n");
      out.write("\t\t\t\n");
      out.write("\t\t\t\t<div class=\"collapse navbar-collapse\" id=\"myNavbar\" style=\"padding-left: 0px;\">\n");
      out.write("\t\t\t\t\t<ul class=\"nav navbar-nav\" >\n");
      out.write("\t\t\t\t\t<li style=\"margin-right:10px;\"><a class=\"navbar-brand\" href=\"/food/index.jsp \">\n");
      out.write("\t\t\t\t\t\t<img src=\"/food/resources/images/LOGO_fooding.png\" style=\"width:65px; height:30px; \">\n");
      out.write("\t\t\t\t\t\t</a></li>\n");
      out.write("\t\t\t\t\t<li ><a href=\"/food/index.jsp\">Home</a></li>\n");
      out.write("\t\t\t\t\t<li><a href=\"/food/slocation\">지역검색</a></li>\n");
      out.write("\t\t\t\t\t<li><a href=\"/food/flist\">푸드트럭</a></li>\n");
      out.write("\t\t\t\t\t<li><a href=\"/food/fes_show\">축제검색</a></li>\n");
      out.write("\t\t\t\t\t</ul>\n");
      out.write("\n");
      out.write("\t\t\t\t\n");
      out.write("<ul class=\"nav navbar-nav navbar-right\">\n");
      out.write("\t\t\t\t\t\t<li class=\"active\"><a href=\"/food/views/logIn/logIn.jsp\"><span\n");
      out.write("\t\t\t\t\t\t\t\tclass=\"glyphicon glyphicon-log-in\"></span> Login</a></li>\n");
      out.write("\t\t\t\t\t\t<li><a href=\"/food/views/signUp/select_signUp.jsp\"><span\n");
      out.write("\t\t\t\t\t\t\t\tclass=\"glyphicon glyphicon-edit\"></span> 회원가입</a></li>\n");
      out.write("\t\t\t\t\t</ul>\n");
      out.write("\t\t\t\t</div>\n");
      out.write("\t\t</nav>\n");
      out.write("\n");
      out.write("\t</div>\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\t<!-- 아이디 찾기  -->\n");
      out.write("\t<div class=\"container\" style=\"margin-top: 50px;\">\n");
      out.write("\t\t<h3 style=\"text-align: center;\">아이디 찾기</h3>\n");
      out.write("\t\t<br> <br>\n");
      out.write("\n");
      out.write("\t\t<center>\n");
      out.write("\t\t\t<form action=\"/food/searchId\" method=\"post\" id=\"idf\" style=\"width: 700px\">\n");
      out.write("\n");
      out.write("\t\t\t\t<!-- 이름 입력란 -->\n");
      out.write("\t\t\t\t<div class=\"input-group\">\n");
      out.write("\t\t\t\t\t<span class=\"input-group-addon\"><i\n");
      out.write("\t\t\t\t\t\tclass=\"glyphicon glyphicon-user\"></i></span> <input\n");
      out.write("\t\t\t\t\t\tstyle=\"width: 475px; height: 25px; margin-right: 95px;\"\n");
      out.write("\t\t\t\t\t\tid=\"userName\" type=\"text\" class=\"form-control\" name=\"userName\"\n");
      out.write("\t\t\t\t\t\tplaceholder=\"이름\" >\n");
      out.write("\t\t\t\t</div>\n");
      out.write("\t\t\t\t<br>\n");
      out.write("\n");
      out.write("\n");
      out.write("\t\t\t\t<!-- 이메일 입력란 -->\n");
      out.write("\t\t\t\t<!-- 인증하기 버튼 누르면  이름과 아이디 값으로 해당 유저가 있는지 검사 있으면\n");
      out.write("\t\t\t\t입력받은 메일로  인증번호 발송   인증번호 보내고 다시 현재 페이지로 와야함 AJAX?-->\n");
      out.write("\t\t\t\t<div class=\"input-group\">\n");
      out.write("\t\t\t\t\t<span class=\"input-group-addon\"><i\n");
      out.write("\t\t\t\t\t\tclass=\"glyphicon glyphicon-user\"></i></span> <input\n");
      out.write("\t\t\t\t\t\tstyle=\"width: 475px; height: 25px;\" type=\"email\"\n");
      out.write("\t\t\t\t\t\tclass=\"form-control\" name=\"email\" id=\"email\" placeholder=\"이메일\">\n");
      out.write("\t\t\t\t\t<span style=\"float: center; margin-left: 20px;\"><input\n");
      out.write("\t\t\t\t\t\tstyle=\"height: 33px;\" type=\"button\" value=\"인증하기\" \n");
      out.write("\t\t\t\t\t\tonclick=\"sendMail();\"></span>\n");
      out.write("\t\t\t\t</div>\n");
      out.write("\t\t\t\t<br>\n");
      out.write("\t\t\t\t<!-- 인증번호 입력란 -->\n");
      out.write("\t\t\t\t<div class=\"input-group\">\n");
      out.write("\t\t\t\t\t<span class=\"input-group-addon\"><i\n");
      out.write("\t\t\t\t\t\tclass=\"glyphicon glyphicon-lock\"></i></span> <input\n");
      out.write("\t\t\t\t\t\tstyle=\"width: 475px; height: 25px;\" id=\"vCode\" type=\"text\"\n");
      out.write("\t\t\t\t\t\tclass=\"form-control\" placeholder=\"인증번호\"> <span\n");
      out.write("\t\t\t\t\t\tstyle=\"float: center; margin-left: 20px;\"><input\n");
      out.write("\t\t\t\t\t\tstyle=\"height: 33px;\" type=\"button\" value=\"확인하기\" onclick=\"vCodeCheck();\" ></span>\n");
      out.write("\t\t\t\t</div>\n");
      out.write("\t\t\t\t<br> <br>\n");
      out.write("\t\t\t\t<!-- 찾기 버튼 -->\n");
      out.write("\t\t\t\t\n");
      out.write("\t\t\t\t<input type=\"button\" style=\"height: 33px;\" onclick=\"findbt();\" value=\"찾기\" > <br>\n");
      out.write("\t\t\t\t\n");
      out.write("\t\t\t\t\n");
      out.write("\t\t\t\t<br> <br> <br>\n");
      out.write("\n");
      out.write("\t\t\t</form>\n");
      out.write("\t</div>\n");
      out.write("\t</center>\n");
      out.write("\t<br>\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\t<div class=\"foot\" style=\"bottom: 0; position: absolute;\">\n");
      out.write("\t\t<p>FoodTruck &nbsp;Copyright @ iei.or.kr &nbsp; All Rights\n");
      out.write("\t\t\tReserved.</p>\n");
      out.write("\t</div>\n");
      out.write("\n");
      out.write("</body>\n");
      out.write("</html>\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
