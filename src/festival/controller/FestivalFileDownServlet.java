package festival.controller;

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FestivalFileDownServlet
 */
@WebServlet("/fes_down")
public class FestivalFileDownServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FestivalFileDownServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    /** 축제 포스터 파일 다운로드 컨트롤러 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String folder=request.getSession().getServletContext().getRealPath("festival_upload");
		
		String originalFile=request.getParameter("originalFile");
		String renameFile=request.getParameter("renameFile");
		
		//클라이언트로 내보낼 출력스트림
		ServletOutputStream fileOut=response.getOutputStream();
		File downFile=new File(folder+"/"+renameFile);
		response.setContentType("text/plain; charset=utf-8");
		
		//한글파일명 인코딩작업
		response.addHeader("Content-Disposition", "attachment; filename=\""+
				new String(originalFile.getBytes("UTF-8"),"ISO-8859-1")+"\"");
		response.setContentLength((int)downFile.length());
		
		//폴더에서 파일을 읽어 클라이언트 화면으로 내보냄
		BufferedInputStream bis=new BufferedInputStream(new FileInputStream(downFile));
		
		int read= -1;
		
		while((read = bis.read()) != -1) {
			fileOut.write(read);
			fileOut.flush();
		}
		fileOut.close();
		bis.close();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
