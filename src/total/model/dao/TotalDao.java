package total.model.dao;

import static common.Jdbctemplate.*;

import java.sql.*;
import java.util.ArrayList;
import total.model.vo.Total;

public class TotalDao {

	public TotalDao() {
		// TODO Auto-generated constructor stub
	}
	
	public ArrayList<Total> total(Connection con) {
		ArrayList<Total> list=new ArrayList<Total>();
		Statement stmt=null;
		ResultSet rset=null;
		String sql="select t_total from total order by t_date asc";
		
		try {
			stmt=con.createStatement();
			rset=stmt.executeQuery(sql);
			
			while (rset.next())
			{
				Total t=new Total();
				t.setT_total(rset.getInt("t_total"));
				
				list.add(t);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			close(stmt);
			close(rset);
		}
		
		return list;
	}
	
	public int totalAdd(Connection con) {
		int result=0;
		Statement stmt=null;
		Statement stmt1=null;
		Statement stmt2=null;
		ResultSet rset=null;
		String sql="select t_date from total where t_date=to_char(sysdate+1,'YYYY.MM.DD')";
		String sql1="insert into total values(to_char(sysdate+1,'YYYY.MM.DD'),0)";
		String sql2="update total set t_total=t_total+1 where t_date=to_char(sysdate,'YYYY.MM.DD')";
		
		try {
			stmt=con.createStatement();
			rset=stmt.executeQuery(sql);
			
			if(rset.next() == false) {
				stmt1=con.createStatement();
				result=stmt1.executeUpdate(sql1);
			}
			
			stmt2=con.createStatement();
			result=stmt2.executeUpdate(sql2);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			close(stmt);
			if(rset==null) {
			close(stmt1);
			}
			close(stmt2);
			
		}
		
		
		return result;
	}
}
