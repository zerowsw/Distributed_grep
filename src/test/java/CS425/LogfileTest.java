package CS425;

import java.io.*;
import java.util.Random;
import java.util.Date;
import java.text.SimpleDateFormat;
/**
 * this is the class use to generate the logfile for testing
 */

public class LogfileTest {

    public static void logfileGenerator(int testnum, int mnum) throws IOException {

        // here we create logfile with the name include the input test number
        try {
            String path = "UnitTest" + testnum + ".log";
            File file = new File(path);

            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String str = "abcdefghijklmnopqrstuvwxyz*& //'=.0123456789";
            Random random = new Random();
            StringBuffer buf1 = new StringBuffer();
            StringBuffer buf2 = new StringBuffer();

            String[] messages = new String[mnum];
            String messages_content1;
            String messages_content2;
            String[] messages_ip = new String[3];
            String[] messages_method = new String[2];
            String messages_time = ft.format(new Date());

            messages_ip[0] = "127.0.0.1 ";
            messages_ip[1] = "159.226.1.1 ";
            messages_ip[2] = "192.168.1.1 ";
            messages_method[0] = " [GET] ";
            messages_method[1] = " [POST] ";


            // we generate all message in the logfile with the input number
            for (int j = 0; j < mnum; ++j) {
                // we randomly generate the content of message
                for (int i = 0; i < 10; ++i) {
                    buf1.append(str.charAt(random.nextInt(str.length())));
                }

                for (int h = 0; h < 20; ++h) {
                    buf2.append(str.charAt(random.nextInt(str.length())));
                }
                messages_content1= buf1.toString();
                messages_content2= buf2.toString();
                messages[j] ="message"+(100000+j)+" : "+messages_ip[j % 3] + messages_content1
                        + (messages_time) + messages_method[j % 2]
                        + messages_content2;
                bw.write(messages[j] + "\n");
                buf1.delete(0,10);
                buf2.delete(0,30);
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {

        try {
            logfileGenerator(6, 100000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
