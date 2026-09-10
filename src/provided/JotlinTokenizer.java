package provided;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class JotlinTokenizer {

    public static ArrayList<JotlinToken> tokenize(String fileName){
        File f = new File(fileName);
        try (Scanner reader = new Scanner(f)) {
            ArrayList<JotlinToken> tokens = new ArrayList<>();
            while(reader.hasNextLine()) {
                String[] lines = reader.nextLine().split(" ");
                for(String line:lines) {

                }
            }
            return tokens;
        }
        catch(FileNotFoundException e) {
            System.out.println("Provided filename not found: " + fileName);
            return null;
        }
    }
}
