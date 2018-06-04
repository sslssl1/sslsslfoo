package foodtruck.model.dao;

import java.sql.*;
import java.util.ArrayList;

import com.sun.corba.se.spi.orbutil.fsm.State;
import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;
import com.sun.org.apache.regexp.internal.REUtil;

import foodtruck.model.vo.*;
import oracle.net.aso.r;
import sun.dc.pr.PRError;

import static common.Jdbctemplate.*;

public class FoodtruckDao {

	public FoodtruckDao() {
	}

	public ArrayList<Foodtruck> allList(Connection con) {
		ArrayList<Foodtruck> list = new ArrayList<Foodtruck>();
		String sql = " select * from FOODTRUCK order by TRUCK_LIVE_ONOFF desc , TRUCK_NAME  ";
		Statement stmt = null;
		ResultSet rset = null;
		try {
			stmt = con.createStatement();
			rset = stmt.executeQuery(sql);

			while (rset.next()) {
				// BUSINESS_ID VARCHAR2(20 BYTE)
				// TRUCK_NAME VARCHAR2(100 BYTE)
				// TRUCK_IMG VARCHAR2(100 BYTE)
				// TRUCK_MAINMENU VARCHAR2(100 BYTE)
				// TRUCK_LOC_X NUMBER(9,6)
				// TRUCK_LOC_Y NUMBER(9,6)
				// TRUCK_NOTICE VARCHAR2(1000 BYTE)
				// TRUCK_TIME VARCHAR2(30 BYTE)
				// TRUCK_DAYOFF VARCHAR2(20 BYTE)
				// TRUCK_GRADE NUMBER(3,1)

				Foodtruck truck = new Foodtruck();
				truck.setBusiness_Id(rset.getString("BUSINESS_ID"));
				truck.setTruck_Name(rset.getString("TRUCK_NAME"));
				truck.setTruck_Img(rset.getString("TRUCK_IMG"));
				truck.setTruck_MainMenu(rset.getString("TRUCK_MAINMENU"));
				truck.setTruck_Loc_X(rset.getDouble("TRUCK_LOC_X"));
				truck.setTruck_Loc_Y(rset.getDouble("TRUCK_LOC_Y"));
				truck.setTruck_Notice(rset.getString("TRUCK_NOTICE"));
				truck.setTruck_Time(rset.getString("TRUCK_TIME"));
				truck.setTruck_Dayoff(rset.getString("TRUCK_DAYOFF"));
				truck.setTruck_Live_OnOff(rset.getString("TRUCK_LIVE_ONOFF"));
				truck.setTruck_Phone(rset.getString("TRUCK_PHONE"));

				list.add(truck);
			}

		} catch (Exception e) {
			System.out.println("FoodtruckDao.allList : " + e.getMessage());
			e.printStackTrace();
		} finally {
			close(rset);
			close(stmt);
		}
		return list;
	}

