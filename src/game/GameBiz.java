package game;

import Mines.*;
import server.GameServer;
import server.SocketClient;
import util.*;
import util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

import com.alibaba.fastjson.JSON;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;

/**
 * Game Logic, No matter how many flags you correctly inserted, the wining rule
 * is all the mines were sweeped
 */

public class GameBiz implements Serializable {
	private static final long serialVersionUID = 715919700682963666L;
	GameWindow gw;
	MinesCreator mc;
	MinesCounter counter;
	ButtonsMap bm;
	Scorer scorer;
	Timer timer;
	GameMenu gm;
	SocketClient socketClient = null;
	private boolean isFail = false;
	private boolean isWin = false;
	private boolean isFirst = true;// First CLick
	private boolean isSecond = false;// Second Click for ending game and double click to restart the game
	private boolean isPause = false;//是否暂停
	private GameBiz self = this;
	public Date date;
	// Menu Event Item Constants
	public static final int SAVE = 1;
	public static final int READ = 2;
	public static final int START = 3;

	public GameBiz() {
	}

	public GameBiz(GameWindow gw, MinesCreator mc, MinesCounter counter, ButtonsMap bm, Scorer scorer, Timer timer,
			GameMenu gm) {
		this.gm = gm;
		this.gw = gw;
		this.mc = mc;
		this.counter = counter;
		this.bm = bm;
		this.scorer = scorer;
		this.timer = timer;
		init();
	}

	// Initialize the board
	public void init() {
		gw.add(bm);
		this.addListener();
		gw.add(scorer);
		gw.add(timer);
		gw.set(gm);
		gw.pack();
		gw.setLocationRelativeTo(null);// Makes the game window to be center display

	}

	// reset the game
	public void reset() {
		gw.dispose();
		gw = new GameWindow("Mine Sweeper");
		bm = new ButtonsMap();
		mc = new MinesCreator();
		counter = new MinesCounter(bm);
		scorer = new Scorer(40);
		gm = new GameMenu();
		timer.end();
		timer.setCurrTime(1000);
		timer.updateTime();
		gw.add(bm);
		gw.add(scorer);
		gw.add(timer);
		gw.set(gm);
		addListener();
		setFail(false);
		setWin(false);
		setFirst(true);
		setSecond(false);
		gw.pack();
		gw.setLocationRelativeTo(null);

	}

