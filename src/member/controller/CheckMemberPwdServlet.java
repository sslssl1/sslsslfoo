package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.service.MemberService;

/**
 * Servlet implementation class CheckMemberPwdServlet
 */
@WebServlet("/checkPwd")
public class CheckMemberPwdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckMemberPwdServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		String userId =request.getParameter("userId");
		String userName=request.getParameter("userName");
		String email=request.getParameter("email");
		
		
		String userPwd =new MemberService().searchPwd(userId, userName, email);
		//입력 이름 이메일 확인해서 일치하는 정보 있으면 보안번호 메일 보냄
		if(userPwd!=null)
		{
			RequestDispatcher view=request.getRequestDispatcher("sendmail");
			request.setAttribute("email", email);
			view.forward(request, response);
		}else
		{
			
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
