package review.model.dao;

import static common.Jdbctemplate.*;
import review.model.vo.*;
import review.controller.ReviewDetailServlet;
import review.model.service.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

public class ReviewDao {
	

	public int reviewWrite(Connection con, Review review) {
		int result=0;
		
		String sql="insert into review values(sq_review_no.nextval,?,?,?,?,?,0,sysdate)";

		PreparedStatement pstmt = null;

		try {
			pstmt=con.prepareStatement(sql);
			
			pstmt.setString(1, review.getBusiness_Id());
			pstmt.setString(2, review.getReview_Writer());
			pstmt.setString(3, review.getReview_title());
			pstmt.setString(4, review.getReview_Content());
			pstmt.setInt(5, review.getReview_Grade());
			
			result=pstmt.executeUpdate();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		
		return result;
	}

	public List<Review> selectList(Connection con,String user_id) {
		List<Review> list = new ArrayList<Review>();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String query = "select * from review where REVIEW_WRITER = ? order by review_no asc";
		
		try {
			pstmt =con.prepareStatement(query);
			pstmt.setString(1, user_id);
			rset= pstmt.executeQuery();
			
			while(rset.next()) {
				Review review = new Review();
				
				review.setBusiness_Id(rset.getString("business_id"));
				review.setNumber(rset.getInt("REVIEW_NO"));
				review.setReview_Content(rset.getString("review_Content"));
				review.setReview_Date(rset.getDate("review_Date"));
				review.setReview_Grade(rset.getInt("REVIEW_GRADE"));
				review.setReview_Writer(rset.getString("review_Writer"));
				review.setReview_title(rset.getString("review_title"));
				review.setReview_Count(rset.getInt("REVIEW_COUNT"));
				System.out.println(review);
				list.add(review);
			}
			
			
		} catch (Exception e) {
			System.out.println("ReviewDao.selectList: "+e.getMessage());
				e.printStackTrace();
		} 
		return list;
	}

	public int getCusReviewCount(Connection con,String userId) {

		int listCount=0;
		ResultSet rset =  null;
		PreparedStatement pstmt= null;
		String sql = " select count(*) from review where REVIEW_WRITER = ?  ";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);
			rset=pstmt.executeQuery();
			if(rset.next())
			{
				listCount=	rset.getInt("count(*)");
			}
			
			
		} catch (Exception e) {
			System.out.println("ReviewDao.getCusReviewNumber: "+e.getMessage());
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		
		return listCount;
	}

