package member.model.vo;



public class ApprovalSearch {

	private int limit;
	private int currentPage;
	private String seType;
	private String seValue;
	
	public ApprovalSearch() {
		
	}
	public ApprovalSearch(int limit, int currentPage, String seType, String seValue) {
		super();
		this.limit = limit;
		this.currentPage = currentPage;
		this.seType = seType;
		this.seValue = seValue;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
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
	@Override
	public String toString() {
		return "ApprovalSearch [limit=" + limit + ", currentPage=" + currentPage + ", seType=" + seType + ", seValue="
				+ seValue + "]";
	}
	
	
	
}
