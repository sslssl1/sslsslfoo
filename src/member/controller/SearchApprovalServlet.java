package member.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.service.MemberService;
import member.model.vo.ApprovalSearch;
import member.model.vo.Business;
import review.model.service.ReviewService;
import review.model.vo.Review;
import review.model.vo.SearchReview;
import member.model.vo.*;
/**
 * Servlet implementation class SearchApprovalServlet
 */
@WebServlet("/searchapproval")
public class SearchApprovalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchApprovalServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String businessId = null;
		int currentPage = 1;
		if (request.getParameter("Page") != null) {
			currentPage = Integer.parseInt(request.getParameter("Page"));
		}

		MemberService mservice = new MemberService();

				//검색 타입
				String seType = request.getParameter("seType");
				//검색 값
				String seValue = request.getParameter("seValue");
		
		int listCount = 0;
		listCount = mservice.getBusApproval_SearchCount(seType,seValue);
		
		
		// 한 페이지당 출력할 목록 갯수 지정
		int limit = 10;
		
		
		ArrayList<Business> blist = mservice.searchBusApprovalList(new ApprovalSearch(limit, currentPage, seType, seValue));
		for(Business b : blist) {
			System.out.println(b);
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
		if(blist.size() > 0) {
			
			view = request.getRequestDispatcher("views/adminMy/admin_my.jsp");
			request.setAttribute("tab", "tab-3");
			request.setAttribute("list",blist);
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("maxPage", maxPage);
			request.setAttribute("startPage", startPage);
			request.setAttribute("endPage", endPage);
			request.setAttribute("listCount", listCount);
			view.forward(request, response);
			System.out.println("검색결과 있니?");
		}else {
			
			view = request.getRequestDispatcher("views/adminMy/admin_my.jsp");
			request.setAttribute("tab", "tab-3");
			request.setAttribute("noSearchArpproval", "검색결과없음");
			view.forward(request, response);
			System.out.println("검색결과 없니?");
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
