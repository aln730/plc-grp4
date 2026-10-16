package created;

import java.util.ArrayList;

import provided.*;


public class NewLineNode implements JotlinTree {

    private final int count;

    private NewLineNode(int count) {
        this.count = count;
    }

    public static NewLineNode parse(ArrayList<JotlinToken> tokens) {
        if (!startsHere(tokens)) {
            throw new IllegalArgumentException("Syntax Error\nExpected a newline.\n"
                + (tokens.isEmpty() ? "" : tokens.get(0).getFileName() + ":" + tokens.get(0).getLineNumber()));
        }
        int count = 0;
        while (startsHere(tokens)) {
            tokens.remove(0);
            count++;
        }
        return new NewLineNode(count);
    }

    public static boolean startsHere(ArrayList<JotlinToken> tokens) {
        return !tokens.isEmpty() && tokens.get(0).getTokenType() == TokenType.LineEnd;
    }

    public int getCount() {
        return count;
    }

    @Override
    public boolean validate() throws Exception {
        return count >= 1;
    }

    @Override
    public String convertToJava() {
        return "\n";
    }

    @Override
    public String convertToJotlin() {
        return "\n";
    }
}
