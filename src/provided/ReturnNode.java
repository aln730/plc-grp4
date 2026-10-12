package provided;

import java.util.ArrayList;

public class ReturnNode implements JotlinTree {

    private final int depth;
    private final ExprNode expr;

    private ReturnNode(int depth, ExprNode expr) {
        this.depth = depth;
        this.expr = expr;
    }

    public static ReturnNode parse(ArrayList<JotlinToken> tokens) {
        // grammar has no <TBS> here, but returns are indented in practice
        int depth = 0;
        while (!tokens.isEmpty() && tokens.get(0).getTokenType() == TokenType.Indent) {
            tokens.remove(0);
            depth++;    
        }

        JotlinToken first = tokens.get(0);

        if (first.getTokenType() != TokenType.L_Arrow) {
            throw new IllegalArgumentException("Error on " + first + ": expected '<-'");
        }

        tokens.remove(0);
        ExprNode expr = ExprNode.parse(tokens);
        return new ReturnNode(depth, expr);
    }

    @Override
    public boolean validate() throws Exception {
        return expr.validate();
    }

    @Override
    public String convertToJava() {
        return "return " + expr.convertToJava() + ";";
    }

    @Override
    public String convertToJotlin() {
        return "\t".repeat(depth) + "<-" + expr.convertToJotlin();
    }
}