	public Foodtruck selectTruck(Connection con, String businessId) {

		Foodtruck truck = null;
		PreparedStatement pstmt = null;
		String sql = " select * from FOODTRUCK where BUSINESS_ID = ? ";
		ResultSet rset = null;

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, businessId);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				truck = new Foodtruck();
				truck.setBusiness_Id(businessId);
				truck.setTruck_Name(rset.getString("TRUCK_NAME"));
				truck.setTruck_Img(rset.getString("TRUCK_IMG"));
				truck.setTruck_MainMenu(rset.getString("TRUCK_MAINMENU"));
				truck.setTruck_Loc_X(rset.getDouble("TRUCK_LOC_X"));
				truck.setTruck_Loc_Y(rset.getDouble("TRUCK_LOC_Y"));
				truck.setTruck_Notice(rset.getString("TRUCK_NOTICE"));
				truck.setTruck_Time(rset.getString("TRUCK_TIME"));
				truck.setTruck_Dayoff(rset.getString("TRUCK_DAYOFF"));
				truck.setTruck_Live_OnOff(rset.getString("TRUCK_LIVE_ONOFF"));
				truck.setTruck_Phone(rset.getString("TRUCK_PHONE"));

			}

		} catch (Exception e) {
			System.out.println("FoodtruckDao.selectTruck : " + e.getMessage());
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return truck;
	}

	public ArrayList<Menu> selectMenu(Connection con, String businessId) {
		ArrayList<Menu> mlist = new ArrayList<Menu>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "select * from MENU where BUSINESS_ID = ?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, businessId);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				// private String business_Id;
				// private String menu_Name;
				// private int menu_price;
				// private String menu_Img;
				// BUSINESS_ID VARCHAR2(20 BYTE)
				// MENU_NAME VARCHAR2(20 BYTE)
				// MENU_PRICE NUMBER(6,0)
				// MENU_IMG VARCHAR2(100 BYTE)
				Menu menu = new Menu();

				menu.setBusiness_Id(rset.getString("BUSINESS_ID"));
				menu.setMenu_Img(rset.getString("MENU_IMG"));
				menu.setMenu_Name(rset.getString("MENU_NAME"));
				menu.setMenu_price(rset.getInt("MENU_PRICE"));
				mlist.add(menu);
			}

		} catch (Exception e) {
			System.out.println("FoodtruckDao.selectMenu : " + e.getMessage());
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return mlist;
	}

	public ArrayList<Foodtruck> slocationList(Connection con) {
		ArrayList<Foodtruck> list = new ArrayList<Foodtruck>();
		Statement stmt = null;
		ResultSet rset = null;
		int cnt=0;

		String sql_location = "select * from foodtruck where TRUCK_LIVE_ONOFF='Y'";

		try {
			stmt = con.createStatement();
			rset = stmt.executeQuery(sql_location);
			
			while (rset.next()) {
				Foodtruck foodtruck = new Foodtruck();

				foodtruck.setTruck_Name(rset.getString("truck_name"));
				foodtruck.setTruck_Loc_X(rset.getDouble("truck_loc_x"));
				foodtruck.setTruck_Loc_Y(rset.getDouble("truck_loc_y"));
				foodtruck.setBusiness_Id(rset.getString("business_id"));
				foodtruck.setTruck_MainMenu(rset.getString("TRUCK_MAINMENU"));
				foodtruck.setTruck_Category(rset.getString("TRUCK_CATEGORY"));
				foodtruck.setTruck_Img(rset.getString("TRUCK_IMG"));
			
				list.add(foodtruck);
				cnt++;				
			}
			
			if(cnt<3) {
				for(int i=0; i<3; i++) {
				Foodtruck foodtruck = new Foodtruck();

				foodtruck.setTruck_Name(""+i);
				foodtruck.setTruck_Loc_X(i);
				foodtruck.setTruck_Loc_Y(i);
				foodtruck.setBusiness_Id(""+i);
				list.add(foodtruck);
				}
			}
			System.out.println("list2 : "+list.size());
		} catch (Exception e) {
			System.out.println("FoodtruckDao.slocationList : " + e.getMessage());
			e.printStackTrace();
		} finally {
			close(rset);
			close(stmt);
		}

		return list;
	}

	public ArrayList<Foodtruck> searchTruckList(Connection con, SearchTruck st) {

		String sql = "select distinct  tf.BUSINESS_ID, TRUCK_NAME, TRUCK_IMG, TRUCK_MAINMENU, TRUCK_LIVE_ONOFF,TRUCK_CATEGORY  from foodtruck tf, menu where tf.BUSINESS_ID = menu.BUSINESS_ID  and  " + st.getSeType()
				+ " like '%" + st.getSeValue() + "%' and TRUCK_CATEGORY like '%" + st.getCtType() + "%' order by TRUCK_LIVE_ONOFF desc , TRUCK_NAME ";
		ArrayList<Foodtruck> list = new ArrayList<Foodtruck>();
		Statement stmt = null;
		ResultSet rset = null;
		try {
			stmt = con.createStatement();
			rset = stmt.executeQuery(sql);

			while (rset.next()) {
				Foodtruck truck = new Foodtruck();
				truck.setBusiness_Id(rset.getString("BUSINESS_ID"));
				truck.setTruck_Name(rset.getString("TRUCK_NAME"));
				truck.setTruck_Img(rset.getString("TRUCK_IMG"));
				truck.setTruck_MainMenu(rset.getString("TRUCK_MAINMENU"));
				truck.setTruck_Live_OnOff(rset.getString("TRUCK_LIVE_ONOFF"));
				truck.setTruck_Category(rset.getString("TRUCK_CATEGORY"));
			
				list.add(truck);
			}

		} catch (Exception e) {
			System.out.println("FoodtruckDao.searchTruckList : " + e.getMessage());
			e.printStackTrace();
		}finally {
			close(rset);
			close(stmt);
		}
		return list;
	}

	public int updateTruck(Connection con, Foodtruck truck) {
		int result =0;
		PreparedStatement pstmt = null;
		String sql = "update FOODTRUCK SET TRUCK_NAME =? , TRUCK_PHONE = ?, TRUCK_IMG = ? ,TRUCK_MAINMENU = ? , TRUCK_CATEGORY = ?, TRUCK_LOC_X = ? , TRUCK_LOC_Y = ? , TRUCK_NOTICE = ? , TRUCK_TIME = ? , TRUCK_DAYOFF = ? , TRUCK_LIVE_ONOFF = ? WHERE  BUSINESS_ID = ?";
		
		try {
			/*TRUCK_NAME =? , 
			 * TRUCK_PHONE = ?, 
			 * TRUCK_IMG = ? ,
			 * TRUCK_MAINMENU = ? , 
			 * TRUCK_CATEGORY = ?, 
			 * TRUCK_LOC_X = ? , 
			 * TRUCK_LOC_Y = ? , 
			 * TRUCK_NOTICE = ? , 
			 * TRUCK_TIME = ? , 
			 * TRUCK_DAYOFF = ? , 
			 * TRUCK_LIVE_ONOFF = ? 
			 * WHERE  BUSINESS_ID = ?
			 * */
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, truck.getTruck_Name());
			pstmt.setString(2, truck.getTruck_Phone());
			pstmt.setString(3, truck.getTruck_Img());
			pstmt.setString(4, truck.getTruck_MainMenu());
			pstmt.setString(5, truck.getTruck_Category());
			pstmt.setDouble(6, truck.getTruck_Loc_X());
			pstmt.setDouble(7, truck.getTruck_Loc_Y());
			pstmt.setString(8, truck.getTruck_Notice());
			pstmt.setString(9, truck.getTruck_Time());
			pstmt.setString(10, truck.getTruck_Dayoff());
			pstmt.setString(11, truck.getTruck_Live_OnOff());
			pstmt.setString(12, truck.getBusiness_Id());
			
			result= pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("FoodtruckDao.updateTruck : " + e.getMessage());
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
	
		
		return result;
	}

	public int resetMenu(Connection con, String bsId) {
		int result = 0;
		String sql="delete from MENU WHERE BUSINESS_ID = ? ";
		PreparedStatement pstmt = null;
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, bsId);
			result=pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			System.out.println("FoodtruckDao.resetMenu : " + e.getMessage());
			e.printStackTrace();
		}finally {
			close(pstmt);
		}

		return result;
	}

	public int insertMenu(Connection con, Menu menu) {
		int result = 0;
		String sql = "insert into MENU VALUES(?,?,?,?)";
		PreparedStatement pstmt = null;
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, menu.getBusiness_Id());
			pstmt.setString(2, menu.getMenu_Name());
			pstmt.setInt(3, menu.getMenu_price());
			pstmt.setString(4, menu.getMenu_Img());
			result=pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("FoodtruckDao.insertMenu : " + e.getMessage());
			e.printStackTrace();
		}finally {
			close(pstmt);
		}

		return result;
	}

	public Boolean checkExistTruck(Connection con, String bsId) {
		
		Boolean result = false;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "select BUSINESS_ID from FOODTRUCK where BUSINESS_ID = ?";
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, bsId);
			rset = pstmt.executeQuery();
			
			if(rset.next())
			{
			
				result = true;
			}
			
		} catch (Exception e) {
			System.out.println("FoodtruckDao.checkExistTruck : " + e.getMessage());
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		
		return result;
	}

	public int insertTruck(Connection con, Foodtruck truck) {
		
		int result = 0;
		String sql = " insert into FOODTRUCK values(?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = null;
		
		try {
//			BUSINESS_ID	VARCHAR2(20 BYTE)1
//			TRUCK_NAME	VARCHAR2(100 BYTE)2
//			TRUCK_PHONE	VARCHAR2(30 BYTE)3
//			TRUCK_IMG	VARCHAR2(100 BYTE)4
//			TRUCK_MAINMENU	VARCHAR2(100 BYTE)5
//			TRUCK_CATEGORY	VARCHAR2(100 BYTE)6
//			TRUCK_LOC_X	NUMBER(20,17)7
//			TRUCK_LOC_Y	NUMBER(20,17)8
//			TRUCK_NOTICE	VARCHAR2(1000 BYTE)9
//			TRUCK_TIME	VARCHAR2(30 BYTE)10
//			TRUCK_DAYOFF	VARCHAR2(20 BYTE)11
//			TRUCK_LIVE_ONOFF	VARCHAR2(1 BYTE)12
			System.out.println("ID: "+truck.getBusiness_Id());
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, truck.getBusiness_Id());
			pstmt.setString(2, truck.getTruck_Name());
			pstmt.setString(3, truck.getTruck_Phone());
			pstmt.setString(4, truck.getTruck_Img());
			pstmt.setString(5, truck.getTruck_MainMenu());
			pstmt.setString(6, truck.getTruck_Category());
			pstmt.setDouble(7, truck.getTruck_Loc_X());
			pstmt.setDouble(8, truck.getTruck_Loc_Y());
			pstmt.setString(9, truck.getTruck_Notice());
			pstmt.setString(10, truck.getTruck_Time());
			pstmt.setString(11, truck.getTruck_Dayoff());
			pstmt.setString(12, truck.getTruck_Live_OnOff());
			
			result = pstmt.executeUpdate();
			
			
			
		} catch (Exception e) {
			System.out.println("FoodtruckDao.insertTruck : " + e.getMessage());
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}

	public int editLocation(Connection con, String userId, String xLoc, String yLoc) {
		int result=0;
		PreparedStatement pstmt=null;
		String sql="update foodtruck set TRUCK_LOC_X=?, TRUCK_LOC_Y=? where business_id=?";
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, xLoc);
			pstmt.setString(2, yLoc);
			pstmt.setString(3, userId);
			
			result=pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("FoodtruckDao.editLocation : " + e.getMessage());
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}
	
	public ArrayList<FoodCoupon> foodCouponList(Connection con, String bid) {
		ArrayList<FoodCoupon> coupon_list = new ArrayList<FoodCoupon>();
		FoodCoupon fcoupon=null;
		Statement stmt=null;
		ResultSet rset= null;
		String sql="select truck_name,coupon_content,coupon_date from foodtruck ft,coupon cp where ft.business_id='"+bid+"' and cp.business_id='"+bid+"'";
		
		try {
			stmt=con.createStatement();
			rset=stmt.executeQuery(sql);
	
			
			while(rset.next()) {
				fcoupon = new FoodCoupon();
				fcoupon.setTruck_name(rset.getString("truck_name"));
				fcoupon.setCoupon_content(rset.getString("coupon_content"));
				fcoupon.setCoupon_date(rset.getDate("coupon_date"));
				
				coupon_list.add(fcoupon);
			}
			
		} catch (Exception e) {
			System.out.println("FoodtruckDao.foodCouponList : " + e.getMessage());
			e.printStackTrace();
		}finally {
			close(rset);
			close(stmt);
		}
		
		return coupon_list;
	}

	public ArrayList<String> searchAutoCompleteTruck(Connection con, String seValue) {
		ArrayList<String> list = new ArrayList<String>();
		String sql = " select DISTINCT TRUCK_NAME from FOODTRUCK where upper(TRUCK_NAME) like upper('"+seValue+"%') ";
		ResultSet rset = null;
		Statement stmt = null;

		try {
			stmt = con.createStatement();
			rset = stmt.executeQuery(sql);
			while(rset.next())
			{
				list.add(rset.getString("TRUCK_NAME"));
			}
			
			
		} catch (Exception e) {
			System.out.println("FoodtruckDao.searchAutoCompleteTruck : " + e.getMessage());
			e.printStackTrace();
		}finally {
			close(rset);
			close(stmt);
		}
		
		
		
		return list;
	}

	public ArrayList<String> searchAutoCompleteMenu(Connection con, String seValue) {
		ArrayList<String> list = new ArrayList<String>();
		String sql = " select DISTINCT MENU_NAME from MENU where upper(MENU_NAME) like upper('%"+seValue+"%') ";
		ResultSet rset = null;
		Statement stmt = null;
			
		try {
			
			stmt = con.createStatement();
			rset = stmt.executeQuery(sql);
			
			while(rset.next())
			{
				list.add(rset.getString("MENU_NAME"));
			}
			
		} catch (Exception e) {
			System.out.println("FoodtruckDao.searchAutoCompleteMenu : " + e.getMessage());
			e.printStackTrace();
		}finally {
			close(rset);
			close(stmt);
		}
		
		return list;
	}

	public ArrayList<FoodTop3> getFoodtruckTop(Connection con) {
		
		String sql = " select gd.*, ft.TRUCK_NAME, ft.TRUCK_IMG, ft.TRUCK_MAINMENU, ft.TRUCK_CATEGORY from FOODTRUCK ft, (select BUSINESS_ID, grade   from (select rownum rnum, g.*   from ( select BUSINESS_ID, round(avg(REVIEW_GRADE),1) grade from REVIEW where REVIEW_DATE >= trunc(sysdate-7)  GROUP BY business_id order by avg(REVIEW_GRADE) desc) g ) where rnum <= 3 )gd where ft.business_id = gd.business_id ";
		Statement stmt = null;
		ResultSet rset = null;
		ArrayList<FoodTop3> list = new ArrayList<FoodTop3>();
		try {
			stmt = con.createStatement();
			rset = stmt.executeQuery(sql);
			
			while(rset.next())
			{
				FoodTop3 ft3=new FoodTop3();
				ft3.setBusinessId(rset.getString("BUSINESS_ID"));
				ft3.setGrade(rset.getDouble("grade"));
				ft3.setTruckCategory(rset.getString("TRUCK_CATEGORY"));
				ft3.setTruckImg(rset.getString("TRUCK_IMG"));
				ft3.setTruckMain(rset.getString("TRUCK_MAINMENU"));
				ft3.setTruckName(rset.getString("TRUCK_NAME"));
				list.add(ft3);
			}
			
			
		} catch (Exception e) {
			System.out.println("FoodtruckDao.getFoodtruckTop : " + e.getMessage());
			e.printStackTrace();
		}finally {
			close(rset);
			close(stmt);
		}
		return list;
	}

