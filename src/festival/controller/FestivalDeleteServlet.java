package festival.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import festival.model.service.FestivalService;
import festival.model.vo.Festival;

/**
 * Servlet implementation class FestivalDeleteServlet
 */
@WebServlet("/fes_delete")
public class FestivalDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FestivalDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    /** 축제리스트 삭제 컨트롤러 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int festivalNo=Integer.parseInt(request.getParameter("fesNo"));
		
		int deleteTruck=new FestivalService().deleteTruck(festivalNo);
		int deleteImages=new FestivalService().deleteImages(festivalNo);
		int deleteFestival=new FestivalService().deleteFestival(festivalNo);
			
			if(deleteImages > 0 && deleteFestival > 0 && deleteTruck >0) {
				response.sendRedirect("/food/fes_show");
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
