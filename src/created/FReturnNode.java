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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validate'");
    }

    @Override
    public String convertToJava() {
        return "return " + (keyword != null ?  keyword.getToken():"");
    }

    @Override
    public String convertToJotlin() {
        return "<-" + (keyword != null ?  keyword.getToken():"");
    }

}
