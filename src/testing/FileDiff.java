package testing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileDiff {

    /**
     * Compare two text files line-by-line and print differences.
     * @param file1 Path to first file
     * @param file2 Path to second file
     */
    public static void compareFiles(String file1, String file2) {
        try (
                BufferedReader br1 = new BufferedReader(new FileReader(file1));
                BufferedReader br2 = new BufferedReader(new FileReader(file2))
        ) {
            String line1, line2;
            int lineNum = 1;
            boolean differencesFound = false;

            while (true) {
                line1 = br1.readLine();
                line2 = br2.readLine();

                // End of both files
                if (line1 == null && line2 == null) {
                    break;
                }

                // If lines differ (including one being null)
                if (line1 == null || !line1.equals(line2)) {
                    differencesFound = true;
                    System.out.printf("Difference at line %d:%n", lineNum);
                    System.out.printf("File1: %s%n", line1);
                    System.out.printf("File2: %s%n%n", line2);
                }
                lineNum++;
            }

            if (!differencesFound) {
                System.out.println("Files are identical.");
            }

        } catch (IOException e) {
            System.err.println("Error reading files: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java FileDiff <file1> <file2>");
            return;
        }

        compareFiles(args[0], args[1]);
    }
}