package festival.model.vo;
public class Festival_Image {

	private int festival_No;
	private String festival_Img;
	
	public Festival_Image() {
		}

	public Festival_Image(int festival_No, String festival_Img) {
		super();
		this.festival_No = festival_No;
		this.festival_Img = festival_Img;
	}

	public int getFestival_No() {
		return festival_No;
	}

	public void setFestival_No(int festival_No) {
		this.festival_No = festival_No;
	}

	public String getFestival_Img() {
		return festival_Img;
	}

	public void setFestival_Img(String festival_Img) {
		this.festival_Img = festival_Img;
	}
	
	@Override
	public String toString() {
			return festival_No+","+festival_Img;
	}
}