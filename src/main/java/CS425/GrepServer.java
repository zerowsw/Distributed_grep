package CS425;


import java.net.ServerSocket;
import java.net.Socket;
import java.net. InetAddress;
import java.io.IOException;


/**
*This class defines the GrepServer which listens to the clients
*and build thread for each connecting client.
*/
public class GrepServer {

	public GrepServer(String serverAddress, String serverPort) {
		try {
			//create a server socket with binding port
			ServerSocket serverSocket = new ServerSocket(Integer.parseInt(serverPort));
			Socket clientSocket = null;
			System.out.println("The server with address" + serverAddress +" is starting, please wait....");
			//keep listening to the client's connection
			while (true) {
				clientSocket = serverSocket.accept();
				GrepServerThread serverThread = new GrepServerThread(this,clientSocket);
				serverThread.start();
				InetAddress clientAddress = clientSocket.getInetAddress();
				System.out.println("The server is connecting to client" + clientAddress);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}