package provided;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class JotlinTokenizer {

    public static ArrayList<JotlinToken> tokenize(String fileName){
        File file = new File(fileName);
        try (Scanner reader = new Scanner(file)) {
            ArrayList<JotlinToken> tokens = new ArrayList<>();
            int lineNumber = 1;
            while(reader.hasNextLine()) {
                String line = reader.nextLine();
                ArrayList<Character> lineChars = new ArrayList<>();
                for(int i = 0; i<line.length(); i++) {
                    lineChars.add(line.charAt(i));
                }
                // is this while loop even going forward in the line?
                while(lineChars.size() > 0) {
                    boolean beginning = true; //is start of line?
                    if(Character.isDigit(lineChars.getFirst()) || lineChars.getFirst() == '.') {
                        JotlinToken token = tokenize_numbers(lineChars,fileName,lineNumber);
                        tokens.add(token);
                        beginning = false;
                    }
                    else if (lineChars.getFirst() == ' ') {
                        //tab can be made up 4 spaces but only at beginning of line
                        if (beginning) {
                            if (check_tab(lineChars)) {
                                tokens.add(tokenize_tab(fileName,lineNumber, true));
                                beginning = false;
                            }
                        }
                        lineChars.removeFirst();
                    }
                    else if (lineChars.getFirst() == '#') {
                        continue; //comments are thrown away
                    }
                    else if (lineChars.getFirst() == '\t') {
                        tokens.add(tokenize_tab(fileName,lineNumber, false));
                        lineChars.removeFirst();
                        beginning = false;
                    }   
                }
                tokens.add(tokenize_newline(fileName, lineNumber));
                lineNumber++;
                
            }
            return tokens;
        }
        catch(FileNotFoundException e) {
            System.out.println("Provided filename not found: " + fileName);
            return null;
        }
    }

    public static JotlinToken tokenize_numbers(ArrayList<Character> line,String filename, int lineNumber) {
        //Build the token string
        String token = "";
        //Check starts with a dot -> double, requires another integer after the dot
        if(line.getFirst() == '.') {
            token += line.removeFirst();
            if(line.size() > 0 && Character.isDigit(line.getFirst())) {
                while(line.size() > 0 && Character.isDigit(line.getFirst())) {
                    token += line.removeFirst();
                }
                return new JotlinToken(token,TokenType.Double,filename,lineNumber);
            }
            //No digit found -> error state
            else {
                throw new IllegalArgumentException("Double token must have at least one digit after the dot");
            }
        }
        //Doesn't start with a dot -> could be an integer or a double, no error state from here
        else {
            //Build integer
            while(line.size() > 0 && Character.isDigit(line.getFirst())) {
                token += line.removeFirst();
            }
            //Dot found after integer -> double
            if(line.size() > 0 && line.getFirst() == '.') {
                token += line.removeFirst();
                //Add while more digits found, otherwise return the double
                while(line.size() > 0 && Character.isDigit(line.getFirst())) {
                    token += line.removeFirst();
                }
                return new JotlinToken(token,TokenType.Double,filename,lineNumber);
            }
            //No dot, return an integer
            else {
                return new JotlinToken(token,TokenType.Integer,filename,lineNumber);
            }
        }
    }

    public static JotlinToken tokenize_newline(String fileName, int lineNumber) {
        return new JotlinToken("\n", TokenType.LineEnd, fileName, lineNumber);
    }

    public static boolean check_tab(ArrayList<Character> line) {
        int spaceCount = 0;
        while (true) {
            if (line.getFirst() == ' ') {
                spaceCount++;
                line.removeFirst();
                if (spaceCount == 4) {
                    return true;
                }
            }
            else {
                return false;
            }
        }
    }

    public static JotlinToken tokenize_tab(String fileName, int lineNumber, boolean useSpaces) {
        return new JotlinToken("\t", TokenType.Indent, fileName, lineNumber);
    }
}
