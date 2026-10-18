package created;

import java.util.ArrayList;
import provided.*;

public class OperandNode implements JotlinTree {
    private JotlinTree operand;

    public OperandNode(JotlinTree op) {
        operand = op;
    }

    public static JotlinTree parse(ArrayList<JotlinToken> tokens) {
        JotlinToken first = tokens.get(0);
        if (first.getTokenType() == TokenType.Keyword || first.getTokenType() == TokenType.Id) {
            return new OperandNode( IdentifierNode.parse(tokens));
        } else if (first.getTokenType() == TokenType.Integer || first.getTokenType() == TokenType.Double) {
            return new OperandNode(NumNode.parse(tokens));
        } else if (first.getTokenType() == TokenType.FC_Header) {
            return new OperandNode(FunccallNode.parse(tokens));
        } else {
            throw new IllegalArgumentException("operand must be Identifier, Num, or Func call");
        }
    }

    @Override
    public boolean validate() throws Exception {
        if (operand == null) {
            return false;
        }
        return operand.validate();
    }

    @Override
    public String convertToJava() {
        return operand.convertToJava();

    }

    @Override
    public String convertToJotlin() {
        return operand.convertToJotlin();
    }
}