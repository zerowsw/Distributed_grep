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

	private int serverId;

	public GrepServer(int serverId, int port)
		try{
			//create a server socket with binding port
			ServerSocket serverSocket = new ServerSocket(port);
			Socket clientSocket = null;
			System.out.println("The server is starting, please wait....")
			//keep listening to the client's connection
			while(true){
				clientSocket = serverSocket.accept();
				ServerThread serverThread = new ServerThread(clientSocket);
				serverThread.start();
				InetAddress clientAddress = clientSocket.getInetAddress;
				System.out.println("The server is connecting to client"+clientAddress);
			}
		}catch (IOException e){
			e.printStackTrace();
		}

	// create server with serverId and port
	public static void main(String[] args){
		GrepServer grepServer = null;
		int defaultServerId = 1;
		int defaultPort = 1234;
		if(args.length == 0)
		{
			System.out.println("Please enter the serverId and port!")
		}else if(args.length == 2)
		{
     
             	serverId = Integer.parseInt(args[0]);
                port = Integer.parseInt(args[1]);
        }else{
        	serverId = defaultServerId;
        	port = defaultPort;
        }
        GrepServer = new GrepServer(serverId, port);
	}
}
