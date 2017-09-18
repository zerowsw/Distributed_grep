package CS425;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * this class take charge of the  unittest of our project
 * we will generate the log file  in advance and distribute them to servers.
 * Then we run the unittest. It will get the query results from the servers and
 * we will compare it to the  answers we get from using local grep oder.
 */
public class GrepUnitTest {

    public static void main(String[] args) {
        //we test some different patterns.
        GrepUnitTest test = new GrepUnitTest();

        test.testRegex();
        test.testServerFailure();
        System.out.println("normal pattern test :" +  test.testNormalPattern() + "\n"
                              + "regex test :" + test.testRegex() + "\n"
                              + "server failure :" + test.testServerFailure() + "\n");
    }

    /**
     * test normal pattern distributed grep
     * @return whether the test succeed or not
     */
    private  boolean testNormalPattern() {

        String[] normalPattern = {"grep", "-n", "abc"};

        try {
            new GrepClient("unitTest.properties").start(normalPattern);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //read the query outcomes from local file
        List<String> lines = getDistributedQueries();

        List<String> expectLines = new ArrayList<String>();
        try {
            Scanner sc = new Scanner(new FileReader("unit-test-outcome1.log"));
            sc.useDelimiter("\n");
            while (sc.hasNext()) {
                expectLines.add(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return lines.toString().equals(expectLines.toString());
    }

    /**
     * get the distributed queries from local files.
     * @return queries
     */
    private List<String> getDistributedQueries() {
        List<String> lines = new ArrayList<String>();
        try {
            Scanner sr;
            for (int i = 2; i < 7; i++) {
                sr = new Scanner(new FileReader("/home/shaowen2/testdata/vm0" + i + "-output.log"));
                sr.useDelimiter("\n");
                while (sr.hasNext()) {
                    lines.add(sr.nextLine());
                }
                sr.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return lines;
    }


    /**
     * test the regex pattern of grep
     * @return whether the regex grep succeed or not
     */
    private boolean testRegex() {
        String[] regexPattern = {"grep", "-n", "\'t[ae]st\'"};

        try {
            new GrepClient("unitTest.properties").start(regexPattern);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //read the query outcomes from local file
        List<String> lines = getDistributedQueries();

        List<String> expectLines = new ArrayList<String>();
        try {
            Scanner sc = new Scanner(new FileReader("unit-test-outcome2.log"));
            sc.useDelimiter("\n");
            while (sc.hasNext()) {
                expectLines.add(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return lines.toString().equals(expectLines.toString());
    }



    /**
     * test fault-tolerance of distributed grep
     * @return whether the grep work well or not when some servers down
     */
    private boolean testServerFailure() {

        //still we use the normal pattern to test.
        String[] normalPattern = {"grep", "-n", "xyz"};

        try {
            new GrepClient("unitTest.properties").start(normalPattern);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //read the query outcomes from local file
        List<String> lines = getDistributedQueries();

        List<String> expectLines = new ArrayList<String>();
        try {
            Scanner sc = new Scanner(new FileReader("unit-test-outcome3.log"));
            sc.useDelimiter("\n");
            while (sc.hasNext()) {
                expectLines.add(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return lines.toString().equals(expectLines.toString());
    }

    /**
     * test infrequent pattern
     * @return
     */
    private boolean testInfrequent() {
        String[] infrequent = {"grep", "-n", "*abc"};

        try {
            new GrepClient("unitTest.properties").start(infrequent);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> lines = getDistributedQueries();

        List<String> expectLines = new ArrayList<String>();
        try {
            Scanner sc = new Scanner(new FileReader("unit-test-outcome4.log"));
            sc.useDelimiter("\n");
            while (sc.hasNext()) {
                expectLines.add(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return lines.toString().equals(expectLines.toString());
    }

}