package festival.controller;

import java.io.*;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import festival.model.service.*;
import festival.model.vo.*;

/**
 * Servlet implementation class FestivalUpdateServlet
 */
@WebServlet("/fes_update")
public class FestivalUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FestivalUpdateServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	/** 축제수정 컨트롤러 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int maxSize = 1024 * 1024 * 10;

		// Tomcat에서 구동중인 root 경로 알아내기
		String root = request.getSession().getServletContext().getRealPath("/");
		// System.out.println(root);

		String savePath = root + "resources/images/festival/";

		// cos.jar파일 사용시 MultipartRequest 생성, 생성과 동시에 자료업로드 가능
		MultipartRequest mRequest = new MultipartRequest(request, savePath, maxSize, "UTF-8",
				new DefaultFileRenamePolicy());
		int fNum = 0;
		if(mRequest.getParameter("fNum")!=null) {
		 fNum = Integer.parseInt(mRequest.getParameter("fNum"));
		}
		else
		{
		 fNum = new FestivalService().nextFesNum();	
		}
		int diResult = new FestivalService().deleteImages(fNum);
		int dfResult = new FestivalService().deleteFestival(fNum);
	

		System.out.println("축제이미지 삭제: " + diResult + " / 축제정보 삭제: " + dfResult);

		Festival upFestival = new Festival();
		upFestival.setFestival_No(fNum);
		upFestival.setFestival_Name(mRequest.getParameter("upfes_title"));
		upFestival.setFestival_Addr(mRequest.getParameter("upfes_addr"));
		upFestival.setFestival_Date_Start(Date.valueOf(mRequest.getParameter("upfes_start")));
		upFestival.setFestival_Date_End(Date.valueOf(mRequest.getParameter("upfes_end")));
		upFestival.setFestival_Phone(mRequest.getParameter("fes_phone"));
		upFestival.setFestival_Host(mRequest.getParameter("upfes_host"));
		
		////////썸네일 사진 등록
		String originalFileName = null;
		
		if(mRequest.getFilesystemName("imgFile0")!=null)
		originalFileName = mRequest.getFilesystemName("imgFile0");
	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String renameFileName=null;
		if (originalFileName != null && !originalFileName.equals("null")) {
				System.out.println("썸네일 ori not null : "+originalFileName);
			renameFileName = upFestival.getFestival_Name() + "_thumbnail_"
					+ sdf.format(new java.sql.Date(System.currentTimeMillis())) + "."
					+ originalFileName.substring(originalFileName.lastIndexOf(".") + 1);

			// 파일 이름 바꾸기에는 renameTo() 사용
			File originalFile = new File(savePath + "/" + originalFileName);
			File renameFile = new File(savePath + "/" + renameFileName);

			if (!originalFile.renameTo(renameFile)) {
				// 이름바꾸기 실패시
				int read = -1;
				byte[] buffer = new byte[1024]; // 한번에 읽을 배열크기 지정

				// 원본을 읽기위한 파일스트림 생성
				FileInputStream fis = new FileInputStream(originalFile);
				// 원본파일 읽은내용을 저장할 복사본 파일 출력용 스트림생성
				FileOutputStream fout = new FileOutputStream(renameFile);

				// 원본 파일을 읽어서 복사본에 기록처리
				while ((read = fis.read(buffer, 0, buffer.length)) != -1) {
					fout.write(buffer, 0, read);

				}
				fis.close();
				fout.close();
				originalFile.delete(); // 원본 삭제
			}
			

		}
		else if(mRequest.getParameter("upfes_image_ori")!=null && !mRequest.getParameter("upfes_image_ori").equals("null") && mRequest.getParameter("upfes_image_ori").length()>0)
		{	
		
			renameFileName=mRequest.getParameter("upfes_image_ori");
			System.out.println("썸네일 rename not null : "+renameFileName);
		}
		else
		{
			renameFileName="20180416212659.jpg";
		}
		upFestival.setFestival_Image_File(renameFileName);
		
		originalFileName=null;
		////////메인사진등록
		if(mRequest.getFilesystemName("imgFile1")!=null)
		originalFileName = mRequest.getFilesystemName("imgFile1");
		if (originalFileName != null && !originalFileName.equals("null")) {

			renameFileName = upFestival.getFestival_Name() + "_mainImg_"
					+ sdf.format(new java.sql.Date(System.currentTimeMillis())) + "."
					+ originalFileName.substring(originalFileName.lastIndexOf(".") + 1);

			// 파일 이름 바꾸기에는 renameTo() 사용
			File originalFile = new File(savePath + "/" + originalFileName);
			File renameFile = new File(savePath + "/" + renameFileName);

			if (!originalFile.renameTo(renameFile)) {
				// 이름바꾸기 실패시
				int read = -1;
				byte[] buffer = new byte[1024]; // 한번에 읽을 배열크기 지정

				// 원본을 읽기위한 파일스트림 생성
				FileInputStream fis = new FileInputStream(originalFile);
				// 원본파일 읽은내용을 저장할 복사본 파일 출력용 스트림생성
				FileOutputStream fout = new FileOutputStream(renameFile);

				// 원본 파일을 읽어서 복사본에 기록처리
				while ((read = fis.read(buffer, 0, buffer.length)) != -1) {
					fout.write(buffer, 0, read);

				}
				fis.close();
				fout.close();
				originalFile.delete(); // 원본 삭제
			}
		}
		else if(mRequest.getParameter("upfes_image_main")!=null && !mRequest.getParameter("upfes_image_main").equals("null") && mRequest.getParameter("upfes_image_main").length()>0 )
		{
			renameFileName=mRequest.getParameter("upfes_image_main");
		}
		else
		{
			renameFileName="FESTIVAL.jpg";
		}
		
		upFestival.setFestival_Image_Main(renameFileName);
		
		
		int result = new FestivalService().insertFestival(upFestival);
		
////////////////////여러사진 등록
				
		String inputImages[] = mRequest.getParameterValues("fileTagName");
		String oriImages[] = mRequest.getParameterValues("oriImg");
		for (int i = 0; i < inputImages.length; i++) {
			 renameFileName = null;
			Festival_Image fi = new Festival_Image();
			fi.setFestival_No(fNum);
			
			
			
			if (mRequest.getFilesystemName(inputImages[i]) != null) {
				originalFileName = mRequest.getFilesystemName(inputImages[i]);
				renameFileName = upFestival.getFestival_Name() + "_fesImage_"
						+ sdf.format(new java.sql.Date(System.currentTimeMillis())) + "." + "_" + i
						+ originalFileName.substring(originalFileName.lastIndexOf(".") + 1);

				// 파일 이름 바꾸기에는 renameTo() 사용
				File originalFile = new File(savePath + "/" + originalFileName);
				File renameFile = new File(savePath + "/" + renameFileName);

				if (!originalFile.renameTo(renameFile)) {
					// 이름바꾸기 실패시
					int read = -1;
					byte[] buffer = new byte[1024]; // 한번에 읽을 배열크기 지정

					// 원본을 읽기위한 파일스트림 생성
					FileInputStream fis = new FileInputStream(originalFile);
					// 원본파일 읽은내용을 저장할 복사본 파일 출력용 스트림생성
					FileOutputStream fout = new FileOutputStream(renameFile);

					// 원본 파일을 읽어서 복사본에 기록처리
					while ((read = fis.read(buffer, 0, buffer.length)) != -1) {
						fout.write(buffer, 0, read);

					}
					fis.close();
					fout.close();
					originalFile.delete(); // 원본 삭제
				}
				
			}
			else if (oriImages[i]!=null && !oriImages[i].equals("null") && oriImages[i].length()>0) {
				System.out.println("실행확인 : "+oriImages[i]);
				renameFileName=oriImages[i];
				}
			else
			{
				renameFileName="festival_image5.jpg";
			}
			fi.setFestival_Img(renameFileName);
	
			new FestivalService().insertImages(fi);
		}

		
	

		// System.out.println("축제정보 등록결과: " + result + "/ 축제이미지 등록결과: " + resultImage);

		response.setContentType("text/html; charset=utf-8");

		if (result > 0 /* && resultImage > 0 */) {
			response.sendRedirect("fes_show");
		} else {
			response.sendRedirect("views/festival/festival_error.html");
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
