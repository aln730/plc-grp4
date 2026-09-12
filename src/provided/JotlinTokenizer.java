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
}
