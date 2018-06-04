package review.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import review.model.service.ReviewService;
import review.model.vo.*;


/**
 * Servlet implementation class CustomerMyReviewListServlet
 */
@WebServlet("/cusmyreviewlist")
public class CustomerMyReviewListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerMyReviewListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userId=request.getParameter("userId");
		int page=Integer.parseInt(request.getParameter("page"));
		String seType=request.getParameter("seType");
		String seValue=request.getParameter("seValue");
		System.out.println("userId :" +userId);
		System.out.println("page :" +page);
		System.out.println("seType :" +seType);
		System.out.println("seValue :" +seValue);
		int limit = 10;
		
		SearchReview sr = new SearchReview();
		sr.setCurrentPage(page);
		sr.setLimit(limit);
		sr.setSeType(seType);
		sr.setSeValue(seValue);
		sr.setUserId(userId);
		
		ReviewService reviewService = new ReviewService();
		ArrayList<Review> rlist = reviewService.cusMyReviewList(sr);
		int listCount =  reviewService.cusMyReviewCount(sr);
		
		int maxPage = (int)((double)listCount / limit + 0.9);
		int startPage = (((int)((double)page / limit + 0.9))
				- 1) * limit + 1;
		int endPage = startPage + limit - 1;
		
		if(maxPage < endPage)
			endPage = maxPage;
		////////////////
		
		JSONObject json = new JSONObject();
		JSONArray jarr = new JSONArray();
		for(Review review :rlist )
		{
			JSONObject js = new JSONObject();
			js.put("no", review.getNumber());
			js.put("title", review.getReview_title());
			js.put("date", review.getReview_Date().toString());
			js.put("count", review.getReview_Count());
		
			jarr.add(js);
		}
		json.put("list", jarr);
		json.put("currentPage", page);
		json.put("maxPage",maxPage);
		json.put("startPage",startPage);
		json.put("endPage",endPage);
		
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out= response.getWriter();
		out.append(json.toJSONString());
		out.flush();
		out.close();
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
