package provided.nodes;

import java.util.ArrayList;

import provided.*;


public class NumNode {

    public static JotlinTree parse(ArrayList<JotlinToken> tokens) {

        JotlinToken first = tokens.get(0);

        if (first.getTokenType() == TokenType.Integer) {
            return IntegerNode.parse(tokens);
        }
        else if (first.getTokenType() == TokenType.Double) {
            return DoubleNode.parse(tokens);
        }
        else {
            throw new IllegalArgumentException(
                "Error on " + tokens.get(0) + ": expected Integer or Double");
        }
    }
}