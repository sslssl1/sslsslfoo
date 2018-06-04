package foodtruck.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import javax.crypto.SealedObject;

import static common.Jdbctemplate.*;
import foodtruck.model.dao.FoodtruckDao;
import foodtruck.model.vo.*;

public class FoodtruckService {

	public FoodtruckService() {
	}

	public ArrayList<Foodtruck> allList() {

		Connection con = getConnection();
		ArrayList<Foodtruck> list = new FoodtruckDao().allList(con);

		close(con);

		return list;

	}

	public Foodtruck selectTruck(String businessId) {
		Connection con = getConnection();

		Foodtruck truck = new FoodtruckDao().selectTruck(con, businessId);

		close(con);

		return truck;
	}

	public ArrayList<Menu> selectMenu(String businessId) {
		Connection con = getConnection();

		ArrayList<Menu> mlist = new FoodtruckDao().selectMenu(con, businessId);
		close(con);
		return mlist;

	}
	public ArrayList<Foodtruck> slocationList() {
		Connection con = getConnection();
		ArrayList<Foodtruck> list = new FoodtruckDao().slocationList(con);
		close(con);
		return list;
	}

	public ArrayList<Foodtruck> searchTruckList(SearchTruck st) {

		Connection con = getConnection();
		ArrayList<Foodtruck> list = new FoodtruckDao().searchTruckList(con,st);
		
		close(con);
		
		
		return list;
	}

	public int updateTruck(Foodtruck truck) {
		
		Connection con = getConnection();
		int result = new FoodtruckDao().updateTruck(con,truck);
		if(result>0)
			commit(con);
		else
			rollback(con);
		close(con);
		
		
		return result;
	}

	public int resetMenu(String bsId) {
		Connection con= getConnection();
		int result = new FoodtruckDao().resetMenu(con,bsId);
		
		if(result>0)
		commit(con);
		else
			rollback(con);
		close(con);
		
		return result;
	}

	public int insertMenu(Menu menu) {
		Connection con= getConnection();
		int result = new FoodtruckDao().insertMenu(con,menu);
		if(result>0)
			commit(con);
		else
			rollback(con);
		close(con);
		return result;

	}

	public Boolean checkExistTruck(String bsId) {
		Connection con = getConnection();
		
		Boolean result = new FoodtruckDao().checkExistTruck(con,bsId);
		
		close(con);
		
		
		return result;
	}

	public int insertTruck(Foodtruck truck) {
		Connection con = getConnection();
		int result = new FoodtruckDao().insertTruck(con,truck);
		
		if(result>0)
			commit(con);
		else
			rollback(con);
		close(con);
		
		
		return result;
	}
	public int editLocation(String userId, String xLoc, String yLoc) {
		Connection con = getConnection();
		
		int result=new FoodtruckDao().editLocation(con,userId,xLoc,yLoc);
		if(result>0) {
			commit(con);
		}else {
			rollback(con);
		}
		close(con);
		
		return result;
	}
	
	public ArrayList<FoodCoupon> foodCouponList(String bid) {
		Connection con = getConnection();
		
		ArrayList<FoodCoupon> coupon_list=new FoodtruckDao().foodCouponList(con,bid);
		close(con);
		
		return coupon_list;
	}

	public ArrayList<String> searchAutoCompleteTruck(String seValue) {
		Connection con = getConnection();
		ArrayList<String>  list = new FoodtruckDao().searchAutoCompleteTruck(con,seValue);
		close(con);
		
		return list;
	}

	public ArrayList<String> searchAutoCompleteMenu(String seValue) {
	
		Connection con = getConnection();
		ArrayList<String>  list = new FoodtruckDao().searchAutoCompleteMenu(con,seValue);
		close(con);
		
		return list;
	}

	public ArrayList<FoodTop3> getFoodtruckTop() {
		
		Connection con = getConnection();
		ArrayList<FoodTop3> list =new FoodtruckDao().getFoodtruckTop(con);
		close(con);
		return list;
	}

public int foodOn(String userid) {
		Connection con = getConnection();
		int result = new FoodtruckDao().foodOn(con,userid);
		
		if(result>0)
			commit(con);
		else
			rollback(con);
		close(con);
		
		
		return result;
	}
	
	
	public int foodOff(String userid) {
		Connection con = getConnection();
		int result = new FoodtruckDao().foodOff(con,userid);
		
		if(result>0)
			commit(con);
		else
			rollback(con);
		close(con);
		
		
		return result;
	}

	public String getLiveState(String userid) {
		Connection con = getConnection();
		String state = new FoodtruckDao().getLiveState(con,userid);
		
		close(con);
		
		return state;
	}

public ArrayList<FoodTop3> getRiceTop3List() {
		
		Connection con = getConnection();
		ArrayList<FoodTop3> list =new FoodtruckDao().getRiceTop3List(con);
		
		close(con);
		return list;
	}
	
	public ArrayList<FoodTop3> getSnackTop3List() {
		
		Connection con = getConnection();
		ArrayList<FoodTop3> list =new FoodtruckDao().getSnackTop3List(con);
		
		close(con);
		return list;
	}
	
	public ArrayList<FoodTop3> getDesertTop3List() {
		
		Connection con = getConnection();
		ArrayList<FoodTop3> list =new FoodtruckDao().getDesertTop3List(con);
		
		close(con);
		return list;
	}

}
