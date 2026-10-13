package provided;
import java.util.ArrayList;

public class OtherwiseNode implements JotlinTree {
    private final BodyNode body;

    private OtherwiseNode(BodyNode body) {
        this.body = body;
    }

    public static OtherwiseNode parse(ArrayList<JotlinToken> tokens) {
        if (tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.Indent) {
            throw new IllegalArgumentException("Expected indentation before Otherwise");
        }
        while (!tokens.isEmpty() && tokens.get(0).getTokenType() == TokenType.Indent) {
            tokens.remove(0);
        }
        if (tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.Keyword || !tokens.get(0).getToken().equals("Otherwise")) {
            throw new IllegalArgumentException("Expected Otherwise keyword");
        }
        tokens.remove(0);
        if (tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.LineEnd) {
            throw new IllegalArgumentException("Expected newline after Otherwise");
        }
        while (!tokens.isEmpty() && tokens.get(0).getTokenType() == TokenType.LineEnd) {
            tokens.remove(0);
        }
        BodyNode body = BodyNode.parse(tokens);
        return new OtherwiseNode(body);
    }

    @Override
    public boolean validate() throws Exception {
        return body.validate();
    }

    @Override
    public String convertToJava() {
        return "else " + body.convertToJava();
    }

    @Override
    public String convertToJotlin() {
        return "Otherwise\n" + body.convertToJotlin();
    }
}