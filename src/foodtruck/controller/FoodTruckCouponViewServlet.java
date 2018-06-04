package foodtruck.controller;

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

import foodtruck.model.service.FoodtruckService;
import foodtruck.model.vo.FoodCoupon;

/**
 * Servlet implementation class FoodTruckCouponViewServlet
 */
@WebServlet("/fcview")
public class FoodTruckCouponViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FoodTruckCouponViewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		String bid=request.getParameter("bid");
		System.out.println("biddddd"+bid);
		
		FoodtruckService fservice = new FoodtruckService();
		ArrayList<FoodCoupon> coupon_list = fservice.foodCouponList(bid);
		
		JSONObject json = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		for(FoodCoupon fc : coupon_list) {
			JSONObject j= new JSONObject();
			j.put("fname", URLEncoder.encode(fc.getTruck_name(),"utf-8"));
			j.put("ccontent", URLEncoder.encode(fc.getCoupon_content(),"utf-8"));
			j.put("cdate", fc.getCoupon_date().toString());
			jarr.add(j);
		}
		
		json.put("list", jarr);
		
		response.setContentType("text/html; charset=utf-8");
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
