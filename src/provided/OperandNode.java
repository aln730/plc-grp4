package provided;

import java.util.ArrayList;

public class OperandNode {

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


    
}
