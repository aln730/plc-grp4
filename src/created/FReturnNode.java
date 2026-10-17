package created;
import java.util.ArrayList;

import provided.*;

public class FReturnNode implements JotlinTree{

    private JotlinToken keyword;

    public FReturnNode(JotlinToken keyword) {
        this.keyword = keyword;
    }

    public static FReturnNode parse(ArrayList<JotlinToken> tokens) {
        if(tokens.size() < 1) {
            throw new IllegalArgumentException("Not enough tokens for return");
        }
        JotlinToken first = tokens.getFirst();
        if(first.getTokenType() == TokenType.L_Arrow) {
            tokens.removeFirst();
            if(tokens.size() > 0) {
                first = tokens.removeFirst();
                if (first.getTokenType() == TokenType.Keyword) {
                    return new FReturnNode(first);
                }
            }
            throw new IllegalArgumentException("Left arrow should be followed by keyword");
        }
        else if(first.getTokenType() == TokenType.String && first.getToken().equals("")) {
            return new FReturnNode(first);
        }
        throw new IllegalArgumentException("Return node must follow the format of <- keyword or an empty string");
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
