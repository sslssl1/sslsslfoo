package festival.model.dao;

import static common.Jdbctemplate.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import festival.model.vo.Festival;
import festival.model.vo.Festival_Image;
import festival.model.vo.Festival_Truck;
import foodtruck.model.vo.Foodtruck;

public class FestivalDao {

	public FestivalDao() {}

	public int insertFestival(Connection con, Festival festival) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query = "insert into festival values (?, ?, ?, ?, ?, ?, ?, ?,?)";			
		
		try {
			pstmt=con.prepareStatement(query);
			pstmt.setInt(1, festival.getFestival_No());
			pstmt.setString(2, festival.getFestival_Name());
			pstmt.setString(3, festival.getFestival_Addr());
			pstmt.setDate(4, festival.getFestival_Date_Start());
			pstmt.setDate(5, festival.getFestival_Date_End());
			pstmt.setString(6, festival.getFestival_Host());
			pstmt.setString(7, festival.getFestival_Phone());
			pstmt.setString(8, festival.getFestival_Image_File());
			pstmt.setString(9, festival.getFestival_Image_Main());

			result = pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}

	//show
	public ArrayList<Festival> festivalList(Connection con) {
		
		ArrayList<Festival> list=new ArrayList<Festival>();
		Statement stmt=null;
		ResultSet rset=null;
		
		String query="select * from festival order by festival_no desc";
		
		try {
			
			stmt=con.createStatement();
			rset=stmt.executeQuery(query);
			
			while(rset.next()) {
				
				Festival fes=new Festival();
				
				fes.setFestival_No(rset.getInt("festival_no"));
				fes.setFestival_Name(rset.getString("festival_name"));
				fes.setFestival_Addr(rset.getString("festival_addr"));
				fes.setFestival_Date_Start(rset.getDate("festival_date_start"));
				fes.setFestival_Date_End(rset.getDate("festival_date_end"));
				fes.setFestival_Host(rset.getString("festival_host"));
				fes.setFestival_Phone(rset.getString("festival_phone"));
				fes.setFestival_Image_File(rset.getString("festival_image_file"));
				
				list.add(fes);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(stmt);
		}
		
		return list;
	}


	//festival
	public Festival festivalInfo(Connection con, int festivalNo) {
		Festival fes=null;
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		
		String query="select * from festival where festival_no=?";
		
		try {
			
			pstmt=con.prepareStatement(query);
			pstmt.setInt(1, festivalNo);
			
			rset=pstmt.executeQuery();
			
			if(rset.next()) {
				fes=new Festival();
				
				fes.setFestival_No(festivalNo);
				fes.setFestival_Name(rset.getString("festival_name"));
				fes.setFestival_Addr(rset.getString("festival_addr"));
				fes.setFestival_Date_Start(rset.getDate("festival_date_start"));
				fes.setFestival_Date_End(rset.getDate("festival_date_end"));
				fes.setFestival_Host(rset.getString("festival_host"));
				fes.setFestival_Phone(rset.getString("festival_phone"));
				fes.setFestival_Image_File(rset.getString("festival_image_file"));
				fes.setFestival_Image_Main(rset.getString("FISTIVAL_IMAGE_MAIN"));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return fes;
	}

	public int insertImages(Connection con, Festival_Image fimage) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query = "insert into festival_image values(?,?)";			
		
		try {
			pstmt=con.prepareStatement(query);
			
			pstmt.setInt(1,fimage.getFestival_No());
			pstmt.setString(2, fimage.getFestival_Img());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}

	
	//축제 삭제 메소드
	public int deleteFestival(Connection con, int festivalNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query = "delete from festival where festival_no = ?";

		try {

			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, festivalNo);

			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}
	
	//등록된 축제 이미지정보
	public ArrayList<String> imagesInfo(Connection con, int festivalNo) {
		ArrayList<String> list = new ArrayList<String>();

		PreparedStatement pstmt=null;
		ResultSet rset=null;
		
		String query="select * from festival_image where festival_no=?";
		
		try {
			
			pstmt=con.prepareStatement(query);
			pstmt.setInt(1, festivalNo);
			
			rset=pstmt.executeQuery();
			
			while(rset.next()) {
				list.add(rset.getString("festival_img"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}
	

	/** 이미지 삭제 메소드 */
	public int deleteImages(Connection con, int festivalNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query = "delete from festival_image where festival_no = ?";

		try {

			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, festivalNo);

			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	// 축제참가 트럭등록 메소드 
	public int insertTruck(Connection con, Festival_Truck fTruck) {
		
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query = "insert into festival_truck values(?,?)";			
		
		try {
			pstmt=con.prepareStatement(query);
			
			pstmt.setInt(1, fTruck.getFestival_No());
			pstmt.setString(2, fTruck.getBusiness_Id());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}

	//축제참가 트럭조회 메소드
	public ArrayList<Foodtruck> truckList(Connection con, int festivalNo) {
		
		ArrayList<Foodtruck> truckList=new ArrayList<Foodtruck>();
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		
		String query="SELECT distinct fest.business_id, ft.truck_name "
				+ "FROM FESTIVAl_truck fest, foodtruck ft "
				+ "where fest.business_id = ft.business_id and festival_no = ?";
		
		try {
			
			pstmt=con.prepareStatement(query);
			pstmt.setInt(1, festivalNo);
			
			rset=pstmt.executeQuery();
			
			while(rset.next()) {
				
				Foodtruck foodTruck=new Foodtruck();
					foodTruck.setBusiness_Id(rset.getString("business_id"));
					foodTruck.setTruck_Name(rset.getString("truck_name"));
					truckList.add(foodTruck);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return truckList;
	}

	public Festival_Truck selectTruck(Connection con, int festivalNo, String businessId) {
		Festival_Truck fTruck=null;
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		
		String query="select * from festival_truck where festival_no=? and business_id=?";
		
		try {
			
			pstmt=con.prepareStatement(query);
			pstmt.setInt(1, festivalNo);
			pstmt.setString(2, businessId);
			
			rset=pstmt.executeQuery();
			
			if(rset.next()) {
				fTruck=new Festival_Truck();
				
				fTruck.setFestival_No(festivalNo);
				fTruck.setBusiness_Id(businessId);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return fTruck;
	}

	//축제참가 취소 트럭 메소드
	public int deleteTruck(Connection con, int fesNum, String businessId) {
		
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query = "delete from festival_truck where festival_no = ? and business_id = ?";

		try {

			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, fesNum);
			pstmt.setString(2, businessId);

			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
		
	}

	/** 축제 리스트 검색용 메소드 */
	public ArrayList<Festival> searchFestival(Connection con, String fSearch) {
		
		ArrayList<Festival> searchFestival=new ArrayList<Festival>();
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		String query="select * from festival where festival_name like '%"+ fSearch +"%'";
		
		try {
			
			pstmt=con.prepareStatement(query);
			rset=pstmt.executeQuery();
			
			while(rset.next()) {
				
				Festival festival=new Festival();
					festival.setFestival_No(rset.getInt("festival_no"));
					festival.setFestival_Name(rset.getString("festival_name"));
					festival.setFestival_Addr(rset.getString("festival_addr"));
					festival.setFestival_Date_Start(rset.getDate("festival_date_start"));
					festival.setFestival_Date_End(rset.getDate("festival_date_end"));
					festival.setFestival_Host(rset.getString("festival_host"));
					festival.setFestival_Phone(rset.getString("festival_phone"));
					festival.setFestival_Image_File(rset.getString("festival_image_file"));
					
					searchFestival.add(festival);
					
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return searchFestival;
	}

	public int nextFesNum(Connection con) {
	
		int num =0;
		Statement stmt = null;
		String sql="select max(festival_no)+1 fnum from FESTIVAL";
		ResultSet rset = null;
		
		try {
			stmt=con.createStatement();
			rset = stmt.executeQuery(sql);
			
			if(rset.next())
			{
				num=rset.getInt("fnum");
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(stmt);
		}
		
		
		return num;
	}

	public int deleteTruck(Connection con, int festivalNo) {
		
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query = "delete from festival_truck where festival_no = ?";

		try {

			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, festivalNo);

			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}
	
	
}
