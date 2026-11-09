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
    public String evaluateValue() {
        if (operand.getClass() == NumNode.class) {
            NumNode num = (NumNode) operand;
            return num.getValue();
        }
        else if(operand.getClass() == IdentifierNode.class) {
            IdentifierNode id = (IdentifierNode) operand;
            return id.evaluateValue();
        }
        else {
            FunccallNode funcCall = (FunccallNode) operand;
            return funcCall.evaluateValue();
        }
    }

    public String evaluateType() {
        if (operand.getClass() == NumNode.class) {
            NumNode number = (NumNode) operand;
            return number.getType();
        }
        else if(operand.getClass() == IdentifierNode.class) {
            IdentifierNode id = (IdentifierNode) operand;
            return id.evaluateType();
        }
        else {
            FunccallNode funcCall = (FunccallNode) operand;
            return funcCall.evaluateType();
        }
    }
}