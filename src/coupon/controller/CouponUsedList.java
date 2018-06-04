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
import coupon.model.vo.Coupon_l;
import member.model.vo.Customer;

/**
 * Servlet implementation class CouponUsedList
 */
@WebServlet("/cusedlist")
public class CouponUsedList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CouponUsedList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");//인코등
		System.out.println("여기까지 완료");
		HttpSession session = request.getSession(); 
		String userId = null;
		if(session.getAttribute("loginMember") != null ) {
			userId = ((Customer)session.getAttribute("loginMember")).getCustomer_Id();
			
		}else {
			// 로그인 안되있거나 개인사용자가 아닌 유저가 접근했을경우 else임.
						// 차단시켜야함
						System.out.println("미확인유저 나가라!");
		}
		int currentPage = 1;
		if (request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}

		CouponService cservice = new CouponService();

		int listCount = 0;
		listCount = cservice.getUsedCouponCount(userId);

		// 한 페이지당 출력할 목록 갯수 지정
		int limit = 10;
		ArrayList<Coupon_l> clist = cservice.getUsedCouponList(userId,currentPage,limit);
		
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
		
		
		response.setContentType("text/html; charset=utf-8");
		RequestDispatcher view = null;
		if(clist.size() > 0) {
			view= request.getRequestDispatcher("views/userMy/user_my.jsp");
			request.setAttribute("coupon_list", clist);
			request.setAttribute("tab", "tab-5");
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("maxPage", maxPage);
			request.setAttribute("startPage", startPage);
			request.setAttribute("endPage", endPage);
			request.setAttribute("listCount", listCount);
			view.forward(request, response);
		}else {
			view= request.getRequestDispatcher("views/userMy/user_my.jsp");
			request.setAttribute("couponMessage", "사용쿠폰없음");
			request.setAttribute("tab", "tab-5");
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
