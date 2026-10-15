package provided;
import java.util.ArrayList;

public class ParamsNode {
    private final ArrayList<ExprNode> params;

    private ParamsNode(ArrayList<ExprNode> params) {
        this.params = params;
    }

    public static ParamsNode parse(ArrayList<JotlinToken> tokens) {
        ArrayList<ExprNode> params = new ArrayList<>();
        if (tokens.isEmpty() || tokens.get(0).getTokenType() == TokenType.R_Bracket) {
            return new ParamsNode(params);
        }
        params.add(ExprNode.parse(tokens));
        while (!tokens.isEmpty() && tokens.get(0).getTokenType() == TokenType.Comma) {
            tokens.remove(0);
            if (tokens.isEmpty() || tokens.get(0).getTokenType() == TokenType.R_Bracket) {
                throw new IllegalArgumentException("Expected an expression after comma");
            }
            params.add(ExprNode.parse(tokens));
        }
        return new ParamsNode(params);
    }

    public String convertToJava() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < params.size(); i++) {
            if (i > 0) {
                result.append(", ");
            }
            result.append(params.get(i).convertToJava());
        }
        return result.toString();
    }

    public String convertToJott() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < params.size(); i++) {
            if (i > 0) {
                result.append(", ");
            }
            result.append(params.get(i).convertToJott());
        }
        return result.toString();
    }
}

