package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public abstract class IOHandler {
	private DataOutputStream outputStream;
	private DataInputStream inputStream;
	private Socket socket;



	public void send(String data) {
		try {
			outputStream.writeUTF(data);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			socket.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
