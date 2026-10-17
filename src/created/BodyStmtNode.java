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
            return new BodyStmtNode(tabs,asmt,newLines);
        }
        else {
            throw new IllegalArgumentException(
                "Unexpected token in body statement: " + first
            );
        }
    }

    @Override
    public boolean validate() throws Exception {
        //todo
        return statement.validate();
    }

    @Override
    public String convertToJava() {
        //todo
        return statement.convertToJava();
    }

    @Override
    public String convertToJotlin() {
        //todo
        return statement.convertToJotlin();
    }
}