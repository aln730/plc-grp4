package created;
import java.util.ArrayList;
import provided.*;


public class OtherwiseNode implements JotlinTree {
    private final int depth;
    private final BodyNode body;
    // private final NewLineNode newLine;

    private OtherwiseNode(int depth, BodyNode body) {
        this.depth = depth;
        this.body = body;
        // this.newLine = newLine;
    }

    public static OtherwiseNode parse(ArrayList<JotlinToken> tokens) {
        if (tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.Indent) {
            throw new IllegalArgumentException("Expected indentation before Otherwise");
        }
        int depth = 0;
        while (!tokens.isEmpty() && tokens.get(0).getTokenType() == TokenType.Indent) {
            tokens.remove(0);
            depth++;
        }
        if (tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.Keyword || !tokens.get(0).getToken().equals("Otherwise")) {
            throw new IllegalArgumentException("Expected Otherwise keyword");
        }
        tokens.remove(0);
        if (tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.LineEnd) {
            throw new IllegalArgumentException("Expected newline after Otherwise");
        }
        NewLineNode newLine = NewLineNode.parse(tokens);
        BodyNode body = BodyNode.parse(tokens);
        return new OtherwiseNode(depth, body);
    }

    @Override
    public boolean validate() throws Exception {
        return body.validate();
    }

    @Override
    public String convertToJava() {
        return "else " + body.convertToJava() + "\n";
    }

    @Override
    public String convertToJotlin() {
        return "\t".repeat(depth) + "Otherwise\n" + body.convertToJotlin();
    }
}