package Mines;

import javax.swing.*;

import game.GameBiz;

import java.awt.*;
import java.io.Serializable;

/**
 * Mines Button The basic layer of this game
 */

public class MineButton extends JButton implements Serializable {
	private static final long serialVersionUID = 731990634439768153L;
	private boolean isSweeped;
	private boolean isMine;
	private boolean isFlag;
	private int mineCntAround;
	private int ix, iy;

	public MineButton() {
		int width = 25;
		int height = 25;
		setFlag(false);
		setSweeped(false);
		setMine(false);
		setMineCntAround(0);
		ImageIcon mineIcon = new ImageIcon(MineButton.class.getResource("/icons/10.png"));
		mineIcon.setImage(mineIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
		this.setIcon(mineIcon);
		this.setBorder(BorderFactory.createLineBorder(new Color(240, 240, 240)));
	}

	@Override
	public String toString() {
		return "[isMine=" + isMine + ", isSweeped=" + isSweeped + ", isFlag=" + isFlag + ", mineCntAround="
				+ mineCntAround + "]";
	}

	// Set button image
	public void setText(int cnt) {
		int width = 25;
		int height = 25;
		if (cnt == 1) {
			ImageIcon mineIcon1 = new ImageIcon(MineButton.class.getResource("/icons/1.png"));
			mineIcon1.setImage(mineIcon1.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
			this.setIcon(mineIcon1);
		} else if (cnt == 2) {
			ImageIcon mineIcon2 = new ImageIcon(MineButton.class.getResource("/icons/2.png"));
			mineIcon2.setImage(mineIcon2.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
			this.setIcon(mineIcon2);
		} else if (cnt == 3) {
			ImageIcon mineIcon3 = new ImageIcon(MineButton.class.getResource("/icons/3.png"));
			mineIcon3.setImage(mineIcon3.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
			this.setIcon(mineIcon3);
		} else if (cnt == 4) {
			ImageIcon mineIcon4 = new ImageIcon(MineButton.class.getResource("/icons/4.png"));
			mineIcon4.setImage(mineIcon4.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
			this.setIcon(mineIcon4);
		} else if (cnt == 5) {
			ImageIcon mineIcon5 = new ImageIcon(MineButton.class.getResource("/icons/5.png"));
			mineIcon5.setImage(mineIcon5.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
			this.setIcon(mineIcon5);
		} else if (cnt == 6) {
			ImageIcon mineIcon6 = new ImageIcon(MineButton.class.getResource("/icons/6.png"));
			mineIcon6.setImage(mineIcon6.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
			this.setIcon(mineIcon6);
		} else if (cnt == 7) {
			ImageIcon mineIcon7 = new ImageIcon(MineButton.class.getResource("/icons/7.png"));
			mineIcon7.setImage(mineIcon7.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
			this.setIcon(mineIcon7);
		} else if (cnt == 8) {
			ImageIcon mineIcon8 = new ImageIcon(MineButton.class.getResource("/icons/8.png"));
			mineIcon8.setImage(mineIcon8.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
			this.setIcon(mineIcon8);
		}
	}

	// set this block has been Sweeped
	public void setHasSweeped() {
		int width = 25;
		int height = 25;
		if (!this.isMine() && !this.isFlag()) {
			ImageIcon mineIcon9 = new ImageIcon(MineButton.class.getResource("/icons/0.png"));
			mineIcon9.setImage(mineIcon9.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
			this.setIcon(mineIcon9);
			if (mineCntAround != 0)
				this.setText(this.mineCntAround);
			this.setSweeped(true);
		}
	}

	// Set this button has a flag
	public void setHasFlag() {
		if (isSweeped) {
			return;
		}
		int width = 25;
		int height = 25;
		ImageIcon mineIcon10 = new ImageIcon(MineButton.class.getResource("/icons/11.png"));
		mineIcon10.setImage(mineIcon10.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
		this.setIcon(mineIcon10);
		this.setFlag(true);
	}

	// Remove this button's flag
	public void removeFlag() {
		int width = 25;
		int height = 25;
		ImageIcon mineIcon11 = new ImageIcon(MineButton.class.getResource("/icons/10.png"));
		mineIcon11.setImage(mineIcon11.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
		this.setIcon(mineIcon11);
		this.setFlag(false);
	}

	// Set this block has been pressed
	public void setPressed() {
		if (!this.isFlag && !this.isSweeped) {
			this.setBorder(BorderFactory.createLineBorder(new Color(240, 240, 240)));
		}
	}

	// Set this block has been released
	public void setReleased() {
		if (!this.isSweeped) {
			this.setBorder(BorderFactory.createLineBorder(new Color(240, 240, 240)));
		}
	}

	// Show button result
	public void showButtonResult(boolean isTouched) {
		int width = 25;
		int height = 25;
		if (this.isMine()) {
			this.setBorder(BorderFactory.createLineBorder(new Color(183, 183, 183)));
			ImageIcon mineIcon13 = new ImageIcon(GameBiz.class.getResource("/icons/9.png"));
			mineIcon13.setImage(mineIcon13.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
			this.setIcon(mineIcon13);
			if (this.isFlag()) {// Correct Flag
				ImageIcon mineIcon15 = new ImageIcon(GameBiz.class.getResource("/icons/11.png"));
				mineIcon15.setImage(mineIcon15.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
				this.setIcon(mineIcon15);
			}
		} else if (this.isFlag()) {// Wrong Flag
			ImageIcon mineIcon14 = new ImageIcon(GameBiz.class.getResource("/icons/12.png"));
			mineIcon14.setImage(mineIcon14.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
			this.setIcon(mineIcon14);
		}
	}

	// Show all buttons' status
	public void showButtonStatus() {
		if (this.isSweeped) {
			setHasSweeped();
		} else if (this.isFlag) {
			setHasFlag();
		}
	}

	// Rest all the buttons
	public void reset() {
		int width = 25;
		int height = 25;
		setFlag(false);
		setSweeped(false);
		setMine(false);
		setMineCntAround(0);
		ImageIcon mineIcon15 = new ImageIcon(MineButton.class.getResource("/icons/10.png"));
		mineIcon15.setImage(mineIcon15.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
		this.setIcon(mineIcon15);
		this.setBorder(BorderFactory.createLineBorder(new Color(240, 240, 240)));

	}

	public int getIx() {
		return ix;
	}

	public void setIx(int ix) {
		this.ix = ix;
	}

	public int getIy() {
		return iy;
	}

	public void setIy(int iy) {
		this.iy = iy;
	}

	public boolean notFlagButMine() {
		if (!this.isFlag && this.isMine)
			return true;
		return false;
	}

	public boolean isSweeped() {
		return this.isSweeped;
	}

	public void setSweeped(boolean isSweeped) {
		this.isSweeped = isSweeped;
	}

	public boolean isMine() {
		return this.isMine;
	}

	public void setMine(boolean isMine) {
		this.isMine = isMine;
	}

	public boolean isFlag() {
		return this.isFlag;
	}

	public void setFlag(boolean isFlag) {
		this.isFlag = isFlag;
	}

	public int getMineCntAround() {
		return this.mineCntAround;
	}

	public void setMineCntAround(int mineCntAround) {
		this.mineCntAround = mineCntAround;
	}
}
