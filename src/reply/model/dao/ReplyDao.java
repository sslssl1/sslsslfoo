package reply.model.dao;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import reply.model.vo.*;
import static common.Jdbctemplate.*;

import reply.model.vo.Reply;

public class ReplyDao {

	public ReplyDao() {
	}

	public ArrayList<Reply> cusReplyList(Connection con, String userId, int currentPage, int limit) {
		ArrayList<Reply> list = new ArrayList<Reply>();
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		
		String sql = " select * from ( select rownum rn, REPLY_NO, REVIEW_NO, REPLY_CS_WRITER, REPLY_CONTENT, REPLY_DATE from reply where REPLY_CS_WRITER = ? order by REPLY_NO asc  ) where rn >= ? and rn <= ? ";
		int startRow = (currentPage - 1) * limit + 1;
		int endRow = startRow + limit - 1;

		try {

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			rset = pstmt.executeQuery();
			// REPLY_NO NUMBER
			// REVIEW_NO NUMBER
			// REPLY_CS_WRITER VARCHAR2(20 BYTE)
			// REPLY_BS_WRITER VARCHAR2(20 BYTE)
			// REPLY_CONTENT VARCHAR2(1000 BYTE)
			// REPLY_DATE DATE
			while (rset.next()) {
				Reply r = new Reply();
				r.setReply_No(rset.getInt("REPLY_NO"));
				r.setReview_No(rset.getInt("REVIEW_NO"));
				r.setReply_Cs_Writer(rset.getString("REPLY_CS_WRITER"));
				r.setReply_Content(rset.getString("REPLY_CONTENT"));
				r.setReply_Date(rset.getDate("REPLY_DATE"));
				list.add(r);
			}

		} catch (Exception e) {
			System.out.println("ReplyDao.cusReplyList :  " + e.getMessage());
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return list;
	}

	public int cusReplyCount(Connection con, String userId) {

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int count = 0;
		String sql = " select count(*) from reply where REPLY_CS_WRITER = ? ";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				count = rset.getInt("count(*)");
			}

		} catch (Exception e) {
			System.out.println("ReplyDao.cusReplyCount :  " + e.getMessage());
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return count;
	}

