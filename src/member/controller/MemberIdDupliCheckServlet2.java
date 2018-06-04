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
 * Servlet implementation class MemberIdDupliCheckServlet2
 */
@WebServlet("/MemberIdDupliCheckServlet2")
public class MemberIdDupliCheckServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberIdDupliCheckServlet2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String userid = request.getParameter("userid");
		
		int result = new MemberService().duplicateBsId(userid);
		
		
		if(result==0) {
		 result = new MemberService().duplicateCusId(userid);
		 
		}
		if(result==0) {
			result = new MemberService().duplicateMgId(userid);
		}
		
		PrintWriter out =response.getWriter();
		out.append(String.valueOf(result));
		
		
		
		out.flush();
		out.close();
	}

}
