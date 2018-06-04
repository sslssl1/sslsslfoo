package foodtruck.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import foodtruck.model.service.FoodtruckService;
import sun.tools.jar.resources.jar;

/**
 * Servlet implementation class SearchAutoComplete
 */
@WebServlet("/searchautocomplete")
public class SearchAutoComplete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchAutoComplete() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		String seType = request.getParameter("seType");
		String seValue = request.getParameter("seValue");
		
		ArrayList<String> valuesList =null;
		
		if(seType.equals("TRUCK_NAME"))
		{
			valuesList = new FoodtruckService().searchAutoCompleteTruck(seValue);
		}else if (seType.equals("MENU_NAME"))
		{
			valuesList = new FoodtruckService().searchAutoCompleteMenu(seValue);
		}
		
		JSONObject json=new JSONObject();
		JSONArray jarr = new JSONArray();
		
		for(String s : valuesList)
		{
			JSONObject j = new JSONObject();
			j.put("values",s );
			jarr.add(j);
		}
		json.put("list", jarr);
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(json.toJSONString());
		out.flush();
		out.close();

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
