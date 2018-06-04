package festival.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.synth.SynthSeparatorUI;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import festival.model.service.FestivalService;
import festival.model.vo.Festival;
import foodtruck.model.vo.Foodtruck;

/**
 * Servlet implementation class FestivalSearchServlet
 */
@WebServlet("/fsearch.do")
public class FestivalSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 823934L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FestivalSearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    
    /** 축제리스트 검색 컨트롤러 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String fSearch=request.getParameter("fSearch");
		System.out.println("검색값: "+fSearch);
		
		ArrayList<Festival> searchFestival=new FestivalService().searchFestival(fSearch);
		
		JSONObject json=new JSONObject();
		JSONArray jarr=new JSONArray();
		
			for(Festival f : searchFestival) {
				JSONObject job=new JSONObject();
					job.put("festival_no", f.getFestival_No());
					job.put("festival_name", f.getFestival_Name());
					job.put("festival_addr", f.getFestival_Addr());
					job.put("festival_date_start", f.getFestival_Date_Start().toString());
					job.put("festival_date_end", f.getFestival_Date_End().toString());
					job.put("festival_host", f.getFestival_Host());
					job.put("festival_phone", f.getFestival_Phone());
					job.put("festival_image_file", f.getFestival_Image_File());
					
//					System.out.println(f.getFestival_No());
//					System.out.println(f.getFestival_Name());
//					System.out.println(f.getFestival_Addr());
//					System.out.println(f.getFestival_Date_Start());
//					System.out.println(f.getFestival_Date_End());
//					System.out.println(f.getFestival_Host());
//					System.out.println(f.getFestival_Phone());
//					System.out.println(f.getFestival_Image_File());
					
					jarr.add(job);
					
			}
			
			json.put("list", jarr);
			response.setContentType("application/json; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(json.toJSONString());
			out.flush();
			out.close();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
