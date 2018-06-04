package festival.model.vo;

public class Festival_Truck {

	private int festival_No;
	private String business_Id;
	
	
	public Festival_Truck() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public Festival_Truck(int festival_No, String business_Id) {
		super();
		this.festival_No = festival_No;
		this.business_Id = business_Id;
	}

	


	public int getFestival_No() {
		return festival_No;
	}



	public void setFestival_No(int festival_No) {
		this.festival_No = festival_No;
	}



	public String getBusiness_Id() {
		return business_Id;
	}



	public void setBusiness_Id(String business_Id) {
		this.business_Id = business_Id;
	}



	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.festival_No+","+this.business_Id;
	}
	
}
