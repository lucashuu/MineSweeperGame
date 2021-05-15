package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;

import com.alibaba.fastjson.JSON;

import game.GameBiz;
import util.*;



public class SocketClient implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1017970612457690693L;
	private static String host = "localhost";
	private GameBiz g = null;
		public SocketClient (Object object){
			Socket socket = null;
			try{
			socket = new Socket(host,1111);
			OutputStream os = socket.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			System.out.println(object);
			g = (GameBiz) object;

			String gameBizString = JSON.toJSONString(g);
			System.out.println("1:"+gameBizString);


			oos.writeObject(g);
			}catch(Exception e){
				e.printStackTrace();
			}finally{

				try {
					if(socket != null)
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		public static void main(String[] args) {

				//SocketClient sc = new SocketClient();

		  }
	}
