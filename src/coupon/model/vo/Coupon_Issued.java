package coupon.model.vo;

import java.sql.Date;

public class Coupon_Issued {

	private int coupon_No;
	private String coupon_Id;
	private Date coupon_Issued_Date;
	private String coupon_Used;

	public Coupon_Issued() {
		// TODO Auto-generated constructor stub
	}

	public Coupon_Issued(int coupon_No, String coupon_Id, Date coupon_Issued_Date, String coupon_Used) {

		this.coupon_No = coupon_No;
		this.coupon_Id = coupon_Id;
		this.coupon_Issued_Date = coupon_Issued_Date;
		this.coupon_Used = coupon_Used;
	}

	public int getCoupon_No() {
		return coupon_No;
	}

	public void setCoupon_No(int coupon_No) {
		this.coupon_No = coupon_No;
	}

	public String getCoupon_Id() {
		return coupon_Id;
	}

	public void setCoupon_Id(String coupon_Id) {
		this.coupon_Id = coupon_Id;
	}

	public Date getCoupon_Issued_Date() {
		return coupon_Issued_Date;
	}

	public void setCoupon_Issued_Date(Date coupon_Issued_Date) {
		this.coupon_Issued_Date = coupon_Issued_Date;
	}

	public String getCoupon_Used() {
		return coupon_Used;
	}

	public void setCoupon_Used(String coupon_Used) {
		this.coupon_Used = coupon_Used;
	}
	
	@Override
	public String toString() {
		return coupon_No+","+coupon_Id+","+coupon_Issued_Date+","+coupon_Used;
	}

}
