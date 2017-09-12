package CS425;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;


/**
*This class represents the action process of the server
*when it recieves a client request.
*/
public class GrepServerThread extends Thread{

  public GrepServerThread(GrepServer grepServer, Socket clientSocket)
  {
  	this.grepServer = grepServer;
  	this.clientSocket = clientSocket;
  }
  public void run(){
  	InputStream inputFromClient = null;
  	OutputStream outputToClient = null;
  	BufferedReader breader = null;
  	InputStreamReader isreader = null;

  	try {
  		inputFromClient = new InputStream(clientSocket.getInputStream());
  		isreader = new InputStreamReader(inputFromClient);
      
      //TODO
      String command = getGrepCommand(clientInfo,logFileName);
      Process pro = Runtime.getRuntiem().exec(new String[] {"/bin/sh","-c",command});
      pro.waitFor();
  		
    // output the information to the client
      in = pro.getInputStream();
      breader = new BufferedReader(new InputStreamReader(in));
  		String info = breader.readLine();
  		clientSocket.shutdownInput();
  		outputToClient = clientSocket.getOutputStream();
  	}catch (IOException e)
  		e.printStackTrace();
  	}finally{
  		try{

  			if(outputToClient != null)
  			{
  				outputToClient.close();
  			}
  			if(breader != null)
  			{
  				breader.close();
  			}
  			if(isreader != null)
  			{
  				isreader.close();
  			}
  			if(inputFromClient != null)
  			{
  				inputFromClient.close();
  			}
  			if(clientSocket != null)
  			{
                clientSocket.close();
  			}catch(IOException e){
  				e.printStackTrace();
  			}
  		}
  	}

  }
}