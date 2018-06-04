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
import festival.model.vo.Festival_Image;

/**
 * Servlet implementation class FestivalListServlet
 */
@WebServlet("/fes_show")
public class FestivalListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FestivalListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ArrayList<Festival> show = new FestivalService().festivalList();
		
		response.setContentType("text/html; charset=utf-8");
		RequestDispatcher view = null;
		
		
		if(show.size() > -1) {
			view = request.getRequestDispatcher("views/festival/festival.jsp");
			request.setAttribute("show", show);
			
			view.forward(request, response);
		}else {
			view=request.getRequestDispatcher("views/festival/festival_error.html");
			view.forward(request, response);
			
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
