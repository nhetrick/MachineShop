package GUI;

import java.awt.Font;
import java.util.Calendar;

import javax.swing.JLabel;

public class Clock extends JLabel implements Runnable {
	
	private volatile Thread clockThread = null;
	
	public Clock(Font f) {
		
		setFont(f);
		clockThread = new Thread(this);
		clockThread.start();
		setVisible(true);
		updateTime();
	}

	@Override
	public void run() {
		Thread thisThread = Thread.currentThread();
		
		while ( clockThread == thisThread ) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) { }
			updateTime();
		}
	}
	
	public void updateTime() {
		
		Calendar calendar = Calendar.getInstance();
		String time = calendar.getTime().toLocaleString();
		setText(time);
		repaint();	
	}
}
