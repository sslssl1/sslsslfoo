package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.service.MemberService;
import member.model.vo.Business;
import member.model.vo.Member;

/**
 * Servlet implementation class BusinessEnrollServlet
 */
@WebServlet("/bsenroll.pwenc")
public class BusinessEnrollServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BusinessEnrollServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		Member member = new Business();
		((Business)member).setBusiness_Id(request.getParameter("userId"));
		((Business)member).setBusiness_Email(request.getParameter("email"));
		((Business)member).setBusiness_Name(request.getParameter("userName"));
		((Business)member).setBusiness_Pwd(request.getParameter("userPwd"));
		((Business)member).setBusiness_Phone(request.getParameter("userPhone"));
		System.out.println(member);
	
		
		int result= new MemberService().BusinessEnroll(member);
		
		response.setContentType("text/html; charset=utf-8"); 
		RequestDispatcher view = null;
		if(result>0) {
			view =request.getRequestDispatcher("views/logIn/logIn.jsp");
			request.setAttribute("message", request.getParameter("userId")+"님, 사업자 회원가입이 성공하였습니다!");
			view.forward(request, response);
			
		}else {
			view =request.getRequestDispatcher("views/signUp/business_Signup.jsp");
			request.setAttribute("message", "회원가입에 실패했습니다! 가입란을 다시 작성해주세요.");
			view.forward(request, response);
		}
	}
	
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
