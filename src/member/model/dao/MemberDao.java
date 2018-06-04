package member.model.dao;

import java.sql.*;
import member.model.service.*;
import member.model.vo.*;
import java.util.*;
import static common.Jdbctemplate.*;

public class MemberDao {
	public MemberDao() {
	}

	public Member memberLogin(Connection con, String userId, String password) {
		Member member = null;
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		System.out.println("memberLogin : "+userId + "\n"+password);
		String sql_customer = " select * from customer where CUSTOMER_ID = ? and CUSTOMER_PWD = ?  ";
		String sql_business = " select * from BUSINESS where BUSINESS_ID = ? and BUSINESS_PWD = ? ";
		String sql_manager = " select * from MANAGER where MANAGER_ID = ? and MANAGER_PWD = ? ";
		try {
			pstmt = con.prepareStatement(sql_customer);
			pstmt.setString(1, userId);
			pstmt.setString(2, password);
			rset = pstmt.executeQuery();
			if (rset.next()) {

				member = new Customer();
				/// 일반회원
				// CUSTOMER_ID VARCHAR2(20 BYTE)
				// CUSTOMER_PWD VARCHAR2(20 BYTE)
				// CUSTOMER_NAME VARCHAR2(10 BYTE)
				// CUSTOMER_EMAIL VARCHAR2(30 BYTE)
				// WITHDRAW VARCHAR2(1 BYTE)
				((Customer) member).setCustomer_Email(rset.getString("CUSTOMER_EMAIL"));
				((Customer) member).setCustomer_Id(rset.getString("CUSTOMER_ID"));
				((Customer) member).setCustomer_Pwd(rset.getString("CUSTOMER_PWD"));
				((Customer) member).setWithdraw(rset.getString("WITHDRAW"));
				((Customer) member).setCustomer_Name(rset.getString("CUSTOMER_NAME"));
			} else {
				pstmt = null;
				pstmt = con.prepareStatement(sql_business);
				pstmt.setString(1, userId);
				pstmt.setString(2, password);
				rset = null;
				rset = pstmt.executeQuery();
				if (rset.next()) {
					member = new Business();

					((Business) member).setApproval(rset.getString("APPROVAL"));
					((Business) member).setBusiness_Email(rset.getString("BUSINESS_EMAIL"));
					((Business) member).setBusiness_Id(rset.getString("BUSINESS_ID"));
					((Business) member).setBusiness_Name(rset.getString("BUSINESS_NAME"));
					((Business) member).setBusiness_Phone(rset.getString("BUSINESS_PHONE"));
					((Business) member).setBusiness_Pwd(rset.getString("BUSINESS_PWD"));
					((Business) member).setWithdraw(rset.getString("WITHDRAW"));
					((Business) member).setBUSINESS_REJECT_REASON(rset.getString("BUSINESS_REJECT_REASON"));
					// 사업자 회원

					// BUSINESS_ID VARCHAR2(20 BYTE)
					// BUSINESS_PWD VARCHAR2(20 BYTE)
					// BUSINESS_NAME VARCHAR2(10 BYTE)
					// BUSINESS_EMAIL VARCHAR2(30 BYTE)
					// BUSINESS_PHONE VARCHAR2(30 BYTE)
					// WITHDRAW VARCHAR2(1 BYTE)
					// APPROVAL VARCHAR2(1 BYTE)

				} else {
					pstmt = null;
					pstmt = con.prepareStatement(sql_manager);
					pstmt.setString(1, userId);
					pstmt.setString(2, password);
					rset = null;
					rset = pstmt.executeQuery();
					if (rset.next()) {
						member = new Manager();
						// 관리자 회원
						((Manager) member).setManager_Id(rset.getString("MANAGER_ID"));
						((Manager) member).setManager_Pwd(rset.getString("MANAGER_PWD"));

						/*
						 * MANAGER_ID VARCHAR2(20 BYTE) MANAGER_PWD VARCHAR2(20 BYTE)
						 */
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return member;
	}

	public int customerEnroll(Connection con, Member member) {
		int result = 0;
		// CUSTOMER_ID VARCHAR2(20 BYTE)
		// CUSTOMER_PWD VARCHAR2(20 BYTE)
		// CUSTOMER_NAME VARCHAR2(10 BYTE)
		// CUSTOMER_EMAIL VARCHAR2(30 BYTE)
		// WITHDRAW VARCHAR2(1 BYTE)
		PreparedStatement pstmt = null;
		String sql = "INSERT into CUSTOMER values (?,?,?,?,default)";
		try {
			pstmt = con.prepareStatement(sql);
			System.out.println("cs mail" +((Customer) member).getCustomer_Email()  );
			pstmt.setString(1, ((Customer) member).getCustomer_Id());
			pstmt.setString(2, ((Customer) member).getCustomer_Pwd());
			pstmt.setString(3, ((Customer) member).getCustomer_Name());
			pstmt.setString(4, ((Customer) member).getCustomer_Email());

			result = pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("MemberDao.customerEnroll : " + e.getMessage());
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;

	}

	public int BusinessEnroll(Connection con, Member member) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = "insert into Business values (?,?,?,?,?,'N','N',NULL)";

		// BUSINESS_ID VARCHAR2(20 BYTE)
		// BUSINESS_PWD VARCHAR2(20 BYTE)
		// BUSINESS_NAME VARCHAR2(10 BYTE)
		// BUSINESS_EMAIL VARCHAR2(30 BYTE)
		// BUSINESS_PHONE VARCHAR2(30 BYTE)
		// WITHDRAW VARCHAR2(1 BYTE)
		// APPROVAL VARCHAR2(1 BYTE)

		try {
			pstmt = con.prepareStatement(query);

			pstmt.setString(1, ((Business) member).getBusiness_Id());
			pstmt.setString(2, ((Business) member).getBusiness_Pwd());
			pstmt.setString(3, ((Business) member).getBusiness_Name());
			pstmt.setString(4, ((Business) member).getBusiness_Email());
			pstmt.setString(5, ((Business) member).getBusiness_Phone());

			result = pstmt.executeUpdate();
			System.out.println(member);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}

		return result;
	}
	/*
	 * public int memberEnroll(Connection con,String userId) { int result=0;
	 * PreparedStatement pstmt= null; String sql="insert into MEMBER values(?) ";
	 * try { pstmt = con.prepareStatement(sql); pstmt.setString(1, userId);
	 * 
	 * result=pstmt.executeUpdate(); } catch (Exception e) {
	 * System.out.println("MemberDao.memberEnroll : "+e.getMessage());
	 * e.printStackTrace(); }finally { close(pstmt); }
	 * 
	 * 
	 * return result; }
	 */

	public String searchBSId(Connection con, String userName, String email) {
		String userId = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "select BUSINESS_ID from BUSINESS where BUSINESS_NAME = ? and BUSINESS_EMAIL =?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userName);
			pstmt.setString(2, email);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				userId = new String(rset.getString("BUSINESS_ID"));
			}

		} catch (Exception e) {

			System.out.println("MemberDao.searchBSId : " + e.getMessage());
			e.printStackTrace();
		} finally {

		}
		return userId;
	}

	public String searchCSId(Connection con, String userName, String email) {
		String userId = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "select CUSTOMER_ID from CUSTOMER where CUSTOMER_NAME = ? and CUSTOMER_EMAIL =?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userName);
			pstmt.setString(2, email);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				userId = new String(rset.getString("CUSTOMER_ID"));
			}

		} catch (Exception e) {

			System.out.println("MemberDao.searchCSId : " + e.getMessage());
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return userId;
	}

	public String searchCSPwd(Connection con, String userId, String userName, String email) {

		PreparedStatement pstmt = null;
		String sql = "select CUSTOMER_PWD from CUSTOMER where CUSTOMER_ID = ? and  CUSTOMER_NAME = ? and CUSTOMER_EMAIL =?";
		ResultSet rset = null;
		String userPwd = null;

		try {
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, userId);
			pstmt.setString(2, userName);
			pstmt.setString(3, email);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				userPwd = new String(rset.getString("CUSTOMER_PWD"));
			}

		} catch (Exception e) {
			System.out.println("MemberDao.searchCSPwd : " + e.getMessage());
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return userPwd;
	}

