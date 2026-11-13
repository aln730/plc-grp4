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

    public boolean matchesFuncDef(FuncDefNode def){
        return SymbolTable.typeOf(def.getId()).equals(name.toString());
    }

    public String evaluateType() {
        SymbolTable.FunctionInfo info =
            SymbolTable.getFunctionName(name.evaluateValue());

        if (info == null) {
            return null;
        }
        return info.returnType;
    }

    public String evaluateValue() {
        SymbolTable.FunctionInfo info =
            SymbolTable.getFunctionName(name.evaluateValue());

        if (info == null) {
            throw new IllegalArgumentException("Function is undefined");
        }

        ArrayList<String> arguments = params.evaluateValues();

        if (arguments.size() != info.paramTypes.size()) {
            throw new IllegalArgumentException(
                "Incorrect number of arguments"
            );
        }

        for (int i = 0; i < arguments.size(); i++) {
            if (arguments.get(i) == null) {
                throw new IllegalArgumentException(
                    "Function argument has no value"
                );
            }

            String actualType = params.getParamType(i);
            String expectedType = info.paramTypes.get(i);

            if (!expectedType.equals(actualType)) {
                throw new IllegalArgumentException(
                    "Incorrect argument type"
                );
            }
        }
        return info.definition.evaluateValue(arguments);
    }

    @Override
    public boolean validate() throws Exception {
        if (!name.validate() || !params.validate()) {
            return false;
        }
        SymbolTable.FunctionInfo info = SymbolTable.getFunctionName(name.evaluateValue());

        return info != null;
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