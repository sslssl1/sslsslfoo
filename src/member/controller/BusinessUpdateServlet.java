package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.service.MemberService;
import member.model.vo.Business;
import member.model.vo.Customer;
import member.model.vo.Member;

/**
 * Servlet implementation class BusinessUpdateServlet
 */
@WebServlet("/bupdate.pwenc")
public class BusinessUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BusinessUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
		String userId=request.getParameter("userId");
		String userPwd=request.getParameter("userPwd");
		String email=request.getParameter("email");
		
		Business business = new Business();
		
		business.setBusiness_Id(userId);
		business.setBusiness_Pwd(userPwd);
		business.setBusiness_Email(email);
		
		System.out.println("servlet : "+userId+userPwd+email);
		
		int result=new MemberService().bsmyupdate(business);
		
		response.setContentType("text/html; charset=utf-8");
		
		if(result>0) {
			Business newBusiness= (Business)request.getSession().getAttribute("loginMember");
			
			HttpSession session = request.getSession(false);
			
			
			
			if(session!=null)
			{
				session.invalidate();
				
				Business loginMember = new Business();
				
				loginMember.setBusiness_Id(userId);
				loginMember.setBusiness_Email(email);
				loginMember.setBusiness_Pwd(userPwd);
				loginMember.setBusiness_Name(newBusiness.getBusiness_Name());
				loginMember.setWithdraw(newBusiness.getWithdraw());
				loginMember.setApproval(newBusiness.getApproval());
				loginMember.setBUSINESS_REJECT_REASON(newBusiness.getBUSINESS_REJECT_REASON());
				loginMember.setBusiness_Phone(newBusiness.getBusiness_Phone());

				session= request.getSession(true);
				session.setAttribute("loginMember", loginMember);
				
			}
			
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
