package member.model.service;

import member.model.vo.*;
import member.model.dao.*;
import static common.Jdbctemplate.*;

import java.sql.Connection;
import java.util.ArrayList;

public class MemberService {
	public MemberService() {
		
	}
	public Member memberLogin(String userId, String password)
	{
		Connection con = getConnection();
		System.out.println("memberLogindd : "+userId +"\n"+password);
		Member member = new MemberDao().memberLogin(con, userId, password);
		 
		close(con);
		
		return member;
	}
	public int bsCheckMail(String userId,String email) {
		Connection con = getConnection();
		int result = new MemberDao().bsCheckMail(con,userId, email);
		close(con);
		return result;
	}public int csCheckMail(String userId, String email) {
		Connection con = getConnection();
		int result = new MemberDao().csCheckMail(con,userId, email);
		close(con);
		return result;
	}
	public int customerEnroll (Member member)
	{
		Connection con = getConnection();

		MemberDao mdao = new MemberDao();
		int result = mdao.customerEnroll(con,member);
		
		if(result>0)
		{
			commit(con);
		}else
		{
			rollback(con);
		}
		
//		if(result>0) {
//			Connection con2 = getConnection();
//			int result2 =mdao.memberEnroll(con2, ((Customer)member).getCustomer_Id());
//			if(result2>0)
//			{
//				commit(con);
//				commit(con2);
//			}
//			else
//			{
//				rollback(con);
//				rollback(con2);
//			}
//			close(con2);
//		}
//		else
//		{
//			rollback(con);
//			
//		}
		
		close(con);
		
		return result;
	}
	public int BusinessEnroll(Member member) {
		
		Connection con = getConnection();

		MemberDao mdao = new MemberDao();
		int result = mdao.BusinessEnroll(con,member);
		
		if(result>0)
		{
			commit(con);
		}else
		{
			rollback(con);
		}
		
		/*Member Table 사용할 때 메서드*/
//		if(result>0) {
//			Connection con2 = getConnection();
//			int result2 =mdao.memberEnroll(con2, ((Business)member).getBusiness_Id());
//			if(result2>0)
//			{
//				commit(con);
//				commit(con2);
//			}
//			else
//			{
//				rollback(con);
//				rollback(con2);
//			}
//			close(con2);
//		}
//		else
//		{
//			rollback(con);
//			
//		}
		
		close(con);
		
		return result;
	}
	
	public String searchCSId(String userName,String email)
	{
		Connection con = getConnection();
		
		String userId = new MemberDao().searchCSId(con,userName,email);
	
		close(con);
	
		return userId;
	}
	
	public String searchBSId(String userName,String email)
	{
		Connection con = getConnection();
		
		String userId = new MemberDao().searchBSId(con,userName,email);
	
		close(con);
	
		return userId;
	}
	

