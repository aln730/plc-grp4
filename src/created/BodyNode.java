package created;
import java.util.ArrayList;
import provided.*;

public class BodyNode implements JotlinTree {
    private final ArrayList<BodyStmtNode> statements;
    private final ReturnNode returnNode;

    private BodyNode(ArrayList<BodyStmtNode> statements, ReturnNode returnNode) {
        this.statements = statements;
        this.returnNode = returnNode;
    }

    public static BodyNode parse(ArrayList<JotlinToken> tokens) {
        ArrayList<BodyStmtNode> statements = new ArrayList<>();
        skipBlankLines(tokens);
        while (nextIsBodyStmt(tokens)) {
            statements.add(BodyStmtNode.parse(tokens));
            skipBlankLines(tokens);
        }
        ReturnNode returnNode = null;
        if (nextIsReturn(tokens)) {
            while (tokens.get(0).getTokenType() == TokenType.Indent) {
                tokens.remove(0);
            }
            returnNode = ReturnNode.parse(tokens);
            skipBlankLines(tokens);
        }
        return new BodyNode(statements, returnNode);
    }

    private static void skipBlankLines(ArrayList<JotlinToken> t) {
        while (!t.isEmpty() && t.get(0).getTokenType() == TokenType.LineEnd) {
            t.remove(0);
        }
    }

    private static int skipTabs(ArrayList<JotlinToken> t) {
        int i = 0;
        while (i < t.size() && t.get(i).getTokenType() == TokenType.Indent) {
            i++;
        }
        return i;
    }

    private static boolean nextIsBodyStmt(ArrayList<JotlinToken> t) {
        int i = skipTabs(t);
        if (i >= t.size()) {
            return false;
        }
        JotlinToken x = t.get(i);
        return x.getTokenType() == TokenType.FC_Header
                || x.getTokenType() == TokenType.Id
                || (x.getTokenType() == TokenType.Keyword && i + 1 < t.size()
                    && t.get(i + 1).getTokenType() == TokenType.Dot)
                    && (x.getToken().equals("If") || x.getToken().equals("While"));
    }

    private static boolean nextIsReturn(ArrayList<JotlinToken> t) {
        int i = skipTabs(t);
        return i < t.size() && t.get(i).getTokenType() == TokenType.L_Arrow;
    }

    @Override
    public boolean validate() throws Exception {
        for (BodyStmtNode statement : statements) {
            if (!statement.validate()) {
                return false;
            }
        }
        return returnNode == null || returnNode.validate();
    }

    @Override
    public String convertToJava() {
        StringBuilder result = new StringBuilder();
        result.append("{\n");
        for (BodyStmtNode statement : statements) {
            result.append("    ")
                  .append(statement.convertToJava())
                  .append("\n");
        }
        if (returnNode != null) {
            result.append("    ")
                  .append(returnNode.convertToJava())
                  .append("\n");
        }
        result.append("}");
        return result.toString();
    }

    @Override
    public String convertToJotlin() {
        StringBuilder result = new StringBuilder();
        for (BodyStmtNode statement : statements) {
            result.append(statement.convertToJotlin());
        }
        if (returnNode != null) {
            result.append(returnNode.convertToJotlin());
        }
        return result.toString();
    }
}