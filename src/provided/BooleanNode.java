package provided;
import java.util.ArrayList;


public class BooleanNode {
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

    public String convertToJott() {
        return token.getToken();
    }

    public String convertToJava() {
        return token.getToken();
    }

}
