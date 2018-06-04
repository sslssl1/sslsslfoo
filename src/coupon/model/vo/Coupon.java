package coupon.model.vo;

import java.sql.Date;

public class Coupon {
	
	private int coupon_No;
	private String business_Id;
	private Date coupon_Date;
	private String coupon_Content;
	
	
	public Coupon() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	public Coupon(int coupon_No, String business_Id, Date coupon_Date, String coupon_Content) {
		super();
		this.coupon_No = coupon_No;
		this.business_Id = business_Id;
		this.coupon_Date = coupon_Date;
		this.coupon_Content = coupon_Content;
	}


	


	public int getCoupon_No() {
		return coupon_No;
	}




	public void setCoupon_No(int coupon_No) {
		this.coupon_No = coupon_No;
	}




	public String getBusiness_Id() {
		return business_Id;
	}




	public void setBusiness_Id(String business_Id) {
		this.business_Id = business_Id;
	}




	public Date getCoupon_Date() {
		return coupon_Date;
	}




	public void setCoupon_Date(Date coupon_Date) {
		this.coupon_Date = coupon_Date;
	}




	public String getCoupon_Content() {
		return coupon_Content;
	}




	public void setCoupon_Content(String coupon_Content) {
		this.coupon_Content = coupon_Content;
	}




	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.coupon_No+","+this.business_Id+","+this.coupon_Date+","+this.coupon_Content;
	}

}
