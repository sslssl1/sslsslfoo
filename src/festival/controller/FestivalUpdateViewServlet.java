package festival.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspPage;

import festival.model.service.FestivalService;
import festival.model.vo.Festival;
import festival.model.vo.Festival_Image;

/**
 * Servlet implementation class FestivalUpdateViewServlet
 */
@WebServlet("/fes_upview")
public class FestivalUpdateViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FestivalUpdateViewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    /** 축제수정 페이지 연결 컨트롤러 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int fesNo=Integer.parseInt(request.getParameter("fNo"));
		
		ArrayList<String> img_list=new FestivalService().imagesInfo(fesNo);
		Festival festival = new FestivalService().festivalInfo(fesNo);	
		
		response.setContentType("text/html; charset=utf-8");
		
		RequestDispatcher view=null;
	
		if(festival != null) {
			view= request.getRequestDispatcher("views/festival/festival_update.jsp");
			request.setAttribute("img_list", img_list);
			request.setAttribute("festival", festival);
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
