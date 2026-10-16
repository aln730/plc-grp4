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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validate'");
    }

    @Override
    public String convertToJava() {
        return "return " + keyword.getToken();
    }

    @Override
    public String convertToJotlin() {
        return "<-" + keyword.getToken();
    }

}
