package created;

import java.util.ArrayList;
import provided.*;

public class OrIfNode implements JotlinTree {
    private final int depth;
    private final ExprNode expr;
    private final BodyNode body;

    private OrIfNode(int depth, ExprNode e, BodyNode b) {
        this.depth = depth;
        this.expr = e;
        this.body = b;
    }

    public static OrIfNode parse(ArrayList<JotlinToken> tokens) {
        int depth = 0;
        while (!tokens.isEmpty() && tokens.get(0).getTokenType() == TokenType.Indent) {
            tokens.remove(0);
            depth++;
        }
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Expecting OrIf");
        }
        JotlinToken first = tokens.get(0);
        if (first.getTokenType() != TokenType.Keyword || !first.getToken().equals("OrIf")) {
            throw new IllegalArgumentException("Error on " + first + ": expected 'OrIf'");
        }
        tokens.remove(0);

        if (tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.L_Bracket) {
            throw new IllegalArgumentException("Expecting left bracket");
        }
        tokens.remove(0);

        ExprNode expr = ExprNode.parse(tokens);

        if (tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.R_Bracket) {
            throw new IllegalArgumentException("Expecting right bracket");
        }
        tokens.remove(0);

        if (tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.LineEnd) {
            throw new IllegalArgumentException("Expecting end of line after OrIf condition");
        }
        while (!tokens.isEmpty() && tokens.get(0).getTokenType() == TokenType.LineEnd) {
            tokens.remove(0);
        }

        BodyNode body = BodyNode.parse(tokens);
        return new OrIfNode(depth, expr, body);
    }

    
    @Override
    public boolean validate() throws Exception {
        return expr != null && expr.validate() && body != null && body.validate();
    }

    @Override
    public String convertToJava() {
        return "else if (" + expr.convertToJava() + ") {\n" + body.convertToJava() + "\n}";
    }

    @Override
    public String convertToJotlin() {
        return "OrIf [" + expr.convertToJotlin() + "]\n" + body.convertToJotlin();
    }

}
