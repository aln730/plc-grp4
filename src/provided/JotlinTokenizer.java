package provided;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class JotlinTokenizer {

    public static ArrayList<JotlinToken> tokenize(String fileName) {
        File file = new File(fileName);

        try (Scanner reader = new Scanner(file)) {
            ArrayList<JotlinToken> tokens = new ArrayList<>();
            int lineNumber = 1;

            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                ArrayList<Character> lineChars = new ArrayList<>();

                for (int i = 0; i < line.length(); i++) {
                    lineChars.add(line.charAt(i));
                }

                while (lineChars.size() > 0) {
                    char first = lineChars.getFirst();

                    if (isSymbolStart(first)) {
                        JotlinToken token = tokenize_symbol(lineChars, fileName, lineNumber);
                        tokens.add(token);
                    }
                    else if (first == ' ') {
                        lineChars.removeFirst();
                    }
                    else {
                        throw new IllegalStateException(
                            "Unhandled character '" + first + "' at line number " + lineNumber);
                    }
                }
                lineNumber++;
            }

            return tokens;
        }
        catch (FileNotFoundException e) {
            System.out.println("Provided filename not found: " + fileName);
            return null;
        }
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