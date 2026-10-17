package created;

import java.util.ArrayList;
import provided.*;

//< func_call > -> :: < identifier >[ < parms >]

public class FunccallNode implements JotlinTree{
        public static IdentifierNode name;
        public static ArrayList<ExprNode> params;

        public FunccallNode(IdentifierNode n, ArrayList<ExprNode> p){
            name = n;
            params = p;
        }

    public static FunccallNode parse(ArrayList<JotlinToken> tokens){
        if(tokens.get(0).getTokenType() != TokenType.FC_Header){
            throw new IllegalArgumentException("Expected Function Call Header");
        }
        tokens.remove(0);
        IdentifierNode name = IdentifierNode.parse(tokens);
        if(tokens.get(0).getTokenType() != TokenType.L_Bracket){
            throw new IllegalArgumentException("Expected left bracket");
        }
        tokens.remove(0);
        //expresion node arraylist called params
        ArrayList<ExprNode> params = new ArrayList<>();
        Boolean first = true;
        while(tokens.get(0).getTokenType() != TokenType.R_Bracket){
            if(!first){
                if(tokens.get(0).getTokenType() != TokenType.Comma){
                    throw new IllegalArgumentException("Expected Comma");
                }
                tokens.remove(0);
            }
            first = false;
            params.add(ExprNode.parse(tokens));
        }
        if(tokens.get(0).getTokenType() != TokenType.R_Bracket){
            throw new IllegalArgumentException("Expected right bracket");
        }
        tokens.remove(0);
        return new FunccallNode(name, params);
    }

	
    @Override
    public boolean validate() throws Exception {
        if (name == null || params == null) {
            return false;
        }
        if (!name.validate()) {
            return false;
        }
        for (ExprNode e : params) {
            if (e == null || !e.validate()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String convertToJava() {
        StringBuilder result = new StringBuilder();
        result.append(name.convertToJava());
        result.append("(");
        for (int i = 0; i < params.size(); i++) {
            if (i > 0) {
                result.append(", ");
            }
            result.append(params.get(i).convertToJava());
        }
        result.append(")");
        return result.toString();
    }

    @Override
    public String convertToJotlin() {
        StringBuilder result = new StringBuilder();
        result.append("::");
        result.append(name.convertToJotlin());
        result.append("[");
        for (int i = 0; i < params.size(); i++) {
            if (i > 0) {
                result.append(", ");
            }
            result.append(params.get(i).convertToJotlin());
        }
        result.append("]");
        return result.toString();
    }


}
