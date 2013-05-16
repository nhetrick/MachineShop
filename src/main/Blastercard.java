package main;

public interface Blastercard {
	
	public boolean checkLegitimacy(int CWID);
	
	public String loadName(int CWID);
	
}
