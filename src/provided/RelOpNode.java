package provided;
import java.util.ArrayList;


public class RelOpNode {
    private JotlinToken token;

    public RelOpNode(JotlinToken token) {
        this.token = token;
    }
    public static RelOpNode parse(ArrayList<JotlinToken> tokens) {
        JotlinToken first = tokens.getFirst();
        if(first.getTokenType() == TokenType.RelOp) {
            tokens.removeFirst();
            return new RelOpNode(first);
        }
        throw new IllegalArgumentException("Expected a RelOp token");
    }

    public String convertToJott() {
        return token.getToken();
    }

    public String convertToJava() {
        return token.getToken();
    }

}