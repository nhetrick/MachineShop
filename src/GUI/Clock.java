package GUI;

import java.awt.Font;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Clock extends JPanel implements Runnable {
	
	private volatile Thread clockThread = null;
	private JLabel timeLabel;
	
	public Clock(Font f) {
		
		timeLabel = new JLabel("");
		timeLabel.setFont(f);
		clockThread = new Thread(this);
		clockThread.start();
		add(timeLabel);
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
		timeLabel.setText(time);
		repaint();
		
	}

}
