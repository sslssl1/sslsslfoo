package coupon.model.service;

import static common.Jdbctemplate.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import coupon.model.dao.CouponDao;
import coupon.model.vo.*;
import review.model.dao.ReviewDao;
import review.model.vo.Review;

public class CouponService {

	public CouponService() {
	}

	/*
	 * public List<Coupon> selectList(String user_id){ Connection con =
	 * getConnection(); List<Coupon> list = new CouponDao().selectList(con,user_id);
	 * 
	 * close(con); return list; }
	 */
	public ArrayList<Coupon_l> getCouponList(String userId, int currentPage, int limit) {
		Connection con = getConnection();
		ArrayList<Coupon_l> clist = new CouponDao().couponIssued(con, userId, currentPage, limit);

		close(con);

		return clist;
	}

	public int getCouReviewCount(String userId) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getCustomerCouponCount(String userId)
	{
		Connection con =  getConnection();
		int count = new CouponDao().getCustomerCouponCount(con,userId);
		close(con);
		return count;
	}
	
	public int getCouponCount(String userId) {
		Connection con = getConnection();

		int count = new CouponDao().getCouponCount(con, userId);

		close(con);

		return count;
	}

	public ArrayList<Coupon_l> getUsedCouponList(String userId, int currentPage, int limit) {
		Connection con = getConnection();
		ArrayList<Coupon_l> clist = new CouponDao().couponUsed(con, userId, currentPage, limit);

		close(con);

		return clist;
	}

	public int getUsedCouponCount(String userId) {
		Connection con = getConnection();

		int count = new CouponDao().getUsedCouponCount(con, userId);

		close(con);

		return count;
	}

	public ArrayList<Coupon_l> searchFoodtruck(String userId, int currentPage, int limit, String cSearchFoodtrcuk) {
		Connection con = getConnection();
		
		System.out.println("===쿠폰서치 푸드트럭 서비스 들어옴");
		 ArrayList<Coupon_l> list = new CouponDao().searchFoodtruck(con, userId, currentPage, limit, cSearchFoodtrcuk);
		 
		 close(con);
		
		
		return list;
	}
	public ArrayList<Coupon_bl> getbCouponList(String userId, int currentPage, int limit) {
		Connection con = getConnection();
		ArrayList<Coupon_bl> blist = new CouponDao().bcouponIssued(con, userId, currentPage, limit);
		
		close(con);
		
		return blist;
	}
	
	public int getbCouponCount(String userId) {
		Connection con = getConnection();
		
		int count = new CouponDao().getbCouponCount(con, userId);
		
		close(con);
		
		return count;
	}
	
	public int getbUsedCouponCount(String userId) {
		Connection con = getConnection();
		
		int count = new CouponDao().getUsedCouponCount(con, userId);
		
		close(con);
		
		return count;
	}
	
	public ArrayList<Coupon_bl> bSearchId(String userId, int currentPage, int limit, String bSearchcusId) {
		Connection con = getConnection();
		
		ArrayList<Coupon_bl> blist = new CouponDao().bSearchId(con, userId, currentPage, limit, bSearchcusId);
		
		close(con);
		
		return blist;
	}
	
	public ArrayList<Coupon_bl> getbUsedCouponList(String userId, int currentPage, int limit) {
		Connection con = getConnection();
		ArrayList<Coupon_bl> blist = new CouponDao().couponbUsed(con, userId, currentPage, limit);

		close(con);

		return blist;
	}
	
	public int changeCouponUsed(String csId, int coupon_no) {
		Connection con = getConnection();
		int result=new CouponDao().changeCouponUsed(con,csId,coupon_no);

		if(result>0) {
			commit(con);
		}else {
			rollback(con);
		}
		close(con);
		return result;
	}

	
	public int wantCoupon(String bid, String cid, String content, Date date) {
		
		
		Connection con= getConnection();
		
		int result=new CouponDao().wantCoupon(con, bid, cid, content, date);
		
		if(result >0) {
			commit(con);
		}else {
			rollback(con);
		}
		close(con);
		
		return result;
	}
	public int couponCreate(String bid,String content, Date date) {
		Connection con =getConnection();
		int result=new CouponDao().couponCreate(con, bid, content, date);
		
		if(result >0) {
			commit(con);
		}else {
			rollback(con);
		}
		close(con);
		return result;
	}
	public ArrayList<Coupon> getCreateCouponList(String userId) {
		Connection con = getConnection();
		ArrayList<Coupon> clist = new CouponDao().getCreateCouponList(con, userId);

		close(con);

		return clist;
	}


}
