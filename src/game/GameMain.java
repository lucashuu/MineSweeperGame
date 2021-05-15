package game;

import Mines.*;
import server.GameServer;
import util.Scorer;
import util.Timer;

/**
 * Main Function
 */

public class GameMain {
	public static void main(String[] args) {

		GameWindow gw = new GameWindow("Mine Sweeper");

		ButtonsMap bm = new ButtonsMap();

		MinesCreator mc = new MinesCreator();

		MinesCounter counter = new MinesCounter(bm);

		Scorer scorer = new Scorer(mc.getMinesCnt());

		Timer timer = new Timer();

		GameMenu gm = new GameMenu();

		GameBiz gb = new GameBiz(gw, mc, counter, bm, scorer, timer, gm);
		GameServer gServer = new GameServer();

	}
}