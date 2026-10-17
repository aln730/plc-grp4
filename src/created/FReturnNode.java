package created;
import java.util.ArrayList;

import provided.*;

public class FReturnNode implements JotlinTree{

    private JotlinToken keyword;

    public FReturnNode(JotlinToken keyword) {
        this.keyword = keyword;
    }

    public static FReturnNode parse(ArrayList<JotlinToken> tokens) {
        // <f_return> -> <- <keyword> | epsilon
        if (tokens.isEmpty() || tokens.getFirst().getTokenType() != TokenType.L_Arrow) {
            return new FReturnNode(null);   // no return type
        }
        tokens.removeFirst();
        if (tokens.isEmpty() || tokens.getFirst().getTokenType() != TokenType.Keyword) {
            throw new IllegalArgumentException("Left arrow should be followed by keyword");
        }
        return new FReturnNode(tokens.removeFirst());
    }

    @Override
    public boolean validate() throws Exception {
        if (keyword == null || keyword.getToken() == null) {
            return false;
        }
        if (keyword.getTokenType() == TokenType.Keyword) {
            return !keyword.getToken().isEmpty();
        }
        return keyword.getTokenType() == TokenType.String && keyword.getToken().isEmpty();
    }

    @Override
    public String convertToJava() {
        if (keyword == null || keyword.getTokenType() == TokenType.String) {
            return "";
        }
        return keyword.getToken();
    }

    @Override
    public String convertToJotlin() {
        if (keyword == null || keyword.getTokenType() == TokenType.String) {
            return "";
        }
        return "<- " + keyword.getToken();
    }

}
