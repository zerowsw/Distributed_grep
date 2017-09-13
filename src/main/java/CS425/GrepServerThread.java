package CS425;

import java.io.*;
import java.net.Socket;


/**
*This class represents the action process of the server
*when it recieves a client request.
*/
public class GrepServerThread extends Thread {

	private GrepServer grepServer = null;
	private Socket clientSocket = null;

	public GrepServerThread(GrepServer grepServer, Socket clientSocket) {
		this.grepServer = grepServer;
		this.clientSocket = clientSocket;
	}

	public void run() {
		ObjectInputStream inputFromClient = null;
		OutputStream outputToClient = null;
		BufferedReader breader = null;
		InputStream in = null;
		InputStreamReader isreader = null;
		String command = null;

		try {
			inputFromClient = new ObjectInputStream(clientSocket.getInputStream());
			isreader = new InputStreamReader(inputFromClient);
			//TODO
			/** analyse the output from the client
            */
			Process pro = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", command});

			// output the information to the client
			in = pro.getInputStream();
			breader = new BufferedReader(new InputStreamReader(in));
			String info = breader.readLine();
			clientSocket.shutdownInput();
			outputToClient = clientSocket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {

				if (outputToClient != null) {
					outputToClient.close();
				}
				if (breader != null) {
					breader.close();
				}
				if (isreader != null) {
					isreader.close();
				}
				if (inputFromClient != null) {
					inputFromClient.close();
				}
				if (clientSocket != null)
					clientSocket.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}

	}