package reply.controller;

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

import com.sun.org.apache.xml.internal.security.keys.keyresolver.implementations.RSAKeyValueResolver;

import reply.model.service.ReplyService;
import reply.model.vo.Reply;
import reply.model.vo.UnderReply;
import reply.model.vo.*;

/**
 * Servlet implementation class CustomerInserUnderReply
 */
@WebServlet("/cInsertUnderReply")
public class CustomerInserUnderReply extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerInserUnderReply() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 상세 후기 보기에서 댓글에 댓글 추가하는 서블릿
			request.setCharacterEncoding("utf-8");
			int rno = Integer.parseInt(request.getParameter("rno"));
			String cid = request.getParameter("cid");
			String bid =request.getParameter("bid");
			String content = request.getParameter("content");
			//인서트 부분
			ReplyService rSerive = new ReplyService();
			
			rSerive.underCInsert(rno,cid,bid,content);
				
			//인서트 하고 나서 다시조회
			response.setContentType("text/html); charset=utf-8");
			
			ArrayList<UnderReply> rlist=new ReplyService().underSelect();
			
			JSONObject json = new JSONObject();
			JSONArray jarr = new JSONArray();
			
			for(UnderReply reply : rlist) {
				
				JSONObject j = new JSONObject();
				j.put("replyuNo", reply.getReply_No());
				if(reply.getUnder_CsWriter() != null) {
					j.put("replyuCid", reply.getUnder_CsWriter());
				}else if(reply.getUnder_BsWriter() != null) {
					j.put("replyuBid", reply.getUnder_BsWriter());	
				}
				j.put("replyuContent", URLEncoder.encode(reply.getUnder_Content(), "utf-8"));
				j.put("relpyuDate", reply.getUnder_Date().toString());
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
