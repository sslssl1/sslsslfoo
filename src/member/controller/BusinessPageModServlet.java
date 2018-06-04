package member.controller;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import foodtruck.model.service.FoodtruckService;
import foodtruck.model.vo.*;

import java.io.*;

/**
 * Servlet implementation class BusinessPageModServlet
 */
@WebServlet("/businessmy")
public class BusinessPageModServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BusinessPageModServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String bsId=null;
		String truckDayOff=null;
		String truckName=null;
		String truckNotice=null;
		String truckTime=null;
		String menuName[];
		String truck_Live_OnOff=null;
		String category=null;
		String truckPhone=null;
		int menuPrice[];
		int addMenuCount=0;
		int oriMenuCount=0;

		double xloc=0;
		double yloc=0;

		// 전송 파일 용량 제한 : 10Mbyte 제한한 경우
		int maxSize = 1024 * 1024 * 10;

		// 웹서버 컨테이너 경로 추출함
		String root = request.getSession().getServletContext().getRealPath("/");
		// 또는 별도의 저장위치를 지정함
		// String root = "c:\\savedFiles";

		// 파일 저장 경로(ex : web/savedFiles) 정함

		String menu_savePath = root + "resources/images/menu/";
		String truck_savePath = root + "resources/images/foodtruck/";
		// String truck_savePath = root + "up/";

		// 업로드 파일명
		String uploadFile = "";
	

		// 실제 저장할 파일명
	
		String new_truck_img = "";
		String new_menu_img[];

		long currentTime = System.currentTimeMillis();
		SimpleDateFormat simDf = new SimpleDateFormat("yyyyMMddHHmmss");
		int read = 0;
		byte[] buf = new byte[1024];
		FileInputStream fin = null;
		FileOutputStream fout = null;

		try {
			MultipartRequest multi = new MultipartRequest(request, truck_savePath, maxSize, "UTF-8",
					new DefaultFileRenamePolicy());

			bsId = multi.getParameter("bsId");
			truckName = multi.getParameter("truckName");
			if(multi.getParameter("truckTel")!=null)
				truckPhone=multi.getParameter("truckTel");
			if(multi.getParameter("truckDayoff")!=null)
				truckDayOff=multi.getParameter("truckDayoff");
			if(multi.getParameter("truckNotice")!=null)
			truckNotice = multi.getParameter("truckNotice");
			truckTime = multi.getParameter("startHour") + "시 " + multi.getParameter("startMin") + "분 ~ "
					+ multi.getParameter("endHour") + "시 " + multi.getParameter("endMin")+"분";
			if(multi.getParameter("menuCount")!=null)
			addMenuCount = Integer.parseInt(multi.getParameter("menuCount"));
			if(multi.getParameter("oriMenuCount")!=null)
			oriMenuCount = Integer.parseInt(multi.getParameter("oriMenuCount"));
		
		
			if (multi.getParameter("xloc").length() >0 && multi.getParameter("yloc").length() >0) {
				truck_Live_OnOff="Y";
				xloc = Double.parseDouble(multi.getParameter("xloc"));
				yloc = Double.parseDouble(multi.getParameter("yloc"));
			}else
				truck_Live_OnOff="N";
			category=multi.getParameter("category");
			menuName = new String[addMenuCount + oriMenuCount];
			menuPrice = new int[addMenuCount + oriMenuCount];
			new_menu_img = new String[addMenuCount + oriMenuCount];
			
			// 전송받은 parameter의 한글깨짐 방지
			// String title = multi.getParameter("title");
			// title = new String(title.getBytes("8859_1"), "UTF-8");
			File oldFile;
			File newFile;
			// 파일업로드
			if (multi.getFilesystemName("truckImg") != null) {
				uploadFile = multi.getFilesystemName("truckImg");

				////////////TESTZONE
				
				
				
				///////////
				new_truck_img = "truck_" + bsId + "_" + simDf.format(new Date(currentTime)) + "."
						+ uploadFile.substring(uploadFile.lastIndexOf(".") + 1);

				oldFile = new File(truck_savePath + uploadFile);

				// 실제 저장될 파일 객체 생성
				newFile = new File(truck_savePath + new_truck_img);
					
				if (!oldFile.renameTo(newFile)) {

					// rename이 되지 않을경우 강제로 파일을 복사하고 기존파일은 삭제

					buf = new byte[1024];
					fin = new FileInputStream(oldFile);
					fout = new FileOutputStream(newFile);
					read = 0;
					while ((read = fin.read(buf, 0, buf.length)) != -1) {
						fout.write(buf, 0, read);
					}

					fin.close();
					fout.close();
					oldFile.delete();
				}

			}  
			else if(multi.getParameter("truckImgOld") != null  ) {
				new_truck_img = multi.getParameter("truckImgOld");
			} else 
			{
				new_truck_img = "truck_sample.png";
			}

			for (int i = 0; i < oriMenuCount; i++) {
				menuName[i] = multi.getParameter("oriMenuName" + i);
				menuPrice[i] = Integer.parseInt(multi.getParameter("oriMenuPrice" + i));
				uploadFile = multi.getFilesystemName("oriMenuFile" + i);
			
				if (uploadFile != null) {
					new_menu_img[i] = "menu_" + bsId + "_" + simDf.format(new Date(currentTime)) + "_" + i + "."
							+ uploadFile.substring(uploadFile.lastIndexOf(".") + 1);
					oldFile = new File(truck_savePath + uploadFile);

					// 실제 저장될 파일 객체 생성
					newFile = new File(menu_savePath + new_menu_img[i]);
					if (!oldFile.renameTo(newFile)) {
						// rename이 되지 않을경우 강제로 파일을 복사하고 기존파일은 삭제
						buf = new byte[1024];
						fin = new FileInputStream(oldFile);
						fout = new FileOutputStream(newFile);
						read = 0;
						while ((read = fin.read(buf, 0, buf.length)) != -1) {
							fout.write(buf, 0, read);
						}
						fin.close();
						fout.close();
						oldFile.delete();
					}
				} else if( multi.getParameter("oriMenuFileImg" + i) !=null   ){
					new_menu_img[i] = multi.getParameter("oriMenuFileImg" + i);
				}
				else 
				{
					new_menu_img[i] = "menu_sample.png";
				}
			}

			for (int i = 1; i <= addMenuCount; i++) {

				menuPrice[oriMenuCount + i - 1] = Integer.parseInt(multi.getParameter("addMenuPrice" + i));
				menuName[oriMenuCount + i - 1] = multi.getParameter("addMenuName" + i);
				uploadFile = multi.getFilesystemName("addMenuFile" + i);
				if (uploadFile != null) {
					new_menu_img[oriMenuCount + i - 1] = "menu_" + bsId + "_" + simDf.format(new Date(currentTime))
							+ "_" + (oriMenuCount + i) + "." + uploadFile.substring(uploadFile.lastIndexOf(".") + 1);
					oldFile = new File(truck_savePath + uploadFile);

					// 실제 저장될 파일 객체 생성
					newFile = new File(menu_savePath + new_menu_img[oriMenuCount + i - 1]);
					if (!oldFile.renameTo(newFile)) {
						// rename이 되지 않을경우 강제로 파일을 복사하고 기존파일은 삭제
						buf = new byte[1024];
						fin = new FileInputStream(oldFile);
						fout = new FileOutputStream(newFile);
						read = 0;
						while ((read = fin.read(buf, 0, buf.length)) != -1) {
							fout.write(buf, 0, read);
						}
						fin.close();
						fout.close();
						oldFile.delete();
					}
				} else {
					new_menu_img[oriMenuCount + i - 1] = "menu_sample.png";
				}

			}
			/////////////////////////////////////////////////////
			////////// 여기서 서비스 실행하면됨
			 Foodtruck truck = new Foodtruck();
			 truck.setBusiness_Id(bsId);
			 truck.setTruck_Category(category);
			 truck.setTruck_Dayoff(truckDayOff);
			 truck.setTruck_Img(new_truck_img);
			 truck.setTruck_Live_OnOff(truck_Live_OnOff);
			 truck.setTruck_Loc_X(xloc);
			 truck.setTruck_Loc_Y(yloc);
			 truck.setTruck_MainMenu(menuName[0]);
			 truck.setTruck_Name(truckName);
			 truck.setTruck_Notice(truckNotice);
			 truck.setTruck_Phone(truckPhone);
			 truck.setTruck_Time(truckTime);
			 
			 FoodtruckService foodservice = new FoodtruckService(); 
			 
			 //트럭 존재여부 확인  true=> 존재 / flase => 없음
		
			 if(foodservice.checkExistTruck(bsId))
			 {// 트럭이 존재한다면~
			 
			///트럭 업데이트
			 int truck_result = foodservice.updateTruck(truck);
			 if(truck_result>0)
				 System.out.println("트럭 업데이트 성공");
			 
			 //메뉴 지우기
			 int menuDelete_result = foodservice.resetMenu(bsId);
			 if(menuDelete_result>0)
				 System.out.println("메뉴 리셋 성공");
			 }else
			 {//트럭이 존재하지 않을때 , 신규 사업자
				 
				 //트럭 등록
				 int truck_result= foodservice.insertTruck(truck);
				 if(truck_result>0)
					 System.out.println("트럭 등록 성공");
				 //신규 사업자는 메뉴 리셋 필요없음
			 }
			 
			Menu menu[]=new Menu[menuName.length];
			for(int i=0;i<menu.length;i++)
			{	menu[i]=new Menu();
				menu[i].setBusiness_Id(bsId);
				menu[i].setMenu_Img(new_menu_img[i]);
				menu[i].setMenu_Name(menuName[i]);
				menu[i].setMenu_price(menuPrice[i]);
				//메뉴 등록
				int menuInsert_result = foodservice.insertMenu(menu[i]);
				if(menuInsert_result>0)
					System.out.println(i+"번째 메뉴 등록 성공");
			}
			
		response.sendRedirect("/food/bstruckinfo?bsId="+bsId);
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
