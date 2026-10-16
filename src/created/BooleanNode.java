package created;
import java.util.ArrayList;

import provided.*;


public class BooleanNode implements JotlinTree{
    private JotlinToken token;

    public BooleanNode(JotlinToken token) {
        this.token = token;
    }
    
    public static BooleanNode parse(ArrayList<JotlinToken> tokens) {
        JotlinToken first = tokens.getFirst();
        if(first.getTokenType() == TokenType.Keyword) {
            if(first.getToken().equals("True") || first.getToken().equals("False")) {
                tokens.removeFirst();
                return new BooleanNode(first);
            }
        }
        throw new IllegalArgumentException("Expected boolean value True or False");  
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
