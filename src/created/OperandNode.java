package created;

import java.util.ArrayList;
import provided.*;

//< operand > -> < identifier > | <num > | < func_call >

public class OperandNode implements JotlinTree{

    private JotlinTree operand;

    public OperandNode(JotlinTree operand) {
        this.operand = operand;
    }

    public static JotlinTree parse(ArrayList<JotlinToken> tokens){
        JotlinToken first = tokens.get(0);
        if(first.getTokenType() == TokenType.Keyword || first.getTokenType() == TokenType.Id){
            return IdentifierNode.parse(tokens);
        } else if (first.getTokenType() == TokenType.Integer || first.getTokenType() == TokenType.Double){
            return NumNode.parse(tokens); //return NumNode.parse
        } else if (first.getTokenType() == TokenType.FC_Header){
            return FunccallNode.parse(tokens);
        } else{
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
        if (operand instanceof IdentifierNode) {
            return operand.convertToJava();
        } else if (operand instanceof IntegerNode || operand instanceof DoubleNode) {
            return operand.convertToJava();
        } else if (operand instanceof FunccallNode) {
            return operand.convertToJava();
        }
        return "";
    }

    @Override
    public String convertToJotlin() {
        if (operand instanceof IdentifierNode) {
            return operand.convertToJotlin();
        } else if (operand instanceof IntegerNode || operand instanceof DoubleNode) {
            return operand.convertToJotlin();
        } else if (operand instanceof FunccallNode) {
            return operand.convertToJotlin();
        }
        return "";
    }


    
}
