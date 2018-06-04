package coupon.model.dao;

import static common.Jdbctemplate.*;
import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;
import coupon.model.service.*;
import coupon.model.vo.*;

import review.model.vo.*;

public class CouponDao {

	public ArrayList<Coupon_l> couponIssued(Connection con, String userId, int currentPage, int limit) {

		ArrayList<Coupon_l> ciList = new ArrayList<Coupon_l>();
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		String sql = "select * from ( SELECT rownum rnum, TRUCK_NAME , coupon_content, COUPON_DATE FROM ( select TRUCK_NAME , coupon.coupon_content, coupon.COUPON_DATE from coupon, COUPON_ISSUED coupon_is , FOODTRUCK ft where coupon.COUPON_NO = coupon_is.COUPON_NO and ft.BUSINESS_ID = coupon.BUSINESS_ID and coupon_used = 'N' and customer_id = ? and COUPON_DATE >=sysdate ORDER BY coupon.COUPON_DATE )) WHERE rnum >= ? AND rnum <= ?  ";

		try {
			int startRow = (currentPage - 1) * limit + 1;
			int endRow = startRow + limit - 1;
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);

			rset = pstmt.executeQuery();

			while (rset.next()) {
				Coupon_l c = new Coupon_l();
					c.setCoupon_content(rset.getString("coupon_content"));
					c.setCoupon_date(rset.getDate("COUPON_DATE"));
					c.setTruck_name(rset.getString("TRUCK_NAME"));
					ciList.add(c);
			}

		} catch (Exception e) {
			System.out.println("CouponDao.getCouponIssued: " + e.getMessage());
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return ciList;
	}

	public int getCouponCount(Connection con, String userId) {
		int count = 0;
		String sql = "select count(*) from  COUPON_ISSUED where CUSTOMER_ID = ? and COUPON_USED = 'N' ";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				count = rset.getInt("count(*)");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return count;
	}

	public ArrayList<Coupon_l> couponUsed(Connection con, String userId, int currentPage, int limit) {

		ArrayList<Coupon_l> ciList = new ArrayList<Coupon_l>();
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		System.out.println("couponUsed dao 들어옴");
		String sql = "SELECT * FROM( select rownum rn, TRUCK_NAME , coupon_content, COUPON_ISSUED_DATE   from (select  TRUCK_NAME , coupon.coupon_content, COUPON_ISSUED_DATE from coupon, COUPON_ISSUED coupon_is , FOODTRUCK ft where coupon.COUPON_NO = coupon_is.COUPON_NO and ft.BUSINESS_ID = coupon.BUSINESS_ID and coupon_used = 'Y' and customer_id = ? ORDER BY COUPON_ISSUED_DATE desc)) WHERE rn >= ? AND rn <= ? ";

		try {
			int startRow = (currentPage - 1) * limit + 1;
			int endRow = startRow + limit - 1;
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);

			rset = pstmt.executeQuery();

			while (rset.next()) {
				Coupon_l c = new Coupon_l();

				c.setCoupon_content(rset.getString("coupon_content"));
				c.setCoupon_date(rset.getDate("COUPON_ISSUED_DATE"));
				c.setTruck_name(rset.getString("TRUCK_NAME"));
				ciList.add(c);
				System.out.println(c);
			}

		} catch (Exception e) {
			System.out.println("CouponDao.couponUsed: " + e.getMessage());
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return ciList;
	}

	public int getUsedCouponCount(Connection con, String userId) {
		int count = 0;
		String sql = "select count(*) from  COUPON_ISSUED where CUSTOMER_ID = ? and COUPON_USED = 'Y' ";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				count = rset.getInt("count(*)");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return count;
	}

