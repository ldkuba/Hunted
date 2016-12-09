package network;

// File Name client.java
import java.net.*;
import java.io.*;

public class ClientGame extends IOHandler{

	public ClientGame(){

	}

	
  	public int connect(String serverName, int port) {
	

	try {
		clientSocket = new Socket(serverName, port);

		//now connected to server host (serverName) on port (port)
		//use client.getRemoteSocketAddress() to print connected address
		//use client.getLocalSocketAddress() to return socket used for outgoing socket  

		//OutputStream toServer = clientSocket.getOutputStream();
		//DataOutputStream out = new DataOutputStream(toServer);

		//TRY THIS: if does not work use above
		out = new DataOutputStream(clientSocket.getOutputStream());

		//now you can send data to server with out.writeUTF("") method	
	
		//InputStream inFromServer = clientSocket.getInputStream();
        	//DataInputStream in = new DataInputStream(inFromServer);

		in = new DataInputStream(clientSocket.getInputStream());

		//use in.readUTF() to return a string of data from server

		//When closing connection
		
         
        }catch(IOException e) {
        		e.printStackTrace();
        		
        		return 1;
        }
	
		return 0;
		
		
	}


	public String getInput() throws IOException {
		
		return in.readUTF();
			
	} 

}
