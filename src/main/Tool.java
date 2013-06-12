package main;

import java.util.Comparator;

import GUI.Driver;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class Tool {
	private boolean isCheckedOut;
	private String name;
	private String UPC;
	private User lastUsedBy;
	
	public Tool(String name, String UPC) {
		isCheckedOut = false;
		this.name = name;
		this.UPC = UPC;
	}
	
	public Tool(String name, String UPC, User u) {
		isCheckedOut = false;
		this.name = name;
		this.UPC = UPC;
		lastUsedBy = u;
	}
	
	public void updateCheckoutStatus(User u) {
		DBCollection toolsCollection = Driver.getAccessTracker().getDatabase().getCollection("Tools");
		DBObject result = toolsCollection.findOne(new BasicDBObject("upc", UPC));
		if (result != null) {
			result.put("isCheckedOut", isCheckedOut);
			result.put("lastUsedBy", u.getCWID());

			toolsCollection.update(new BasicDBObject("upc", UPC), result);
		}
		lastUsedBy = u;
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

	public String getUPC() {
		return UPC;
	}
	
	public String toString() {
		return name + " (" + UPC + ")";
	}
	
	public User getLastUsedBy() {
		return lastUsedBy;
	}

	// only the UPC is compared to check if two tools are the same.
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Tool))
			return false;
		Tool obj = (Tool) o;
		return (this.UPC.equals(obj.getUPC()));
	}

	public void setCheckedOut(boolean b) {
		isCheckedOut = b;
		
	}

	public void setLastUsedBy(String string) {
		lastUsedBy = new User("", "", string, "", "");		
	}
}
