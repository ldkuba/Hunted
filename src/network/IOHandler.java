package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

public class IOHandler {

	private DataOutputStream outputStream;

	private DataInputStream inputStream;

	private Socket clientSocket;

	private ServerSocket serverSocket;

	public void connectToServer(String serverName, int port){
		try {
			clientSocket = new Socket(serverName, port);
			outputStream = new DataOutputStream(clientSocket.getOutputStream());
			inputStream = new DataInputStream(clientSocket.getInputStream());
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void createServer(int port){
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.setSoTimeout(10000);

			System.out.println("Server created on"  + InetAddress.getLocalHost() + ":" + serverSocket.getLocalPort());
		} catch (SocketException e) {
			System.err.println("Unable to use port: " + port);
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void runServer(){
		try {
			clientSocket = serverSocket.accept();
			inputStream = new DataInputStream(clientSocket.getInputStream());
			outputStream = new DataOutputStream(clientSocket.getOutputStream());
		}catch(SocketTimeoutException s) {
			System.out.println("Socket timed out");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String recieveData(){
		try {
			return inputStream.readUTF();
		}catch(IOException e){
			e.printStackTrace();
		}
		return null;
	}

	public void sendData(String data) {
		try {
			outputStream.writeUTF(data);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void closeS() {
		try {
			clientSocket.close();
			serverSocket.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}


}
