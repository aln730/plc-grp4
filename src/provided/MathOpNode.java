package provided;
import java.util.ArrayList;


public class MathOpNode {
    private JotlinToken token;

    public MathOpNode(JotlinToken token) {
        this.token = token;
    }
    public static MathOpNode parse(ArrayList<JotlinToken> tokens) {
        JotlinToken first = tokens.getFirst();
        if(first.getTokenType() == TokenType.MathOp) {
            tokens.removeFirst();
            return new MathOpNode(first);
        }
        throw new IllegalArgumentException("Expected MathOp token");
    }

    public String convertToJott() {
        return token.getToken();
    }

    public String convertToJava() {
        return token.getToken();
    }

}
