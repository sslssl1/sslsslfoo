package member.model.vo;

public class Manager extends Member implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4506278175800399414L;
	private String manager_Id;
	private String manager_Pwd;
	
	public Manager() {
		// TODO Auto-generated constructor stub
	}

	public Manager(String manager_Id, String manager_Pwd) {
		super();
		this.manager_Id = manager_Id;
		this.manager_Pwd = manager_Pwd;
	}

	public String getManager_Id() {
		return manager_Id;
	}

	public void setManager_Id(String manager_Id) {
		this.manager_Id = manager_Id;
	}

	public String getManager_Pwd() {
		return manager_Pwd;
	}

	public void setManager_Pwd(String manager_Pwd) {
		this.manager_Pwd = manager_Pwd;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.manager_Id+","+this.manager_Pwd;
	}
	
	
	

}
