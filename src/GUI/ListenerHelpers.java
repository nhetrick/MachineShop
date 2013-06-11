package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// TODO make other buttons to this if needed.

public class ListenerHelpers {
	
	public static class LogOutListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Driver.getAccessTracker().processLogOut(Driver.getAccessTracker().getCurrentUser().getCWID());
			Driver.getMainGui().restart();
		}
	}
	
	public static class DoneListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Driver.getMainGui().restart();
		}
	}
}