	public int deleteCusReply(Connection con, int reply_no) {
		
		int result = 0;
		
		PreparedStatement pstmt = null;
		String sql = "delete from reply where REPLY_NO = ? ";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, reply_no);
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("ReplyDao.deleteCusReply :  " + e.getMessage());
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}
	public ArrayList<Reply> busReplyList(Connection con, String userId, int currentPage, int limit) {
		ArrayList<Reply> list = new ArrayList<Reply>();
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		
		String sql = " select * from ( select rownum rn, REPLY_NO, REVIEW_NO, REPLY_BS_WRITER, REPLY_CONTENT, REPLY_DATE from reply where REPLY_BS_WRITER = ? order by REPLY_NO asc  ) where rn >= ? and rn <= ? ";
		int startRow = (currentPage - 1) * limit + 1;
		int endRow = startRow + limit - 1;

		try {
		

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			rset = pstmt.executeQuery();
			// REPLY_NO NUMBER
			// REVIEW_NO NUMBER
			// REPLY_CS_WRITER VARCHAR2(20 BYTE)
			// REPLY_BS_WRITER VARCHAR2(20 BYTE)
			// REPLY_CONTENT VARCHAR2(1000 BYTE)
			// REPLY_DATE DATE
			while (rset.next()) {
				Reply r = new Reply();
				r.setReply_No(rset.getInt("REPLY_NO"));
				r.setReview_No(rset.getInt("REVIEW_NO"));
				r.setReply_Bs_Writer(rset.getString("REPLY_BS_WRITER"));
				r.setReply_Content(rset.getString("REPLY_CONTENT"));
				r.setReply_Date(rset.getDate("REPLY_DATE"));
				list.add(r);
			}

		} catch (Exception e) {
			System.out.println("ReplyDao.busReplyList :  " + e.getMessage());
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return list;
	}

	public int busReplyCount(Connection con, String userId) {

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int count = 0;
		String sql = " select count(*) from reply where REPLY_BS_WRITER = ? ";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				count = rset.getInt("count(*)");
			}

		} catch (Exception e) {
			System.out.println("ReplyDao.busReplyCount :  " + e.getMessage());
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return count;
	}

	public int deleteBusReply(Connection con, int reply_no) {
		
		int result = 0;
		
		PreparedStatement pstmt = null;
		String sql = "delete from reply where REPLY_NO = ? ";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, reply_no);
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("ReplyDao.deleteBusReply :  " + e.getMessage());
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}

	public ArrayList<Reply> customerDetailReplyList(Connection con, int review_no) {
		// 이용후기상세보기
		ArrayList<Reply> list = new ArrayList<Reply>();
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		
		String sql = " select * from ( select rownum rn, REPLY_NO, REVIEW_NO, REPLY_CS_WRITER,REPLY_BS_WRITER, REPLY_CONTENT, REPLY_DATE from reply where REVIEW_NO = ? order by REPLY_DATE asc  )  ";

		try {

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, review_no);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				Reply r = new Reply();
				r.setReply_No(rset.getInt("REPLY_NO"));
				r.setReview_No(rset.getInt("REVIEW_NO"));
				r.setReply_Cs_Writer(rset.getString("REPLY_CS_WRITER"));
				r.setReply_Bs_Writer(rset.getString("REPLY_BS_WRITER"));				
				r.setReply_Content(rset.getString("REPLY_CONTENT"));
				r.setReply_Date(rset.getDate("REPLY_DATE"));
				list.add(r);
			}

		} catch (Exception e) {
			System.out.println("ReplyDao.customerDetailReplyList :  " + e.getMessage());
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

	public int customerInsertReply(Connection con, int review_no, String cutomerId,String bId, String content) {
		//리플 인서트 다오
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sql ="insert into reply values (sq_reply_no.nextval, ?, ?, ?, ?, sysdate)";
		try {
			pstmt = con.prepareStatement(sql);
			
			if(cutomerId != null) {
			pstmt.setInt(1, review_no);
			pstmt.setString(2, cutomerId);
			pstmt.setString(3, null);
			pstmt.setString(4, content);
			}else if(bId != null) {
			pstmt.setInt(1, review_no);
			pstmt.setString(2, null);
			pstmt.setString(3, bId);
			pstmt.setString(4, content);
			}
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("ReplyDao.customerInsertReply :  " + e.getMessage());
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}

	public int underCInsert(Connection con, int rno, String cid,String bid, String content) {
		// 댓글의댓글 등록 Dao
		int result = 0;
		PreparedStatement pstmt =null;
		
		String sql="insert into under_reply values (sq_reply_no.nextval, ?, ?, ?, ?, sysdate)";
		try {
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, rno);
			
			if(cid != null) {
				pstmt.setString(2, cid);
				pstmt.setString(3, null);
			}else if(bid != null) {
				pstmt.setString(2, null);
				pstmt.setString(3, bid);
			}
			pstmt.setString(4, content);
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("ReplyDao.customerInsertUnderReply :  " + e.getMessage());
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}
	
	public int underBInsert(Connection con, int rno, String bid, String content) {
		// 댓글의댓글 등록 Dao
		int result = 0;
		PreparedStatement pstmt =null;
		
		String sql="insert into under_reply values (sq_reply_no.nextval, ?, null, ?, ?, sysdate)";
		try {
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, rno);
			pstmt.setString(2, bid);
			pstmt.setString(3, content);
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("ReplyDao.businessInsertUnderReply :  " + e.getMessage());
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}

	public ArrayList<UnderReply> underSelect(Connection con) {
		ArrayList<UnderReply> list = new ArrayList<UnderReply>();
		ResultSet rset = null;
		Statement stmt = null;
		
		String sql = " select * from UNDER_REPLY";

		try {

			stmt=con.createStatement();
			rset=stmt.executeQuery(sql);
			while (rset.next()) {
				UnderReply ur = new UnderReply();
				ur.setReply_No(rset.getInt("REPLY_NO"));
				ur.setUnder_No(rset.getInt("UNDER_REPLY_NO"));
				ur.setUnder_CsWriter(rset.getString("UNDER_REPLY_CS_WRITER"));
				ur.setUnder_BsWriter(rset.getString("UNSER_REPLY_BS_WRITER"));
				ur.setUnder_Content(rset.getString("UNDER_REPLY_CONTENT"));
				ur.setUnder_Date(rset.getDate("UNDER_REPLY_DATE"));
				list.add(ur);
			}
			

		} catch (Exception e) {
			System.out.println("ReplyDao.customerDetailReplyList :  " + e.getMessage());
			e.printStackTrace();
		} finally {
			close(rset);
			close(stmt);
		}
		return list;
	}

	public int modifyReply(Connection con, int replyNo, String content) {
		PreparedStatement pstmt=null;
		String sql="update reply set REPLY_CONTENT=? where REPLY_NO=?";
		int result=0;
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, content);
			pstmt.setInt(2, replyNo);
			result=pstmt.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("ReplyDao.modifyReply :  " + e.getMessage());
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		
		return result;
	}

	public int deleteReply(Connection con, int replyNo) {
		PreparedStatement pstmt=null;
		String sql="delete from reply where REPLY_NO=?";
		int result=0;
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, replyNo);
			result=pstmt.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("ReplyDao.deleteReply :  " + e.getMessage());
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}

	public int modifyUnderReply(Connection con, int replyNo, String content) {
		System.out.println(replyNo+content);
		PreparedStatement pstmt=null;
		String sql="update under_reply set UNDER_REPLY_CONTENT=? where UNDER_REPLY_NO=?";
		int result=0;
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, content);
			pstmt.setInt(2, replyNo);
			result=pstmt.executeUpdate();
			System.out.println(result);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("ReplyDao.modifyUnderReply :  " + e.getMessage());
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		
		return result;
	}

	public int deleteUnderReply(Connection con, int replyNo) {
		PreparedStatement pstmt=null;
		String sql="delete from under_reply where UNDER_REPLY_NO=?";
		int result=0;
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, replyNo);
			result=pstmt.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("ReplyDao.deleteUnderReply :  " + e.getMessage());
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}

}
