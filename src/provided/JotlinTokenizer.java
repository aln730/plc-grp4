package provided;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class JotlinTokenizer {

    public static ArrayList<JotlinToken> tokenize(String fileName){
        File f = new File(fileName);
        try (Scanner reader = new Scanner(f)) {
            ArrayList<JotlinToken> tokens = new ArrayList<>();
            int lineNumber = 1;
            while(reader.hasNextLine()) {
                String line = reader.nextLine();
                ArrayList<Character> lineChars = new ArrayList<>();
                for(int i = 0; i <line.length(); i++){
                    lineChars.add(line.charAt(i));
                }
                while(!lineChars.isEmpty()){
                    if(Character.isUpperCase(lineChars.getFirst()) || Character.isLowerCase(lineChars.getFirst()) || lineChars.getFirst() == '\"'){
                        JotlinToken token = tokenizeStrings(lineChars, fileName, lineNumber);
                        tokens.add(token);
                    } else if (lineChars.getFirst() == ' '){
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

    
    private static JotlinToken tokenizeStrings(ArrayList<Character> line, String filename, int lineNum){
        ArrayList<Character> tokenList = new ArrayList<>();
        Boolean isString = false;
        Boolean isKeyword = false;
        Boolean isId = false;
        //tokenizeStrings
        //check for quotes, at first quote move into loop taking in digits and chars
        //at second quote move into accepting state and create string token
        if(line.getFirst() == '\"'){
            isString = true;
            tokenList.add(line.getFirst());
            line.removeFirst();
            while(!line.isEmpty() || line.getFirst() != '\"'){
                tokenList.add(line.getFirst());
                line.removeFirst();
            }
            if(line.getFirst() == '\"'){
                tokenList.add(line.getFirst());
                line.removeFirst();
            } 
        //tokenizeKeywords
        //check for uppercase letter (A-Z), at uppercase letter move into loop taking in digits and chars
        //move into accepting state and create keyword token
        } else if(Character.isUpperCase(line.getFirst())){
            isKeyword = true;
            tokenList.add(line.getFirst());
            line.removeFirst();
            while(Character.isLetter(line.getFirst()) || Character.isDigit(line.getFirst())){
                tokenList.add(line.getFirst());
                line.removeFirst();
            }
        //tokenizeIds
        //check for lowercase letter (a-z), at lowercase letter move into loop taking in digits and chars
        //move into accepting state and create id token
        } else if(Character.isLowerCase(line.getFirst())){
            isId = true;
            tokenList.add(line.getFirst());
            line.removeFirst();
            while(Character.isLetter(line.getFirst()) || Character.isDigit(line.getFirst())){
                tokenList.add(line.getFirst());
                line.removeFirst();
            }
        }
        
        //take array of characters and turn into a string
        String sToken = String.valueOf(tokenList);
        JotlinToken token = null;
        //check to see if token is a String, Keyword, or Id
        if(isString){
            token = new JotlinToken(sToken, TokenType.String, filename, lineNum);
        } else if(isKeyword){
            token = new JotlinToken(sToken, TokenType.Keyword, filename, lineNum);
        } else if(isId){
            token = new JotlinToken(sToken, TokenType.Id, filename, lineNum);
        }
        
        return token;
    }

}
