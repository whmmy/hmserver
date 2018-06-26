package util;

import java.io.Closeable;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

public class CloseUtil {
	
	public static void closeIO(Closeable... io) {
		for(Closeable t:io) {
			try {
				t.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @param socket
	 */
	public static void closeSocket(Socket socket) {
		try {
			if(null!=socket) {				
				socket.close();
			}
		} catch (IOException e) {
		}
	}
	public static void closeSocket(ServerSocket socket) {
		try {
			if(null!=socket) {				
				socket.close();
			}
		} catch (IOException e) {
		}
	}
	
	public static void closeSocket(DatagramSocket socket) {
		socket.close();
	}

}
