package member.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.service.MemberService;

/**
 * Servlet implementation class MemberMailDupliCheckServlet
 */
@WebServlet("/memmaildupli")
public class MemberMailDupliCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberMailDupliCheckServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		String mail =null;
		int result = 0;
		if(request.getParameter("cusMail")!=null)
		{	//customer에서 메일 중복확인
			System.out.println("cus mail");
			mail=request.getParameter("cusMail");
			result = new MemberService().CusMailDuplicate(mail);
		}
		else if(request.getParameter("busMail")!=null)
		{	//bus에서 메일 중복확인
			mail=request.getParameter("busMail");
			result = new MemberService().BusMailDuplicate(mail);

		}
		
		
		response.setContentType("text/html; charset=utf-8;");
		PrintWriter out = response.getWriter();
		out.append(String.valueOf(result));
		out.flush();
		out.close();
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
