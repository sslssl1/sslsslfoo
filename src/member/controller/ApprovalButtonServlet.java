package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.service.MemberService;
import member.model.vo.Business;
import review.model.service.ReviewService;

/**
 * Servlet implementation class ApprovalButtonServlet
 */
@WebServlet("/approvbtn")
public class ApprovalButtonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApprovalButtonServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8"); 
		
		String bsId[] =request.getParameterValues("delCheck");
		String bsEmail[] =request.getParameterValues("business_email");
		for(int i=0;i<bsEmail.length;i++)
		{
			System.out.println(bsEmail[i]);
		}
		int result=0;
		
		if(request.getParameterValues("delCheck")!=null) {
			 bsId= request.getParameterValues("delCheck");
			 bsEmail= request.getParameterValues("business_email");
			}
		MemberService mservice = new MemberService();
		
		for(int i=0; i<bsId.length;i++) {
			result =  mservice.changeApproval(bsId[i]);
			System.out.println(result);
		}
		
		
		
		
		RequestDispatcher view = null;
		if(result > 0) {
			
			view=request.getRequestDispatcher("bapprov");
			request.setAttribute("page", "1");
			request.setAttribute("apvbtnMessage", "승인 완료!");
			request.setAttribute("business_email", bsEmail);
			request.setAttribute("mailMessage", "메일이 발송되었습니다");
			
			view.forward(request, response);
			System.out.println("appvbtn오니");
		}else {
			view = request.getRequestDispatcher("views/adminMy/admin_my.jsp");
			request.setAttribute("tab", "tab-3");
			request.setAttribute("apvFailMessage", "승인 실패!");
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
