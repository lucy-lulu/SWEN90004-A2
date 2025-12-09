import java.io.*;
public class FileUtil {

    private static FileWriter writer;
    //create the output CSV file
    public static void createCSV(String name, String attribute) throws IOException {

        writer = new FileWriter(name);
        writer.append(attribute + " \n");
    }

    //update the output CSV file by four attribute
    public static void updateCSV(long tick, double muscle, double anabolic, double catabolic) throws IOException {
        writer.append(tick + "," + muscle + "," + anabolic + "," + catabolic + " \n");
    }

    //close the output CSV file
    public static void closeCSV() throws IOException {
        writer.close();
        writer = null;
    }
}
