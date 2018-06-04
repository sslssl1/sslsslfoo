package common;

import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class Jdbctemplate {

	public static Connection getConnection() {
		Connection con = null;

		try {
			Context initContext = new InitialContext();
			DataSource ds = (DataSource)initContext.lookup("java:comp/env/jdbc/oraDB");
			con = ds.getConnection();
			con.setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return con;
	}
	
	public static void commit(Connection con)
	{
		try {
			if(con!=null && !con.isClosed())
				con.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void rollback(Connection con)
	{
		try {
			if(con!=null && !con.isClosed())
				con.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void close(Connection con)
	{
		try {
			if(  con!=null && !con.isClosed()  )
				con.close();
		} catch (Exception e) {
		e.printStackTrace();
		}
	}
	
	public static void close(Statement stmt)
	{
		try {
			if(stmt!=null && !stmt.isClosed())
				stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void close(ResultSet rset)
	{
		try {
			if(rset!=null && !rset.isClosed())
				rset.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
