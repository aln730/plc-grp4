package created;

import java.util.ArrayList;
import provided.*;

public class FBodyNode implements JotlinTree {

    private ArrayList<VarDecNode> varDecNodes;
    private ArrayList<BodyStmtNode> bodyStmtNodes;
    private ReturnNode returnVal;
    private NewLineNode newLines;

    public FBodyNode(ArrayList<VarDecNode> varDecNodes, ArrayList<BodyStmtNode> bodyStmtNodes,
                     ReturnNode returnVal, NewLineNode newLines) {
        this.varDecNodes = varDecNodes;
        this.bodyStmtNodes = bodyStmtNodes;
        this.returnVal = returnVal;
        this.newLines = newLines;
    }

    public static FBodyNode parse(ArrayList<JotlinToken> tokens) {
        ArrayList<VarDecNode> varNodes = new ArrayList<>();
        ArrayList<BodyStmtNode> bodyNodes = new ArrayList<>();

        skipBlankLines(tokens);

        while (nextIsVarDec(tokens)) {
            varNodes.add(VarDecNode.parse(tokens));
            skipBlankLines(tokens);
        }

        while (nextIsBodyStmt(tokens)) {
            bodyNodes.add(BodyStmtNode.parse(tokens));
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

        return new FBodyNode(varNodes, bodyNodes, returnNode, null);
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

    private static boolean nextIsVarDec(ArrayList<JotlinToken> t) {
        int i = skipTabs(t);
        if (i + 1 >= t.size()) {
            return false;
        }
        JotlinToken kw = t.get(i);
        return kw.getTokenType() == TokenType.Keyword
                && !kw.getToken().equals("If")
                && !kw.getToken().equals("While")
                && t.get(i + 1).getTokenType() == TokenType.Id;
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
                    || (x.getToken().equals("If") || x.getToken().equals("While"));
    }

    private static boolean nextIsReturn(ArrayList<JotlinToken> t) {
        int i = skipTabs(t);
        return i < t.size() && t.get(i).getTokenType() == TokenType.L_Arrow;
    }

    @Override
    public boolean validate() throws Exception {
        throw new UnsupportedOperationException("Unimplemented method 'validate'");
    }

    @Override
    public String convertToJava() {
        String java = "";
        for (int i = 0; i < varDecNodes.size(); i++) {
            java += varDecNodes.get(i).convertToJava();
        }
        for (int i = 0; i < bodyStmtNodes.size(); i++) {
            java += bodyStmtNodes.get(i).convertToJava();
        }
        if (returnVal != null) {
            java += returnVal.convertToJava();
        }
        if (newLines != null) {
            java += newLines.convertToJava();
        }
        return java;
    }

    @Override
    public String convertToJotlin() {
        String jotlin = "";
        for (int i = 0; i < varDecNodes.size(); i++) {
            jotlin += varDecNodes.get(i).convertToJotlin();
        }
        for (int i = 0; i < bodyStmtNodes.size(); i++) {
            jotlin += bodyStmtNodes.get(i).convertToJotlin();
        }
        if (returnVal != null) {
            jotlin += returnVal.convertToJotlin();
        }
        if (newLines != null) {
            jotlin += newLines.convertToJotlin();
        }
        return jotlin;
    }
}