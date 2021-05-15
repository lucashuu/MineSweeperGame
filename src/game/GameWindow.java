package game;

import javax.swing.*;
import util.Timer;
import util.Scorer;

import java.awt.*;
import java.io.Serializable;

/**
 * Game Window
 */
public class GameWindow extends JFrame implements Serializable {
	private static final long serialVersionUID = -7673336490024786648L;
	JPanel pMap, pUp, pDown;

	public GameWindow(String title) {
		this.setTitle(title);
		this.setLayout(new BorderLayout());
		this.setVisible(true);
		this.setResizable(false);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		pUp = new JPanel(new BorderLayout());
		pDown = new JPanel(new BorderLayout());
		this.add(pUp, BorderLayout.NORTH);
		this.add(pDown, BorderLayout.SOUTH);
	}

	public void add(ButtonsMap bm) {
		pMap = new JPanel(new GridLayout(bm.getRows(), bm.getCols()));

		for (int i = 0; i < bm.getRows(); i++) {
			for (int j = 0; j < bm.getCols(); j++) {
				pMap.add(bm.getMineButton(i, j));
			}
		}
		this.add(pMap, BorderLayout.CENTER);
	}

	public void add(Scorer scorer) {

		pDown.add(scorer, BorderLayout.WEST);

	}

	public void add(Timer timer) {

		pUp.add(timer, BorderLayout.CENTER);
		timer.setHorizontalAlignment(JLabel.CENTER);
	}

	public void set(GameMenu gm) {
		this.setJMenuBar(gm);
	}

}
