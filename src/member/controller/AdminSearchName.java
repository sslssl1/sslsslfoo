package member.controller;

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

import member.model.service.MemberService;
import member.model.vo.ApprovalSearch;
import member.model.vo.Customer;

/**
 * Servlet implementation class AdminSearchName
 */
@WebServlet("/aSearchName")
public class AdminSearchName extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminSearchName() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		MemberService mService = new MemberService();
		int currentPage = 1;
		if(request.getParameter("page") != null) {
		 currentPage = Integer.parseInt(request.getParameter("page"));
		}
		
		
		String seType = request.getParameter("cSeType");
		String seValue =  request.getParameter("cSeValue");
		
		
		int listCount =0 ;
		
		int limit = 10;
		
		listCount = mService.adminSearchName(seType,seValue);
		System.out.println("adminSearchName servlet"+seType + "  " + seValue);
		// 여기까지는 통과
		
		ArrayList<Customer> cList = mService.adminSearchNameList(new ApprovalSearch(limit, currentPage, seType, seValue));
	
		
		//총 페이지 수 계산 : 목록이 1개일때 1페이지로 처리
		int maxPage = (int)((double)listCount / limit + 0.9);
		
		// 현재 페이지 그룹(10개 페이지를 한그룹처리)에 보여줄 시작 페이지수
		// 현재 페이지가 13페이지이면 그룹은 11~20페이지가 보여지게함
		
		int startPage = (((int)((double)currentPage / limit + 0.9))-1) * limit + 1;
		int endPage = startPage + limit -1;
		
		if(maxPage < endPage)
			endPage = maxPage;
		
		JSONObject json = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		for(Customer customer : cList)
		{
			JSONObject j = new JSONObject();
			j.put("customerId", customer.getCustomer_Id());
			j.put("customerName", URLEncoder.encode(customer.getCustomer_Name(), "utf-8"));
			j.put("customerEmail", customer.getCustomer_Email());
			
			jarr.add(j);
		}
		
			json.put("startPage", startPage);
			json.put("endPage", endPage);
			json.put("maxPage", maxPage);
			json.put("currentPage", currentPage);
			
			json.put("list", jarr);
			
			System.out.println("관리자 고객 조회 서치 json : " + json.toJSONString());
			
			response.setContentType("application/json; charset=UTF-8");
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
