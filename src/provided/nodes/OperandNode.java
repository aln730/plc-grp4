package provided.nodes;

import java.util.ArrayList;
import provided.*;


public class OperandNode implements JotlinTree{

    public static JotlinTree parse(ArrayList<JotlinToken> tokens){
        JotlinToken first = tokens.get(0);
        if(first.getTokenType() == TokenType.Keyword || first.getTokenType() == TokenType.Id){
            return IdentifierNode.parse(tokens);
        } else if (first.getTokenType() == TokenType.Integer || first.getTokenType() == TokenType.Double){
            return null; //return NumNode.parse
        } else if (first.getTokenType() == TokenType.FC_Header){
            return FunccallNode.parse(tokens);
        } else{
            throw new IllegalArgumentException("operand must be Identifier, Num, or Func call");
        }
    }

    @Override
    public boolean validate() throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validate'");
    }

    @Override
    public String convertToJava() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToJava'");
    }

    @Override
    public String convertToJotlin() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToJotlin'");
    }


    
}
