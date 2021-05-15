package game;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

/**
 * Menu
 */

public class GameMenu extends JMenuBar implements Serializable {
	private static final long serialVersionUID = 3944252560798350733L;
	private JMenu game;
	private JMenuItem save;
	private JMenuItem read;
	private JMenuItem exit;
	private JMenuItem newg;

	public GameMenu() {

		this.setBorder(BorderFactory.createRaisedSoftBevelBorder());

		game = new JMenu("File");
		newg = new JMenuItem("New   ⌘N"); 
		save = new JMenuItem("Save  ⌘S");
		read = new JMenuItem("Open  ⌘O");
		exit = new JMenuItem("Exit  ⌘X");

		game.add(newg);
		game.add(save);
		game.add(read);

		game.add(exit);
		this.add(game);

	}

	public JMenu getGame() {
		return game;
	}

	public void setGame(JMenu game) {
		this.game = game;
	}

	public JMenuItem getExit() {
		return exit;
	}

	public void setExit(JMenuItem exit) {
		this.exit = exit;
	}

	public JMenuItem getSave() {
		return save;
	}

	public void setSave(JMenuItem save) {
		this.save = save;
	}

	public JMenuItem getRead() {
		return read;
	}

	public void setRead(JMenuItem read) {
		this.read = read;
	}

	public JMenuItem getNewg() {
		return newg;
	}

	public void setStart(JMenuItem newg) {
		this.newg = newg;
	}

}
