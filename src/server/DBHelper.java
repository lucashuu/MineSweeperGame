package server;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import game.GameBiz;

public class DBHelper {
	private static Connection conn;
	private PreparedStatement pres;
	private Connection connection = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	private int portNumber = 1111;

	static {
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:game.db");
			System.out.println("数据库连接成功!!!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
			;
		}
		System.out.println("Database connected");
	}

	public void savePerson(List<GameBiz> games) {
		String sql = "insert into objtest(obj) values(?)";

		try {
			pres = conn.prepareStatement(sql);
			for (int i = 0; i < games.size(); i++) {
				pres.setObject(1, games.get(i));

				pres.addBatch();
			}

			pres.executeBatch();

			if (pres != null)
				pres.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<GameBiz> list() {
		List<GameBiz> list = new ArrayList<GameBiz>();
		String sql = "select obj from objtest";

		try {
			pres = conn.prepareStatement(sql);

			ResultSet res = pres.executeQuery();
			while (res.next()) {
				Blob inBlob = res.getBlob(1);

				InputStream is = inBlob.getBinaryStream();
				BufferedInputStream bis = new BufferedInputStream(is);

				byte[] buff = new byte[(int) inBlob.length()];
				while (-1 != (bis.read(buff, 0, buff.length))) {
					ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(buff));
					GameBiz p = (GameBiz) in.readObject();

					list.add(p);
				}

			}
		} catch (SQLException | IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}
}