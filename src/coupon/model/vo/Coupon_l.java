package coupon.model.vo;

import java.sql.Date;

public class Coupon_l implements java.io.Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 237864L;
	private String truck_name;
	private String coupon_content;
	private Date coupon_date;
	
	
	public Coupon_l() {
		// TODO Auto-generated constructor stub
	}


	public Coupon_l(String truck_name, String coupon_content, Date coupon_date) {
		super();
		this.truck_name = truck_name;
		this.coupon_content = coupon_content;
		this.coupon_date = coupon_date;
	}


	@Override
	public String toString() {
		return "Coupon_l [truck_name=" + truck_name + ", coupon_content=" + coupon_content + ", coupon_date="
				+ coupon_date + "]";
	}


	public String getTruck_name() {
		return truck_name;
	}


	public void setTruck_name(String truck_name) {
		this.truck_name = truck_name;
	}


	public String getCoupon_content() {
		return coupon_content;
	}


	public void setCoupon_content(String coupon_content) {
		this.coupon_content = coupon_content;
	}


	public Date getCoupon_date() {
		return coupon_date;
	}


	public void setCoupon_date(Date coupon_date) {
		this.coupon_date = coupon_date;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
