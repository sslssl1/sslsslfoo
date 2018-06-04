package member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import member.model.service.MemberService;
import member.model.vo.Customer;
import member.model.vo.Manager;

import member.model.vo.Member;

/**
 * Servlet implementation class AdminAllSearch
 */
@WebServlet("/adminAllSearchList")
public class AdminAllSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminAllSearch() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 관리자 모든 회원정보보기
		
		HttpSession session = request.getSession(true);
		
		String userId = null;
		if(session.getAttribute("loginMember") !=null
				&& session.getAttribute("loginMember") instanceof Manager) {
			userId = ((Manager)session.getAttribute("loginMember")).getManager_Id();
		}else {
			//관리자아닌경우
			System.out.println("관리자 아님");
		}
		
		
		MemberService mService = new MemberService();
		int currentPage = 1;
		if(request.getParameter("page") != null) {
		 currentPage = Integer.parseInt(request.getParameter("page"));
		}
		
		int listCount =0 ;
		
		int limit = 10;
		
		listCount = mService.getAllSearchCount();
		ArrayList<Customer> cList = mService.getAllList(currentPage, limit);
		
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
			
			System.out.println("어드민얼서치json : " + json.toJSONString());
			
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print(json.toJSONString());
			out.flush();
			out.close();
		
		
		/*response.setContentType("text/html; charset=utf-8");
		RequestDispatcher view = null;
		if(cList.size() > 0) {
			view = request.getRequestDispatcher("views/adminMy/admin_my.jsp");
			request.setAttribute("adminAllSearch", cList);
			request.setAttribute("tab", "tab-1");
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("maxPage", maxPage);
			request.setAttribute("startPage", startPage);
			request.setAttribute("endPage", endPage);
			request.setAttribute("listCount", listCount);
			view.forward(request, response);
			System.out.println("어드민얼서치서블릿" + cList);
		}else {
			System.out.println("AdminAllSearch Page Error");
			view = request.getRequestDispatcher("views/adminMy/admin_my.jsp");
			request.setAttribute("tab", "tab-1");
			request.setAttribute("message", "조회 실패!!");
		}*/
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
