package network;

import java.net.*;
import java.io.*;

public class Server {
	String Host;
 	DataInputStream in;
	DataOutputStream out;

	private ServerSocket serverSocket;

	
	public Server(int port) throws IOException {
	    serverSocket = new ServerSocket(port);
	    serverSocket.setSoTimeout(100000);
		
		System.out.println("Server created: "  + InetAddress.getLocalHost() + ":" + serverSocket.getLocalPort());
	}

	public void run() throws IOException {
			try {
				//use serverSocket.getLocalPort() to return which port server is using
				
				//This creates the server Socket object and begins listening for connections.
				//Once this is complete there will be a Socket object called server
				Socket server = serverSocket.accept();
				//Host = server.getLocalAddress;
				//System.out.println("Accepted connection from " + serverSocket.getRemoteSocketAddress());
				in = new DataInputStream(server.getInputStream());
				//use server.getLocalSocketAddress() to return socket server socket object is using
				out = new DataOutputStream(server.getOutputStream());
	        }catch(SocketTimeoutException s) {
            	System.out.println("Socket timed out");
            }
    
   }
   
   	


	public String getInput() throws IOException {
		return in.readUTF();
	} 
	public void send(String Data) {
		try {
			out.writeUTF(Data);
		}catch(IOException e) {
        		e.printStackTrace();
       		}	
	}
	public void close() {
		try {		
			serverSocket.close();
		}catch(IOException e) {
       	 		e.printStackTrace();
       		}	
	}
	


}