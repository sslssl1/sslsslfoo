package member.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.service.MemberService;

/**
 * Servlet implementation class MemberIdDupliCheckServlet
 */

@WebServlet("/midcheck")
public class MemberIdDupliCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberIdDupliCheckServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String userId = request.getParameter("userId");
		System.out.println(userId);
		PrintWriter out = response.getWriter();
		int result2=0;
		int result = new MemberService().duplicateCusId(userId);
		if(result>0)
		{
			//일반회원이랑 아이디 겹침
		result2=1;
			
		}
		else
		{
			result = new MemberService().duplicateBsId(userId);
			if(result>0)
			{
				//사업자와 아이디 겹침
				result2=1;
			}
			else
			{
				result = new MemberService().duplicateMgId(userId);
				if(result>0)
				{
					result2=1;
				}
			}
		}
		
		out.append(String.valueOf(result2));
	}

}
