package game;

import java.io.Serializable;
import Mines.MineButton;

/**
 * Button Maps Generate Maps Return buttons'properties
 */
public class ButtonsMap implements Serializable {
	private static final long serialVersionUID = -331165561281752614L;
	private int rows;
	private int cols;
	private MineButton[][] mineButtons;

	public ButtonsMap() {
		this.rows = 16;
		this.cols = 16;
		createMap();
	}

	public void createMap() {
		mineButtons = new MineButton[rows][cols];
		for (int i = 0; i < rows; i++) {
			mineButtons[i] = new MineButton[cols];
			for (int j = 0; j < cols; j++) {
				mineButtons[i][j] = new MineButton();
				mineButtons[i][j].setIx(j);
				mineButtons[i][j].setIy(i);
			}
		}
	}

	public void resetButtons() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				mineButtons[i][j].reset();
			}
		}
	}

	// flag all the button, we used this after we win the game
	// Return flag count
	public int flagAll() {
		int cnt = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (!mineButtons[i][j].isSweeped() && !mineButtons[i][j].isFlag()) {
					mineButtons[i][j].setHasFlag();
					cnt++;
				}
			}
		}
		return cnt;
	}

	// Unflag all
	public void unFlagAll() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (!mineButtons[i][j].isSweeped()) {
					mineButtons[i][j].removeFlag();
				}
			}
		}
	}

	// show all the buttons' result
	public void showMapStatus() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				mineButtons[i][j].showButtonStatus();
			}
		}
	}

	// Show around buttons
	public void showAround(MineButton mb) {
		int x = mb.getIx();
		int y = mb.getIy();
		if (y - 1 >= 0)
			mineButtons[y - 1][x].setPressed();
		if (y + 1 < this.getRows())
			mineButtons[y + 1][x].setPressed();
		if (x + 1 < this.getCols())
			mineButtons[y][x + 1].setPressed();
		if (x - 1 >= 0)
			mineButtons[y][x - 1].setPressed();
		if (y - 1 >= 0 && x - 1 >= 0)
			mineButtons[y - 1][x - 1].setPressed();
		if (y + 1 < this.getRows() && x + 1 < this.getCols())
			mineButtons[y + 1][x + 1].setPressed();
		if (x + 1 < this.getCols() && y - 1 >= 0)
			mineButtons[y - 1][x + 1].setPressed();
		if (x - 1 >= 0 && y + 1 < this.getRows())
			mineButtons[y + 1][x - 1].setPressed();
	}

	// cover around and return if you touch the mines
	public boolean coverAround(MineButton mb, GameBiz gb) {
		boolean touchMines = false;
		if (flagCntAround(mb) != mb.getMineCntAround()) {
			coverAround(mb);
		} else {
			touchMines = autoSweep(mb, gb);
		}
		return touchMines;
	}

	// cover around
	public void coverAround(MineButton mb) {
		int x = mb.getIx();
		int y = mb.getIy();
		if (y - 1 >= 0)
			mineButtons[y - 1][x].setReleased();
		if (y + 1 < this.getRows())
			mineButtons[y + 1][x].setReleased();
		if (x + 1 < this.getCols())
			mineButtons[y][x + 1].setReleased();
		if (x - 1 >= 0)
			mineButtons[y][x - 1].setReleased();
		if (y - 1 >= 0 && x - 1 >= 0)
			mineButtons[y - 1][x - 1].setReleased();
		if (y + 1 < this.getRows() && x + 1 < this.getCols())
			mineButtons[y + 1][x + 1].setReleased();
		if (x + 1 < this.getCols() && y - 1 >= 0)
			mineButtons[y - 1][x + 1].setReleased();
		if (x - 1 >= 0 && y + 1 < this.getRows())
			mineButtons[y + 1][x - 1].setReleased();
	}

	public boolean autoSweep(MineButton mb, GameBiz gb) {
		int x = mb.getIx();
		int y = mb.getIy();

		if (y - 1 >= 0)
			gb.autoSweep(mineButtons[y - 1][x]);
		if (y + 1 < this.getRows())
			gb.autoSweep(mineButtons[y + 1][x]);
		if (x + 1 < this.getCols())
			gb.autoSweep(mineButtons[y][x + 1]);
		if (x - 1 >= 0)
			gb.autoSweep(mineButtons[y][x - 1]);
		if (y - 1 >= 0 && x - 1 >= 0)
			gb.autoSweep(mineButtons[y - 1][x - 1]);
		if (y + 1 < this.getRows() && x + 1 < this.getCols())
			gb.autoSweep(mineButtons[y + 1][x + 1]);
		if (x + 1 < this.getCols() && y - 1 >= 0)
			gb.autoSweep(mineButtons[y - 1][x + 1]);
		if (x - 1 >= 0 && y + 1 < this.getRows())
			gb.autoSweep(mineButtons[y + 1][x - 1]);
		if (judge(mb)) {
			return true;
		}
		if (gb.judge()) {
			int currTime = gb.timer.getCurrTime();
			gb.timer.end();
			gb.scorer.setFlagCnt(gb.bm.flagAll() + gb.scorer.getFlagCnt());
			gb.scorer.update();
			gb.setWin(true);
		}
		return false;
	}

	public void showAutoTouched(MineButton mb) {
		int x = mb.getIx();
		int y = mb.getIy();
		if (y - 1 >= 0)
			mineButtons[y - 1][x].showButtonResult(true);
		if (y + 1 < this.getRows())
			mineButtons[y + 1][x].showButtonResult(true);
		if (x + 1 < this.getCols())
			mineButtons[y][x + 1].showButtonResult(true);
		if (x - 1 >= 0)
			mineButtons[y][x - 1].showButtonResult(true);
		if (y - 1 >= 0 && x - 1 >= 0)
			mineButtons[y - 1][x - 1].showButtonResult(true);
		if (y + 1 < this.getRows() && x + 1 < this.getCols())
			mineButtons[y + 1][x + 1].showButtonResult(true);
		if (x + 1 < this.getCols() && y - 1 >= 0)
			mineButtons[y - 1][x + 1].showButtonResult(true);
		if (x - 1 >= 0 && y + 1 < this.getRows())
			mineButtons[y + 1][x - 1].showButtonResult(true);
	}

	public int flagCntAround(MineButton mb) {
		int x = mb.getIx();
		int y = mb.getIy();
		int cnt = 0;
		if (y - 1 >= 0 && mineButtons[y - 1][x].isFlag())
			cnt++;
		if (y + 1 < this.getRows() && mineButtons[y + 1][x].isFlag())
			cnt++;
		if (x + 1 < this.getCols() && mineButtons[y][x + 1].isFlag())
			cnt++;
		if (x - 1 >= 0 && mineButtons[y][x - 1].isFlag())
			cnt++;
		if (y - 1 >= 0 && x - 1 >= 0 && mineButtons[y - 1][x - 1].isFlag())
			cnt++;
		if (y + 1 < this.getRows() && x + 1 < this.getCols() && mineButtons[y + 1][x + 1].isFlag())
			cnt++;
		if (x + 1 < this.getCols() && y - 1 >= 0 && mineButtons[y - 1][x + 1].isFlag())
			cnt++;
		if (x - 1 >= 0 && y + 1 < this.getRows() && mineButtons[y + 1][x - 1].isFlag())
			cnt++;
		return cnt;
	}

	// Return unsweeped count around this button
	public int notSweepedCntAround(MineButton mb) {
		int x = mb.getIx();
		int y = mb.getIy();
		int cnt = 0;
		if (y - 1 >= 0 && !mineButtons[y - 1][x].isSweeped())
			cnt++;
		if (y + 1 < this.getRows() && !mineButtons[y + 1][x].isSweeped())
			cnt++;
		if (x + 1 < this.getCols() && !mineButtons[y][x + 1].isSweeped())
			cnt++;
		if (x - 1 >= 0 && !mineButtons[y][x - 1].isSweeped())
			cnt++;
		if (y - 1 >= 0 && x - 1 >= 0 && !mineButtons[y - 1][x - 1].isSweeped())
			cnt++;
		if (y + 1 < this.getRows() && x + 1 < this.getCols() && !mineButtons[y + 1][x + 1].isSweeped())
			cnt++;
		if (x + 1 < this.getCols() && y - 1 >= 0 && !mineButtons[y - 1][x + 1].isSweeped())
			cnt++;
		if (x - 1 >= 0 && y + 1 < this.getRows() && !mineButtons[y + 1][x - 1].isSweeped())
			cnt++;
		return cnt;
	}

	// judge if you touch the mine
	public boolean judge(MineButton mb) {
		int x = mb.getIx();
		int y = mb.getIy();
		if (y - 1 >= 0 && mineButtons[y - 1][x].notFlagButMine())
			return true;
		if (y + 1 < this.getRows() && mineButtons[y + 1][x].notFlagButMine())
			return true;
		if (x + 1 < this.getCols() && mineButtons[y][x + 1].notFlagButMine())
			return true;
		if (x - 1 >= 0 && mineButtons[y][x - 1].notFlagButMine())
			return true;
		if (y - 1 >= 0 && x - 1 >= 0 && mineButtons[y - 1][x - 1].notFlagButMine())
			return true;
		if (y + 1 < this.getRows() && x + 1 < this.getCols() && mineButtons[y + 1][x + 1].notFlagButMine())
			return true;
		if (x + 1 < this.getCols() && y - 1 >= 0 && mineButtons[y - 1][x + 1].notFlagButMine())
			return true;
		if (x - 1 >= 0 && y + 1 < this.getRows() && mineButtons[y + 1][x - 1].notFlagButMine())
			return true;
		return false;
	}

	public int getTotalCnt() {
		return rows * cols;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public MineButton getMineButton(int i, int j) {
		return mineButtons[i][j];
	}
}
