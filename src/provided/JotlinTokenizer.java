package provided;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class JotlinTokenizer {

    public static ArrayList<JotlinToken> tokenize(String fileName){
        File file = new File(fileName);
        try (Scanner reader = new Scanner(file)) {
            ArrayList<JotlinToken> tokens = new ArrayList<>();
            int lineNumber = 1;
            while(reader.hasNextLine()) {
                String line = reader.nextLine();
                ArrayList<Character> lineChars = new ArrayList<>();
                for(int i = 0; i < line.length(); i++) {
                    lineChars.add(line.charAt(i));
                }
                while(!lineChars.isEmpty()){
                    boolean beginning = true; //is start of line?
                    char first = lineChars.getFirst();
                    if(Character.isDigit(lineChars.getFirst()) || lineChars.getFirst() == '.') {
                        JotlinToken token = tokenize_numbers(lineChars,fileName,lineNumber);
                        tokens.add(token);
                    }
                    if (isSymbolStart(first)) {
                        JotlinToken token = tokenize_symbol(lineChars, fileName, lineNumber);
                        tokens.add(token);
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
                    }
                    if(Character.isUpperCase(lineChars.getFirst()) || Character.isLowerCase(lineChars.getFirst()) || lineChars.getFirst() == '\"'){
                        JotlinToken token = tokenizeStrings(lineChars, fileName, lineNumber);
                        tokens.add(token);
                    }
                    else {
                        throw new IllegalStateException(
                            "Unhandled character '" + first + "' at line number " + lineNumber);
                    }
                }
                tokens.add(tokenize_newline(fileName, lineNumber));
                lineNumber++;
 
            }

            return tokens;
        }
        catch (FileNotFoundException e) {
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

    private static boolean isSymbolStart(char c) {
        return c == '[' || c == ']' || c == ':' || c == ',' || c == '.';
    }

    public static JotlinToken tokenize_symbol(
            ArrayList<Character> line,
            String fileName,
            int lineNumber) {

        char curr = line.getFirst();

        if (curr == '[') {
            line.removeFirst();
            return new JotlinToken("[", TokenType.L_Bracket, fileName, lineNumber);
        }

        if (curr == ']') {
            line.removeFirst();
            return new JotlinToken("]", TokenType.R_Bracket, fileName, lineNumber);
        }

        if (curr == ':') {
            line.removeFirst();

            if (!line.isEmpty() && line.getFirst() == ':') {
                line.removeFirst();
                return new JotlinToken("::", TokenType.FC_Header, fileName, lineNumber);
            }
            return new JotlinToken(":", TokenType.Colon, fileName, lineNumber);
        }

        if (curr == ',') {
            line.removeFirst();
            return new JotlinToken(",", TokenType.Comma, fileName, lineNumber);
        }

        if (curr == '.') {
            line.removeFirst();
            return new JotlinToken(".", TokenType.Dot, fileName, lineNumber);
        }

        return null;
    }
}
