package coupon.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import coupon.model.service.CouponService;
import coupon.model.vo.*;
import member.model.vo.Customer;
import review.model.service.ReviewService;

/**
 * Servlet implementation class CouponSearchFoodtruck
 */
@WebServlet("/cserachFoodtruck")
public class CouponSearchFoodtruck extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CouponSearchFoodtruck() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 쿠폰내 가계명 검색
		request.setCharacterEncoding("utf-8");
		//로그인 회원정보 가져옴
		// 현 서블릿은 개인사용자만 접근 가능한 페이지임
		HttpSession session = request.getSession();
		String userId = null;
		if(session.getAttribute("loginMember") != null && session.getAttribute("loginMember") instanceof Customer) {
			userId = ((Customer)session.getAttribute("loginMember")).getCustomer_Id();
		}else {
			//로그인 안되있거나 개인사용자가 아니면 else
			System.out.println("미확인 유저");
		}
		
		int currentPage = 1;
		if(request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		CouponService cservice = new CouponService();
		
		int listCount = 0;
		listCount = cservice.getCustomerCouponCount(userId);
		
		String cSearchFoodtrcuk = request.getParameter("cSearchFoodtrcuk");
		
		int limit = 10;
		
ArrayList<Coupon_l> clist = cservice.searchFoodtruck(userId,currentPage,limit,cSearchFoodtrcuk);
		
		System.out.println("======쿠폰서치");
		
		for(Coupon_l c : clist)  
		{
			System.out.println(c);
		}
		
		
		//총 페이지수 계산 : 목록이 1개일 때 1페이지로 처리
		int maxPage = (int)((double)listCount / limit + 0.9);
		//현재 페이지 그룹(10개페이지를 한그룹처리)에 보여줄 시작 페이지수
		//현재 페이지가 13페이지이면 그룹은 11 ~ 20페이지가 보여지게함
		int startPage = (((int)((double)currentPage / limit + 0.9))
				- 1) * limit + 1;
		int endPage = startPage + limit - 1;
		
		if(maxPage < endPage)
			endPage = maxPage;
		
		
		System.out.println("currentPage : "+currentPage);
		System.out.println("maxPage : "+maxPage);
		System.out.println("startPage : "+startPage);
		System.out.println("endPage : "+endPage);
		System.out.println("listCount : "+listCount);
		
		
		response.setContentType("text/html; charset=utf-8");
		RequestDispatcher view = null;
//		if(clist.size() > 0) {
			view= request.getRequestDispatcher("views/userMy/user_my.jsp");
			request.setAttribute("coupon_list", clist);
			request.setAttribute("tab", "tab-4");
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("maxPage", maxPage);
			request.setAttribute("startPage", startPage);
			request.setAttribute("endPage", endPage);
			request.setAttribute("listCount", listCount);
			view.forward(request, response);
		/*}else {
			view= request.getRequestDispatcher("views/userMy/user_my.jsp");
			request.setAttribute("tab", "tab-4");
			view.forward(request, response);
		}*/
		
		
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
