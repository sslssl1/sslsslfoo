package member.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import member.model.service.MemberService;
import member.model.vo.*;

/**
 * Servlet implementation class MemberUpdateServlet
 */
@WebServlet("/mupdate.pwenc")
public class MemberUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MemberUpdateServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// fooding 꺼
		// 회원 정보 수정 처리용 컨트롤러
		// 1. 전송온 값에 한글이 있을 경우 인코딩함
		request.setCharacterEncoding("utf-8");

		// 2. 전송온 값 꺼내서 변수 또는 객체에 저장하기
		
		String userId=request.getParameter("userId");
		String userPwd=request.getParameter("userPwd");
		String email=request.getParameter("email");
		String userName=request.getParameter("userName");
		String withdraw=request.getParameter("withdraw");
		
		
		Customer customer = new Customer();

		
		customer.setCustomer_Id(userId);
		customer.setCustomer_Pwd(userPwd);
		customer.setCustomer_Email(email);
		
		
		
		// 취미

		// 3. 서비스 클래스 메소드로 전달하고 결과받기
		int result = new MemberService().updateMember(customer);

		// 4. 받은 결과를 가지고 성공/실패 뷰를 내보냄
		response.setContentType("text/html; charset=utf-8");
	
		if (result > 0) {
		
			HttpSession session = request.getSession(false);
			
			
			
			
			
			if(session!=null)
			{
				session.invalidate();
				
			}
			Customer loginMember = new Customer();
			loginMember.setCustomer_Id(userId);
			loginMember.setCustomer_Pwd(userPwd);
			loginMember.setCustomer_Email(email);
			loginMember.setCustomer_Name(userName);
			loginMember.setWithdraw(withdraw);
			
			
			session= request.getSession(true);
			session.setAttribute("loginMember", loginMember);
			
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
