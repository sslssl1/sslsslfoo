package foodtruck.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import foodtruck.model.service.FoodtruckService;
import foodtruck.model.vo.Foodtruck;
import foodtruck.model.vo.Menu;

/**
 * Servlet implementation class BusinessTruckInfoServlet
 */
@WebServlet("/bstruckinfo")
public class BusinessTruckInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BusinessTruckInfoServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");

		String businessId = request.getParameter("bsId");
		Foodtruck truck = new FoodtruckService().selectTruck(businessId);
		ArrayList<Menu> mlist = new FoodtruckService().selectMenu(businessId);

		response.setContentType("text/html; charset=utf-8");
		RequestDispatcher view = request.getRequestDispatcher("views/businessMy/business_my.jsp");

		if(truck!=null) {
		request.setAttribute("mlist", mlist);
		request.setAttribute("truck", truck);
		request.setAttribute("tab", "tab-6");
		view.forward(request, response);
		}else {
			request.setAttribute("newBusiness", "newBusiness");
			request.setAttribute("tab", "tab-6");
			view.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