	// multi-threading to process menus selection
	// Every time you save the game, there will be a new thread
	public void eventDeal(int event) {
		new Thread() {
			@Override
			public void run() {
				if (event == SAVE) {
					timer.end();
					try {
						String gameBizString = JSON.toJSONString(self);
						System.out.println("0:" + gameBizString);

						socketClient = new SocketClient(self);

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (!isFirst && !isFail && !isWin)
						timer.startAgain();
				} else if (event == READ) {

					GameArchieve gameArchieve = new GameArchieve(gw);
					resetForSaved(gameArchieve.chooseToRead());

				} else if (event == START) {
					reset();
				}
			}
		}.start();
	}

	// Read from the save
	public void resetForSaved(GameBiz gbNew) {
		if (gbNew == null)
			return;
		gw.dispose();

		this.gw = gbNew.gw;
		this.mc = gbNew.mc;
		this.counter = gbNew.counter;
		this.bm = gbNew.bm;
		this.scorer = gbNew.scorer;
		this.timer = gbNew.timer;
		this.gm = gbNew.gm;
		this.isFail = gbNew.isFail;
		this.isWin = gbNew.isWin;
		this.isFirst = gbNew.isFirst;
		
		this.isSecond = gbNew.isSecond;

		addListener();
		timer.startAgain();
		setPause(true);
		gw.setVisible(true);
	}

	// add action listener to the buttons and menu
	public void addListener() {
		for (int i = 0; i < bm.getRows(); i++) {
			for (int j = 0; j < bm.getCols(); j++) {
				bm.getMineButton(i, j).addMouseListener(new MineMouseListener());
				bm.getMineButton(i, j).addActionListener(new MineActionListener());
			}
		}
		this.gm.getSave().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eventDeal(SAVE);
			}
		});
		this.gm.getRead().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eventDeal(READ);
			}
		});
		this.gm.getExit().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		this.gm.getNewg().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eventDeal(START);
			}
		});
	}

	// Show the button result after you left click the button
	public void leftClickResult(MineButton mb) {
		if (!mb.isSweeped() && !mb.isFlag()) {
			if (mb.isMine()) {
				fail(mb);
			} else if (mb.isFlag()) {
			} else {
				autoSweep(mb);
				if (judge()) {
					// Win game
					int currTime = timer.getCurrTime();
					timer.end();
					scorer.setFlagCnt(bm.flagAll() + scorer.getFlagCnt());
					// if you win, all the buttons would be sweeped and flag if there is a mine
					scorer.update();
					scorer.update("Congratulations Game Won");
					setWin(true);
				}
			}
		}
	}

	// Sweep around button
	public void autoSweep(MineButton mb) {
		int x = mb.getIx();
		int y = mb.getIy();

		if(mb.isFlag() || mb.isMine() || mb.isSweeped()){
            return;
        }else if(mb.getMineCntAround() != 0){
            mb.setHasSweeped();
            scorer.sweepOne();
            return;
        }
		// if it has been sweeped
		mb.setHasSweeped();
		scorer.sweepOne();

		// Recursion to sweep around buttons
		if (y - 1 >= 0)
			autoSweep(bm.getMineButton(y - 1, x));
		if (y + 1 < bm.getRows())
			autoSweep(bm.getMineButton(y + 1, x));
		if (x + 1 < bm.getCols())
			autoSweep(bm.getMineButton(y, x + 1));
		if (x - 1 >= 0)
			autoSweep(bm.getMineButton(y, x - 1));
		if (y - 1 >= 0 && x - 1 >= 0)
			autoSweep(bm.getMineButton(y - 1, x - 1));
		if (y + 1 < bm.getRows() && x + 1 < bm.getCols())
			autoSweep(bm.getMineButton(y + 1, x + 1));
		if (x + 1 < bm.getCols() && y - 1 >= 0)
			autoSweep(bm.getMineButton(y - 1, x + 1));
		if (x - 1 >= 0 && y + 1 < bm.getRows())
			autoSweep(bm.getMineButton(y + 1, x - 1));
	}

	// Fail the game
	public void fail(MineButton mb) {
		scorer.update("Game Lose");
		this.setFail(true);
		timer.end();
		for (int i = 0; i < bm.getRows(); i++) {
			for (int j = 0; j < bm.getCols(); j++) {
				bm.getMineButton(i, j).showButtonResult(false);
				// show around button result
			}
		}
		mb.showButtonResult(true);
	}

	// Judge if you win
	public boolean judge() {
		// all the mines have been sweeped
		if (scorer.getSweepedCnt() == bm.getTotalCnt() - mc.getMinesCnt()) {
			return true;
		}
		return false;
	}

	// Action listener in button area
	class MineActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!isFail() && !isWin()) {
				MineButton mb = (MineButton) e.getSource();
				if (isFirst()) {
					// Create mines when player first clicked the buttons
					mc.createMines(bm, mb.getIx(), mb.getIy());
					counter.countMines();
					timer.restart();
					setFirst(false);
				}
				 if (isPause()) {//如果停止，则开始计时
	                    timer.end();//开始之前end一次比较保险
	                    timer.start();
	                    setPause(false);
	            }
				leftClickResult(mb);
			}
		}
	}

	// Action listener for mouse actions
	class MineMouseListener extends MouseAdapter {
		@Override
		// Mouse pressed
		public void mousePressed(MouseEvent e) {
			if (!isFail() && !isWin()) {
				MineButton mb = (MineButton) e.getSource();
				if (!mb.isFlag() && e.getButton() == MouseEvent.BUTTON1) {
					mb.setPressed();
				} else if (!mb.isFlag() && !mb.isSweeped() && e.getButton() == MouseEvent.BUTTON3) {
					if (scorer.getRestMinesCnt() >= 1) {
						mb.setHasFlag();
						scorer.addFlag();
					}
				} else if (mb.isFlag() && !mb.isSweeped() && e.getButton() == MouseEvent.BUTTON3) {
					mb.removeFlag();
					scorer.removeFlag();
				}
			}
		}

		@Override // Mouse Released
		public void mouseReleased(MouseEvent e) {
			if (!isFail() && !isWin()) {
				MineButton mb = (MineButton) e.getSource();
				if (!mb.isFlag() && !mb.isSweeped() && e.getButton() == MouseEvent.BUTTON1) {
					mb.setReleased();
				}
			}
		}

		@Override // Mouse Clicked
		public void mouseClicked(MouseEvent e) {
			if (isFail() || isWin()) {
				if ((e.getButton() == MouseEvent.BUTTON1) && !isSecond()) {
					System.out.println("First Click");
					setSecond(true);
				} else if ((e.getButton() == MouseEvent.BUTTON1) && isSecond()) {
					System.out.println("Second CLick");
					reset();
					setSecond(false);
				}
			}
		}
	}

	public boolean isFail() {
		return isFail;
	}

	public void setFail(boolean fail) {
		isFail = fail;
	}

	public boolean isWin() {
		return isWin;
	}

	public void setWin(boolean win) {
		isWin = win;
	}

	public boolean isFirst() {
		return isFirst;
	}

	public void setFirst(boolean first) {
		isFirst = first;
	}

	public boolean isSecond() {
		return isSecond;
	}

	public void setSecond(boolean sec) {
		isSecond = sec;
	}
	public boolean isPause() {
        return isPause;
    }

    public void setPause(boolean pause) {
        isPause = pause;
    }
	public static long getserialVersionUID() {
		return serialVersionUID;
	}
}
