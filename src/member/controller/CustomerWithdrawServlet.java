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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import member.model.service.MemberService;
import member.model.vo.Customer;

/**
 * Servlet implementation class CustomerWithdrawServlet
 */
@WebServlet("/cWithdraw")
public class CustomerWithdrawServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerWithdrawServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		String customerId = request.getParameter("cId");
		System.out.println("cId : " + customerId);
		
		int result = new MemberService().customerWithdraw(customerId);
		// 여기위에 까지가 회원탈퇴 처리
		
		//여기부터 탈퇴처리후 게시판 새로고침
		
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
			
			System.out.println("고객강퇴json : " + json.toJSONString());
			
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print(json.toJSONString());
			out.flush();
			out.close();
		
		
		
		
		/*RequestDispatcher view =null;
		if(result > 0) {
			view = request.getRequestDispatcher("views/adminMy/admin_my.jsp");
			request.setAttribute("tab", "tab-1");
			view.forward(request, response);
			
		}else {
			System.out.println("관리자페이지 고객 탈퇴서블릿 오류");
		}
		
		*/

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
