package server;

import com.alibaba.fastjson.JSON;
import game.GameBiz;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

public class GameServer extends JFrame implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -2005744094245624523L;
	/**
	 *
	 */

	private JTextArea wordsBox;
	private ServerSocket serverSocket = null;
	private ObjectOutputStream toClient = null;
	private Socket socket = null;
	private ObjectInputStream inputFromClient = null;
	private Connection connection = null;
	private Statement statement = null;
	private ResultSet resultSet = null;

	public GameServer() {
		createMainPanel();
		wordsBox.append("Listening");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(200, 200);
		setVisible(true);
		ConnectDB();
		Start();
	}

	public void createMainPanel() {
		wordsBox = new JTextArea(35, 10);

		JScrollPane listScroller = new JScrollPane(wordsBox);
		this.add(listScroller, BorderLayout.CENTER);
		listScroller.setPreferredSize(new Dimension(250, 80));
	}

	public void Start() {

		ServerSocket ss = null;
		try {
			ss = new ServerSocket(1111);
			while (true) {
				final Socket socket = ss.accept();
				new Runnable() {

					public void run() {
						try {
							System.out.println("Welcome to connect");
							InputStream is = socket.getInputStream();
							OutputStream os = socket.getOutputStream();
							os.write("Welcome to connect1".getBytes());
							System.out.println("Welcome to connect 2");
							ObjectInputStream ois = new ObjectInputStream(is);
							GameBiz s = (GameBiz) (ois.readObject());

							String gameBizString = JSON.toJSONString(s);
							System.out.println("2:" + gameBizString);

							addSqliteDb(s);
							
							socket.close();
						} catch (Exception e) {
							e.printStackTrace();

						} finally {
							if (socket != null)
								try {
									socket.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						}
					}
				}.run();

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			System.out.println("Close the connections！");
			try {
				if (ss != null)
					ss.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void addSqliteDb(GameBiz gameBiz) {
		try {

			// JSONObject jsonobj = JSON.parseObject(gameBizString);
			// System.out.println("gameBizString:"+jsonobj.getString("gw"));
			// System.out.println(gameBizString);
			ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(arrayOutputStream);
			objectOutputStream.writeObject(gameBiz);
			objectOutputStream.flush();
			byte data[] = arrayOutputStream.toByteArray();

			System.out.println(data);

			PreparedStatement prepInsert = connection.prepareStatement("insert into tasks(task) values (?);");
			prepInsert.setBytes(1, data);
			prepInsert.addBatch();
			prepInsert.executeBatch();
			connection.setAutoCommit(true);
			System.err.println("Succes insert into sqlite");
			if (statement != null)
				statement.close();
		} catch (Exception e) {
			System.err.println("Failing insert into sqlite");
			e.printStackTrace();
		} finally {
//		      if (connection != null)
//		       try {
//		        connection.close();
//		       } catch (SQLException e) {
//		        e.printStackTrace();
//		       }
		}
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
