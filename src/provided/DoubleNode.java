package provided;

import java.util.ArrayList;

public class DoubleNode implements JotlinTree {

    private final String value;

    private DoubleNode(String value) {
        this.value = value;
    }

    public static DoubleNode parse(ArrayList<JotlinToken> tokens) {
        JotlinToken first = tokens.get(0);

        if (first.getTokenType() != TokenType.Double) {
            throw new IllegalArgumentException(
                "Error on " + first + ": expected a Double token");
        }

        tokens.remove(0);
        return new DoubleNode(first.getToken());
    }

    @Override
    public boolean validate() throws Exception {
        return true;
    }

    @Override
    public String convertToJava() {
        return value;
    }

    @Override
    public String convertToJotlin() {
        return value;
    }
}