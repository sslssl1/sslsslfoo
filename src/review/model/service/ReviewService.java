package review.model.service;

import static common.Jdbctemplate.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import review.model.dao.ReviewDao;
import review.model.vo.*;

public class ReviewService {

	public ReviewService() {

	}

	public int reviewWrite(Review review) {
		Connection con = getConnection();

		int result = new ReviewDao().reviewWrite(con, review);

		if (result > 0) {
			commit(con);
		} else {
			rollback(con);
		}
		close(con);
		return result;
	}

	public List<Review> selectList(String user_id) {
		Connection con = getConnection();
		List<Review> list = new ReviewDao().selectList(con, user_id);

		close(con);

		return list;
	}

	public int getCusReviewCount(String userId) {
		Connection con = getConnection();
		int listCount = new ReviewDao().getCusReviewCount(con, userId);

		close(con);

		return listCount;
	}

	public ArrayList<Review> getCusReviewList(String userId, int currentPage, int limit) {
		Connection con = getConnection();
		ArrayList<Review> rlist = new ReviewDao().getCusReviewList(con, userId, currentPage, limit);

		close(con);

		return rlist;
	}

	public ArrayList<Review> searchCusReviewList(SearchReview sr) {
		Connection con = getConnection();

		ArrayList<Review> list = new ReviewDao().searchCusReviewList(con, sr);

		close(con);

		return list;

	}

	public void deleteCusReview(int review_no) {

		Connection con = getConnection();

		int result = new ReviewDao().deleteCusReview(con, review_no);

		if (result > 0) {
			commit(con);
		} else {
			rollback(con);
		}
		close(con);
	}

	public ArrayList<Review> bsSelectReview(SearchReview sr) {
		Connection con = getConnection();
		ArrayList<Review> rlist = new ReviewDao().bsSelectReview(con, sr);
		close(con);

		return rlist;
	}

	public double avgTruckGrade(String businessId) {
		Connection con = getConnection();
		Double tgrade = new ReviewDao().avgTruckGrade(con, businessId);
		close(con);
		return tgrade;
	}

	public int getBusReviewCount(SearchReview sr) {
		Connection con = getConnection();
		int count = new ReviewDao().getBusReviewCount(con, sr);
		close(con);
		return count;
	}

	public DetailReview reviewDetail(int reviewNo) {
		Connection con = getConnection();
		DetailReview review = new ReviewDao().reviewDetail(con, reviewNo);
		close(con);

		return review;
	}

	public void addReviewCount(int reviewNo) {
		Connection con = getConnection();
		int result = new ReviewDao().addReviewCount(con, reviewNo);
		if (result > 0)
			commit(con);
		else
			rollback(con);
		close(con);

	}

	public ArrayList<Review> cusMyReviewList(SearchReview sr) {
		Connection con =getConnection();
		ArrayList<Review> rlist = new ReviewDao().cusMyReviewList(con,sr);
		
		close(con);
		
		
		return rlist;
	}
	
	public int cusMyReviewCount(SearchReview sr)
	{
		Connection con = getConnection();
		int count = new ReviewDao().cusMyReviewCount(con,sr);
		close(con);
		return count;
	}
	public ArrayList<Review> reviewmodify(String rno) {
		Connection con=getConnection();
		
		ArrayList<Review> list=new ReviewDao().reviewmodify(con,rno);
		
		close(con);
		return list;
	}

	public int reviewWriteModify(Review review) {
		Connection con = getConnection();
		
		int result = new ReviewDao().reviewWriteModify(con, review); 
		
		if (result > 0) {
			commit(con);
		} else {
			rollback(con);
		}
		close(con);
		return result;
	}
}
