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
        return token.getToken();
    }

    public String convertToJava() {
        return token.getToken();
    }
    
    @Override
    public boolean validate() throws Exception {
        if (token == null || token.getToken() == null) {
            return false;
        }
        if (token.getTokenType() != TokenType.MathOp) {
            return false;
        }
        String op = token.getToken();
        return op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/") || op.equals("%");
    }
}
