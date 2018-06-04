package festival.model.service;

import static common.Jdbctemplate.close;
import static common.Jdbctemplate.commit;
import static common.Jdbctemplate.getConnection;
import static common.Jdbctemplate.rollback;

import java.sql.Connection;
import java.util.ArrayList;

import festival.model.dao.FestivalDao;
import festival.model.vo.Festival;
import festival.model.vo.Festival_Image;
import festival.model.vo.Festival_Truck;
import foodtruck.model.vo.Foodtruck;

public class FestivalService {

	public FestivalService() {}

	public int insertFestival(Festival festival) {
		
		Connection con=getConnection();
		
		int result=new FestivalDao().insertFestival(con, festival);
		
			if(result > 0) {
				commit(con);
			}else {
				rollback(con);
			}
			close(con);
			
		return result;
	}

	public ArrayList<Festival> festivalList() {
		Connection con=getConnection();
		ArrayList<Festival> list=new FestivalDao().festivalList(con);
		close(con);
		return list;
	}


	// 축제정보 상세페이지
	public Festival festivalInfo(int festivalNo) {
		Connection con=getConnection();
		Festival festival=new FestivalDao().festivalInfo(con, festivalNo);
		close(con);
		return festival;
	}

	public int insertImages(Festival_Image fesImage) {
		Connection con=getConnection();
		int result=new FestivalDao().insertImages(con, fesImage);
		
			if(result > 0) {
				commit(con);
			}else {
				rollback(con);
			}
			close(con);
		
			return result;
	}

//	public ArrayList<Festival_Image> imagesList() {
//		Connection con=getConnection();
//		ArrayList<Festival_Image> fiList=new FestivalDao().imagesList(con);
//		close(con);
//		return fiList;
//	}

	public int deleteFestival(int festivalNo) {
		Connection con=getConnection();
		int result=new FestivalDao().deleteFestival(con, festivalNo);
			if(result > 0)
				commit(con);
			else
				rollback(con);
			close(con);
		return result;
	}

	public ArrayList<String> imagesInfo(int festivalNo) {
		Connection con=getConnection();
		ArrayList<String> list=new FestivalDao().imagesInfo(con, festivalNo);
		close(con);
		return list;
	}

//	public Festival_Image selectImages() {
//		Connection con=getConnection();
//		Festival_Image fImage=new FestivalDao().selectImages(con);
//		close(con);
//		return fImage;
//	}

	public int deleteImages(int festivalNo) {
		Connection con=getConnection();
		int result=new FestivalDao().deleteImages(con, festivalNo);
			if(result > 0)
				commit(con);
			else
				rollback(con);
			close(con);
		return result;
	}

	/** 축제참가 등록 메소드 */
	public int insertTruck(Festival_Truck fTruck) {
		Connection con=getConnection();
		
		int result=new FestivalDao().insertTruck(con, fTruck);
		
			if(result > 0) {
				commit(con);
			}else {
				rollback(con);
			}
			close(con);
			
		return result;
	}

	//축제참가 트럭리스트 메소드
	public ArrayList<Foodtruck> truckList(int festivalNo) {
		Connection con=getConnection();
		ArrayList<Foodtruck> truckList=new FestivalDao().truckList(con, festivalNo);
		close(con);
		return truckList;
	}

	//특정축제 참가 트럭확인
	public Festival_Truck selectTruck(int festivalNo, String businessId) {
		Connection con=getConnection();
		Festival_Truck fTruck=new FestivalDao().selectTruck(con, festivalNo, businessId);
		close(con);
		return fTruck;
	}
	
	//참가트럭 삭제 메소드
	public int deleteTruck(int fesNum, String businessId) {
		Connection con=getConnection();
		int result=new FestivalDao().deleteTruck(con, fesNum, businessId);
			if(result > 0)
				commit(con);
			else
				rollback(con);
			close(con);
		return result;
		
	}

	public ArrayList<Festival> searchFestival(String fSearch) {
		Connection con=getConnection();
		ArrayList<Festival> searchFestival=new FestivalDao().searchFestival(con, fSearch);
		close(con);
		return searchFestival;
	}

	public int nextFesNum() {
		
		Connection con = getConnection();
		int num =  new FestivalDao().nextFesNum(con);
		close(con);
		
		return num;
	}

	public int deleteTruck(int festivalNo) {
		Connection con=getConnection();
		int result=new FestivalDao().deleteTruck(con, festivalNo);
			if(result > 0)
				commit(con);
			else
				rollback(con);
			close(con);
			return result;
	}


	
}
