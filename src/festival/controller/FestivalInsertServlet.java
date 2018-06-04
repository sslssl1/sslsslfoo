package festival.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import festival.model.service.FestivalService;
import festival.model.vo.Festival;
import festival.model.vo.Festival_Image;

/**
 * Servlet implementation class FestivalInsertServlet
 */
@WebServlet("/fes_list")
public class FestivalInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FestivalInsertServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    /** 축제등록 컨트롤러 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		request.setCharacterEncoding("utf-8");
		//용량은 10mbyte로 정의
		int maxSize=1024*1024*10;
		
		//Tomcat에서 구동중인 root 경로 알아내기
		String root=request.getSession().getServletContext().getRealPath("/");
//			System.out.println(root);
		
		String savePath=root+"festival_upload";
		
		//cos.jar파일 사용시 MultipartRequest 생성, 생성과 동시에 자료업로드 가능
		MultipartRequest mRequest=new MultipartRequest(request, savePath, maxSize, "UTF-8", new DefaultFileRenamePolicy());
		
		//전송온 값 저장하기
		Festival festival=new Festival();
			festival.setFestival_Name(mRequest.getParameter("fes_title"));
			festival.setFestival_Addr(mRequest.getParameter("fes_addr"));
			festival.setFestival_Date_Start(Date.valueOf(mRequest.getParameter("fes_start")));
			festival.setFestival_Date_End(Date.valueOf(mRequest.getParameter("fes_end")));
			festival.setFestival_Host(mRequest.getParameter("fes_host"));
			festival.setFestival_Phone(mRequest.getParameter("fes_phone"));
			festival.setFestival_Image_File(mRequest.getFilesystemName("fes_image"));
			
			Festival_Image fImage=new Festival_Image();
				fImage.setFestival_Img(mRequest.getFilesystemName("fes_image"));
		
			
		String originalFileName=fImage.getFestival_Img();
		System.out.println("등록 이미지: "+originalFileName);
		
			if(originalFileName != null) {
				SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
				
				String renameFileName=sdf.format(new java.sql.Date(System.currentTimeMillis()))
						+"."+originalFileName.substring(originalFileName.lastIndexOf(".") +1);
				
				//파일 이름 바꾸기에는 renameTo() 사용
				File originalFile=new File(savePath+"/"+originalFileName);
				File renameFile=new File(savePath+"/"+renameFileName);
				
				//파일 이름바꾸기 실행함 
				//이름바꾸기 실패할 경우에는 직접 바꾸기함
				//직접바꾸기는 원본파일에 대한 복사본 파일을 만든 다음
				//원본 삭제함
				
				if(!originalFile.renameTo(renameFile)) {
					//이름바꾸기 실패시
					int read= -1;
					byte[] buffer=new byte[1024];	//한번에 읽을 배열크기 지정
					
					//원본을 읽기위한 파일스트림 생성
					FileInputStream fis=new FileInputStream(originalFile);
					//원본파일 읽은내용을 저장할 복사본 파일 출력용 스트림생성
					FileOutputStream fout=new FileOutputStream(renameFile);
					
					//원본 파일을 읽어서 복사본에 기록처리
					while((read=fis.read(buffer,0,buffer.length)) != -1) {
						fout.write(buffer, 0, read);
						
					}
					fis.close();
					fout.close();
					originalFile.delete();	//원본 삭제
				}
				fImage.setFestival_Img(renameFileName);
				festival.setFestival_Image_File(renameFileName);
				
			}
		
		// 서비스클래스로 값 전달 후 결과 받기	
		int result = new FestivalService().insertFestival(festival);
		int resultImage=new FestivalService().insertImages(fImage);
		
		
		
		System.out.println("축제정보 등록결과: "+result+"/ 축제이미지 등록결과: "+resultImage);
		
		response.setContentType("text/html; charset=utf-8");
		
		if(result > 0 && resultImage > 0) {
			response.sendRedirect("fes_show");
		}else {
			response.sendRedirect("views/festival/festival_error.html");
		}
	}
		

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
