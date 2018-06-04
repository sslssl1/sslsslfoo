package total.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import total.model.service.TotalService;
import total.model.vo.Total;

/**
 * Servlet implementation class MemberTotalServlet
 */
@WebServlet("/mtotal")
public class MemberTotalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberTotalServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		request.setCharacterEncoding("utf-8");
		ArrayList<Total> tlist=new TotalService().total();
		for(int i=0; i<6; i++) {
		System.out.println("서블릭 접속했다. "+tlist.get(i).getT_total());
		}
		response.setContentType("text/html; charset=utf-8");
		RequestDispatcher view=null;
		if(tlist.size()>0) {
			view =request.getRequestDispatcher("views/adminMy/admin_my.jsp");
			request.setAttribute("tlist", tlist);
			request.setAttribute("tab", "tab-6");
			view.forward(request, response);
		}else {
			
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
