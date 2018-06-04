package coupon.model.vo;

import java.sql.Date;

public class Coupon_bl implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 65132L;

	private String customer_id;
	private String coupon_content; 
	private Date coupon_date;
	private int coupon_No;
	
	public int getCoupon_No() {
		return coupon_No;
	}

	public void setCoupon_No(int coupon_No) {
		this.coupon_No = coupon_No;
	}

	public Coupon_bl() {
		// TODO Auto-generated constructor stub
	}
	
	public Coupon_bl(String customer_id, String coupon_content, Date coupon_date) {
		super();
		this.customer_id = customer_id;
		this.coupon_content = coupon_content;
		this.coupon_date = coupon_date;
	}

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
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
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Coupon_bl [customer_id=" + customer_id + ", coupon_content=" + coupon_content + ", coupon_date="
				+ coupon_date + "]";
	}
	
	
}