public int foodOn(Connection con, String userid) {
	PreparedStatement pstmt = null;
	int result = 0;
	String query = "update foodtruck set TRUCK_LIVE_ONOFF='Y' where BUSINESS_ID=? and TRUCK_LIVE_ONOFF='N'";
	 try {
		
		 pstmt = con.prepareStatement(query);
		 pstmt.setString(1, userid);
		 
		 result = pstmt.executeUpdate();
		 
	} catch (Exception e) {
		System.out.println("FoodtruckDao.foodOn : " + e.getMessage());
		e.printStackTrace();
	}finally {
		close(pstmt);
	}
	 
	 return result;
}

public int foodOff(Connection con, String userid) {
	PreparedStatement pstmt = null;
	int result = 0;
	String query = "update foodtruck set TRUCK_LIVE_ONOFF='N' where BUSINESS_ID=? and TRUCK_LIVE_ONOFF='Y' ";
	 try {
		
		 pstmt = con.prepareStatement(query);
		 pstmt.setString(1, userid);
		 
		 result = pstmt.executeUpdate();
		 
	} catch (Exception e) {
		System.out.println("FoodtruckDao.foodOff : " + e.getMessage());
		e.printStackTrace();
	}finally {
		close(pstmt);
	}
	 
	 return result;
}

