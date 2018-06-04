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
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import member.model.vo.Member;
import reply.model.service.ReplyService;
import reply.model.vo.Reply;
import reply.model.vo.UnderReply;

/**
 * Servlet implementation class CustomerDetailReplyList
 */
@WebServlet("/cDetailReplyList")
public class CustomerDetailReplyList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerDetailReplyList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 유저 내 후기 보기 디테일 서블릿
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html); charset=utf-8");
		int review_no = Integer.parseInt(request.getParameter("rNo"));
		ReplyService rSerive = new ReplyService();
		ArrayList<Reply> rList = rSerive.customerDetailReplyList(review_no);
		
		ArrayList<UnderReply> ulist=new ReplyService().underSelect();
		
		JSONObject json = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		for(Reply reply : rList) {
			
			JSONObject j = new JSONObject();
			j.put("replyNumber", reply.getReply_No());
			if(reply.getReply_Cs_Writer() != null) {
				j.put("replyCsWriter", reply.getReply_Cs_Writer());
			}else if(reply.getReply_Bs_Writer() != null) {
				j.put("replyBsWriter", reply.getReply_Bs_Writer());
			}

			j.put("replyContent", URLEncoder.encode(reply.getReply_Content(), "utf-8"));
			j.put("relpyDate", reply.getReply_Date().toString());
			jarr.add(j);
		}
		
		for(UnderReply ureply : ulist) {
			
			JSONObject j = new JSONObject();
			j.put("replyuNo", ureply.getReply_No());
			j.put("replyuuNo", ureply.getUnder_No());
			if(ureply.getUnder_CsWriter() != null) {
			j.put("replyuCid", ureply.getUnder_CsWriter());
			}else {
				j.put("replyuCid", null);
			}
			
			if(ureply.getUnder_BsWriter() != null) {
			j.put("replyuBid", ureply.getUnder_BsWriter());
			}else {
				j.put("replyuBid", null);
			}
			j.put("replyuContent", URLEncoder.encode(ureply.getUnder_Content(), "utf-8"));
			j.put("relpyuDate", ureply.getUnder_Date().toString());
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
