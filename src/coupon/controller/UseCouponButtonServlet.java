package coupon.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import coupon.model.service.CouponService;


/**
 * Servlet implementation class UseCouponButtonServlet
 */
@WebServlet("/usecouponbtn")
public class UseCouponButtonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UseCouponButtonServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
		request.setCharacterEncoding("utf-8");
		
		
		String csId  = request.getParameter("cuid");
		String cno= request.getParameter("coupon_no");
		
		
		
		int result = new CouponService().changeCouponUsed(csId,Integer.parseInt(cno));
		System.out.println("result: "+result+" "+csId+" "+cno);
		RequestDispatcher view = null;
		if(result>0) {
			view = request.getRequestDispatcher("bclist");			
			
			request.setAttribute("couponMessage", "쿠폰사용완료!");
			

			view.forward(request, response);
			
		}else{
			view=request.getRequestDispatcher("bclist");
			request.setAttribute("couponMessage", "쿠폰사용실패!");
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