public String getLiveState(Connection con, String userid) {
	String state=null;
	
	String sql="select TRUCK_LIVE_ONOFF from FOODTRUCK where BUSINESS_ID = ? ";
	PreparedStatement pstmt = null;
	ResultSet rset =null;
	
	try {
		pstmt=con.prepareStatement(sql);
		pstmt.setString(1, userid);
		rset=pstmt.executeQuery();
		if(rset.next())
		{
			state=rset.getString("TRUCK_LIVE_ONOFF");
			//test
			System.out.println("dao state : "+state);
		}
	} catch (Exception e) {
		System.out.println("FoodtruckDao.getLiveState : " + e.getMessage());
		e.printStackTrace();
	}finally {
		close(rset);
		close(pstmt);
	}
	
	
	return state;
}

public ArrayList<FoodTop3> getRiceTop3List(Connection con) {
	
	String sql ="select * from(select rownum rnum , BUSINESS_ID,grade,truck_name,truck_img,truck_mainMenu,truck_category  from  (select tf.*, grade  from (select BUSINESS_ID, round(avg(REVIEW_GRADE),1) grade from REVIEW where REVIEW_DATE >= trunc(sysdate-7) GROUP BY business_id order by avg(REVIEW_GRADE) desc) g , foodtruck tf  where g.business_id = tf.business_id and  TRUCK_CATEGORY = '식사류' order by grade desc)) where rnum <=3";
	Statement stmt = null;
	ResultSet rset = null;
	ArrayList<FoodTop3> list = new ArrayList<FoodTop3>();
	try {
		stmt = con.createStatement();
		rset = stmt.executeQuery(sql);
		
		while(rset.next())
		{
			FoodTop3 ft3=new FoodTop3();
			ft3.setBusinessId(rset.getString("BUSINESS_ID"));
			ft3.setGrade(rset.getDouble("grade"));
			ft3.setTruckCategory(rset.getString("TRUCK_CATEGORY"));
			ft3.setTruckImg(rset.getString("TRUCK_IMG"));
			ft3.setTruckMain(rset.getString("TRUCK_MAINMENU"));
			ft3.setTruckName(rset.getString("TRUCK_NAME"));
			list.add(ft3);
		}
		
		
	} catch (Exception e) {
		System.out.println("FoodtruckDao.getFoodtruckTop : " + e.getMessage());
		e.printStackTrace();
	}finally {
		close(rset);
		close(stmt);
	}
	return list;
}

