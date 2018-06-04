package member.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.service.MemberService;

/**
 * Servlet implementation class FindPwdServlet
 */
@WebServlet("/findpwd")
public class FindPwdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FindPwdServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String userId = request.getParameter("userid");
		
		String userName = request.getParameter("username");
		
		String email = request.getParameter("email");
		
		String userPwd = new MemberService().findPwd(userId, userName, email);
		
		response.setContentType("text/html; charset=utf-8");
		
		if (userPwd != null) {
			RequestDispatcher view = request.getRequestDispatcher("sendmail");
			request.setAttribute("email", email);
			view.forward(request, response);
		} else {
			//response.sendRedirect("/food/views/logIn/pwd_Find.jsp");
			PrintWriter out = response.getWriter();
			out.append("회원정보가 없습니다.");
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
