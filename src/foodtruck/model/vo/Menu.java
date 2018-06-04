package foodtruck.model.vo;

public class Menu {
	
	private String business_Id;
	private String menu_Name;
	private int menu_price;
	private String menu_Img;
	
	public Menu() {
		// TODO Auto-generated constructor stub
	}

	public Menu(String business_Id, String menu_Name, int menu_price, String menu_Img) {
		super();
		this.business_Id = business_Id;
		this.menu_Name = menu_Name;
		this.menu_price = menu_price;
		this.menu_Img = menu_Img;
	}

	public String getBusiness_Id() {
		return business_Id;
	}

	public void setBusiness_Id(String business_Id) {
		this.business_Id = business_Id;
	}

	public String getMenu_Name() {
		return menu_Name;
	}

	public void setMenu_Name(String menu_Name) {
		this.menu_Name = menu_Name;
	}

	public int getMenu_price() {
		return menu_price;
	}

	public void setMenu_price(int menu_price) {
		this.menu_price = menu_price;
	}

	public String getMenu_Img() {
		return menu_Img;
	}

	public void setMenu_Img(String menu_Img) {
		this.menu_Img = menu_Img;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.business_Id+","+this.menu_Name+","+this.menu_price+","+this.menu_Img;
	}
	

}
