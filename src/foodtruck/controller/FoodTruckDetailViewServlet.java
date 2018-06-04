package foodtruck.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import foodtruck.model.service.FoodtruckService;
import foodtruck.model.vo.*;
import review.model.vo.*;
import review.model.service.*;

/**
 * Servlet implementation class FoodTruckDetailViewServlet
 */
@WebServlet("/foodtruckdetail")
public class FoodTruckDetailViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FoodTruckDetailViewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		response.setContentType("text/html;  charset=utf-8; ");
		//BusinessId로 FoodTruck 정보 가져와야함
		String businessId =  request.getParameter("bId");
		Foodtruck truck = new FoodtruckService().selectTruck(businessId);
		if(truck==null)
		{
			RequestDispatcher view = request.getRequestDispatcher("views/businessMy/business_my.jsp");
			request.setAttribute("businessPageFail", "트럭정보를 입력하세요.");
			request.setAttribute("tab", "tab-6");
			view.forward(request, response);
		}
		///메뉴는 리스트임!
		ArrayList<Menu> mlist = new FoodtruckService().selectMenu(businessId);
		
		int limit = 10;
		int currentPage=1;
		SearchReview sr = new SearchReview(businessId, currentPage, limit, "REVIEW_TITLE", "");
		ReviewService rservice = new ReviewService();
		ArrayList<Review> review_list = new ReviewService().bsSelectReview(sr);
		int listCount = rservice.getBusReviewCount(sr);
	
		int maxPage = (int)((double)listCount / limit + 0.9);
		//현재 페이지 그룹(10개페이지를 한그룹처리)에 보여줄 시작 페이지수
		//현재 페이지가 13페이지이면 그룹은 11 ~ 20페이지가 보여지게함
		int startPage = (((int)((double)currentPage / limit + 0.9))- 1) * limit + 1;
		int endPage = startPage + limit - 1;
		if(maxPage < endPage)
			endPage = maxPage;
		
		double tgrade = new ReviewService().avgTruckGrade(businessId);
		//+BusinessId로 메뉴정보 가져와야함
		if(truck!=null)
		{//성공
			RequestDispatcher view = request.getRequestDispatcher("views/businessPage/business_page.jsp");
			request.setAttribute("mlist", mlist);
			request.setAttribute("truck", truck);
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("maxPage", maxPage);
			request.setAttribute("startPage", startPage);
			request.setAttribute("endPage", endPage);
			request.setAttribute("review_list", review_list);
			request.setAttribute("tgrade", tgrade);
			if(request.getParameter("tab")!=null)
			request.setAttribute("tab", request.getParameter("tab"));
			if(request.getParameter("write")!=null)
			request.setAttribute("write", request.getParameter("write"));
			view.forward(request, response);
		}else
		{//실패
			
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
