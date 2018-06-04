package reply.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.vo.Business;
import reply.model.service.ReplyService;
import reply.model.vo.Reply;

/**
 * Servlet implementation class BusinessReplyListServlet
 */
@WebServlet("/bsreplylist")
public class BusinessReplyListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BusinessReplyListServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		String userId = null;
		if (request.getSession().getAttribute("loginMember") != null) {
			userId = ((Business) request.getSession().getAttribute("loginMember")).getBusiness_Id();

		} else {
			System.out.println("미확인유저 나가라!");
			response.sendRedirect("/food/index.jsp");
		}

		int currentPage = 1;
		if (request.getParameter("page") != null)
			currentPage = Integer.parseInt(request.getParameter("page"));

		int limit = 10;

		System.out.println("id: " + userId + "\npage: " + currentPage + "\nlimit: " + limit);
		ArrayList<Reply> list = new ReplyService().busReplyList(userId, currentPage, limit);

		int count = new ReplyService().busReplyCount(userId);

		int maxPage = (int) ((double) count / limit + 0.9);
		int startPage = (((int) ((double) currentPage / limit + 0.9)) - 1) * limit + 1;
		int endPage = startPage + limit - 1;

		if (maxPage < endPage)
			endPage = maxPage;

		response.setContentType("text/html; charset=utf-8;");
		RequestDispatcher view = null;
		if (list.size() > 0) {
			view = request.getRequestDispatcher("views/businessMy/business_my.jsp");
			request.setAttribute("reply_list", list);
			request.setAttribute("tab", "tab-3");
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("maxPage", maxPage);
			request.setAttribute("startPage", startPage);
			request.setAttribute("endPage", endPage);
			request.setAttribute("listCount", count);
			view.forward(request, response);
		} else {
			ArrayList<Reply> rl = new ArrayList<Reply>();
			
			view = request.getRequestDispatcher("views/businessMy/business_my.jsp");
			request.setAttribute("rl", rl);
			request.setAttribute("tab", "tab-3");
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("maxPage", maxPage);
			request.setAttribute("startPage", startPage);
			request.setAttribute("endPage", endPage);
			request.setAttribute("listCount", count);
			request.setAttribute("noneMessage","내 댓글 없음");
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
