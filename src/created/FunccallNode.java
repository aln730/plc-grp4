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

    private static final java.util.Map<String, Integer> LIST_METHODS =
        java.util.Map.of("add", 1, "first", 0, "last", 0, "pop", 0, "count", 0);

    // declared type of the variable a call is made on, or null (class name / Self / unknown)
    private String qualifierType() {
        String q = name.getQualifier();
        if (q == null || q.equals("Self")) {
            return null;
        }
        try {
            return SymbolTable.typeOf(q);
        } catch (IllegalStateException e) {
            return null;
        }
    }

    // a call to a function of this class: f[], Self.f[] or ThisClass.f[]
    private boolean isLocalCall() {
        String q = name.getQualifier();
        return q == null || q.equals("Self") || q.equals(SymbolTable.getCurrClass());
    }

    private static boolean typesMatch(ArrayList<String> expected, ArrayList<String> given) {
        if (expected.size() != given.size()) {
            return false;
        }
        for (int i = 0; i < expected.size(); i++) {
            if (given.get(i) != null && !given.get(i).equals(expected.get(i))) {
                return false;
            }
        }
        return true;
    }

    public String evaluateType() {
        if ("List".equals(qualifierType())) {
            return "count".equals(name.getMember()) ? "Integer" : null;
        }
        if (isLocalCall()) {
            SymbolTable.FunctionInfo info = SymbolTable.getFunctionName(name.getMember());
            return info == null ? null : info.returnType;
        }
        return null;   // other classes are virtual in phase 3
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
        if (!params.validate()) {
            return false;
        }
        String q = name.getQualifier();
        String m = name.getMember();
        if ("Printer".equals(q)) {
            return "display".equals(m) && params.size() == 1;
        }
        if ("List".equals(qualifierType())) {
            Integer expected = LIST_METHODS.get(m);
            return expected != null && params.size() == expected;
        }
        if (!isLocalCall()) {
            return true;   // functions of other classes are virtual until linking (phase 4)
        }
        SymbolTable.FunctionInfo info = SymbolTable.getFunctionName(m);
        return info != null && typesMatch(info.paramTypes, params.getTypes());
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