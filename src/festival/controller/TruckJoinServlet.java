package festival.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import festival.model.service.FestivalService;
import festival.model.vo.Festival;
import festival.model.vo.Festival_Truck;

/**
 * Servlet implementation class TruckJoinServlet
 */
@WebServlet("/join.do")
public class TruckJoinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TruckJoinServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    /** 푸드트럭 참가 컨트롤러 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int fesNum=Integer.parseInt(request.getParameter("fesno"));
		String businessId=request.getParameter("truckid");
		
		Festival_Truck ft=new FestivalService().selectTruck(fesNum, businessId);
			
		//참여한적이 없으면 참여해라
		if(ft==null) {
			Festival_Truck fTruck=new Festival_Truck();
			fTruck.setFestival_No(fesNum);
			fTruck.setBusiness_Id(businessId);
			
			new FestivalService().insertTruck(fTruck);	
		}
		
//		String title=request.getParameter("title");
//		
//		response.setContentType("text/html; charset=utf-8");
//		PrintWriter out = response.getWriter(); //클라이언트와 출력스트림 생성
//		out.append(title); //출력스트림을 통해 값 전송함
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
