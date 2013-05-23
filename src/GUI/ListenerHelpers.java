package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListenerHelpers {
	
	public static class LogOutListner implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO restarts now, but needs to log out later
			Driver.getMainGui().restart();
		}
	}
}
