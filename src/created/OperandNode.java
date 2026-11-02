package created;

import java.util.ArrayList;
import provided.*;

public class OperandNode implements JotlinTree {
    private JotlinTree operand;
    private JotlinToken token;

    public OperandNode(JotlinTree op, JotlinToken token) {
        operand = op;
        this.token = token;
    }

    public static JotlinTree parse(ArrayList<JotlinToken> tokens) {
        JotlinToken first = tokens.get(0);
        if (first.getTokenType() == TokenType.Keyword || first.getTokenType() == TokenType.Id) {
            return new OperandNode( IdentifierNode.parse(tokens), first);
        } else if (first.getTokenType() == TokenType.Integer || first.getTokenType() == TokenType.Double) {
            return new OperandNode(NumNode.parse(tokens), first);
        } else if (first.getTokenType() == TokenType.FC_Header) {
            return new OperandNode(FunccallNode.parse(tokens), first);
        } else {
            throw new IllegalArgumentException("operand must be Identifier, Num, or Func call");
        }
    }

    @Override
    public boolean validate() throws Exception {
        return operand != null && operand.validate();
    }

    @Override
    public String convertToJava() {
        return operand.convertToJava();

    }

    @Override
    public String convertToJotlin() {
        return operand.convertToJotlin();
    }

    public JotlinToken getToken() {
        return this.token;
    }
}