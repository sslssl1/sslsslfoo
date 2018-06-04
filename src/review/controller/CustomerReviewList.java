package review.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.vo.*;
import review.model.service.ReviewService;
import review.model.vo.*;

/**
 * Servlet implementation class CustomerReviewList
 */
@WebServlet("/csreview")
public class CustomerReviewList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CustomerReviewList() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 일반 회원 내가쓴 리뷰 목록 서블렛

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		// 로그인 회원정보 가져옴
		// 현 서블릿은 개인사용자만 접근 가능한 페이지임
		HttpSession session = request.getSession();
		String userId = null;
		if (session.getAttribute("loginMember") != null && session.getAttribute("loginMember") instanceof Customer) {
			userId = ((Customer) session.getAttribute("loginMember")).getCustomer_Id();
		} else {
			// 로그인 안되있거나 개인사용자가 아닌 유저가 접근했을경우 else임.
			// 차단시켜야함
			System.out.println("미확인유저 나가라!");
		}

		int currentPage = 1;
		if (request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}

		ReviewService rservice = new ReviewService();

		int listCount = 0;
		listCount = rservice.getCusReviewCount(userId);

		// 한 페이지당 출력할 목록 갯수 지정
		int limit = 10;
		ArrayList<Review> rlist = rservice.getCusReviewList(userId,currentPage,limit);
		
		//총 페이지수 계산 : 목록이 1개일 때 1페이지로 처리
		int maxPage = (int)((double)listCount / limit + 0.9);
		//현재 페이지 그룹(10개페이지를 한그룹처리)에 보여줄 시작 페이지수
		//현재 페이지가 13페이지이면 그룹은 11 ~ 20페이지가 보여지게함
		int startPage = (((int)((double)currentPage / limit + 0.9))
				- 1) * limit + 1;
		int endPage = startPage + limit - 1;
		
		if(maxPage < endPage)
			endPage = maxPage;
		
		
		
		RequestDispatcher view = null;
		if(rlist.size() > 0) {
			view= request.getRequestDispatcher("views/userMy/user_my.jsp");
			request.setAttribute("review_list", rlist);
			request.setAttribute("tab", "tab-2");
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("maxPage", maxPage);
			request.setAttribute("startPage", startPage);
			request.setAttribute("endPage", endPage);
			request.setAttribute("listCount", listCount);
			view.forward(request, response);
		}else {
			System.out.println("CustomerReviewList Page Error");
			view= request.getRequestDispatcher("views/userMy/user_my.jsp");
			request.setAttribute("tab", "tab-2");
			request.setAttribute("message", "리뷰 조회 실패");
			view.forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
