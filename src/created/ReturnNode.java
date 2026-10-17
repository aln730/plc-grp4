package created;

import java.util.ArrayList;
import provided.*;


public class ReturnNode implements JotlinTree {

    private final ExprNode expr;
    private final NewLineNode nl;

    private ReturnNode(ExprNode expr, NewLineNode nl) {
        this.expr = expr;
        this.nl = nl;
    }

    public static ReturnNode parse(ArrayList<JotlinToken> tokens) {
        JotlinToken first = tokens.get(0);

        if (first.getTokenType() != TokenType.L_Arrow) {
            return new ReturnNode(null,null);
        }

        tokens.remove(0);
        ExprNode expr = ExprNode.parse(tokens);
        NewLineNode nl = NewLineNode.parse(tokens);
        return new ReturnNode(expr, nl);
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
        return "<-" + expr.convertToJotlin();
    }
}