package festival.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import festival.model.service.FestivalService;
import festival.model.vo.Festival_Truck;
import foodtruck.model.vo.Foodtruck;

/**
 * Servlet implementation class TruckListServlet
 */
@WebServlet("/trucklist.do")
public class TruckListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TruckListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    /** 참여트럭 리스트 컨트롤러 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int festivalNo=Integer.parseInt(request.getParameter("fNum"));
		String truckId=request.getParameter("truckid");
		
		//System.out.println("해당 축제 넘버 : "+festivalNo);
		ArrayList<Foodtruck> truckList=new FestivalService().truckList(festivalNo);
		
		JSONObject json=new JSONObject();
		JSONArray jarr=new JSONArray();
		
			for(Foodtruck ft : truckList) {
				JSONObject job=new JSONObject();
					job.put("truckName",ft.getTruck_Name());
					job.put("businessid", ft.getBusiness_Id());
					
					System.out.println("트럭이름: "+ft.getTruck_Name());
					jarr.add(job);
			}
			
			json.put("truckName", jarr);
			
			response.setContentType("application/json; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(json.toJSONString());
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
