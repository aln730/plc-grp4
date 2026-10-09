package provided;
import java.util.ArrayList;


public class StringNode {
    private JotlinToken token;

    public StringNode(JotlinToken token) {
        this.token = token;
    }
    public static StringNode parse(ArrayList<JotlinToken> tokens) {
        JotlinToken first = tokens.getFirst();
        if(first.getTokenType() == TokenType.String) {
            return new StringNode(first);
        }
        throw new IllegalArgumentException("Expected string token");
    }

    public JotlinToken getToken() {return this.token;}



    public String convertToJott() {
        return '"' + token.getToken() + '"';
    }

    public String convertToJava() {
        return '"' + token.getToken() + '"';
    }

}