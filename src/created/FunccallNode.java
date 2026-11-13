package created;

import java.util.ArrayList;
import provided.*;

public class FunccallNode implements JotlinTree {

    private final IdentifierNode name;
    private final ParamsNode params;

    public FunccallNode(IdentifierNode name, ParamsNode params) {
        this.name = name;
        this.params = params;
    }

    public static FunccallNode parse(ArrayList<JotlinToken> tokens) {
        if (tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.FC_Header) {
            throw new IllegalArgumentException("Expected Function Call Header");
        }
        tokens.remove(0);

        IdentifierNode name = IdentifierNode.parse(tokens);

        if (tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.L_Bracket) {
            throw new IllegalArgumentException("Expected left bracket");
        }
        tokens.remove(0);

        ParamsNode params = ParamsNode.parse(tokens);

        if (tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.R_Bracket) {
            throw new IllegalArgumentException("Expected right bracket");
        }
        tokens.remove(0);

        return new FunccallNode(name, params);
    }

    public String evaluateType() {
        return SymbolTable.getFunctionName(name.evaluateValue()).returnType;
    }

    public String evaluateValue() {
        SymbolTable.FunctionInfo func = SymbolTable.getFunctionName(name.getName());
        return func.returnVal;
    }

    @Override
    public boolean validate() throws Exception {
        boolean validate =  params.validate() && name.validate() && SymbolTable.getFunctionName(name.getName()) != null;
        if(validate) {
            ArrayList<String> types = SymbolTable.getFunctionName(name.getName()).paramTypes;
            ArrayList<String> compare = params.getReturnTypes();
            for(int i = 0; i < types.size(); i++) {
                if(!types.get(i).equals(compare.get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String convertToJava() {
        return name.convertToJava() + "(" + params.convertToJava() + ")";
    }

    @Override
    public String convertToJotlin() {
        return "::" + name.convertToJotlin() + "[" + params.convertToJotlin() + "]";
    }
}