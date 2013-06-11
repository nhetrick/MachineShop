package main;

import java.util.Comparator;

public class UserComparator implements Comparator<User> {
	@Override
	public int compare(User o1, User o2) {
		String o1Name = o1.getFirstName()+o1.getLastName();
		String o2Name = o2.getFirstName()+o2.getLastName();
		return o1Name.compareTo(o2Name);
	}
}