package GP;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Utils {
    private static final Random GLOBAL_RANDOM = new Random();
    public static final TreeGenerator treeGenerator = new TreeGenerator();

    // generate a random seed
    public static long generateRandomSeed() {
        return System.currentTimeMillis();
    }

    public static int squash(double x) {
        return (int) (1.0 / (1.0 + Math.exp(-x))); // Sigmoid
        // return (Math.tanh(x) + 1.0) / 2.0; // Tanh-based
        // return (Math.atan(x) / Math.PI) + 0.5; // Arctangent-based
    }

    public static ArrayList<DataPoint> readFile(String filePath) {
        /*
         * CSV File format:
         * Open,High,Low,Close,Adj Close,Output
         * -1.104550546,-1.103639854,-1.103311839,-1.100806056,-1.460681595,0
         * ...
         */

        // Check if file exists
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("File not found.");
            System.exit(0);
        }

        // Read the file
        ArrayList<DataPoint> dataPoints = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(file);
            // Skip the header line
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
            while (scanner.hasNextLine()) {
                String[] values = scanner.nextLine().split(",");
                double open = Double.parseDouble(values[0]);
                double high = Double.parseDouble(values[1]);
                double low = Double.parseDouble(values[2]);
                double close = Double.parseDouble(values[3]);
                double adjClose = Double.parseDouble(values[4]);
                int output = Integer.parseInt(values[5]);
                dataPoints.add(new DataPoint(open, high, low, close, adjClose, output));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return dataPoints;
    }

    public static TerminalNode[] createTerminals() {
        // Create Terminals
        // TerminalNode[] terminals = new TerminalNode[5];
        // terminals[0] = new TerminalNode("Open", 0);
        // terminals[1] = new TerminalNode("High", 1);
        // terminals[2] = new TerminalNode("Low", 2);
        // terminals[3] = new TerminalNode("Close", 3);
        // terminals[4] = new TerminalNode("Adj Close", 4);

        TerminalNode[] terminals = new TerminalNode[4];
        terminals[0] = new TerminalNode("Open", 0);
        terminals[1] = new TerminalNode("High", 1);
        terminals[2] = new TerminalNode("Low", 2);
        terminals[3] = new TerminalNode("Close", 3);
        return terminals;
    }

    public static void initGlobalRandom(long seed) {
        GLOBAL_RANDOM.setSeed(seed);
    }

    public static Random getGlobalRandom() {
        return GLOBAL_RANDOM;
    }

}
