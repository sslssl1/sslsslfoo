package foodtruck.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import foodtruck.model.service.FoodtruckService;

/**
 * Servlet implementation class FoodTruckOnOffServlet
 */
@WebServlet("/onoff")
public class FoodTruckOnOffServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FoodTruckOnOffServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		String userid = request.getParameter("userid");
		System.out.println("userid:"+userid);
		String onoffState =request.getParameter("state");
		int result=0;
		System.out.println("onoffState:"+onoffState);
		
		
		if(onoffState.equals("N")) {
		 result = new FoodtruckService().foodOn(userid);	
		 
		}
		else if(onoffState.equals("Y")) {
		 result =new FoodtruckService().foodOff(userid);
		 if(result>0)
				result=2;
		}
		
		
		if(result>0) {
			PrintWriter out=response.getWriter();
			out.append(String.valueOf(result));
			
		}
		
		} 
	

	/**1
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
