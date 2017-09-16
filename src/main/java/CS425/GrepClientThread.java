package CS425;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

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
            socket.setSoTimeout(3000);


        } catch (IOException e) {
           // e.printStackTrace();

            System.err.println("Cannot connect to the server: " + servers.getServerAddress()
                                                 + "  at port:" + servers.getServerPort());
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
        int count = 0;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream pr = new PrintStream(out);


        try {
            while((line = toClient.readLine()) != null) {
                System.out.println("<" + servers.getServerAddress() + "> :" + line);
                count++;
                //at the same time, we store them in local file
                pr.println("<" + servers.getServerAddress() + "> :" + line);
            }
            pr.flush();
            System.out.println("query count: " + count);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //sava in local files
        FileWriter fw = null;
        File file = new File("/home/shaowen2/testdata/" + "vm"+servers.getServerAddress().substring(15, 17)+"-ouput.log");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(out.toString());
            bw.write("\n" + "Query Count:" + count + "|| Time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\n");
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
