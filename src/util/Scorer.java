package util;

import javax.swing.*;

import game.GameWindow;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

/**
 * Scorer
 */

public class Scorer extends JLabel implements Serializable {
	private static final long serialVersionUID = -7021786508730606485L;

	private int flagCnt;

	private int minesCnt;

	private int restMinesCnt;

	private int sweepedCnt;

	GameWindow gw;

	public Scorer(int minesCnt) {
		this.minesCnt = minesCnt;
		// this.restMinesCnt = minesCnt;
		this.flagCnt = 0;
		// this.sweepedCnt = 0;
		this.setPreferredSize(new Dimension(120, 30));
		// this.setEnabled(true);
		this.setForeground(new Color(0, 0, 0));
		// this.setBorder(BorderFactory.createLoweredBevelBorder());
		this.setText(String.valueOf(this.getRestMinesCnt()));
		this.setFont(new Font("alias", Font.PLAIN, 14));

	}

	public void addFlag() {
		flagCnt++;
		showScore();
	}

	public void addFlag(int cnt) {
		flagCnt += cnt;
		showScore();
	}

	public void removeFlag() {
		flagCnt--;
		showScore();
	}

	public void sweepOne() {
		sweepedCnt++;
	}

	public void update() {
		showScore();
	}

	public void update(String x) {
		this.setText(x);
	}

	public boolean returnupdate() {
		if (this.getText().equals("Congratulations Game Won") || this.getText().equals("Game Lose")) {
			return true;
		}
		return false;
	}

	public void showScore() {
		if (this.getRestMinesCnt() > 0) {
			this.setText(String.valueOf(this.getRestMinesCnt()));
		} else {
			this.setText("No Marks left");
		}
	}

	public int getFlagCnt() {
		return flagCnt;
	}

	public void setFlagCnt(int flagCnt) {
		this.flagCnt = flagCnt;
	}

	public int getMinesCnt() {
		return minesCnt;
	}

	public void setMinesCnt(int minesCnt) {
		this.minesCnt = minesCnt;
	}

	public int getRestMinesCnt() {
		if (minesCnt - flagCnt >= 0) {
			return minesCnt - flagCnt;
		} else {
			return 0;
		}
	}

	public void setRestMinesCnt(int restMinesCnt) {
		this.restMinesCnt = restMinesCnt;
	}

	public int getSweepedCnt() {
		return sweepedCnt;
	}

	public void setSweepedCnt(int sweepedCnt) {
		this.sweepedCnt = sweepedCnt;
	}
}
