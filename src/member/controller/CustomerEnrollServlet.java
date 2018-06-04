package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import member.model.service.*;
import member.model.vo.*;

/**
 * Servlet implementation class CustomerEnrollServlet
 */
@WebServlet("/csenroll.pwenc")
public class CustomerEnrollServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CustomerEnrollServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

		Member member = new Customer();
		((Customer)member).setCustomer_Email( request.getParameter("email"));
		((Customer)member).setCustomer_Id(request.getParameter("userId"));
		((Customer)member).setCustomer_Name(request.getParameter("userName"));
		((Customer)member).setCustomer_Pwd(request.getParameter("userPwd"));
		
		int result = new MemberService().customerEnroll(member);
		
		response.setContentType("text/html; charset=utf-8"); 
		
		RequestDispatcher view = null;
		if(result>0)
		{
			view = request.getRequestDispatcher("views/logIn/logIn.jsp");
			request.setAttribute("message", request.getParameter("userId")+"님, 회원가입이 성공하였습니다");
			view.forward(request, response);
			
			
		}
		else {
			view=request.getRequestDispatcher("views/signUp/signUp.jsp");
			request.setAttribute("message", "회원가입에 실패했습니다. 가입란을 다시 작성해주세요");
			view.forward(request, response);
		}
		
		
	
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
