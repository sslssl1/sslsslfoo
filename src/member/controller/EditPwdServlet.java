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
 * Servlet implementation class EditPwdServlet
 */
@WebServlet("/editpwd.pwenc")
public class EditPwdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditPwdServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String userId = request.getParameter("userid");
		
		String userPwd = request.getParameter("userPwd");
		System.out.println("servlet : "+userId+userPwd);
		
		int result = new MemberService().editpwd(userId, userPwd);
		System.out.println("servlet result  : "+result);
		response.setContentType("text/html;  charset=utf-8;");
		
		if(result>0) {
			RequestDispatcher view=request.getRequestDispatcher("views/logIn/pwd_Edit.jsp");
			request.setAttribute("message", "비밀번호가 변경되었습니다.");
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
