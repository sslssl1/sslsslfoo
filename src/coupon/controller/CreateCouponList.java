package coupon.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import coupon.model.service.CouponService;
import coupon.model.vo.Coupon;
import coupon.model.vo.Coupon_l;
import member.model.vo.Business;
import member.model.vo.Customer;
import oracle.sql.DATE;
import java.util.Date;
/**
 * Servlet implementation class CreateCouponList
 */
@WebServlet("/createclist")
public class CreateCouponList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**=
     * @see HttpServlet#HttpServlet()
     */
    public CreateCouponList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");//인코등
		System.out.println("여기까지 완료");
		
	
		 
	
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
				
		HttpSession session = request.getSession(); 
		String userId = null;
		if(session.getAttribute("loginMember") != null ) {
			userId = ((Business)session.getAttribute("loginMember")).getBusiness_Id();
			
		}else {
			// 로그인 안되있거나 개인사용자가 아닌 유저가 접근했을경우 else임.
						// 차단시켜야함
						System.out.println("미확인유저 나가라!");
		}
	
		CouponService cservice = new CouponService();

	
		
		// 한 페이지당 출력할 목록 갯수 지정ㅇ
				
				ArrayList<Coupon> clist = cservice.getCreateCouponList(userId);
				
			
				
			
				
				response.setContentType("text/html; charset=utf-8");
				
				RequestDispatcher view=null;
				if(clist.size() > 0) {
					view= request.getRequestDispatcher("views/businessMy/business_my.jsp");
					request.setAttribute("tab", "tab-0");
					request.setAttribute("clist", clist);
					view.forward(request, response);
				}else {
					view= request.getRequestDispatcher("views/businessMy/business_my.jsp");
					request.setAttribute("tab", "tab-0");
					request.setAttribute("clistcouponMessage", "쿠폰없음");
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
