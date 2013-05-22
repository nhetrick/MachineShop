package main;

public class Tool {
	private boolean isCheckedOut;
	private String name;
	private int UPC;
	
	public Tool(String name, int UPC) {
		isCheckedOut = false;
		this.name = name;
		this.UPC = UPC;
	}
	
	public void checkoutTool() {
		isCheckedOut = true;
	}
	
	public void returnTool() {
		isCheckedOut = false;
	}

	public boolean isCheckedOut() {
		return isCheckedOut;
	}

	public String getName() {
		return name;
	}

	public int getUPC() {
		return UPC;
	}

}
