package review.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import review.model.service.ReviewService;
import review.model.vo.*;

/**
 * Servlet implementation class ReviewDetailServlet
 */
@WebServlet("/reviewdetail")
public class ReviewDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReviewDetailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		request.setCharacterEncoding("utf-8");
		
		int reviewNo = Integer.parseInt( request.getParameter("reviewNo"));
	
		
		ReviewService reviewService = new ReviewService();
		
		reviewService.addReviewCount(reviewNo);
		
		DetailReview review = reviewService.reviewDetail(reviewNo);
		
		
		
		response.setContentType("text/html;  charset=utf-8;");
		RequestDispatcher view = request.getRequestDispatcher("views/review/review_detail.jsp");
		request.setAttribute("review", review);
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
