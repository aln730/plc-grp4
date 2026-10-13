package provided;
import java.util.ArrayList;

public class IncludeNode implements JotlinTree {
    private final JotlinToken id;

    private IncludeNode(JotlinToken id) {
        this.id = id;
    }

    public static IncludeNode parse(ArrayList<JotlinToken> tokens) {
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Expected Include keyword");
        }
        JotlinToken first = tokens.get(0);
        if (first.getTokenType() != TokenType.Keyword || !first.getToken().equals("Include")) {
            throw new IllegalArgumentException("expected 'Include'");
        }
        tokens.remove(0);
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Expected identifier after Include");
        }
        first = tokens.get(0);
        if (first.getTokenType() != TokenType.Id) {
            throw new IllegalArgumentException("expected identifier");
        }
        JotlinToken id = first;
        tokens.remove(0);
        if (tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.LineEnd) {
            throw new IllegalArgumentException("Expected line ending after Include statement");
        }
        while (!tokens.isEmpty() && tokens.get(0).getTokenType() == TokenType.LineEnd) {
            tokens.remove(0);
        }
        return new IncludeNode(id);
    }

    @Override
    public boolean validate() throws Exception {
        return id != null && !id.getToken().isEmpty();
    }

    @Override
    public String convertToJava() {
        return "import " + id.getToken() + ";";
    }

    @Override
    public String convertToJotlin() {
        return "Include " + id.getToken();
    }
}