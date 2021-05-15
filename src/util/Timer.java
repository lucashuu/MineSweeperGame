package util;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Timer extends JLabel implements Serializable {
	private static final long serialVersionUID = -3451745779567892274L;
	private int currTime = 1000;
	private String timeremainC = "Time Remaining: ";

	transient java.util.Timer t;// java timer

	public Timer() {
		this.setPreferredSize(new Dimension(70, 35));
		// this.setEnabled(true);
		// this.setBackground(new Color(183, 183, 183));
		this.setForeground(new Color(0, 0, 0));
		// this.setMargin(new Insets(0,0,0,0));
		this.setText(timeremainC + String.valueOf(getCurrTime()));
		this.setFont(new Font("alias", Font.PLAIN, 18));
		this.setFocusable(false);
	}

	public void start() {
		setCurrTime(1000);
		t = new java.util.Timer(true);
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				if (currTime != 0)
					currTime--;
				setText(timeremainC + String.valueOf(getCurrTime()));
			}
		}, 1000, 1000);

	}

	public void startAgain() {
		// setCurrTime(1000);
		t = new java.util.Timer(true);
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				if (currTime != 0)
					currTime--;
				setText(timeremainC + String.valueOf(getCurrTime()));
			}
		}, 1000, 1000);

	}

	public void end() {
		try {
			t.cancel();
		} catch (Exception e) {

		}
	}

	public void restart() {
		end();
		setCurrTime(0);
		start();
	}

	public void updateTime() {
		setText(getString() + String.valueOf(getCurrTime()));
	}

	public String getString() {
		return timeremainC;
	}

	public int getCurrTime() {
		return currTime;
	}

	public void setCurrTime(int currTime) {
		this.currTime = currTime;
	}
}
