package foodtruck.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

import foodtruck.model.service.FoodtruckService;
import foodtruck.model.vo.FoodTop3;
import foodtruck.model.vo.Foodtruck;

/**
 * Servlet implementation class FoodTruckListServlet
 */
@WebServlet("/flist")
public class FoodTruckListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FoodTruckListServlet() {
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
		
		ArrayList<Foodtruck> list = new FoodtruckService().allList();

		ArrayList<FoodTop3> riceTop3List = new ArrayList<FoodTop3>();
		ArrayList<FoodTop3> snackTop3List = new ArrayList<FoodTop3>();
		ArrayList<FoodTop3> desertTop3List = new ArrayList<FoodTop3>();
		
		riceTop3List = new FoodtruckService().getRiceTop3List();
		snackTop3List = new FoodtruckService().getSnackTop3List();
		desertTop3List= new FoodtruckService().getDesertTop3List();
		
		response.setContentType("text/html; charset=utf-8");
		RequestDispatcher view = null;
		if(list.size()>0)
		{
			view = request.getRequestDispatcher("views/food_Truck/food_Truck.jsp");
			request.setAttribute("list", list);
			request.setAttribute("riceTop3List", riceTop3List);
			request.setAttribute("snackTop3List", snackTop3List);
			request.setAttribute("desertTop3List", desertTop3List);
			
			view.forward(request, response);
		}else
		{
			
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
