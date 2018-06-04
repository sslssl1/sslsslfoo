package review.model.vo;

import java.sql.Date;

public class Review implements java.io.Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 123745L;
	private int number;//글번호
	private String business_Id;
	private String review_Writer;
	private String review_Content;
	private String review_title;//제목
	private int review_Grade;   //review 평점은 1~10의 정수로 표현,  truck평점은 평균이기때문에 double형
	private Date review_Date;//작성일
	private int review_Count;

	public Review() {
		// TODO Auto-generated constructor stub
	}

	public Review(int number, String business_Id, String review_Writer, String review_Content, String review_title,
			int review_Grade, Date review_Date, int review_Count) {
		super();
		this.number = number;
		this.business_Id = business_Id;
		this.review_Writer = review_Writer;
		this.review_Content = review_Content;
		this.review_title = review_title;
		this.review_Grade = review_Grade;
		this.review_Date = review_Date;
		this.review_Count = review_Count;
	}

	@Override
	public String toString() {
		return "Review [number=" + number + ", business_Id=" + business_Id + ", review_Writer=" + review_Writer
				+ ", review_Content=" + review_Content + ", review_title=" + review_title + ", review_Grade="
				+ review_Grade + ", review_Date=" + review_Date + ", review_Count=" + review_Count + "]";
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getBusiness_Id() {
		return business_Id;
	}

	public void setBusiness_Id(String business_Id) {
		this.business_Id = business_Id;
	}

	public String getReview_Writer() {
		return review_Writer;
	}

	public void setReview_Writer(String review_Writer) {
		this.review_Writer = review_Writer;
	}

	public String getReview_Content() {
		return review_Content;
	}

	public void setReview_Content(String review_Content) {
		this.review_Content = review_Content;
	}

	public String getReview_title() {
		return review_title;
	}

	public void setReview_title(String review_title) {
		this.review_title = review_title;
	}

	public int getReview_Grade() {
		return review_Grade;
	}

	public void setReview_Grade(int review_Grade) {
		this.review_Grade = review_Grade;
	}

	public Date getReview_Date() {
		return review_Date;
	}

	public void setReview_Date(Date review_Date) {
		this.review_Date = review_Date;
	}

	public int getReview_Count() {
		return review_Count;
	}

	public void setReview_Count(int review_Count) {
		this.review_Count = review_Count;
	}
	
	

}
