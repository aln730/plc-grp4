package created;
import java.util.ArrayList;

import provided.*;


public class MathOpNode implements JotlinTree{
    private JotlinToken token;

    public MathOpNode(JotlinToken token) {
        this.token = token;
    }
    public static MathOpNode parse(ArrayList<JotlinToken> tokens) {
        if(tokens.size() < 1) {
            throw new IllegalArgumentException("Not enough tokens for math operation");
        }
        JotlinToken first = tokens.getFirst();
        if(first.getTokenType() == TokenType.MathOp) {
            tokens.removeFirst();
            return new MathOpNode(first);
        }
        throw new IllegalArgumentException("Expected MathOp token");
    }

    public String convertToJotlin() {
        return token != null ?  token.getToken():"";
    }

    public String convertToJava() {
        return token != null ?  token.getToken():"";
    }
    @Override
    public boolean validate() throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validate'");
    }

}
