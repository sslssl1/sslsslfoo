package total.model.service;

import static common.Jdbctemplate.*;
import java.sql.*;
import java.util.ArrayList;

import total.model.dao.TotalDao;
import total.model.vo.Total;

public class TotalService {
	public TotalService() {
		// TODO Auto-generated constructor stub
	}
	
	public ArrayList<Total> total() {
		Connection con = getConnection();
		ArrayList<Total> list=new TotalDao().total(con);
		System.out.println("서비스 접속했다."+list);
		close(con);
		
		return list;
	}
	
	public int totalAdd() {
		Connection con=getConnection();
		
		int result=new TotalDao().totalAdd(con);
		if(result>0) {
			commit(con);
		}else {
			rollback(con);
		}
		close(con);
		return 0;
	}
}
