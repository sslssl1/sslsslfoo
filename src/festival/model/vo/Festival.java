package festival.model.vo;

import java.sql.Date;

public class Festival implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 943864235L;
	private int festival_No;
	private String festival_Name;
	private String festival_Addr;
	private Date festival_Date_Start;
	private Date festival_Date_End;
	private String festival_Host;
	private String festival_Phone;
	private String festival_Image_File;
	private String festival_Image_Main;
	
	public Festival() {	}

	public Festival(int festival_No, String festival_Name, String festival_Addr, Date festival_Date_Start,
			Date festival_Date_End, String festival_Host, String festival_Phone, String festival_Image_File,
			String festival_Image_Main) {
		super();
		this.festival_No = festival_No;
		this.festival_Name = festival_Name;
		this.festival_Addr = festival_Addr;
		this.festival_Date_Start = festival_Date_Start;
		this.festival_Date_End = festival_Date_End;
		this.festival_Host = festival_Host;
		this.festival_Phone = festival_Phone;
		this.festival_Image_File = festival_Image_File;
		this.festival_Image_Main = festival_Image_Main;
	}

	@Override
	public String toString() {
		return "Festival [festival_No=" + festival_No + ", festival_Name=" + festival_Name + ", festival_Addr="
				+ festival_Addr + ", festival_Date_Start=" + festival_Date_Start + ", festival_Date_End="
				+ festival_Date_End + ", festival_Host=" + festival_Host + ", festival_Phone=" + festival_Phone
				+ ", festival_Image_File=" + festival_Image_File + ", festival_Image_Main=" + festival_Image_Main + "]";
	}

	public int getFestival_No() {
		return festival_No;
	}

	public void setFestival_No(int festival_No) {
		this.festival_No = festival_No;
	}

	public String getFestival_Name() {
		return festival_Name;
	}

	public void setFestival_Name(String festival_Name) {
		this.festival_Name = festival_Name;
	}

	public String getFestival_Addr() {
		return festival_Addr;
	}

	public void setFestival_Addr(String festival_Addr) {
		this.festival_Addr = festival_Addr;
	}

	public Date getFestival_Date_Start() {
		return festival_Date_Start;
	}

	public void setFestival_Date_Start(Date festival_Date_Start) {
		this.festival_Date_Start = festival_Date_Start;
	}

	public Date getFestival_Date_End() {
		return festival_Date_End;
	}

	public void setFestival_Date_End(Date festival_Date_End) {
		this.festival_Date_End = festival_Date_End;
	}

	public String getFestival_Host() {
		return festival_Host;
	}

	public void setFestival_Host(String festival_Host) {
		this.festival_Host = festival_Host;
	}

	public String getFestival_Phone() {
		return festival_Phone;
	}

	public void setFestival_Phone(String festival_Phone) {
		this.festival_Phone = festival_Phone;
	}

	public String getFestival_Image_File() {
		return festival_Image_File;
	}

	public void setFestival_Image_File(String festival_Image_File) {
		this.festival_Image_File = festival_Image_File;
	}

	public String getFestival_Image_Main() {
		return festival_Image_Main;
	}

	public void setFestival_Image_Main(String festival_Image_Main) {
		this.festival_Image_Main = festival_Image_Main;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	
	
}
