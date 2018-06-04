package foodtruck.model.vo;

public class FoodTop3 implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 91048187L;
	private String businessId;
	private double grade;
	private String truckName;
	private String truckImg;
	private String truckMain;
	private String truckCategory;
	
	
	public FoodTop3() {}


	public FoodTop3(String businessId, double grade, String truckName, String truckImg, String truckMain,
			String truckCategory) {
		super();
		this.businessId = businessId;
		this.grade = grade;
		this.truckName = truckName;
		this.truckImg = truckImg;
		this.truckMain = truckMain;
		this.truckCategory = truckCategory;
	}


	@Override
	public String toString() {
		return "FoodTop3 [businessId=" + businessId + ", grade=" + grade + ", truckName=" + truckName + ", truckImg="
				+ truckImg + ", truckMain=" + truckMain + ", truckCategory=" + truckCategory + "]";
	}


	public String getBusinessId() {
		return businessId;
	}


	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}


	public double getGrade() {
		return grade;
	}


	public void setGrade(double grade) {
		this.grade = grade;
	}


	public String getTruckName() {
		return truckName;
	}


	public void setTruckName(String truckName) {
		this.truckName = truckName;
	}


	public String getTruckImg() {
		return truckImg;
	}


	public void setTruckImg(String truckImg) {
		this.truckImg = truckImg;
	}


	public String getTruckMain() {
		return truckMain;
	}


	public void setTruckMain(String truckMain) {
		this.truckMain = truckMain;
	}


	public String getTruckCategory() {
		return truckCategory;
	}


	public void setTruckCategory(String truckCategory) {
		this.truckCategory = truckCategory;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