public ArrayList<FoodTop3> getSnackTop3List(Connection con) {
	
	String sql ="select * from(select rownum rnum , BUSINESS_ID,grade,truck_name,truck_img,truck_mainMenu,truck_category  from  (select tf.*, grade  from (select BUSINESS_ID, round(avg(REVIEW_GRADE),1) grade from REVIEW where REVIEW_DATE >= trunc(sysdate-7) GROUP BY business_id order by avg(REVIEW_GRADE) desc) g , foodtruck tf  where g.business_id = tf.business_id and  TRUCK_CATEGORY = '간식류' order by grade desc)) where rnum <=3";
	Statement stmt = null;
	ResultSet rset = null;
	ArrayList<FoodTop3> list = new ArrayList<FoodTop3>();
	try {
		stmt = con.createStatement();
		rset = stmt.executeQuery(sql);
		
		while(rset.next())
		{
			FoodTop3 ft3=new FoodTop3();
			ft3.setBusinessId(rset.getString("BUSINESS_ID"));
			ft3.setGrade(rset.getDouble("grade"));
			ft3.setTruckCategory(rset.getString("TRUCK_CATEGORY"));
			ft3.setTruckImg(rset.getString("TRUCK_IMG"));
			ft3.setTruckMain(rset.getString("TRUCK_MAINMENU"));
			ft3.setTruckName(rset.getString("TRUCK_NAME"));
			list.add(ft3);
		}
		
		
	} catch (Exception e) {
		System.out.println("FoodtruckDao.getFoodtruckTop : " + e.getMessage());
		e.printStackTrace();
	}finally {
		close(rset);
		close(stmt);
	}
	return list;
}


