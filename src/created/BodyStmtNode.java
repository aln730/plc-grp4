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
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException(
                "Expected a body statement"
            );
        }
        JotlinToken first = tokens.get(0);
        if (first.getTokenType() == TokenType.Keyword
                && first.getToken().equals("If")) {
            return new BodyStmtNode(null,IfStmtNode.parse(tokens),null);
        }
        if (first.getTokenType() == TokenType.Keyword
                && first.getToken().equals("While")) {
            return new BodyStmtNode(null,WhileNode.parse(tokens),null);
        }
        if (first.getTokenType() == TokenType.Indent) {
            TabNode tabs = TabNode.parse(tokens);
            if (tokens.size() > 1
                    && tokens.get(0).getTokenType() == TokenType.FC_Header) {
                        FunccallNode funccall = FunccallNode.parse(tokens);
                        NewLineNode newLines = NewLineNode.parse(tokens);
                return new BodyStmtNode(tabs,funccall,newLines);
            }
            AsmtStmtNode asmt = AsmtStmtNode.parse(tokens);
            NewLineNode newLines = NewLineNode.parse(tokens);
            return new BodyStmtNode(null,asmt,newLines);
        }
        throw new IllegalArgumentException(
            "Unexpected token in body statement: " + first
        );
    }

    @Override
    public boolean validate() throws Exception {
        if (statement == null) {
            return false;
        }
        if (!statement.validate()) {
            return false;
        }
        if (tabs != null && !tabs.validate()) {
            return false;
        }
        if (newLines != null && !newLines.validate()) {
            return false;
        }
        return true;
    }

    @Override
    public String convertToJava() {
        StringBuilder result = new StringBuilder();
        if (tabs != null) {
            result.append(tabs.convertToJava());
        }
        result.append(statement.convertToJava());
        if (newLines != null) {
            result.append(newLines.convertToJava());
        }
        return result.toString();
    }

    @Override
    public String convertToJotlin() {
        StringBuilder result = new StringBuilder();
        if (tabs != null) {
            result.append(tabs.convertToJotlin());
        }
        result.append(statement.convertToJotlin());
        if (newLines != null) {
            result.append(newLines.convertToJotlin());
        }
        return result.toString();
    }
}