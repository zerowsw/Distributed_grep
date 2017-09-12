package CS425;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * this is the entrance of the program
 * we will get the Command inpput here and use it to start the grepClinentThread
 */
public class GrepClient {

    public static void main(String[] args) {

        try {
            start(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //start the client
    private static void start(String[] args) throws IOException {

        //we assume that the position of the config files which records the information of servers is fixed
        Properties pr = new Properties();

        FileInputStream  inpro = new FileInputStream("./config.properties");

        pr.load(inpro);

        inpro.close();

        String[] serverAddresses = pr.getProperty("serverAddress").split(",");
        String[] serverPorts = pr.getProperty("serverPorts").split(",");
        String[] fileAddress = pr.getProperty("fileAddress").split(",");

        //we assume that we provide the serverports and fileaddress for each servers
        ArrayList<ServerProperties> servers = new ArrayList<ServerProperties>();

        for(int i = 0; i < serverAddresses.length; i++) {

            servers.add(new ServerProperties(serverAddresses[i], serverPorts[i], fileAddress[i]));

        }

        /*
         *according to the numbers of the server, we create some client threads to do the grep
         * and use the arraylist for future management
         */

        ArrayList<GrepClientThread>  clientThreads = new ArrayList<GrepClientThread>(servers.size());

        /*
         * the Command class hasn't been implemented
         */
        Command command = new Command();

        for(int i = 0; i < servers.size(); i++) {

            GrepClientThread grepThread = new GrepClientThread(command, servers.get(i));
            grepThread.start();
            clientThreads.add(grepThread);

        }



    }


}
