package created;

import java.util.ArrayList;
import provided.*;


public class AsmtStmtNode implements JotlinTree {

    private final TabNode tabs;
    private final IdentifierNode id;
    private final ExprNode expr;
    private final KeywordNode className;
    private final ParamsNode params;

    private AsmtStmtNode(TabNode tabs, IdentifierNode id, ExprNode expr, KeywordNode className, ParamsNode params) {
        this.tabs = tabs;
        this.id = id;
        this.expr = expr;
        this.className = className;
        this.params = params;
    }

    public static AsmtStmtNode parse(ArrayList<JotlinToken> tokens) {
        TabNode tabs = TabNode.parse(tokens);

        IdentifierNode id = IdentifierNode.parse(tokens);

        JotlinToken assign = tokens.get(0);
        if (assign.getTokenType() != TokenType.Assign) {
            throw new IllegalArgumentException("Error on " + assign + ": expected '='");
        }
        tokens.remove(0);

        JotlinToken next = tokens.get(0);
        if (next.getTokenType() == TokenType.Keyword && next.getToken().equals("Create")) {
            tokens.remove(0);
            KeywordNode className = KeywordNode.parse(tokens);

            JotlinToken open = tokens.get(0);
            if (open.getTokenType() != TokenType.L_Bracket) {
                throw new IllegalArgumentException("Error on " + open + ": expected '['");
            }
            tokens.remove(0);

            ParamsNode params = null;
            if (tokens.get(0).getTokenType() != TokenType.R_Bracket) {
                params = ParamsNode.parse(tokens);
            }

            JotlinToken close = tokens.get(0);
            if (close.getTokenType() != TokenType.R_Bracket) {
                throw new IllegalArgumentException("Error on " + close + ": expected ']'");
            }
            tokens.remove(0);

            return new AsmtStmtNode(tabs, id, null, className, params);
        }

        ExprNode expr = ExprNode.parse(tokens);
        return new AsmtStmtNode(tabs, id, expr, null, null);
    }

    @Override
    public boolean validate() throws Exception {
        if (expr != null) {
            return expr.validate();
        }
        return params == null || params.validate();
    }

    @Override
    public String convertToJava() {
        if (expr != null) {
            return id.convertToJava() + " = " + expr.convertToJava() + ";";
        }
        String args = (params == null) ? "" : params.convertToJava();
        return id.convertToJava() + " = new " + className.convertToJava() + "(" + args + ");";
    }

    @Override
    public String convertToJotlin() {
        if (expr != null) {
            return tabs.convertToJotlin() + id.convertToJotlin() + " = " + expr.convertToJotlin();
        }
        String args = (params == null) ? "" : params.convertToJotlin();
        return tabs.convertToJotlin() + id.convertToJotlin() + " = Create " + className.convertToJotlin() + "[" + args + "]";
    }
}