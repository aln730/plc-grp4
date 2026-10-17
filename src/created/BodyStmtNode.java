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
        if (first.getTokenType() == TokenType.Indent && tokens.size() > 1) {
            if (tokens.get(1).getTokenType() == TokenType.Keyword
                && tokens.get(1).getToken().equals("If")) {
                    IfStmtNode ifstat = IfStmtNode.parse(tokens);
                    return new BodyStmtNode(null,ifstat,null);
            }
            else if (tokens.get(1).getTokenType() == TokenType.Keyword
                && tokens.get(1).getToken().equals("While")) {
                    WhileNode whl = WhileNode.parse(tokens);
                    return new BodyStmtNode(null,whl,null);
                }
            else if (tokens.get(1).getTokenType() == TokenType.FC_Header) {
                        TabNode tabs = TabNode.parse(tokens);
                        FunccallNode funccall = FunccallNode.parse(tokens);
                        NewLineNode newLines = NewLineNode.parse(tokens);
                return new BodyStmtNode(tabs,funccall,newLines);
            }
            else {
                AsmtStmtNode asmt = AsmtStmtNode.parse(tokens);
                NewLineNode newLines = NewLineNode.parse(tokens);
                return new BodyStmtNode(null,asmt,newLines);
            }
        }
        else {
            throw new IllegalArgumentException(
                "Unexpected token in body statement: " + first
            );
        }
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