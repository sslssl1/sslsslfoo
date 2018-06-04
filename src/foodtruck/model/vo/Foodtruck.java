package foodtruck.model.vo;

public class Foodtruck implements java.io.Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 19342L;
	

	
	private String business_Id;
	private String truck_Name;
	private String truck_Img;
	private String truck_MainMenu;
	private String truck_Category;
	private String truck_Phone;
	private double truck_Loc_X;
	private double truck_Loc_Y;
	private String truck_Notice;
	private String truck_Time;
	private String truck_Dayoff;
	private String truck_Live_OnOff;


	public Foodtruck() {}


	public Foodtruck(String business_Id, String truck_Name, String truck_Img, String truck_MainMenu,
			String truck_Category, String truck_Phone, double truck_Loc_X, double truck_Loc_Y, String truck_Notice,
			String truck_Time, String truck_Dayoff, String truck_Live_OnOff) {
		super();
		this.business_Id = business_Id;
		this.truck_Name = truck_Name;
		this.truck_Img = truck_Img;
		this.truck_MainMenu = truck_MainMenu;
		this.truck_Category = truck_Category;
		this.truck_Phone = truck_Phone;
		this.truck_Loc_X = truck_Loc_X;
		this.truck_Loc_Y = truck_Loc_Y;
		this.truck_Notice = truck_Notice;
		this.truck_Time = truck_Time;
		this.truck_Dayoff = truck_Dayoff;
		this.truck_Live_OnOff = truck_Live_OnOff;
	}


	


	@Override
	public String toString() {
		return "Foodtruck [business_Id=" + business_Id + ", truck_Name=" + truck_Name + ", truck_Img=" + truck_Img
				+ ", truck_MainMenu=" + truck_MainMenu + ", truck_Category=" + truck_Category + ", truck_Phone="
				+ truck_Phone + ", truck_Loc_X=" + truck_Loc_X + ", truck_Loc_Y=" + truck_Loc_Y + ", truck_Notice="
				+ truck_Notice + ", truck_Time=" + truck_Time + ", truck_Dayoff=" + truck_Dayoff + ", truck_Live_OnOff="
				+ truck_Live_OnOff + "]";
	}


	public String getBusiness_Id() {
		return business_Id;
	}


	public void setBusiness_Id(String business_Id) {
		this.business_Id = business_Id;
	}


	public String getTruck_Name() {
		return truck_Name;
	}


	public void setTruck_Name(String truck_Name) {
		this.truck_Name = truck_Name;
	}


	public String getTruck_Img() {
		return truck_Img;
	}


	public void setTruck_Img(String truck_Img) {
		this.truck_Img = truck_Img;
	}


	public String getTruck_MainMenu() {
		return truck_MainMenu;
	}


	public void setTruck_MainMenu(String truck_MainMenu) {
		this.truck_MainMenu = truck_MainMenu;
	}


	public String getTruck_Category() {
		return truck_Category;
	}


	public void setTruck_Category(String truck_Category) {
		this.truck_Category = truck_Category;
	}


	public String getTruck_Phone() {
		return truck_Phone;
	}


	public void setTruck_Phone(String truck_Phone) {
		this.truck_Phone = truck_Phone;
	}


	public double getTruck_Loc_X() {
		return truck_Loc_X;
	}


	public void setTruck_Loc_X(double truck_Loc_X) {
		this.truck_Loc_X = truck_Loc_X;
	}


	public double getTruck_Loc_Y() {
		return truck_Loc_Y;
	}


	public void setTruck_Loc_Y(double truck_Loc_Y) {
		this.truck_Loc_Y = truck_Loc_Y;
	}


	public String getTruck_Notice() {
		return truck_Notice;
	}


	public void setTruck_Notice(String truck_Notice) {
		this.truck_Notice = truck_Notice;
	}


	public String getTruck_Time() {
		return truck_Time;
	}


	public void setTruck_Time(String truck_Time) {
		this.truck_Time = truck_Time;
	}


	public String getTruck_Dayoff() {
		return truck_Dayoff;
	}


	public void setTruck_Dayoff(String truck_Dayoff) {
		this.truck_Dayoff = truck_Dayoff;
	}


	public String getTruck_Live_OnOff() {
		return truck_Live_OnOff;
	}


	public void setTruck_Live_OnOff(String truck_Live_OnOff) {
		this.truck_Live_OnOff = truck_Live_OnOff;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	

}
