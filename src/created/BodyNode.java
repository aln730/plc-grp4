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
        while (!tokens.isEmpty() && !(tokens.get(0).getTokenType() == TokenType.L_Arrow)) {
            statements.add(BodyStmtNode.parse(tokens));
        }
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Expected a return statement at the end of the body");
        }
        ReturnNode returnNode = ReturnNode.parse(tokens);
        // if (tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.LineEnd) {
        //     throw new IllegalArgumentException("Expected a newline after the return statement");
        // }
        // while (!tokens.isEmpty() && tokens.get(0).getTokenType() == TokenType.LineEnd) {
        //     tokens.remove(0);
        // }
        return new BodyNode(statements, returnNode);
    }

    @Override
    public boolean validate() throws Exception {
        for (BodyStmtNode statement : statements) {
            if (!statement.validate()) {
                return false;
            }
        }
        return returnNode.validate();
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
        result.append("    ")
              .append(returnNode.convertToJava())
              .append("\n}");
        return result.toString();
    }

    @Override
    public String convertToJotlin() {
        StringBuilder result = new StringBuilder();
        for (BodyStmtNode statement : statements) {
            result.append(statement.convertToJotlin()).append("\n");
        }
        result.append(returnNode.convertToJotlin());
        return result.toString();
    }
}