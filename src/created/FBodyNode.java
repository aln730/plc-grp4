package created;

import java.util.ArrayList;
import provided.*;

public class FBodyNode implements JotlinTree {

    private ArrayList<VarDecNode> varDecNodes;
    private ArrayList<BodyStmtNode> bodyStmtNodes;
    private ReturnNode returnVal;
    private NewLineNode newLines;
    private TabNode tabs;

    public FBodyNode(ArrayList<VarDecNode> varDecNodes, ArrayList<BodyStmtNode> bodyStmtNodes,
                     ReturnNode returnVal, NewLineNode newLines, TabNode tabs) {
        this.varDecNodes = varDecNodes;
        this.bodyStmtNodes = bodyStmtNodes;
        this.returnVal = returnVal;
        this.newLines = newLines;
        this.tabs = tabs;
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
        TabNode tabs = null;
        if (nextIsReturn(tokens)) {
            tabs = TabNode.parse(tokens);
            returnNode = ReturnNode.parse(tokens);
            skipBlankLines(tokens);
        }

        if (varNodes.isEmpty() && bodyNodes.isEmpty() && returnNode == null) {
            throw new IllegalArgumentException("Syntax Error\nFunction body cannot be empty.\n"
                + (tokens.isEmpty() ? "" : tokens.get(0).getFileName() + ":" + tokens.get(0).getLineNumber()));
        }

        return new FBodyNode(varNodes, bodyNodes, returnNode, null, tabs);
    }

    private static void skipBlankLines(ArrayList<JotlinToken> t) {
        while (!t.isEmpty()) {
            int i = skipTabs(t);
            if (i < t.size() && t.get(i).getTokenType() != TokenType.LineEnd) {
                return;
            }
            for (int k = 0; k < i; k++) {
                t.remove(0);
            }
            if (!t.isEmpty()) {
                t.remove(0);
            }
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
        for(VarDecNode varDec:varDecNodes) {
            if(!varDec.validate()) {
                return false;
            }
        }
        for(BodyStmtNode bodyStmt:bodyStmtNodes) {
            if(!bodyStmt.validate()) {
                return false;
            }            
        }
        return returnVal.validate();
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
            if(tabs != null) {
                jotlin += tabs.convertToJotlin();
            }
            jotlin += returnVal.convertToJotlin();
        }
        if (newLines != null) {
            jotlin += newLines.convertToJotlin();
        }
        return jotlin;
    }
}