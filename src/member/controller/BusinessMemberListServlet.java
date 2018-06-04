package member.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.service.MemberService;
import member.model.vo.Business;

/**
 * Servlet implementation class BusinessMemberListServlet
 */
@WebServlet("/blist")
public class BusinessMemberListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BusinessMemberListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	int currentPage = 1;
		
		//전달된 페이지 값 추출
		if(request.getParameter("currentPage")!= null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
			System.out.println(request.getParameter("currentPage"));
		}
		
		//한 페이지당 출력할 목록 갯수 지정
		int limit = 10;
		
		MemberService mservice = new MemberService();
		
		//전체 목록 갯수 조회
		int listCount = mservice.getListBusinessMemberCount();
		
		//현재 페이지에 출력할 목록 조회
		ArrayList<Business> list = mservice.selectBusinessMemberList(currentPage, limit);
		
		//총 페이지 수 계산 : 목록이 1개일 때 1페이지로 처리'
		int maxPage = (int)((double)listCount / limit + 0.9);
		//현재 페이지 그룹(10개페이지를 한그룹처리)에 보여줄 시작 페이지수
		//현재 페이지가 13페이지이면 그룹은 11 ~ 20페이지가 보여지게함
		int startPage = (((int)((double)currentPage / limit + 0.9))
				- 1) * limit + 1;
		int endPage = startPage + limit - 1;
		
		if(maxPage <endPage) {
			endPage = maxPage;
		}
		
		response.setContentType("text/html; charset=utf-8");
		RequestDispatcher view = null;
		
		if(list.size()>0) {
			view =request.getRequestDispatcher("views/adminMy/admin_my.jsp");
			request.setAttribute("list", list);
			request.setAttribute("tab", "tab-2");
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("maxPage", maxPage);
			request.setAttribute("startPage",startPage);
			request.setAttribute("endPage", endPage);
			request.setAttribute("listCount", listCount);
			view.forward(request, response);
			
			
			
		}else {
			view = request.getRequestDispatcher("views/adminMy/admin_my.jsp");
			request.setAttribute("tab", "tab-2");
			request.setAttribute("noSearchArpproval", "검색결과없음");
			view.forward(request, response);
			
		}
		
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
