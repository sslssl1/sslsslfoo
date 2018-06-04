package foodtruck.model.vo;

public class SearchTruck implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 523563L;

	private String ctType;
	private String seType;
	private String seValue;

	public SearchTruck() {

	}

	public SearchTruck(String ctType, String seType, String seValue) {
		super();
		this.ctType = ctType;
		this.seType = seType;
		this.seValue = seValue;
	}

	@Override
	public String toString() {
		return "SearchTruck [ctType=" + ctType + ", seType=" + seType + ", seValue=" + seValue + "]";
	}

	public String getCtType() {
		return ctType;
	}

	public void setCtType(String ctType) {
		this.ctType = ctType;
	}

	public String getSeType() {
		return seType;
	}

	public void setSeType(String seType) {
		this.seType = seType;
	}

	public String getSeValue() {
		return seValue;
	}

	public void setSeValue(String seValue) {
		this.seValue = seValue;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
