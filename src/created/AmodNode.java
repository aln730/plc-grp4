package created;
import java.util.ArrayList;

import provided.*;

public class AmodNode implements JotlinTree {

    private final JotlinToken token;

    private AmodNode(JotlinToken token) {
        this.token = token;
    }

    public static AmodNode parse(ArrayList<JotlinToken> tokens) {
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Syntax Error\nExpected an access modifier.\n");
        }
        JotlinToken first = tokens.get(0);
        String t = first.getToken();
        if (first.getTokenType() != TokenType.Keyword
                || !(t.equals("Open") || t.equals("Closed") || t.equals("Shared"))) {
            throw new IllegalArgumentException("Syntax Error\nExpected Open, Closed, or Shared but found '"
                + t + "'.\n" + first.getFileName() + ":" + first.getLineNumber());
        }
        tokens.remove(0);
        return new AmodNode(first);
    }

    @Override
    public boolean validate() throws Exception {
        return true;
    }

    @Override
    public String convertToJava() {
        switch (token.getToken()) {
            case "Open":   return "public";
            case "Closed": return "private";
            default:       return "protected"; // Shared
        }
    }

    @Override
    public String convertToJotlin() {
        return token.getToken();
    }
}