	public ArrayList<Review> getCusReviewList(Connection con, String userId,int currentPage ,int limit) {
	
		ArrayList<Review> rlist = new ArrayList<Review>();
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		String sql = " select * from (select rownum rnum,REVIEW_COUNT, REVIEW_NO,BUSINESS_ID, REVIEW_WRITER, REVIEW_TITLE, REVIEW_CONTENT, REVIEW_GRADE, REVIEW_DATE from( select REVIEW_COUNT, REVIEW_NO,BUSINESS_ID,REVIEW_WRITER,REVIEW_TITLE,REVIEW_CONTENT,REVIEW_GRADE,REVIEW_DATE from review where REVIEW_WRITER = ?   order by REVIEW_NO desc )) where rnum>= ? and rnum<= ? ";
		
		try {
			int startRow = (currentPage - 1) * limit + 1;
			int endRow = startRow + limit - 1;
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			rset = pstmt.executeQuery();
			
			while(rset.next())
			{
	Review review = new Review();
				
				review.setBusiness_Id(rset.getString("business_id"));
				review.setNumber(rset.getInt("REVIEW_NO"));
				review.setReview_Content(rset.getString("review_Content"));
				review.setReview_Date(rset.getDate("review_Date"));
				review.setReview_Grade(rset.getInt("REVIEW_GRADE"));
				review.setReview_Writer(rset.getString("review_Writer"));
				review.setReview_title(rset.getString("review_title"));
				review.setReview_Count(rset.getInt("REVIEW_COUNT"));
			
				rlist.add(review);
			}
			
			
		} catch (Exception e) {
			System.out.println("ReviewDao.getCusReviewList: "+e.getMessage());
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
	
		
		return rlist;
	}

	public ArrayList<Review> searchCusReviewList(Connection con, SearchReview sr) {
		
		ArrayList<Review> rlist = new ArrayList<Review>();
		ResultSet rset=null;
		String sql = " select * from ( select rownum rnum, REVIEW_NO,BUSINESS_ID,REVIEW_WRITER,REVIEW_TITLE,REVIEW_CONTENT,REVIEW_GRADE,REVIEW_DATE from ( select REVIEW_NO,BUSINESS_ID,REVIEW_WRITER,REVIEW_TITLE,REVIEW_CONTENT,REVIEW_GRADE,REVIEW_DATE from review where REVIEW_WRITER = ?  and "+sr.getSeType()+" like '%"+sr.getSeValue()+"%'  order by REVIEW_NO desc )) " + 
				"where rnum>= ? and rnum<= ? ";
		PreparedStatement pstmt = null;
		
		try {
			int startRow = (sr.getCurrentPage() - 1) * sr.getLimit() + 1;
			int endRow = startRow + sr.getLimit() - 1;
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, sr.getUserId());
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			rset = pstmt.executeQuery();
			
			while(rset.next())
			{
	Review review = new Review();
				
				review.setBusiness_Id(rset.getString("business_id"));
				review.setNumber(rset.getInt("REVIEW_NO"));
				review.setReview_Content(rset.getString("review_Content"));
				review.setReview_Date(rset.getDate("review_Date"));
				review.setReview_Grade(rset.getInt("REVIEW_GRADE"));
				review.setReview_Writer(rset.getString("review_Writer"));
				review.setReview_title(rset.getString("review_title"));
			
				rlist.add(review);
			}
			
			
		} catch (Exception e) {
			System.out.println("ReviewDao.searchCusReviewList: "+e.getMessage());
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		
		
		return rlist;
	}

	public int deleteCusReview(Connection con, int review_no) {
	
		String sql = "delete from REVIEW where REVIEW_NO = ? ";
		int result = 0;
		PreparedStatement pstmt = null;
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, review_no);
			result=pstmt.executeUpdate();
			
			
			
		} catch (Exception e) {
			System.out.println("ReviewDao.deleteCusReview: "+e.getMessage());
			e.printStackTrace();
		}finally {
			close(pstmt);
		}

