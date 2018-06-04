package foodtruck.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import foodtruck.model.vo.*;
import foodtruck.model.service.*;
import java.util.*;
/**
 * Servlet implementation class FoodTruckSearchList
 */
@WebServlet("/trucksearchlist")
public class FoodTruckSearchListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FoodTruckSearchListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		String ctType = request.getParameter("ctType");
		String seType = request.getParameter("seType");
		String seValue = request.getParameter("seValue");
		SearchTruck st = new SearchTruck(ctType, seType, seValue);
		ArrayList<Foodtruck> list = new FoodtruckService().searchTruckList(st);

		
		JSONObject json = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		for(Foodtruck ft : list)
		{
			JSONObject j = new JSONObject();
			j.put("bsId", ft.getBusiness_Id());
			j.put("tName",URLEncoder.encode(ft.getTruck_Name() ,"utf-8"));
			j.put("tImg", ft.getTruck_Img());
			j.put("tMainMenu", URLEncoder.encode( ft.getTruck_MainMenu() ,"utf-8"));
			j.put("tLive", ft.getTruck_Live_OnOff());
			jarr.add(j);
		}
		json.put("list", jarr);
		
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out= response.getWriter();
	out.append(json.toJSONString());
	out.flush();
	out.close();
		
		
		response.setContentType("text/html; charset=utf-8");
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