	public String searchPwd(String userId, String userName, String email)
	{
		Connection con=getConnection();
		
		String userPwd = new MemberDao().searchCSPwd(con,userId,userName,email);
		
		if(userPwd==null)
		{
			con=getConnection();
			userPwd = new MemberDao().searchBSPwd(con,userId,userName,email);
		}
		close(con);
		return userPwd;
		
		
	}
	public int duplicateCusId(String userId) {
		Connection con = getConnection();
		
		int result = new MemberDao().duplicateCusId(con,userId);
		
		close (con);
		return result;
	}
	public int duplicateBsId(String userId) {
	Connection con = getConnection();
		
		int result = new MemberDao().duplicateBsId(con,userId);
		
		close (con);
		return result;
	}
	public int duplicateMgId(String userId) {
	
	Connection con = getConnection();
		
		int result = new MemberDao().duplicateMgId(con,userId);
		
		close (con);
		return result;
	}
	public int updateMember(Customer customer) {
		Connection con = getConnection();
		
		int result = new MemberDao().updateMember(con,customer);
		if(result> 0)
		{
			commit(con);
		}else {
			rollback(con);
		}
		close(con);
		return result;
	}
	public int withDraw(String userid) {
		Connection con = getConnection();
		int result = new MemberDao().withDraw(con,userid);
		if(result==1) {
			commit(con);
		}else if(result ==0) {
			rollback(con);
		}
		close(con);
		return result;
	}
	public int csPwdCheck(String userPwd, String userId) {
	Connection con = getConnection();
		int result = new MemberDao().csPwdCheck(con,userId,userPwd);
		
	close(con);
		
		return result;
	}
	public int changeApproval(String bsId) {
		Connection con = getConnection();
		int result= new MemberDao().changeAppoval(con,bsId);
		
		if(result>0) {
			commit(con);
		}else {
			rollback(con);
		}
		
		close(con);
		return result;
	}public int rejectApproval(String rejectReason,String bsId) {
		Connection con= getConnection();
		int result = new MemberDao().rejectApproval(con,rejectReason,bsId);
		
		if(result>0) {
			commit(con);
		}else {
			rollback(con);
		}
		close(con);
		return result;
	}
	public int deleteBusiness(String bsId) {
		Connection con = getConnection();
		
		int result = new MemberDao().deleteBusiness(con,bsId);
		
		if(result>0) {
			commit(con);
		}else {
			rollback(con);
		}
		
		close(con);
		return result;
	}	public int getListApprovalCount() {
		Connection con = getConnection();
		int listCount = new MemberDao().getListApprovalCount(con);
		close(con);
		return listCount;
	}public ArrayList<Business> selectApproval(int currentPage, int limit) {
		Connection con = getConnection();
		ArrayList<Business>list = new MemberDao().selectApproval(con,currentPage,limit);
	
		close(con);
		return list;
	}public int getListRejectCount() {
		Connection con = getConnection();
		int listCount = new MemberDao().getListRejectCount(con);
		close(con);
		return listCount;
	}public ArrayList<Business> selectReject(int currentPage, int limit) {
		Connection con = getConnection();
		ArrayList<Business>list = new MemberDao().selectReject(con,currentPage,limit);
	
		close(con);
		return list;
		
	}public int getBusApproval_SearchCount (String seType, String seValue) {
		//사업자 미승인 회원 카운트
		Connection con = getConnection();
		int listCount = new MemberDao().getBusApproval_SearchCount (con,seType,seValue);
		
		
		close(con);
		return listCount;
	}public ArrayList<Business> searchBusApprovalList(ApprovalSearch approvalSearch) {
		 Connection con = getConnection();
		 ArrayList<Business> blist = new MemberDao().searchBusApprovalList(con,approvalSearch);
			
			close(con);
			
			return blist;
		
	}
	public int compulsionWithDraw(String bsId) {
		Connection con = getConnection();
		int result = new MemberDao().compulsionWithDraw(con,bsId);
		
		if(result>0) {
			commit(con);
		}else {
			rollback(con);
		}
		close(con);
		return result;
	}
	public int getListBusinessMemberCount() {
		Connection con = getConnection();
		int listCount = new MemberDao().getListBusinessMemberCount(con);
		close(con);
		return listCount;
	}
	public ArrayList<Business> selectBusinessMemberList(int currentPage, int limit) {
		Connection con = getConnection();
		ArrayList<Business> list = new MemberDao().selectBusinessMemberList(con,currentPage,limit);
	
		close(con);
		return list;
	}
public String findId(String userName, String email) {
		
		Connection con = getConnection();
		
		String userId = new MemberDao().findId(con,userName,email);
	
		if(userId==null)
		{
			con=getConnection();
			userId = new MemberDao().findId(con,userName,email);
		}
		
		close(con);
	
		return userId;
	}
	public String findPwd(String userId, String userName, String email) {
		
		Connection con = getConnection();
		
		String userPwd = new MemberDao().findPwd(con,userId,userName,email);
		
		if(userPwd==null)
		{
			con=getConnection();
			
			userPwd = new MemberDao().findPwd(con,userId,userName,email);
		}
		close(con);
		
		return userPwd;
		
	}
	public int editpwd(String userId, String userPwd) {
		Connection con = getConnection();
		System.out.println("service : "+userId+userPwd);
		int result = new MemberDao().editpwd(con,userId,userPwd);
		System.out.println("service result  : "+result);
		if(result > 0) {
			commit(con);
		}else {
			rollback(con);
		}
		close(con);
		return result;
	}
	public int getAllSearchCount() {
		Connection con = getConnection();
		int listCount = new MemberDao().getAllSearchCount(con);
		close(con);
		
		return listCount;
	}
	public ArrayList<Customer> getAllList(int currentPage, int limit) {
		Connection con = getConnection();
		ArrayList<Customer> list = new MemberDao().getAllList(con,currentPage,limit);
		close(con);
		return list;
	}
	public int customerWithdraw(String customerId) {
		// 관리자페이지 고객 탈퇴 처리 서비스
		Connection con = getConnection();
		int result = new MemberDao().customerWithDraw(con ,customerId);
		if(result>0) {
			commit(con);
		}else {
			rollback(con);
		}
		close(con);
		
		return result;
	}
	public int adminSearchName(String seType, String seValue) {
		// 관리자 페이지 회원관리 내 이름으로 찾기
		Connection con= getConnection();
		int listCount = new MemberDao().adminSearchName(con,seType,seValue);
		close(con);
		return listCount;
	}
	public ArrayList<Customer> adminSearchNameList(ApprovalSearch approvalSearch) {
		// 관리자 페이지 회뤈관리 내 에서 이름으로 찾기리스트 생성
		Connection con = getConnection();
		ArrayList<Customer> clist = new MemberDao().adminSearchNameList(con,approvalSearch);
		close(con);
		return clist;
	}
	public int checkMail(String userId,String email) {
		Connection con = getConnection();
		int result = new MemberDao().checkMail(con,userId, email);
		close(con);
		return result;
	}
	public int bsmyupdate(Business business) {
		Connection con = getConnection();
		int result=new MemberDao().bsmyupdate(con,business);

		if(result>0) {
			commit(con);
		}else {
			rollback(con);
		}
		close(con);
		
		System.out.println("sercice : "+business.getBusiness_Id()+business.getBusiness_Pwd()+business.getBusiness_Email());
		
		return result;
	}
	  public int bsPwdCheck( String userId,String userPwd) {
	      Connection con = getConnection();
	      int result= new MemberDao().bsPwdCheck(con, userId, userPwd);
	      close(con);
	      
	      return result;
	   }
	  public int withDraw(String cuserid, String buserid) {
			Connection con = getConnection();
			int result = new MemberDao().withDraw(con,cuserid,buserid);
			if(result==1) {
				commit(con);
			}else if(result ==0) {
				rollback(con);
			}
			close(con);
			return result;
		}
	public int getBus_SearchCount(String seType, String seValue) {
		//사업자 일반 회원  카운트
		Connection con = getConnection();
		int listCount = new MemberDao().getBus_SearchCount(con,seType,seValue);
		
		
		close(con);
		return listCount;
	}
	public ArrayList<Business> searchBusList(ApprovalSearch approvalSearch) {
		 Connection con = getConnection();
		 ArrayList<Business> blist = new MemberDao().searchBusList(con,approvalSearch);
			
			close(con);
			
			return blist;
	}
	
	public int CusMailDuplicate(String mail)
	{
		Connection con = getConnection();
		int result = new MemberDao().CusMailDuplicate(con,mail);
		close(con);
		return result;
		
	}
	public int BusMailDuplicate(String mail)
	{
		Connection con = getConnection();
		int result = new MemberDao().BusMailDuplicate(con,mail);
		close(con);
		return result;
		
	}
	
}


