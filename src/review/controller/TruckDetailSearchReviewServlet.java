package review.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


import review.model.service.ReviewService;
import review.model.vo.*;

/**
 * Servlet implementation class TruckDetailSearchReviewServlet
 */
@WebServlet("/trucksearchreview")
public class TruckDetailSearchReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TruckDetailSearchReviewServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");

		SearchReview sr =new SearchReview();
		sr.setCurrentPage(1);
		sr.setLimit(10);
		sr.setSeType(request.getParameter("seType"));
		sr.setSeValue(request.getParameter("seValue"));
		
		
		ArrayList<Review> list = new ReviewService().searchTruckReview(sr);
		
		
		response.setContentType("text/html; charset=utf-8;");
		
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
