package review.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.output.WriterOutputStream;

import review.model.service.ReviewService;
import review.model.vo.Review;

/**
 * Servlet implementation class ReviewWriteServlet
 */
@WebServlet("/reviewwrite")
public class ReviewWriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReviewWriteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		int rno=0;
		int count=0;
		String check="0";
		if(request.getParameter("rno") != null || request.getParameter("count") !=null) {
			rno=Integer.parseInt(request.getParameter("rno"));
			count = Integer.parseInt(request.getParameter("count"));
			check=request.getParameter("check");
		}
		
		String bid=request.getParameter("bid");
		String lid=request.getParameter("lid");
		String title=request.getParameter("title");
		String content=request.getParameter("content");
		int starpoint=Integer.parseInt(request.getParameter("starpoint"));
		
		
		int result=0;
		if(!(check.equals("1"))) {
		Review review=new Review();
		review.setReview_title(title);
		review.setReview_Content(content);
		review.setBusiness_Id(bid);
		review.setReview_Writer(lid);
		review.setReview_Grade(starpoint);

		result=new ReviewService().reviewWrite(review);
		}else if(check.equals("1")) {
			Review review=new Review();
			review.setNumber(rno);
			review.setReview_title(title);
			review.setReview_Content(content);
			review.setBusiness_Id(bid);
			review.setReview_Writer(lid);
			review.setReview_Grade(starpoint);
			review.setReview_Count(count);
			
			result=new ReviewService().reviewWriteModify(review);
		}
		System.out.println("몇이냐" + result);
		response.setContentType("text/html; charset=utf-8;");
		
		if(result > 0) 
		{
			PrintWriter out = response.getWriter();
			out.flush();
			out.close();
		}else{
			
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
