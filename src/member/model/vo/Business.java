package member.model.vo;

public class Business extends Member implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5292010243300055385L;
	
	private String business_Id;
	private String business_Pwd;
	private String business_Name;
	private String business_Email;
	private String business_Phone;
	private String withdraw;
	private String approval;
	private String BUSINESS_REJECT_REASON;
	
	public Business() {
		// TODO Auto-generated constructor stub
	}

	public Business(String business_Id, String business_Pwd, String business_Name, String business_Email,
			String business_Phone, String withdraw, String approval, String bUSINESS_REJECT_REASON) {
		super();
		this.business_Id = business_Id;
		this.business_Pwd = business_Pwd;
		this.business_Name = business_Name;
		this.business_Email = business_Email;
		this.business_Phone = business_Phone;
		this.withdraw = withdraw;
		this.approval = approval;
		BUSINESS_REJECT_REASON = bUSINESS_REJECT_REASON;
	}

	@Override
	public String toString() {
		return "Business [business_Id=" + business_Id + ", business_Pwd=" + business_Pwd + ", business_Name="
				+ business_Name + ", business_Email=" + business_Email + ", business_Phone=" + business_Phone
				+ ", withdraw=" + withdraw + ", approval=" + approval + ", BUSINESS_REJECT_REASON="
				+ BUSINESS_REJECT_REASON + "]";
	}

	public String getBusiness_Id() {
		return business_Id;
	}

	public void setBusiness_Id(String business_Id) {
		this.business_Id = business_Id;
	}

	public String getBusiness_Pwd() {
		return business_Pwd;
	}

	public void setBusiness_Pwd(String business_Pwd) {
		this.business_Pwd = business_Pwd;
	}

	public String getBusiness_Name() {
		return business_Name;
	}

	public void setBusiness_Name(String business_Name) {
		this.business_Name = business_Name;
	}

	public String getBusiness_Email() {
		return business_Email;
	}

	public void setBusiness_Email(String business_Email) {
		this.business_Email = business_Email;
	}

	public String getBusiness_Phone() {
		return business_Phone;
	}

	public void setBusiness_Phone(String business_Phone) {
		this.business_Phone = business_Phone;
	}

	public String getWithdraw() {
		return withdraw;
	}

	public void setWithdraw(String withdraw) {
		this.withdraw = withdraw;
	}

	public String getApproval() {
		return approval;
	}

	public void setApproval(String approval) {
		this.approval = approval;
	}

	public String getBUSINESS_REJECT_REASON() {
		return BUSINESS_REJECT_REASON;
	}

	public void setBUSINESS_REJECT_REASON(String bUSINESS_REJECT_REASON) {
		BUSINESS_REJECT_REASON = bUSINESS_REJECT_REASON;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

}
