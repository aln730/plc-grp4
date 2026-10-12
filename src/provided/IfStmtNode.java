package provided;

import java.util.ArrayList;

public class IfStmtNode implements JotlinTree {

    //Depth is the layer of the line of code
    private final int depth;
    private final ExprNode condition;
    private final BodyNode body;
    private final ArrayList<OrIfNode> orIfs;
    private final OtherwiseNode otherwise;   // null if absent

    private IfStmtNode(int depth, ExprNode condition, BodyNode body, ArrayList<OrIfNode> orIfs, OtherwiseNode otherwise) {
        this.depth = depth;
        this.condition = condition;
        this.body = body;
        this.orIfs = orIfs;
        this.otherwise = otherwise;
    }

    public static IfStmtNode parse(ArrayList<JotlinToken> tokens) {
        // the tab count is this If's depth; OrIf/Otherwise must match it
        int depth = 0;
        while (!tokens.isEmpty() && tokens.get(0).getTokenType() == TokenType.Indent) {
            tokens.remove(0);
            depth++;
        }

        JotlinToken first = tokens.get(0);
        if (first.getTokenType() != TokenType.Keyword || !first.getToken().equals("If")) {
            throw new IllegalArgumentException("Error on " + first + ": expected 'If'");
        }
        tokens.remove(0);

        expect(tokens, TokenType.L_Bracket, "[");
        ExprNode condition = ExprNode.parse(tokens);
        expect(tokens, TokenType.R_Bracket, "]");
        expect(tokens, TokenType.LineEnd, "end of line");
        skipNewlines(tokens);

        BodyNode body = BodyNode.parse(tokens, depth + 1);

        ArrayList<OrIfNode> orIfs = new ArrayList<>();
        while (nextLineStartsWith(tokens, depth, "OrIf")) {
            skipNewlines(tokens);
            orIfs.add(OrIfNode.parse(tokens));
        }

        OtherwiseNode otherwise = null;
        if (nextLineStartsWith(tokens, depth, "Otherwise")) {
            skipNewlines(tokens);
            otherwise = OtherwiseNode.parse(tokens);
        }

        skipNewlines(tokens);
        return new IfStmtNode(depth, condition, body, orIfs, otherwise);
    }

    // Peeks at the next non-blank line without consuming it. True only if it has exactly
    // `depth` tabs followed by the keyword, so nested Ifs attach their Otherwise correctly.
    private static boolean nextLineStartsWith(ArrayList<JotlinToken> tokens, int depth, String keyword) {
        int i = 0;
        while (i < tokens.size() && tokens.get(i).getTokenType() == TokenType.LineEnd) {
            i++;
        }
        int tabs = 0;
        while (i < tokens.size() && tokens.get(i).getTokenType() == TokenType.Indent) {
            i++;
            tabs++;
        }
        if (tabs != depth || i >= tokens.size()) {
            return false;
        }
        JotlinToken t = tokens.get(i);
        return t.getTokenType() == TokenType.Keyword && t.getToken().equals(keyword);
    }

    private static void skipNewlines(ArrayList<JotlinToken> tokens) {
        while (!tokens.isEmpty() && tokens.get(0).getTokenType() == TokenType.LineEnd) {
            tokens.remove(0);
        }
    }

    private static void expect(ArrayList<JotlinToken> tokens, TokenType type, String text) {
        JotlinToken first = tokens.get(0);
        if (first.getTokenType() != type) {
            throw new IllegalArgumentException("Error on " + first + ": expected '" + text + "'");
        }
        tokens.remove(0);
    }

    @Override
    public boolean validate() throws Exception {
        boolean ok = condition.validate() && body.validate();
        for (OrIfNode o : orIfs) {
            ok = ok && o.validate();
        }
        if (otherwise != null) {
            ok = ok && otherwise.validate();
        }
        return ok;
    }

    @Override
    public String convertToJava() {
        StringBuilder sb = new StringBuilder();
        sb.append("if (").append(condition.convertToJava()).append(") {\n");
        sb.append(body.convertToJava()).append("}\n");
        for (OrIfNode o : orIfs) {
            sb.append(o.convertToJava());
        }
        if (otherwise != null) {
            sb.append(otherwise.convertToJava());
        }
        return sb.toString();
    }

    @Override
    public String convertToJotlin() {
        StringBuilder sb = new StringBuilder();
        sb.append("\t".repeat(depth)).append("If[").append(condition.convertToJotlin()).append("]\n");
        sb.append(body.convertToJotlin());
        for (OrIfNode o : orIfs) {
            sb.append(o.convertToJotlin());
        }
        if (otherwise != null) {
            sb.append(otherwise.convertToJotlin());
        }
        return sb.toString();
    }
}