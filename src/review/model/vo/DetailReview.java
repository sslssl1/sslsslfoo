package review.model.vo;

import java.sql.Date;

public class DetailReview implements java.io.Serializable{

/**
	 * 
	 */
	private static final long serialVersionUID = 983981L;
private int	REVIEW_NO;
private String	REVIEW_WRITER;
private String	REVIEW_TITLE;
private String	REVIEW_CONTENT;
private int	REVIEW_GRADE;
private int	REVIEW_COUNT;
private Date REVIEW_DATE;
private String TRUCK_NAME;
private String BUSINESS_ID;

public DetailReview() {}

public DetailReview(int rEVIEW_NO, String rEVIEW_WRITER, String rEVIEW_TITLE, String rEVIEW_CONTENT, int rEVIEW_GRADE,
		int rEVIEW_COUNT, Date rEVIEW_DATE, String tRUCK_NAME,String business_ID) {
	super();
	REVIEW_NO = rEVIEW_NO;
	REVIEW_WRITER = rEVIEW_WRITER;
	REVIEW_TITLE = rEVIEW_TITLE;
	REVIEW_CONTENT = rEVIEW_CONTENT;
	REVIEW_GRADE = rEVIEW_GRADE;
	REVIEW_COUNT = rEVIEW_COUNT;
	REVIEW_DATE = rEVIEW_DATE;
	TRUCK_NAME = tRUCK_NAME;
	BUSINESS_ID= business_ID;
}
@Override
public String toString() {
	return "DetailReview [REVIEW_NO=" + REVIEW_NO + ", REVIEW_WRITER=" + REVIEW_WRITER + ", REVIEW_TITLE="
			+ REVIEW_TITLE + ", REVIEW_CONTENT=" + REVIEW_CONTENT + ", REVIEW_GRADE=" + REVIEW_GRADE + ", REVIEW_COUNT="
			+ REVIEW_COUNT + ", REVIEW_DATE=" + REVIEW_DATE + ", TRUCK_NAME=" + TRUCK_NAME +"business_ID : "+ BUSINESS_ID+ "]";
}
public int getREVIEW_NO() {
	return REVIEW_NO;
}
public void setREVIEW_NO(int rEVIEW_NO) {
	REVIEW_NO = rEVIEW_NO;
}
public String getREVIEW_WRITER() {
	return REVIEW_WRITER;
}
public void setREVIEW_WRITER(String rEVIEW_WRITER) {
	REVIEW_WRITER = rEVIEW_WRITER;
}
public String getREVIEW_TITLE() {
	return REVIEW_TITLE;
}
public void setREVIEW_TITLE(String rEVIEW_TITLE) {
	REVIEW_TITLE = rEVIEW_TITLE;
}
public String getREVIEW_CONTENT() {
	return REVIEW_CONTENT;
}
public void setREVIEW_CONTENT(String rEVIEW_CONTENT) {
	REVIEW_CONTENT = rEVIEW_CONTENT;
}
public int getREVIEW_GRADE() {
	return REVIEW_GRADE;
}
public void setREVIEW_GRADE(int rEVIEW_GRADE) {
	REVIEW_GRADE = rEVIEW_GRADE;
}
public int getREVIEW_COUNT() {
	return REVIEW_COUNT;
}
public void setREVIEW_COUNT(int rEVIEW_COUNT) {
	REVIEW_COUNT = rEVIEW_COUNT;
}
public Date getREVIEW_DATE() {
	return REVIEW_DATE;
}
public void setREVIEW_DATE(Date rEVIEW_DATE) {
	REVIEW_DATE = rEVIEW_DATE;
}
public String getTRUCK_NAME() {
	return TRUCK_NAME;
}
public void setTRUCK_NAME(String tRUCK_NAME) {
	TRUCK_NAME = tRUCK_NAME;
}
public static long getSerialversionuid() {
	return serialVersionUID;
}

public String getBUSINESS_ID() {
	return BUSINESS_ID;
}

public void setBUSINESS_ID(String bUSINESS_ID) {
	BUSINESS_ID = bUSINESS_ID;
}


	
	
}
