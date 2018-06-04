package reply.model.service;

import java.sql.*;
import java.util.*;
import static common.Jdbctemplate.*;

import reply.model.dao.*;
import reply.model.vo.*;

public class ReplyService {

	public ReplyService() {
	}

	public ArrayList<Reply> cusReplyList(String userId, int currentPage, int limit) {
		Connection con = getConnection();
		ArrayList<Reply> list = new ReplyDao().cusReplyList(con,userId,currentPage,limit);
		
		close(con);
		
		return list;
	}

	public int cusReplyCount(String userId) {
		Connection con = getConnection();
		int count = new ReplyDao().cusReplyCount(con,userId);
		
		close(con);
		
		return count;
	}

	public void deleteCusReply(int reply_no) {
		Connection con = getConnection();
		
		int result = new ReplyDao().deleteCusReply(con,reply_no);
		
		if(result>0)
		{
			commit(con);
		}else {
			rollback(con);
		}
		
		close(con);
	}
	public ArrayList<Reply> busReplyList(String userId, int currentPage, int limit) {
		Connection con = getConnection();
		ArrayList<Reply> list = new ReplyDao().busReplyList(con,userId,currentPage,limit);
		
		close(con);
		
		return list;
	}

	public int busReplyCount(String userId) {
		Connection con = getConnection();
		int count = new ReplyDao().busReplyCount(con,userId);
		
		close(con);
		
		return count;
	}

	public void deleteBusReply(int reply_no) {
		Connection con = getConnection();
		
		int result = new ReplyDao().deleteBusReply(con,reply_no);
		
		if(result>0)
		{
			commit(con);
		}else {
			rollback(con);
		}
		close(con);
	}
	
	public ArrayList<Reply> customerDetailReplyList(int review_no) {
		// 내 후기상세보기
		Connection con =getConnection();
		
		ArrayList<Reply> list = new ReplyDao().customerDetailReplyList(con,review_no);
		
		close(con);
		
		return list;
	}

	public int customerInsertReply(int review_no, String cutomerId,String bId, String content) {
		// 리플달기
		Connection con = getConnection();
		int result = new ReplyDao().customerInsertReply(con, review_no, cutomerId,bId, content);
		
		if(result > 0) {
			commit(con);
		}else {
			rollback(con);
		}
		close(con);
		return result;
	}

	public int underCInsert(int rno, String cid,String bid, String content) {
		// 리플에 리플달기
		Connection con = getConnection();
		int result = new ReplyDao().underCInsert(con, rno,cid,bid,content);
		if(result >0) {
			commit(con);
		}else {
			rollback(con);
		}
		close(con);
		return result;
	}
	
	public int underBInsert(int rno, String bid, String content) {
		// 리플에 리플달기
		Connection con = getConnection();
		int result = new ReplyDao().underBInsert(con, rno,bid,content);
		if(result >0) {
			commit(con);
		}else {
			rollback(con);
		}
		close(con);
		return result;
	}
	

	public ArrayList<UnderReply> underSelect() {
		
		Connection con=getConnection();
		ArrayList<UnderReply> list = new ReplyDao().underSelect(con);
		close(con);
		
		return list;
	}

	public int modifyReply(int replyNo, String content) {
		Connection con =getConnection();
		
		int result = new ReplyDao().modifyReply(con,replyNo,content);
		if(result >0) {
			commit(con);
		}else {
			rollback(con);
		}
		close(con);
	
		return result;
		
	}

	public int deleteReply(int replyNo) {
		Connection con=getConnection();
		
		int result = new ReplyDao().deleteReply(con,replyNo);
		
		if(result >0) {
			commit(con);
		}else {
			rollback(con);
		}
		close(con);
	
		return result;
		
	}

	public int modifyUnderReply(int replyNo, String content) {
		Connection con =getConnection();
		
		int result = new ReplyDao().modifyUnderReply(con,replyNo,content);
		if(result >0) {
			commit(con);
		}else {
			rollback(con);
		}
		close(con);
	
		return result;
		
	}

	public int deleteUnderReply(int replyNo) {
		Connection con=getConnection();
		
		int result = new ReplyDao().deleteUnderReply(con,replyNo);
		
		if(result >0) {
			commit(con);
		}else {
			rollback(con);
		}
		close(con);
	
		return result;
		
	}

	
	
}
