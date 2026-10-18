package created;

import java.util.ArrayList;
import provided.*;


public class KeywordNode implements JotlinTree {

    private final JotlinToken token;

    private KeywordNode(JotlinToken token) {
        this.token = token;
    }

    public static KeywordNode parse(ArrayList<JotlinToken> tokens) {
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Syntax Error\nExpected a keyword.\n");
        }
        JotlinToken first = tokens.get(0);
        if (first.getTokenType() != TokenType.Keyword) {
            throw new IllegalArgumentException("Syntax Error\nExpected a keyword but found '"
                + first.getToken() + "'.\n" + first.getFileName() + ":" + first.getLineNumber());
        }
        tokens.remove(0);
        return new KeywordNode(first);
    }

    public String getValue() {
        return token.getToken();
    }

    public boolean isBuiltinType() {
        switch (token.getToken()) {
            case "Integer":
            case "Double":
            case "String":
            case "Bool":
            case "Boolean":
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean validate() throws Exception {
        return token != null && !token.getToken().isEmpty();
    }

    @Override
    public String convertToJava() {
        switch (token.getToken()) {
            case "Integer": return "int";
            case "Double":  return "double";
            case "Bool":
            case "Boolean": return "boolean";
            default:        return token.getToken();
        }
    }

    @Override
    public String convertToJotlin() {
        return (token != null )? token.getToken():"";
    }
}
