package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListenerHelpers {
	
	public static class LogOutListner implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Driver.getAccessTracker().processLogOut(Driver.getAccessTracker().getCurrentUser().getCWID());
			Driver.getMainGui().restart();
		}
	}
}
