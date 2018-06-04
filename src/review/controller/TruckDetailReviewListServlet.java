package review.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
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
 * Servlet implementation class TruckDetailReviewListServlet
 */
@WebServlet("/truckdetailreviewlist")
public class TruckDetailReviewListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TruckDetailReviewListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		
		int currentPage = 1;
		if (request.getParameter("page") != null) {
			currentPage = Integer.parseInt(request.getParameter("page"));
		}
		
		String bsid = null;
		if(request.getParameter("bsid")!=null) {
			bsid = request.getParameter("bsid");
		}
	
		ReviewService rservice = new ReviewService();
		int limit = 10;
		int listCount = 0;
		
		String seType = request.getParameter("seType");
		String seValue = request.getParameter("seValue");
		SearchReview sr = new SearchReview(bsid, currentPage, limit, seType, seValue);
		listCount = rservice.getBusReviewCount(sr);
		// 한 페이지당 출력할 목록 갯수 지정
		ArrayList<Review> review_list = rservice.bsSelectReview(sr);
		
		//총 페이지수 계산 : 목록이 1개일 때 1페이지로 처리
		int maxPage = (int)((double)listCount / limit + 0.9);
		//현재 페이지 그룹(10개페이지를 한그룹처리)에 보여줄 시작 페이지수
		//현재 페이지가 13페이지이면 그룹은 11 ~ 20페이지가 보여지게함
		int startPage = (((int)((double)currentPage / limit + 0.9))- 1) * limit + 1;
		int endPage = startPage + limit - 1;
		
		if(maxPage < endPage)
			endPage = maxPage;
		
		JSONObject json = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		for(Review r : review_list)
		{
			
			
			JSONObject j= new JSONObject();
		j.put("rno", r.getNumber());
		j.put("rcount",r.getReview_Count() );	
		j.put("rdate",r.getReview_Date().toString() );	
		j.put("rwriter", r.getReview_Writer());	
	 j.put("rtitle", URLEncoder.encode(r.getReview_title(),"utf-8"));
		jarr.add(j);
		}
		
	
		
		json.put("startPage",startPage );
		json.put("endPage",endPage );
		json.put("maxPage",maxPage );
		json.put("currentPage",currentPage );
		
		json.put("list",jarr);
		System.out.println("json : "+json.toJSONString());
		
		 response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(json.toJSONString());
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
