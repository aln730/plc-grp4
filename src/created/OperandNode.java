package created;

import java.util.ArrayList;
import provided.*;

public class OperandNode implements JotlinTree {
    private JotlinTree op;
//< operand > -> < identifier > | <num > | < func_call >

    public OperandNode(JotlinTree op) {
        this.op = op;
    }

    public static OperandNode parse(ArrayList<JotlinToken> tokens) {
        JotlinToken first = tokens.get(0);
        if (first.getTokenType() == TokenType.Keyword || first.getTokenType() == TokenType.Id) {
            JotlinTree id = IdentifierNode.parse(tokens);
            return new OperandNode(id);
        } else if (first.getTokenType() == TokenType.Integer || first.getTokenType() == TokenType.Double) {
            JotlinTree num = NumNode.parse(tokens);
            return new OperandNode(num);
        } else if (first.getTokenType() == TokenType.FC_Header) {
            JotlinTree call = FunccallNode.parse(tokens);
            return new OperandNode(call);
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
        return op.convertToJava();
    }

    @Override
    public String convertToJotlin() {
        return op.convertToJotlin();
    }
}