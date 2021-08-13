package com.yj.mybatis_plus;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;

public class Test {

	public static void main(String[] args) {
		
		int PORT_CLIENT = 50001;
		Thread ServerThread = null;
		try {
			final ServerSocket server=new ServerSocket(PORT_CLIENT);
			
			if (server == null) {
				System.out.println("Server : The Server is cannot start !!!! ");
			}
			System.out.println("Server : The Server is start: " + server);
			Socket socket = server.accept();

			ServerThread = new Thread(new Runnable() {
				
				public void run() {
					while (true) {
						try {
							if (socket == null) {
								System.out.println("Server :" + "input  para  error !");
							}
							System.out.println("Server Enter ProcessMessage !!!!!!");
							InputStream is = socket.getInputStream();
							byte[] data =receiveBytes100(is);
							if(data != null) {
								String msg = new String(data, "utf-8");
								System.out.println(msg);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			});
			ServerThread.start();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static byte[] receiveBytes100(InputStream is) throws Exception {
		
		int capacity = 100;
		byte[] buffer = new byte[capacity];
		int size = is.read(buffer, 0, buffer.length);
		byte[] data = null;
		if (size > 0) {
			data = new byte[size];
			System.arraycopy(buffer, 0, data, 0, size);
		}
		buffer = null;
		return data;
	}
	
}
