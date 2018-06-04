package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.service.MemberService;
import member.model.vo.Customer;
import member.model.vo.Member;

/**
 * Servlet implementation class SearchMemberIdServlet
 */
@WebServlet("/searchId")
public class SearchMemberIdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchMemberIdServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		//이메일 인증 끝난뒤 입력받은 정보로 아이디를 찾아서 이메일로 아이디를 알라줌
		
		String userName=request.getParameter("userName");
		String email=request.getParameter("email");
		
		String userCSId = new MemberService().searchCSId(userName, email);
		String userBSId = new MemberService().searchBSId(userName, email);

		System.out.println("userCSId : "+userCSId);
		System.out.println("userBSId : "+userBSId);
	
		response.setContentType("text/html; charset=utf-8; ");

		if(userCSId!=null || userBSId!=null)
		{// 아이디 찾음  , 이메일주소로 아이디 보내줘야함
			//메일 보내고 로그인 페이지로 이동
			RequestDispatcher view=request.getRequestDispatcher("sendmail");
			request.setAttribute("email", email);
//			request.setAttribute("userId", userCSId);
			request.setAttribute("userCSId", userCSId);
			request.setAttribute("userBSId", userBSId);
			view.forward(request, response);
			
		}
		else
		{//아이디 못찾음, 다시 아이디 찾기 페이지로 이동
			response.sendRedirect("/food/views/login/id_Find.jsp");
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