	public ArrayList<Coupon_l> searchFoodtruck(Connection con, String userId, int currentPage, int limit,
			String cSearchFoodtrcuk) {

		ArrayList<Coupon_l> ciList = new ArrayList<Coupon_l>();
		ResultSet rset = null;
		
		System.out.println("쿠폰 다오 서치푸드트럭 들어옴");
		
		String sql = "SELECT * from ( select rownum rn, TRUCK_NAME , coupon.coupon_content, coupon.COUPON_DATE from coupon, COUPON_ISSUED coupon_is , FOODTRUCK ft " + 
				"            where coupon.COUPON_NO = coupon_is.COUPON_NO and ft.BUSINESS_ID = coupon.BUSINESS_ID and coupon_used = 'N' and customer_id = ?" + 
				"            and ft.TRUCK_NAME like '%"+cSearchFoodtrcuk+"%' " + 
				"            ORDER BY coupon.COUPON_DATE" + 
				"            )" + 
				"            WHERE rn >= ? AND rn <= ? ";
		PreparedStatement pstmt = null;
		System.out.println("id :" + userId +"\n page : "+currentPage+"\nlimit : "+limit+"\ntruck: "+cSearchFoodtrcuk);
		
		try {
			int startRow = (currentPage - 1) * limit + 1;
			int endRow = startRow + limit -1 ;
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
				Coupon_l c = new Coupon_l();

				c.setCoupon_content(rset.getString("coupon_content"));
				c.setCoupon_date(rset.getDate("COUPON_DATE"));
				c.setTruck_name(rset.getString("TRUCK_NAME"));
				ciList.add(c);
				System.out.println(c);
			}
			
			
		} catch (Exception e) {
			System.out.println("CouponDao.searchFoodtruck: " + e.getMessage());
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return ciList;
	}

	
	public ArrayList<Coupon_bl> bcouponIssued(Connection con, String userId, int currentPage, int limit) {
		int result = 0;
		java.util.Date date=new java.util.Date();
		String newstring = new SimpleDateFormat("yyyy-MM-dd").format(date);
		ArrayList<Coupon_bl> biList = new ArrayList<Coupon_bl>();
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		String sql = "select * from ( SELECT rownum rnum, customer_id , coupon_content, COUPON_DATE, COUPON_NO FROM ( select customer_id , coupon.coupon_content, coupon.COUPON_DATE, coupon.COUPON_NO  from coupon, COUPON_ISSUED coupon_is where coupon.COUPON_NO = coupon_is.COUPON_NO and ? = coupon.BUSINESS_ID and coupon_used = 'N' and COUPON_DATE >= sysdate ORDER BY coupon.COUPON_DATE )) WHERE rnum >= ? AND rnum <= ?";

		try {
			int startRow = (currentPage - 1) * limit + 1;
			int endRow = startRow + limit - 1;
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);

			rset = pstmt.executeQuery();
			
			
			while (rset.next()) {
				Coupon_bl bc = new Coupon_bl();

				
					bc.setCoupon_content(rset.getString("coupon_content"));
					bc.setCoupon_date(rset.getDate("COUPON_DATE"));
					bc.setCustomer_id(rset.getString("customer_id"));
					bc.setCoupon_No(rset.getInt("COUPON_NO"));
					biList.add(bc);
			
				
				
			}

		} catch (Exception e) {
			System.out.println("CouponDao.getCouponIssued: " + e.getMessage());
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return biList;
	}
	
	public int getbCouponCount(Connection con, String userId) {
		int count = 0;
		String sql = "select count(*) from (select coupon_used from COUPON_ISSUED cis,coupon cou where COUPON_USED = 'N' and cis.coupon_no=cou.coupon_no and business_id = ?)";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				count = rset.getInt("count(*)");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return count;
	}
	
	public int getbUsedCouponCount(Connection con, String userId) {
		int count = 0;
		String sql = "select count(*) from (select coupon_used from COUPON_ISSUED cis,coupon cou where COUPON_USED = 'Y' and cis.coupon_no=cou.coupon_no and business_id = ?)";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				count = rset.getInt("count(*)");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return count;
	}
	
