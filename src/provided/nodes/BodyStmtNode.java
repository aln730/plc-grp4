package provided.nodes;

import java.util.ArrayList;

import provided.*;

public class BodyStmtNode implements JotlinTree {

    private final JotlinTree statement;
    private final WhileNode whileNode;

    private BodyStmtNode(JotlinTree statement) {
        this.statement = statement;
        this.whileNode = null;
    }

    private BodyStmtNode(WhileNode whileNode) {
        this.statement = null;
        this.whileNode = whileNode;
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
            return new BodyStmtNode(IfStmtNode.parse(tokens));
        }
        if (first.getTokenType() == TokenType.Keyword
                && first.getToken().equals("While")) {
            return new BodyStmtNode(WhileNode.parse(tokens));
        }
        if (first.getTokenType() == TokenType.Indent) {
            if (tokens.size() > 1
                    && tokens.get(1).getTokenType() == TokenType.FC_Header) {
                return new BodyStmtNode(FunccallNode.parse(tokens));
            }
            return new BodyStmtNode(AsmtStmtNode.parse(tokens));
        }
        throw new IllegalArgumentException(
            "Unexpected token in body statement: " + first
        );
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