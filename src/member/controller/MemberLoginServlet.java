package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import member.model.vo.*;
import member.model.service.*;

/**
 * Servlet implementation class MemberLoginServlet
 */
@WebServlet("/memberlogin.pwenc")
public class MemberLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MemberLoginServlet() {
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
		String userId = request.getParameter("userId");
		String password = request.getParameter("userPwd");
		System.out.println(userId);
		System.out.println(password);
		Member loginMember = new MemberService().memberLogin(userId, password);
		System.out.println(loginMember);
		response.setContentType("text/html; charset=utf-8");
		RequestDispatcher view =null;
		
//		if (loginMember instanceof Business && ((Business)loginMember).getApproval().equals("N")) {
//			view = request.getRequestDispatcher("views/logIn/logIn.jsp");
//			request.setAttribute("message", ((Business) loginMember).getBusiness_Id() + "회원님은 미승인 상태입니다.");
//			loginMember = null;
//			view.forward(request, response);
//		} else {
//
//			if (loginMember != null && ((Business)loginMember).getWithdraw().equals("N")) {
//				HttpSession session = request.getSession();
//				session.setAttribute("loginMember", loginMember);
//				response.sendRedirect("/food/index.jsp");
//			} else {
//				view = request.getRequestDispatcher("views/logIn/logIn.jsp");
//				request.setAttribute("message", "탈퇴한 회원입니다.");
//				view.forward(request, response);
//			}
//		}
		
		if (loginMember instanceof Business) {
			if(loginMember !=null && ((Business)loginMember).getApproval().equals("Y") && ((Business)loginMember).getWithdraw().equals("N")) {
				HttpSession session = request.getSession();
				session.setAttribute("loginMember", loginMember);
				response.sendRedirect("/food/index.jsp");
			}else if(loginMember !=null && ((Business)loginMember).getApproval().equals("N")) {
				view = request.getRequestDispatcher("views/logIn/logIn.jsp");
				request.setAttribute("message", ((Business) loginMember).getBusiness_Id() + "회원님은 미승인 상태입니다.");
				loginMember = null;
				view.forward(request, response);
			}else if(loginMember != null &&((Business)loginMember).getWithdraw().equals("Y")) {
				view = request.getRequestDispatcher("views/logIn/logIn.jsp");
				request.setAttribute("message", "탈퇴한 회원입니다!");
				view.forward(request, response);
			}else if( ((Business)loginMember).getApproval().equals("R") ){
				view = request.getRequestDispatcher("views/logIn/logIn.jsp");
				request.setAttribute("message", "사업자 승인 거절당한 회원입니다.사유 : "+((Business)loginMember).getBUSINESS_REJECT_REASON());
				view.forward(request, response);
			}
		}else if(loginMember instanceof Customer) {
			if(loginMember !=null && ((Customer)loginMember).getWithdraw().equals("N")) {
				HttpSession session = request.getSession();
				session.setAttribute("loginMember", loginMember);
				response.sendRedirect("/food/index.jsp");
			}else if(loginMember !=null && ((Customer)loginMember).getWithdraw().equals("Y")) {
				view = request.getRequestDispatcher("views/logIn/logIn.jsp");
				request.setAttribute("message", "탈퇴한 회원입니다!");
				view.forward(request, response);
			}
		}else if(loginMember instanceof Manager) {
			HttpSession session = request.getSession();
			session.setAttribute("loginMember", loginMember);
			response.sendRedirect("/food/index.jsp");
		}else if(loginMember ==null) {
			view = request.getRequestDispatcher("views/logIn/logIn.jsp");
			request.setAttribute("message", "아이디와 비밀번호를 확인해주세요!");
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
