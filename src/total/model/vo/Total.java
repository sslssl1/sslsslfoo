package total.model.vo;

import java.util.Date;

public class Total {

	private Date t_date;
	private	int t_total;
	
	public Total() {
		// TODO Auto-generated constructor stub
	}
	
	public Total(Date t_date, int t_total) {
		super();
		this.t_date = t_date;
		this.t_total = t_total;
	}




	public Date getT_date() {
		return t_date;
	}



	public void setT_date(Date t_date) {
		this.t_date = t_date;
	}



	public int getT_total() {
		return t_total;
	}



	public void setT_total(int t_total) {
		this.t_total = t_total;
	}



	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return t_date+","+t_total;
	}
}
