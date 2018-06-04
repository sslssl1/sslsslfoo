package festival.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import festival.model.service.FestivalService;
import festival.model.vo.Festival;

/**
 * Servlet implementation class FestivalCalendarShowServlet
 */
@WebServlet("/cal_show")
public class FestivalCalendarShowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FestivalCalendarShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    /** 캘린더 정보출력 컨트롤러 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<Festival> show = new FestivalService().festivalList();
		
		response.setContentType("text/html; charset=utf-8");
		RequestDispatcher view = null;
		
//		System.out.println(show);
		
		view = request.getRequestDispatcher("views/festival/festival_calendar.jsp");
		request.setAttribute("show", show);
		view.forward(request, response);
		
//		if(show.size() > -1) {
//		}else {
//			view=request.getRequestDispatcher("views/festival/festival_error.html");
//			view.forward(request, response);
//			
//		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
