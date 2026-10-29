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

    @Override
    public boolean validate() throws Exception {
        return params.validate() && name.validate();
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