package member.model.vo;

public class Customer extends Member implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7032054612749936554L;
	private String customer_Id;
	private String customer_Pwd;
	private String customer_Name;
	private String customer_Email;
	private String withdraw;
	
	public Customer() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	public Customer(String customer_Id, String customer_Pwd, String customer_Name, String customer_Email,
			String withdraw) {
		this.customer_Id = customer_Id;
		this.customer_Pwd = customer_Pwd;
		this.customer_Name = customer_Name;
		this.customer_Email = customer_Email;
		this.withdraw = withdraw;
	}

	



	public String getCustomer_Id() {
		return customer_Id;
	}




	public void setCustomer_Id(String customer_Id) {
		this.customer_Id = customer_Id;
	}




	public String getCustomer_Pwd() {
		return customer_Pwd;
	}




	public void setCustomer_Pwd(String customer_Pwd) {
		this.customer_Pwd = customer_Pwd;
	}




	public String getCustomer_Name() {
		return customer_Name;
	}




	public void setCustomer_Name(String customer_Name) {
		this.customer_Name = customer_Name;
	}




	public String getCustomer_Email() {
		return customer_Email;
	}




	public void setCustomer_Email(String customer_Email) {
		this.customer_Email = customer_Email;
	}




	public String getWithdraw() {
		return withdraw;
	}




	public void setWithdraw(String withdraw) {
		this.withdraw = withdraw;
	}




	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.customer_Id+","+this.customer_Pwd+","+this.customer_Name+","+this. customer_Email
				+","+this.withdraw;
	}

}
