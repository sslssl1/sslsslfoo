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
 * Servlet implementation class FestivalInfoServlet
 */
@WebServlet("/fes_info")
public class FestivalInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FestivalInfoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    /** 축제 상세페이지 관련 컨트롤러 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int festivalNo=Integer.parseInt(request.getParameter("fesNo"));
		
		//System.out.println("선택한 축제번호: "+festivalNo);
		
		Festival festival = new FestivalService().festivalInfo(festivalNo);
		ArrayList<String> fImage = new FestivalService().imagesInfo(festivalNo);
		//Festival_Truck fTruck=new FestivalService().selectTruck(festivalNo);
		
		response.setContentType("text/html; charset=utf-8");
		RequestDispatcher view=null;
		
			if(festival != null) {
				view= request.getRequestDispatcher("views/festival/festival_Info.jsp");
				request.setAttribute("fImage", fImage);
				request.setAttribute("festival", festival);
		//		request.setAttribute("fTruck", fTruck);
				
				view.forward(request, response);
				
			}else {
				view=request.getRequestDispatcher("views/festival/festival_error.html");
				request.setAttribute("message", "실패!!!!!!!!");
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