	public String searchBSPwd(Connection con, String userId, String userName, String email) {

		PreparedStatement pstmt = null;
		String sql = "select BUSINESS_PWD from BUSINESS where BUSINESS_ID = ? and  BUSINESS_NAME = ? and BUSINESS_EMAIL =?";
		ResultSet rset = null;
		String userPwd = null;

		try {
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, userId);
			pstmt.setString(2, userName);
			pstmt.setString(3, email);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				userPwd = new String(rset.getString("BUSINESS_PWD"));
			}

		} catch (Exception e) {
			System.out.println("MemberDao.searchCSPwd : " + e.getMessage());
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return userPwd;
	}

	public int duplicateCusId(Connection con, String userId) {
	
		PreparedStatement pstmt = null;
		ResultSet rset =null;
		String sql = "select * from customer where CUSTOMER_ID = ?";
		int result = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);
			rset = pstmt.executeQuery();
			
			if(rset.next())
			{
				result = 1;
			}
			
		} catch (Exception e) {
			System.out.println("MemberDao.duplicateId : " + e.getMessage());
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return result;
	}

	public int duplicateBsId(Connection con, String userId) {
	
		PreparedStatement pstmt = null;
		ResultSet rset =null;
		String sql = "select * from BUSINESS where BUSINESS_ID = ?";
		int result = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);
			rset = pstmt.executeQuery();
			
			if(rset.next())
			{
				result = 1;
			}
			
		} catch (Exception e) {
			System.out.println("MemberDao.duplicateId : " + e.getMessage());
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return result;
	}

	public int duplicateMgId(Connection con, String userId) {
		PreparedStatement pstmt = null;
		ResultSet rset =null;
		String sql = "select * from MANAGER where MANAGER_ID = ?";
		int result = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);
			rset = pstmt.executeQuery();
			
			if(rset.next())
			{
				result = 1;
			}
			
		} catch (Exception e) {
			System.out.println("MemberDao.duplicateId : " + e.getMessage());
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return result;
	}
	public int updateMember(Connection con, Customer customer) {
		int result=0;
		PreparedStatement pstmt =null;
		String query ="update customer set CUSTOMER_PWD=?, CUSTOMER_EMAIL=? "
				+ " where customer_id=? ";
		
		try {
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1,customer.getCustomer_Pwd());
			pstmt.setString(2,customer.getCustomer_Email());
			pstmt.setString(3,customer.getCustomer_Id());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}

	public int withDraw(Connection con, String userid) {
	
		int result =0;
		PreparedStatement pstmt = null;
		
		String query ="update customer set withdraw ='Y' where customer_id=?";
				
		try {
		
		pstmt = con.prepareStatement(query);
		pstmt.setString(1, userid);
		result = pstmt.executeUpdate();
		
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public int csPwdCheck(Connection con, String userId, String userPwd) {

		String sql = " select * from customer where customer_id = ? and  CUSTOMER_PWD = ? ";
		ResultSet rset = null;
		PreparedStatement pstmt=null;
		int result = 0;
	
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setString(2, userPwd);
			rset = pstmt.executeQuery();
			if(rset.next())
			{
				result=1;
			}
			
			
		} catch (Exception e) {
			System.out.println("MemberDao.csPwdCheck : " + e.getMessage());
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return result;
	}
	public int changeAppoval(Connection con, String bsId) {
		int result =0;
		PreparedStatement pstmt= null;
		
		String query = "update Business set APPROVAL='Y' where BUSINESS_ID= ? ";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, bsId);
			
			result = pstmt.executeUpdate(); 
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result ;
	}
	public int rejectApproval(Connection con,String rejectReason, String bsId) {
		int result = 0;
		
		PreparedStatement pstmt = null;
		String query = "update Business set APPROVAL='R' , BUSINESS_REJECT_REASON = ? where BUSINESS_ID=? ";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(2, bsId);
			pstmt.setString(1, rejectReason);
			
			result = pstmt.executeUpdate(); 
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result ;
			
	}
	public int deleteBusiness(Connection con, String bsId) {
		int result=0;
		PreparedStatement pstmt =null;
		String query ="update business set withdraw='Y' "
				+ " where business_id=? ";
		
		try {
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1,bsId);
			
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}public int getListApprovalCount(Connection con) {
		int listCount=0;
		
		Statement stmt = null;
		ResultSet rset = null;
		
		String query ="select count(*) from business where approval ='N' and WITHDRAW ='N'";
		
		try {
			stmt = con.createStatement();
			rset= stmt.executeQuery(query);
			
			if(rset.next()) {
				listCount = rset.getInt("count(*)");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(stmt);
		}
		return listCount;
	}public ArrayList<Business> selectApproval(Connection con, int currentPage, int limit) {
		ArrayList<Business> list = new ArrayList<Business>();
		PreparedStatement pstmt =null;
		ResultSet rset = null;

		
		int startRow =(currentPage-1)* limit+1;
		int endRow =startRow + limit-1;
		
		String query = "select * from (select rownum rn,BUSINESS_ID,BUSINESS_PWD,BUSINESS_NAME,BUSINESS_EMAIL,BUSINESS_PHONE,WITHDRAW,APPROVAL from business where withdraw =  'N' and approval = 'N' order by business_id ) where rn >=? and rn <=  ?";
   
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Business b = new Business();
				
				b.setBusiness_Id(rset.getString("BUSINESS_ID"));
				b.setBusiness_Email(rset.getString("BUSINESS_EMAIL"));
				b.setBusiness_Name(rset.getString("BUSINESS_NAME"));
				
				list.add(b);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}
	public int getListRejectCount(Connection con) {
		int listCount=0;
		
		Statement stmt = null;
		ResultSet rset = null;
		
		String query ="select count(*) from business where approval ='R' and WITHDRAW ='N'";
		
		try {
			stmt = con.createStatement();
			rset= stmt.executeQuery(query);
			
			if(rset.next()) {
				listCount = rset.getInt("count(*)");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(stmt);
		}
		return listCount;
	}
	public ArrayList<Business> selectReject(Connection con, int currentPage, int limit) {
		ArrayList<Business> list = new ArrayList<Business>();
		PreparedStatement pstmt =null;
		ResultSet rset = null;

		
		int startRow =(currentPage-1)* limit+1;
		int endRow =startRow + limit-1;
		
		String query = "select * from (select rownum rn,BUSINESS_ID,BUSINESS_PWD,BUSINESS_NAME,BUSINESS_EMAIL,BUSINESS_PHONE,WITHDRAW,APPROVAL from business where withdraw =  'N' and approval = 'R' order by business_id ) where rn >=? and rn <=  ?";
   
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Business b = new Business();
				
				b.setBusiness_Id(rset.getString("BUSINESS_ID"));
				b.setBusiness_Email(rset.getString("BUSINESS_EMAIL"));
				b.setBusiness_Name(rset.getString("BUSINESS_NAME"));
				
				list.add(b);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}public int getBusApproval_SearchCount(Connection con, String seType, String seValue) {
		
		
		int listCount=0;
		ResultSet rset =  null;
		PreparedStatement pstmt= null;
		String sql = " select count(*) from business where "+seType+" = ?" ;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, seValue);
		
			rset= pstmt.executeQuery();
			if(rset.next()) {
				listCount=rset.getInt("count(*)");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
			close(rset);
		}
		return listCount;
	}public ArrayList<Business> searchBusApprovalList(Connection con, ApprovalSearch as) {
		
		ArrayList<Business> blist = new ArrayList<Business>();
		ResultSet rset=null;
		
		int startRow =(as.getCurrentPage()-1)* as.getLimit()+1;
		int endRow =startRow + as.getLimit()-1;
		String query = "select * from (select rownum rn,BUSINESS_ID,BUSINESS_PWD,BUSINESS_NAME,BUSINESS_EMAIL,BUSINESS_PHONE,WITHDRAW,APPROVAL from business where withdraw =  'N' and approval = 'N' and "+as.getSeType()+ " like '%"+as.getSeValue()+"%' order by business_id ) where rn >=? and rn <=  ?";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Business b = new Business();
				
				b.setBusiness_Id(rset.getString("BUSINESS_ID"));
				b.setBusiness_Email(rset.getString("BUSINESS_EMAIL"));
				b.setBusiness_Name(rset.getString("BUSINESS_NAME"));
				
				blist.add(b);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return blist;
	}
	
	public int getListBusinessMemberCount(Connection con) {
		int listCount=0;
				
				Statement stmt = null;
				ResultSet rset = null;
				
				String query ="select count(*) from business where approval ='Y' and WITHDRAW ='N'";
				
				try {
					stmt = con.createStatement();
					rset= stmt.executeQuery(query);
					
					if(rset.next()) {
						listCount = rset.getInt("count(*)");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}finally {
					close(rset);
					close(stmt);
				}
				return listCount;
			}
	public ArrayList<Business> selectBusinessMemberList(Connection con, int currentPage, int limit) {
		ArrayList<Business> list = new ArrayList<Business>();
		PreparedStatement pstmt =null;
		ResultSet rset = null;

		
		int startRow =(currentPage-1)* limit+1;
		int endRow =startRow + limit-1;

		String query = "select * from (select rownum rn,BUSINESS_ID,BUSINESS_PWD,BUSINESS_NAME,BUSINESS_EMAIL,BUSINESS_PHONE,WITHDRAW,APPROVAL from business where withdraw = 'N' and approval = 'Y' order by business_id ) where rn >=? and rn <=  ?";
   
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Business b = new Business();
				
				b.setBusiness_Id(rset.getString("BUSINESS_ID"));
				b.setBusiness_Email(rset.getString("BUSINESS_EMAIL"));
				b.setBusiness_Name(rset.getString("BUSINESS_NAME"));
				
				
				list.add(b);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return list;
		
	}
	public int compulsionWithDraw(Connection con, String bsId) {
		int result = 0;
		PreparedStatement pstmt= null;
		String query = "update business set withdraw ='Y' where BUSINESS_ID=? ";
		
		try {
		pstmt=con.prepareStatement(query);
		pstmt.setString(1, bsId);
		
		result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		
		return result;
		
	}
public String findId(Connection con, String userName, String email) {
		
		String userId = null;
		
		PreparedStatement pstmt = null;
		
		ResultSet rset = null;
		
		String query = "select * from customer where customer_name = ? and customer_email = ?";
		String query_bs = "select * from business where business_name = ? and business_email = ?";
		
		
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, userName);
			pstmt.setString(2, email);
			//System.out.println("username : " +userName +"\nemail : "+email);
			rset = pstmt.executeQuery();
			
			if(rset.next())
			{   //System.out.println("dao 실행");
				userId = rset.getString("customer_id");    
				}
			
			else {
				pstmt = con.prepareStatement(query_bs);
				pstmt.setString(1, userName);
				pstmt.setString(2, email);
				rset = pstmt.executeQuery();
				//System.out.println("username : " +userName +"\nemail : "+email);
				if(rset.next()) {
					userId = rset.getString("business_id");
					
				}
			
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return userId;
		
		
		
	}

	
	public String findPwd(Connection con, String userId, String userName, String email) {
		String userPwd = null;
		
		PreparedStatement pstmt = null;
		
		ResultSet rset = null;
		
		String query = "select * from customer where customer_id = ? and customer_name = ? and customer_email = ?";
		String query_bs = "select * from business where business_id = ? and business_name = ? and business_email = ?";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, userId);
			pstmt.setString(2, userName);
			pstmt.setString(3, email);
			
			rset = pstmt.executeQuery();
			
			if(rset.next())
			{ userPwd = rset.getString("customer_pwd");    }
			else {
				pstmt = con.prepareStatement(query_bs);
				pstmt.setString(1, userId);
				pstmt.setString(2, userName);
				pstmt.setString(3, email);
				rset = pstmt.executeQuery();
				if(rset.next()) {
					userPwd = rset.getString("business_pwd");
				}
			}
			
			} catch (Exception e) {
			e.printStackTrace();
		
			}finally {
				close(rset);
				close(pstmt);
			}
		
		return userPwd;
	}

	public int editpwd(Connection con, String userId, String userPwd) {
		
		int result =0;
		
		PreparedStatement pstmt = null;
		
		String query = "update customer set customer_pwd = ? where customer_id = ?";
		String query_bs = "update business set business_pwd = ? where business_id = ?";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, userPwd);
			pstmt.setString(2, userId);
			System.out.println("dao : "+userId+userPwd);
			result = pstmt.executeUpdate();
			
			System.out.println("dao result  : "+result);
			
			if(result==0)
			{
			pstmt = con.prepareStatement(query_bs);
			pstmt.setString(1, userPwd);
			pstmt.setString(2, userId);
			result = pstmt.executeUpdate();
			System.out.println(result);
			}
			
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}
	public int getAllSearchCount(Connection con) {
		//모든 회원정보 조회
		int listCount = 0;
		ResultSet rset = null;
		Statement stmt = null;
		String sql= "select count(*) from CUSTOMER";
		
		try {
			stmt = con.createStatement();
			rset=stmt.executeQuery(sql);
			
			if(rset.next()) {
				listCount = rset.getInt("count(*)");
			}
			
		} catch (Exception e) {
			System.out.println("MemberDao.getAllSearchCount"+  e.getMessage());
			e.printStackTrace();
		}finally {
			close(rset);
			close(stmt);
		}
		
		
		return listCount;
	}

	public ArrayList<Customer> getAllList(Connection con, int currentPage, int limit) {

		ArrayList<Customer> cList = new ArrayList<Customer>();
		
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		String sql = "select * from (select rownum rnum, CUSTOMER_ID, CUSTOMER_NAME, CUSTOMER_EMAIL from CUSTOMER " 
				+ " where withdraw = 'N' order by CUSTOMER_NAME asc )" 
				+ " where rnum>= ? and rnum <= ? ";
		
		try {
			int startRow = (currentPage - 1) * limit + 1;
			int endRow = startRow + limit -1;
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rset = pstmt.executeQuery();
			
			while (rset.next())
			{
				Customer cm = new Customer();
				cm.setCustomer_Id(rset.getString("CUSTOMER_ID"));
				cm.setCustomer_Name(rset.getString("CUSTOMER_NAME"));
				cm.setCustomer_Email(rset.getString("CUSTOMER_EMAIL"));
				
				cList.add(cm);
			}
			
			
		} catch (Exception e) {
			System.out.println("MemberDao.getAllList : " + e.getMessage());
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		
		return cList;
	}

	public int customerWithDraw(Connection con, String customerId) {
		// 관리자 페이지 고객 탈퇴 
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = "update customer set WITHDRAW = 'Y' " + 
				" where CUSTOMER_ID = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, customerId);
			result = pstmt.executeUpdate();
		} catch (Exception e) {

		}finally {
		close(pstmt);	
		}
		
		return result;
	}

	public int adminSearchName(Connection con, String seType, String seValue) {
	      // 관리자 페이지 회원관리 이름으로 검색 카운트
	      int listCount = 0;
	      ResultSet rset = null;
	      Statement stmt = null;
	      String sql = "select count(*) from customer where "+seType+" like '%"+seValue+"%' ";
	      try {
	         stmt = con.createStatement();
	         rset=stmt.executeQuery(sql);
	         
	         if(rset.next()) {
	            listCount = rset.getInt("count(*)");
	         }
	         
	      } catch (Exception e) {
	         e.printStackTrace();
	      }finally {
	         close(stmt);
	         close(rset);
	      }
	      
	      return listCount;
	   }

	public ArrayList<Customer> adminSearchNameList(Connection con, ApprovalSearch as) {
		// 관리자 페이지 회원관리 이름으로 검색 카운트 리스트
		
		ArrayList<Customer> clist = new ArrayList<Customer>();
		ResultSet rset = null;
		System.out.println("어드민서치내임리스트 DAO 들어옴");
		int startRow = (as.getCurrentPage() - 1) * as.getLimit() + 1;
		int endRow = startRow + as.getLimit() - 1;
		String sql = "select * from "
				+ "(select rownum rn,CUSTOMER_ID,CUSTOMER_NAME,CUSTOMER_EMAIL from CUSTOMER "
				+ " where "+as.getSeType()+ " like '%"+ as.getSeValue()+ "%' "
						+ "order by CUSTOMER_NAME ) where rn >=? and rn <=  ?";
		
		System.out.println("쿼리문 끝");
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Customer c = new Customer();
				c.setCustomer_Id(rset.getString("CUSTOMER_ID"));
				c.setCustomer_Name(rset.getString("CUSTOMER_NAME"));
				c.setCustomer_Email(rset.getString("CUSTOMER_EMAIL"));
				
				clist.add(c);
	
			}
		} catch (Exception e) {
				e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}

		return clist;
	}
	
	
	public int checkMail(Connection con,String userId,String email) {
		Business business=null;
		Statement stmt=null;
		ResultSet rset=null;
		
		int result=1;
		//0은 중복메일
		//1은 가능메일
		String sql="select business_email from business where business_email='"+email+"'";
		
		try {
			stmt=con.createStatement();
			rset=stmt.executeQuery(sql);
			
			if(rset.next()) {
				business=new Business();
				business.setBusiness_Email(rset.getString("business_email"));
			}
			
			if(business != null) {
				result=0;
			}else if(business == null){
				result=1;
			}
	System.out.println("result :" + result);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			close(rset);
			close(stmt);
		}
		
		return result;
		
	}

	public int bsmyupdate(Connection con, Business business) {
		int result=0;
		PreparedStatement pstmt=null;
		
		System.out.println("dao : "+business.getBusiness_Id()+business.getBusiness_Pwd()+business.getBusiness_Email());
		
		String sql="update business set business_pwd=?,business_email=? where business_id=?";
		
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, business.getBusiness_Pwd());
			pstmt.setString(2, business.getBusiness_Email());
			pstmt.setString(3, business.getBusiness_Id());
			
			result = pstmt.executeUpdate();
			
			System.out.println("dao : "+result);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int bsPwdCheck(Connection con, String userId, String userPwd) {

		String sql = "select * from business where business_id=? and business_pwd=?";
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		int result = 0;

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setString(2, userPwd);
			
			rset = pstmt.executeQuery();

			if (rset.next()) {
				result = 1;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return result;
	}
	public int csCheckMail(Connection con, String userId, String email) {
		Customer customer =null;
		
		Statement stmt=null;
		ResultSet rset=null;
		
		int result=1;
		//0은 중복메일
		//1은 가능메일
		String sql="select CUSTOMER_EMAIL from customer where CUSTOMER_EMAIL='"+email+"'";
		
		try {
			stmt=con.createStatement();
			rset=stmt.executeQuery(sql);
			
			if(rset.next()) {
				customer=new Customer();
				customer.setCustomer_Email(rset.getString("CUSTOMER_EMAIL"));
			}
			
			if(customer != null) {
				result=0;
			}else if(customer == null){
				result=1;
			}
	System.out.println("result :" + result);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			close(rset);
			close(stmt);
		}
		
		return result;
	}
	public int bsCheckMail(Connection con,String userId,String email) {
		Business business=null;
		
		Statement stmt=null;
		ResultSet rset=null;
		
		int result=1;
		//0은 중복메일
		//1은 가능메일
		String sql="select business_email from business where business_email='"+email+"'";
		
		try {
			stmt=con.createStatement();
			rset=stmt.executeQuery(sql);
			
			if(rset.next()) {
				business=new Business();
				business.setBusiness_Email(rset.getString("business_email"));
			}
			
			if(business != null) {
				result=0;
			}else if(business == null){
				result=1;
			}
	System.out.println("result :" + result);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			close(rset);
			close(stmt);
		}
		
		return result;
		
	}public int withDraw(Connection con, String cuserid, String buserid) {
	
		int result =0;
		PreparedStatement pstmt = null;
		
		String csquery ="update customer set withdraw ='Y' where customer_id=?";
		String bsquery ="update business set withdraw ='Y' where business_id=?";
		
		try {
		if(cuserid != null) {
			pstmt = con.prepareStatement(csquery);
			pstmt.setString(1, cuserid);
			result = pstmt.executeUpdate();
		}else if(buserid != null) {
			pstmt = con.prepareStatement(bsquery);
			pstmt.setString(1, buserid);
			result = pstmt.executeUpdate();
		}
		
		
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public int getBus_SearchCount(Connection con, String seType, String seValue) {
		int listCount=0;
		ResultSet rset =  null;
		PreparedStatement pstmt= null;
		String sql = " select count(*) from business where "+seType+" = ?" ;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, seValue);
		
			rset= pstmt.executeQuery();
			if(rset.next()) {
				listCount=rset.getInt("count(*)");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
			close(rset);
		}
		return listCount;
	}

	public ArrayList<Business> searchBusList(Connection con, ApprovalSearch as) {
		ArrayList<Business> blist = new ArrayList<Business>();
		ResultSet rset=null;
		
		int startRow =(as.getCurrentPage()-1)* as.getLimit()+1;
		int endRow =startRow + as.getLimit()-1;
		String query = "select * from (select rownum rn,BUSINESS_ID,BUSINESS_PWD,BUSINESS_NAME,BUSINESS_EMAIL,BUSINESS_PHONE,WITHDRAW,APPROVAL from business where withdraw =  'N' and approval = 'Y' and "+as.getSeType()+ " like '%"+as.getSeValue()+"%' order by business_id ) where rn >=? and rn <=  ?";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Business b = new Business();
				
				b.setBusiness_Id(rset.getString("BUSINESS_ID"));
				b.setBusiness_Email(rset.getString("BUSINESS_EMAIL"));
				b.setBusiness_Name(rset.getString("BUSINESS_NAME"));
				
				blist.add(b);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return blist;
	}

	public int CusMailDuplicate(Connection con, String mail) {
		int result=0;
		String sql="select * from CUSTOMER where CUSTOMER_EMAIL = ?";
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mail);
			rset=pstmt.executeQuery();
			if(rset.next())
			{
				result = 1;
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}

		return result;
	}

	public int BusMailDuplicate(Connection con, String mail) {
		int result=0;
		String sql="select * from BUSINESS where BUSINESS_EMAIL = ?";
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mail);
			rset=pstmt.executeQuery();
			if(rset.next())
			{
				result = 1;
				
			}
			System.out.println("result : "+result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}

		return result;
	}
}
