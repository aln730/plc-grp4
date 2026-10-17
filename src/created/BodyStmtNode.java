package created;

import java.util.ArrayList;
import provided.*;

public class BodyStmtNode implements JotlinTree {

    private final JotlinTree statement;
    private final TabNode tabs;
    private final NewLineNode newLines;

    public BodyStmtNode(TabNode tabs, JotlinTree statement, NewLineNode newLines) {
        this.tabs = tabs;
        this.statement = statement;
        this.newLines = newLines;
    }

    public static BodyStmtNode parse(ArrayList<JotlinToken> tokens) {
        int i = 0;
        while (i < tokens.size() && tokens.get(i).getTokenType() == TokenType.Indent) {
            i++;
        }
        if (i >= tokens.size()) {
            throw new IllegalArgumentException("Expected a body statement");
        }
        JotlinToken first = tokens.get(i);

        if (first.getTokenType() == TokenType.Keyword && first.getToken().equals("If")) {
            return new BodyStmtNode(null, IfStmtNode.parse(tokens), null);
        }
        if (first.getTokenType() == TokenType.Keyword && first.getToken().equals("While")) {
            return new BodyStmtNode(null, WhileNode.parse(tokens), null);
        }
        if (first.getTokenType() == TokenType.FC_Header) {
            TabNode tabs = TabNode.parse(tokens);
            FunccallNode funccall = FunccallNode.parse(tokens);
            NewLineNode newLines = NewLineNode.parse(tokens);
            return new BodyStmtNode(tabs, funccall, newLines);
        }
        if (first.getTokenType() == TokenType.Id
                || (first.getTokenType() == TokenType.Keyword
                    && i + 1 < tokens.size()
                    && tokens.get(i + 1).getTokenType() == TokenType.Dot)) {
            AsmtStmtNode asmt = AsmtStmtNode.parse(tokens);
            NewLineNode newLines = NewLineNode.parse(tokens);
            return new BodyStmtNode(null, asmt, newLines);
        }
        throw new IllegalArgumentException("Unexpected token in body statement: " + first);
    }

    @Override
    public boolean validate() throws Exception {
        return statement.validate();
    }

    @Override
    public String convertToJava() {
        return statement.convertToJava();
    }

    @Override
    public String convertToJotlin() {
        String out = "";
        if (tabs != null) {
            out += tabs.convertToJotlin();
        }
        out += statement.convertToJotlin();
        if (newLines != null) {
            out += newLines.convertToJotlin();
        }
        return out;
    }
}