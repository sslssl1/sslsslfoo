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
 * Servlet implementation class Delete_RejectBusinessButtonServlet
 */
@WebServlet("/deletebtn")
public class Delete_RejectBusinessButtonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Delete_RejectBusinessButtonServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;"); 
		String bsId =request.getParameter("bsid");
		int result =  new MemberService().deleteBusiness(bsId);
		RequestDispatcher view = null;
		if(result > 0) {
			
			view = request.getRequestDispatcher("breject");
			request.setAttribute("deleteSuccess", "회원삭제 성공!");
			view.forward(request, response);
			
		}else {
			view = request.getRequestDispatcher("views/adminMy/admin_my.jsp");
			request.setAttribute("tab", "tab-5");
			request.setAttribute("deleteFail", "회원 삭제 실패!");
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
