
package reply.model.vo;

import java.sql.Date;

public class Reply implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1531235L;
	private int reply_No;
	private int review_No;
	private String reply_Cs_Writer;
	private String reply_Bs_Writer;
	private String reply_Content;
	private Date reply_Date;

	public Reply() {
		
	}

	public Reply(int reply_No, int review_No, String reply_Cs_Writer, String reply_Bs_Writer, String reply_Content,
			Date reply_Date) {
		super();
		this.reply_No = reply_No;
		this.review_No = review_No;
		this.reply_Cs_Writer = reply_Cs_Writer;
		this.reply_Bs_Writer = reply_Bs_Writer;
		this.reply_Content = reply_Content;
		this.reply_Date = reply_Date;
	}

	@Override
	public String toString() {
		return "Reply [reply_No=" + reply_No + ", review_No=" + review_No + ", reply_Cs_Writer=" + reply_Cs_Writer
				+ ", reply_Bs_Writer=" + reply_Bs_Writer + ", reply_Content=" + reply_Content + ", reply_Date="
				+ reply_Date + "]";
	}

	public int getReply_No() {
		return reply_No;
	}

	public void setReply_No(int reply_No) {
		this.reply_No = reply_No;
	}

	public int getReview_No() {
		return review_No;
	}

	public void setReview_No(int review_No) {
		this.review_No = review_No;
	}

	public String getReply_Cs_Writer() {
		return reply_Cs_Writer;
	}

	public void setReply_Cs_Writer(String reply_Cs_Writer) {
		this.reply_Cs_Writer = reply_Cs_Writer;
	}

	public String getReply_Bs_Writer() {
		return reply_Bs_Writer;
	}

	public void setReply_Bs_Writer(String reply_Bs_Writer) {
		this.reply_Bs_Writer = reply_Bs_Writer;
	}

	public String getReply_Content() {
		return reply_Content;
	}

	public void setReply_Content(String reply_Content) {
		this.reply_Content = reply_Content;
	}

	public Date getReply_Date() {
		return reply_Date;
	}

	public void setReply_Date(Date reply_Date) {
		this.reply_Date = reply_Date;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
