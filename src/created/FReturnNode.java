package created;
import java.util.ArrayList;

import provided.*;

public class FReturnNode implements JotlinTree{

    private KeywordNode keyword;

    public FReturnNode(KeywordNode keyword) {
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
        KeywordNode keyword = KeywordNode.parse(tokens);
        return new FReturnNode(keyword);
    }

    @Override
    public boolean validate() throws Exception {
        if(keyword != null) {
            if(!keyword.isBuiltinType()) {
                //May need to check if keyword refers to created class
                return false;
            }
            return keyword.validate();
        }
        return true;
    }

    @Override
    public String convertToJava() {
        return "return " + (keyword != null ?  keyword.convertToJava():"");
    }

    @Override
    public String convertToJotlin() {
        return "<-" + (keyword != null ?  keyword.convertToJotlin():"");
    }

}
