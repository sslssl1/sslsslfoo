package reply.controller;

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

import reply.model.service.ReplyService;
import reply.model.vo.Reply;

/**
 * Servlet implementation class CustomerInsertReply
 */
@WebServlet("/cInsertReply")
public class CustomerInsertReply extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerInsertReply() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 상세 후기 보기에서 리플 추가하는 서블릿
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html); charset=utf-8");
		int review_no = Integer.parseInt(request.getParameter("rNo"));
		String cutomerId = request.getParameter("cId");
		String bId = request.getParameter("bId");
		String content = request.getParameter("content");
		
		//인서트 부분
		ReplyService rSerive = new ReplyService();
		int result = rSerive.customerInsertReply(review_no,cutomerId,bId,content);
		
		//인서트 하고 나서 다시조회
		
ArrayList<Reply> rList = rSerive.customerDetailReplyList(review_no);
		
		JSONObject json = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		for(Reply reply : rList) {
			
			JSONObject j = new JSONObject();
			j.put("replyCsWriter", reply.getReply_Cs_Writer());
			j.put("replyContent", URLEncoder.encode(reply.getReply_Content(), "utf-8"));
			j.put("relpyDate", reply.getReply_Date().toString());
			j.put("replyNumber", reply.getReply_No());
			jarr.add(j);
		}
		
		json.put("list", jarr);
		
		
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
