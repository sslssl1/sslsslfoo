package reply.model.vo;

import java.sql.Date;


public class UnderReply implements java.io.Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 358539L;
	
	private int under_No;
	private int reply_No;
	private String under_CsWriter;
	private String under_BsWriter;
	private String under_Content;
	private Date under_Date;
	
	public UnderReply() {}

	public UnderReply(int under_No, int reply_No, String under_CsWriter, String under_BsWriter, String under_Content,
			Date under_Date) {
		super();
		this.under_No = under_No;
		this.reply_No = reply_No;
		this.under_CsWriter = under_CsWriter;
		this.under_BsWriter = under_BsWriter;
		this.under_Content = under_Content;
		this.under_Date = under_Date;
	}

	@Override
	public String toString() {
		return "UnderReply [under_No=" + under_No + ", reply_No=" + reply_No + ", under_CsWriter=" + under_CsWriter
				+ ", under_BsWriter=" + under_BsWriter + ", under_Content=" + under_Content + ", under_Date="
				+ under_Date + "]";
	}

	public int getUnder_No() {
		return under_No;
	}

	public void setUnder_No(int under_No) {
		this.under_No = under_No;
	}

	public int getReply_No() {
		return reply_No;
	}

	public void setReply_No(int reply_No) {
		this.reply_No = reply_No;
	}

	public String getUnder_CsWriter() {
		return under_CsWriter;
	}

	public void setUnder_CsWriter(String under_CsWriter) {
		this.under_CsWriter = under_CsWriter;
	}

	public String getUnder_BsWriter() {
		return under_BsWriter;
	}

	public void setUnder_BsWriter(String under_BsWriter) {
		this.under_BsWriter = under_BsWriter;
	}

	public String getUnder_Content() {
		return under_Content;
	}

	public void setUnder_Content(String under_Content) {
		this.under_Content = under_Content;
	}

	public Date getUnder_Date() {
		return under_Date;
	}

	public void setUnder_Date(Date under_Date) {
		this.under_Date = under_Date;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