public ArrayList<FoodTop3> getDesertTop3List(Connection con) {

String sql ="select * from(select rownum rnum , BUSINESS_ID,grade,truck_name,truck_img,truck_mainMenu,truck_category  from  (select tf.*, grade  from (select BUSINESS_ID, round(avg(REVIEW_GRADE),1) grade from REVIEW where REVIEW_DATE >= trunc(sysdate-7) GROUP BY business_id order by avg(REVIEW_GRADE) desc) g , foodtruck tf  where g.business_id = tf.business_id and  TRUCK_CATEGORY = '디저트류' order by grade desc)) where rnum <=3";
Statement stmt = null;
ResultSet rset = null;
ArrayList<FoodTop3> list = new ArrayList<FoodTop3>();
try {
	stmt = con.createStatement();
	rset = stmt.executeQuery(sql);
	
	while(rset.next())
	{
		FoodTop3 ft3=new FoodTop3();
		ft3.setBusinessId(rset.getString("BUSINESS_ID"));
		ft3.setGrade(rset.getDouble("grade"));
		ft3.setTruckCategory(rset.getString("TRUCK_CATEGORY"));
		ft3.setTruckImg(rset.getString("TRUCK_IMG"));
		ft3.setTruckMain(rset.getString("TRUCK_MAINMENU"));
		ft3.setTruckName(rset.getString("TRUCK_NAME"));
		list.add(ft3);
	}
	
	
} catch (Exception e) {
	System.out.println("FoodtruckDao.getFoodtruckTop : " + e.getMessage());
	e.printStackTrace();
}finally {
	close(rset);
	close(stmt);
}
return list;
}
	
	
}
