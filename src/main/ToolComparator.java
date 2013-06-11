package main;

import java.util.Comparator;

public class ToolComparator implements Comparator<Tool> {
	@Override
	public int compare(Tool o1, Tool o2) {
		return o1.getName().compareTo(o2.getName());
	}
}