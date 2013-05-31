package main;

public class Machine {
	String name;
	String ID;
	boolean inUse;
	
	public Machine(String name, String ID) {
		this.name = name;
		this.ID = ID;
		inUse = false;
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

	public boolean isInUse() {
		return inUse;
	}
	
	public void use() {
		inUse = true;
	}
	
	public void stopUsing() {
		inUse = false;
	}
}