		return result;
	}

	public ArrayList<Review> bsSelectReview(Connection con, SearchReview sr) {
		ArrayList<Review> rlist = new ArrayList<Review>();
		String sql  = "select * from(select rownum rnum, REVIEW_COUNT,REVIEW_NO,BUSINESS_ID,REVIEW_WRITER,REVIEW_TITLE,REVIEW_CONTENT,REVIEW_GRADE,REVIEW_DATE  from ( select  REVIEW_COUNT,REVIEW_NO,BUSINESS_ID,REVIEW_WRITER,REVIEW_TITLE,REVIEW_CONTENT,REVIEW_GRADE,REVIEW_DATE from review where BUSINESS_ID = ? and "+sr.getSeType()+" like '%"+sr.getSeValue()+"%'  order by REVIEW_NO desc  )) where rnum>= ? and rnum <= ? ";
		PreparedStatement pstmt= null;
		ResultSet rset = null;
		try {
			int startRow = (sr.getCurrentPage() - 1) * sr.getLimit() + 1;
			int endRow = startRow + sr.getLimit() - 1;
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, sr.getUserId());
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			rset = pstmt.executeQuery();
			
			while(rset.next())
			{
	Review review = new Review();
				
				review.setBusiness_Id(rset.getString("business_id"));
				review.setNumber(rset.getInt("REVIEW_NO"));
				review.setReview_Content(rset.getString("review_Content"));
				review.setReview_Date(rset.getDate("review_Date"));
				review.setReview_Grade(rset.getInt("REVIEW_GRADE"));
				review.setReview_Writer(rset.getString("review_Writer"));
				review.setReview_title(rset.getString("review_title"));
				review.setReview_Count(rset.getInt("REVIEW_COUNT"));
			
				rlist.add(review);
			}
			
			
//			
//			int startRow = (currentPage - 1) * limit + 1;
//			int endRow = startRow + limit - 1;
//			pstmt=con.prepareStatement(sql);
//			pstmt.setString(1, userId);
//			pstmt.setInt(2, startRow);
//			pstmt.setInt(3, endRow);
//			rset = pstmt.executeQuery();
			
			
		} catch (Exception e) {
			System.out.println("ReviewDao.bsSelectReview: "+e.getMessage());
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		
		return rlist;
	}

	public Double avgTruckGrade(Connection con, String businessId) {
		
		
		Double tgrade= 0.0;
		String sql = "select avg(REVIEW_GRADE) from review where BUSINESS_ID = ? ";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, businessId);
			rset = pstmt.executeQuery();
			if(rset.next())
			{
				tgrade = rset.getDouble("avg(REVIEW_GRADE)");
				System.out.println(businessId+" : "+tgrade);
			}
			else
			{
				tgrade=5.0;
			}
			
		} catch (Exception e) {
			System.out.println("ReviewDao.avgTruckGrade: "+e.getMessage());
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return tgrade;
	}

	public int getBusReviewCount(Connection con, SearchReview sr) {
		int count = 0;
		String sql = " select count(*) from review where BUSINESS_ID = ? and "+sr.getSeType()+" like '%"+sr.getSeValue()+"%'";
		PreparedStatement  pstmt = null;
		ResultSet rset = null;
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, sr.getUserId());
			rset= pstmt.executeQuery();
			if(rset.next())
			{
				count = rset.getInt("count(*)");
			}
			
			
		} catch (Exception e) {
			System.out.println("ReviewDao.getBusReviewCount: "+e.getMessage());
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return count;
	}

	public DetailReview reviewDetail(Connection con, int reviewNo) {
		
		DetailReview review = null;
		String sql = "select TRUCK_NAME, rv.BUSINESS_ID,REVIEW_NO, REVIEW_WRITER, REVIEW_TITLE, REVIEW_CONTENT, REVIEW_GRADE, REVIEW_COUNT, REVIEW_DATE from review rv, foodtruck ft where rv.BUSINESS_ID = ft.BUSINESS_ID and   REVIEW_NO = ?";
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		
//		private int	REVIEW_NO;
//		private String	REVIEW_WRITER;
//		private String	REVIEW_TITLE;
//		private String	REVIEW_CONTENT;
//		private int	REVIEW_GRADE;
//		private int	REVIEW_COUNT;
//		private Date REVIEW_DATE;
//		private String TRUCK_NAME;
		
		try {	
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, reviewNo);
			rset=pstmt.executeQuery();
			
			if(rset.next())
			{
				review= new DetailReview();
				
				review.setBUSINESS_ID(rset.getString("BUSINESS_ID"));
				review.setREVIEW_CONTENT(rset.getString("REVIEW_CONTENT"));
				review.setREVIEW_COUNT(rset.getInt("REVIEW_COUNT"));
				review.setREVIEW_DATE(rset.getDate("REVIEW_DATE"));
				review.setREVIEW_GRADE(rset.getInt("REVIEW_GRADE"));
				review.setREVIEW_NO(rset.getInt("REVIEW_NO"));
				review.setREVIEW_TITLE(rset.getString("REVIEW_TITLE"));
				review.setREVIEW_WRITER(rset.getString("REVIEW_WRITER"));
				review.setTRUCK_NAME(rset.getString("TRUCK_NAME"));
			}
			
			
		} catch (Exception e) {
			System.out.println("ReviewDao.reviewDetail: "+e.getMessage());
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
			
		return review;
	}

	public int addReviewCount(Connection con, int reviewNo) {
		int result = 0;

		PreparedStatement pstmt = null;
		String sql = "update review set REVIEW_COUNT = (select REVIEW_COUNT+1 from review where REVIEW_NO = ?) where REVIEW_NO = ?";
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, reviewNo);
			pstmt.setInt(2, reviewNo);
			result=pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("ReviewDao.addReviewCount: "+e.getMessage());
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		
		return result;
	}

	public ArrayList<Review> cusMyReviewList(Connection con, SearchReview sr) {
		String sql=" select * from ( select rownum rnum, r.* from ( select * from review where REVIEW_WRITER = ? and "+sr.getSeType()+" like '%"+sr.getSeValue()+"%'  order by REVIEW_NO desc ) r )where rnum >= ? and rnum <= ? ";
		ArrayList<Review> rlist = new ArrayList<Review>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		int startRow = (sr.getCurrentPage() - 1) * sr.getLimit() + 1;
		int endRow = startRow + sr.getLimit() - 1;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, sr.getUserId());
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			rset = pstmt.executeQuery();
			
			while(rset.next())
			{
	Review review = new Review();
				
				review.setBusiness_Id(rset.getString("business_id"));
				review.setNumber(rset.getInt("REVIEW_NO"));
				review.setReview_Content(rset.getString("review_Content"));
				review.setReview_Date(rset.getDate("review_Date"));
				review.setReview_Grade(rset.getInt("REVIEW_GRADE"));
				review.setReview_Writer(rset.getString("review_Writer"));
				review.setReview_title(rset.getString("review_title"));
				review.setReview_Count(rset.getInt("REVIEW_COUNT"));
			
				rlist.add(review);
			}
			
			
		} catch (Exception e) {
			System.out.println("ReviewDao.cusMyReviewList: "+e.getMessage());
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return rlist;
	}

	public int cusMyReviewCount(Connection con, SearchReview sr) {
		String sql ="select count(*) from review where REVIEW_WRITER = ? and "+sr.getSeType()+" like '%"+sr.getSeValue()+"%' ";
		int count = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, sr.getUserId());
			rset=pstmt.executeQuery();
			if(rset.next())
			{
				count=rset.getInt("count(*)");
			}
			
		} catch (Exception e) {
			System.out.println("ReviewDao.cusMyReviewCount: "+e.getMessage());
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		
		return count;
	}
	public ArrayList<Review> reviewmodify(Connection con, String rno) {
		ArrayList<Review> list=new ArrayList<Review>();
		
		Statement stmt=null;
		ResultSet rset=null;
		String sql="select * from Review where REVIEW_NO='"+rno+"'";
		
		try {
			
			
			while(rset.next()) {
				Review r=new Review();
				r.setReview_title(rset.getString("REVIEW_TITLE"));
				//r.set
				
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			close(rset);
			close(stmt);
		}
		
		return list;
	}

	public int reviewWriteModify(Connection con, Review review) {
int result=0;
		
		String sql="update review set BUSINESS_ID=?,REVIEW_WRITER=?,REVIEW_TITLE=?,REVIEW_CONTENT=?,REVIEW_GRADE=?,REVIEW_COUNT=?,REVIEW_DATE=sysdate where REVIEW_NO=?";

		PreparedStatement pstmt = null;

		try {
			pstmt=con.prepareStatement(sql);
			
			pstmt.setString(1, review.getBusiness_Id());
			pstmt.setString(2, review.getReview_Writer());
			pstmt.setString(3, review.getReview_title());
			pstmt.setString(4, review.getReview_Content());
			pstmt.setInt(5, review.getReview_Grade());
			pstmt.setInt(6, review.getReview_Count());
			pstmt.setInt(7, review.getNumber());
			
			result=pstmt.executeUpdate();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		
		return result;
	}
}
