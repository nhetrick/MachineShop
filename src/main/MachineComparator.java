package main;

import java.util.Comparator;

public class MachineComparator implements Comparator<Machine> {
		@Override
		public int compare(Machine o1, Machine o2) {
			return o1.getName().compareTo(o2.getName());
		}
	}