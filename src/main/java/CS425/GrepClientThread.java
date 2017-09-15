package CS425;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Hello world!
 *
 */
public class GrepClientThread extends Thread {

    private ArgsToServer argsToServer;
    private ServerProperties servers;


    public GrepClientThread(ArgsToServer argsToServer, ServerProperties servers) {

        this.argsToServer = argsToServer;

        this.servers = servers;

    }




    public void run() {
        //mainly deal with the socket program

        Socket socket;

        try {
            socket = new Socket(servers.getServerAddress(),Integer.parseInt(servers.getServerPort()));

        } catch (IOException e) {

            System.err.println("Cannot connect to the server: "+ servers.getServerAddress());
            return;
        }


        ObjectOutputStream toServer = null;
        try {
            toServer = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }


        BufferedReader toClient = null;
        try {
            toClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            toServer.writeObject(argsToServer);

        } catch (IOException e) {
            e.printStackTrace();
        }

        String line;

        try {
            while((line = toClient.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
