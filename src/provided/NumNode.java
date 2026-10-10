package provided;

import java.util.ArrayList;

public class NumNode {

    public static JotlinTree parse(ArrayList<JotlinToken> tokens) {
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Expected a number but found end of input");
        }

        TokenType type = tokens.get(0).getTokenType();

        if (type == TokenType.Integer) {
            return IntegerNode.parse(tokens);
        }
        else if (type == TokenType.Double) {
            return DoubleNode.parse(tokens);
        }
        else {
            throw new IllegalArgumentException(
                "Error on " + tokens.get(0) + ": expected Integer or Double");
        }
    }
}