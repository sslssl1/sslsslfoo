package review.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import review.model.service.ReviewService;

/**
 * Servlet implementation class CustomerReviewDeleteServlet
 */
@WebServlet("/csreviewdel")
public class CustomerReviewDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CustomerReviewDeleteServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String no[]=null;

		if(request.getParameterValues("delCheck")!=null) {
		 no= request.getParameterValues("delCheck");
	
		}
		ReviewService rservice = new ReviewService();
		
		for(int i=0; i<no.length ;i++)
		{
			rservice.deleteCusReview(Integer.parseInt(no[i]));
		}
		
		response.setCharacterEncoding("text/html;  charset=utf-8;");
		
		response.sendRedirect("/food/csreview");
		
		
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
