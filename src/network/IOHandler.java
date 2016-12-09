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

	/**
	 * Try to connect using the specific ip address and port.
	 * @param ipAddress
	 * @param port
	 */
	public void connectToServer(String ipAddress, int port){
		try {
			clientSocket = new Socket(ipAddress, port);
			outputStream = new DataOutputStream(clientSocket.getOutputStream());
			inputStream = new DataInputStream(clientSocket.getInputStream());
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Try to create a server using local ip address of the machine with a given port.
	 * @param port
	 */
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

	/**
	 * Run server.
	 */
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

	/**
	 * Listens for incoming data.
	 * @return String of the received data.
	 */
	public String receiveData(){
		try {
			return inputStream.readUTF();
		}catch(IOException e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Send a set amount of data to paired game.
	 * @param data String of data.
	 */
	public void sendData(String data) {
		try {
			outputStream.writeUTF(data);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Close all I/O connections.
	 */
	public void closeS() {
		try {
			clientSocket.close();
			serverSocket.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}


}
