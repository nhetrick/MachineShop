package main;

public class Machine {
	String name;
	String ID;
	int numUsers;
	
	public Machine(String name, String ID) {
		this.name = name;
		this.ID = ID;
		numUsers = 0;
	}
	
	public String getID() {
		return ID;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Machine))
			return false;
		Machine obj = (Machine) o;
		return (this.ID.equals(obj.getID()));
	}

	public int getNumUsers() {
		return numUsers;
	}
	
	public void use() {
		numUsers++;
	}
	
	public void stopUsing() {
		numUsers--;
	}
}