	public ArrayList<Coupon_bl> bSearchId(Connection con, String userId, int currentPage, int limit,
			String bSearchcusId) {

		ArrayList<Coupon_bl> biList = new ArrayList<Coupon_bl>();
		ResultSet rset = null;

		String sql = "SELECT * from ( select rownum rn, customer_id , coupon.coupon_content, coupon.COUPON_DATE from coupon, COUPON_ISSUED coupon_is where coupon.COUPON_NO = coupon_is.COUPON_NO and ? = coupon.BUSINESS_ID and coupon_used = 'N' and coupon_is.customer_id like '%" + bSearchcusId + "%' ORDER BY coupon.COUPON_DATE) WHERE rn >= ? AND rn <= ?";
		PreparedStatement pstmt = null;
		
		try {
			int startRow = (currentPage - 1) * limit + 1;
			int endRow = startRow + limit - 1;
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			rset = pstmt.executeQuery();

			while (rset.next()) {
				Coupon_bl bc = new Coupon_bl();
				bc.setCoupon_content(rset.getString("coupon_content"));
				bc.setCoupon_date(rset.getDate("COUPON_DATE"));
				bc.setCustomer_id(rset.getString("customer_id"));
				biList.add(bc);
			}

		} catch (Exception e) {
			System.out.println("CouponDao.bSearchId: " + e.getMessage());
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return biList;
	}
	
	public ArrayList<Coupon_bl> couponbUsed(Connection con, String userId, int currentPage, int limit) {

		ArrayList<Coupon_bl> biList = new ArrayList<Coupon_bl>();
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		System.out.println("couponbUsed dao 들어옴");
		String sql = "SELECT * FROM( select rownum rn, customer_id , coupon_content, COUPON_ISSUED_DATE from (select  customer_id , coupon.coupon_content, COUPON_ISSUED_DATE from coupon, COUPON_ISSUED coupon_is where coupon.COUPON_NO = coupon_is.COUPON_NO and ? = coupon.BUSINESS_ID and coupon_used = 'Y' ORDER BY COUPON_ISSUED_DATE desc)) WHERE rn >= ? AND rn <= ?";

		try {
			int startRow = (currentPage - 1) * limit + 1;
			int endRow = startRow + limit - 1;
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);

			rset = pstmt.executeQuery();

			while (rset.next()) {
				Coupon_bl bc = new Coupon_bl();

				bc.setCoupon_content(rset.getString("coupon_content"));
				bc.setCoupon_date(rset.getDate("COUPON_ISSUED_DATE"));
				bc.setCustomer_id(rset.getString("customer_id"));
				biList.add(bc);
				System.out.println(bc);
			}

		} catch (Exception e) {
			System.out.println("CouponDao.couponbUsed: " + e.getMessage());
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return biList;
	}
	
	public int changeCouponUsed(Connection con, String csId, int coupon_no) {
		PreparedStatement pstmt =null;
		int result =0;
		String query = "update COUPON_ISSUED set COUPON_USED='Y', COUPON_ISSUED_DATE=sysdate where CUSTOMER_ID=? and COUPON_NO=? ";
		
		System.out.println(coupon_no);
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1,csId);
			pstmt.setInt(2, coupon_no);
			
			result= pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
				
		return result;
	}

	
	public int wantCoupon(Connection con, String bid, String cid, String content, Date date){
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		String sql="select * from coupon_issued where coupon_no=(select coupon_no from coupon where business_id=? and coupon_date=? and coupon_content=?)and customer_id=? and coupon_used='N'";
		String sql2="select coupon_no from coupon where business_id=? and coupon_date=? and coupon_content=?";
		String sql3="insert into coupon_issued values(?,?,null,'N')";
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, bid);
			pstmt.setDate(2, date);
			pstmt.setString(3, content);
			pstmt.setString(4, cid);
			
			rset=pstmt.executeQuery();
			if(rset.next() == false) {
				pstmt=con.prepareStatement(sql2);
				pstmt.setString(1, bid);
				pstmt.setDate(2, date);
				pstmt.setString(3, content);
				rset=pstmt.executeQuery();
				
				while(rset.next()) {
				pstmt=con.prepareStatement(sql3);
				pstmt.setInt(1, rset.getInt("coupon_no"));
				pstmt.setString(2, cid);
				result=pstmt.executeUpdate();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return result;
	}
	
	public int couponCreate(Connection con, String bid, String content, Date date) {
		int result=0;
		Statement stmt=null;
		ResultSet rset=null;
		String sql="select * from coupon where business_id='"+bid+"' and coupon_content='"+content+"'";
		String sql2="insert into coupon values((select max(COUPON_NO)+1 from coupon),'"+bid+"','"+date+"','"+content+"')";
		
		try {
			stmt=con.createStatement();
			rset=stmt.executeQuery(sql);

			if(rset.next() == false) {
				stmt=con.createStatement();
				result=stmt.executeUpdate(sql2);
				System.out.println("da222o : "+result);
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			close(rset);
			close(stmt);
		}
		
		
		return result;
	}
	public ArrayList<Coupon> getCreateCouponList(Connection con, String userId) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql ="select * from coupon where business_id=? and coupon_date >= sysdate";
		ArrayList<Coupon> clist = new ArrayList<Coupon>();
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);
			rset=pstmt.executeQuery();
			
			while(rset.next()) {
				Coupon cp = new Coupon();
				cp.setBusiness_Id(userId);
				cp.setCoupon_Content(rset.getString("COUPON_CONTENT"));
				cp.setCoupon_Date(rset.getDate("COUPON_DATE"));
				cp.setCoupon_No(rset.getInt("COUPON_NO"));
				
				clist.add(cp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
			

		return clist;
		
			
		}

	public int getCustomerCouponCount(Connection con, String userId) {
		String sql = "select count(*) from COUPON_ISSUED ci, coupon  where ci.coupon_no = coupon.coupon_no and CUSTOMER_ID = ? and COUPON_USED = 'N' and COUPON_DATE >= sysdate ";
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		int count = 0;
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);
			rset=pstmt.executeQuery();
			if(rset.next())
			{
				count = rset.getInt("count(*)");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		
		
		
		return count;
	}
}
