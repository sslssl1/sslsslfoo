package review.model.vo;

public class SearchReview implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 151234L;
	private String userId;
	private int currentPage;
	private int limit;
	private String  seType;
	private String seValue;
	
	public SearchReview() {}

	public SearchReview(String userId, int currentPage, int limit, String seType, String seValue) {
		super();
		this.userId = userId;
		this.currentPage = currentPage;
		this.limit = limit;
		this.seType = seType;
		this.seValue = seValue;
	}

	@Override
	public String toString() {
		return "SearchReview [userId=" + userId + ", currentPage=" + currentPage + ", limit=" + limit + ", seType="
				+ seType + ", seValue=" + seValue + "]";
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
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
