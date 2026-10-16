package created;
import java.util.ArrayList;

import provided.*;



public class RelOpNode implements JotlinTree{
    private JotlinToken token;

    public RelOpNode(JotlinToken token) {
        this.token = token;
    }
    public static RelOpNode parse(ArrayList<JotlinToken> tokens) {
        if(tokens.size() < 1) {
            throw new IllegalArgumentException("Not enough tokens for rel operation");
        }
        JotlinToken first = tokens.getFirst();
        if(first.getTokenType() == TokenType.RelOp) {
            tokens.removeFirst();
            return new RelOpNode(first);
        }
        throw new IllegalArgumentException("Expected a RelOp token");
    }

    public String convertToJotlin() {
        return token.getToken();
    }

    public String convertToJava() {
        return token.getToken();
    }
    @Override
    public boolean validate() throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validate'");
    }

}