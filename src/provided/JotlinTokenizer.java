package provided;
import java.util.ArrayList;
import java.util.Scanner;

import testing.FileDiff;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class JotlinTokenizer {

    /**
     * Tokenize function loops through a file containing the Jotlin code
     * reads each line and creates tokens based on the project DFA
     * @param filename the file to read in
     * @return returns a list of JotlinTokens
     */
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
                while(lineChars.size() > 0) {
                    if(Character.isDigit(lineChars.getFirst()) || lineChars.getFirst() == '.') {
                        JotlinToken token = tokenize_numbers(lineChars,fileName,lineNumber);
                        tokens.add(token);
                    }
                    else if (lineChars.getFirst() == ' ') {
                        lineChars.removeFirst();
                    }

                }
                lineNumber++;
            }
            return tokens;
        }
        catch(FileNotFoundException e) {
            System.out.println("Provided filename not found: " + fileName);
            return null;
        }
        catch(IllegalArgumentException e) {
            System.out.println(e);
            return null;
        }
    }

    
    /**
     * tokenize_numbers is called if a token starts with an integer or a ., determines if the token
     * is an integer or a double, and returns a Jotlin token
     * @param line the current line being read
     * @param filename the file being read from
     * @param lineNumber the current line the token was read from
     * @return returns a JotlinToken specifying type, file, and lineNumber, or an error if a double
     * doesn't follow the correct format
     */
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
                throw new IllegalArgumentException("Double token must have at least one digit");
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
}
