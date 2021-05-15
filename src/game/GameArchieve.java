package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.alibaba.fastjson.JSON;
//import com.apple.eawt.event.GestureListener;

import Mines.MinesCounter;
import Mines.MinesCreator;
import server.GameServer;
import util.Scorer;
import util.Timer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GameArchieve {

	private GameWindow gameWindow;
	private int chosen = 0;

	private GameBiz gb1 = null, gb2 = null, gb3 = null;
	JComboBox<String> jComboBox;

	public GameArchieve(GameWindow gw) {
		this.gameWindow = gw;
	}

	private Connection connection = null;
	private Statement statement = null;
	private ResultSet resultSet = null;

	public GameBiz chooseToRead() {
		try {
			java.util.List<GameBiz> gList = selectSqliteDb();
			System.out.println(gList);
			// for(int gList.size())
			if (gList.size() >= 1)
				gb1 = gList.get(0);
			if (gList.size() >= 2)
				gb2 = gList.get(1);
			if (gList.size() >= 3)
				gb3 = gList.get(2);

			new ReadDialog(gList.size());
			// System.out.println(gList.size());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (chosen == 1) {
			return gb1;
		} else if (chosen == 2) {
			return gb2;
		} else if (chosen == 3) {
			return gb3;
		} else if (chosen == 0) {
			return null;
		}
		return null;
	}

	class ReadDialog extends JDialog {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JButton b1, b2;
		private ReadDialog self = this;
		protected JPanel p1, p2;

		public ReadDialog(int num) {
			// super("LOAD", "You can load anyone of the following：");
			setModal(true);
			setTitle("LOAD");
			setSize(400, 200);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			setLocationRelativeTo(gameWindow);

			setResizable(false);
			Font font = new Font("Arial", 1, 20);
			setLayout(new BorderLayout());
			p1 = new JPanel();
			p2 = new JPanel();
			jComboBox = new JComboBox();
			for (int i = 0; i < num; i++) {
				jComboBox.addItem("Last Round " + i);
			}

			p1.add(jComboBox);

			this.add(p1, BorderLayout.CENTER);
			add(p2, BorderLayout.SOUTH);
			p1.setLayout(new GridLayout());

			p1.setFont(font);

			p1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 3),
					"You can load anyone of the following："));
			p2.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 1));

			b1 = new JButton("LOAD");
			b2 = new JButton("CANCEL");
			Font font2 = new Font("Arial", 1, 18);
			b1.setFont(font2);
			b2.setFont(font2);

			p2.add(b1);
			p2.add(b2);

			addListener();
			setVisible(true);
		}

		private void addListener() {
			b1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON1) {
						// System.out.println(jComboBox.getSelectedIndex());
						chosen = jComboBox.getSelectedIndex() + 1;
						self.dispose();
					}
				}
			});
			b2.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON1) {
						chosen = 0;
						self.dispose();

					}
				}
			});
			this.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					chosen = 0;
				}
			});

		}
	}

	public java.util.List<GameBiz> selectSqliteDb() {
		ConnectDB();

		java.util.List<GameBiz> gList = new ArrayList<>();
//		     String gameBizString = JSON.toJSONString(gameBiz);
//		     System.out.println(gameBizString);
		try {
			ResultSet rs = statement.executeQuery("select * from tasks order by id desc limit 1;");
			Integer id = rs.getInt(1);
			System.out.println("id:" + id);
			byte[] data = rs.getBytes(2);
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
			ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
			GameBiz task = (GameBiz) (objectInputStream.readObject());
			byteArrayInputStream.close();
			objectInputStream.close();
			if (task != null) {
				gList.add(task);
			}

			ResultSet rs1 = statement.executeQuery("select * from tasks order by id desc limit 1,1;");
			Integer id1 = rs.getInt(1);
			System.out.println("id1:" + id1);
			byte[] data1 = rs1.getBytes(2);
			ByteArrayInputStream byteArrayInputStream1 = new ByteArrayInputStream(data1);
			ObjectInputStream objectInputStream1 = new ObjectInputStream(byteArrayInputStream1);
			GameBiz task1 = (GameBiz) (objectInputStream1.readObject());
			byteArrayInputStream1.close();
			objectInputStream1.close();

			if (task1 != null) {
				gList.add(task1);
			}

			ResultSet rs2 = statement.executeQuery("select * from tasks order by id desc limit 2,1;");
			Integer id2 = rs.getInt(1);
			System.out.println("id2:" + id2);
			byte[] data2 = rs2.getBytes(2);
			ByteArrayInputStream byteArrayInputStream2 = new ByteArrayInputStream(data2);
			ObjectInputStream objectInputStream2 = new ObjectInputStream(byteArrayInputStream2);
			GameBiz task2 = (GameBiz) (objectInputStream2.readObject());
			byteArrayInputStream2.close();
			objectInputStream2.close();
			if (task2 != null) {
				gList.add(task2);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

//		while (rs.next()) {
//			String data1 = rs.getString("data");
//			String _id = rs.getString("_id");
//			System.out.println(_id);
//
//			GameBiz gameBiz = JSON.parseObject(data, GameBiz.class);
//		}

		return gList;
	}

	public void ConnectDB() {
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:game.db");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		System.out.println("Database connected");

//		     
		// Create a statement
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}

	}

}
