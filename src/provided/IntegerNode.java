package provided;

import java.util.ArrayList;

public class IntegerNode implements JotlinTree {

    private final String value;

    private IntegerNode(String value) {
        this.value = value;
    }

    public static IntegerNode parse(ArrayList<JotlinToken> tokens) {
        JotlinToken first = tokens.get(0);

        if (first.getTokenType() != TokenType.Integer) {
            throw new IllegalArgumentException(
                "Error on " + first + ": expected an Integer token");
        }   

        tokens.remove(0);
        return new IntegerNode(first.getToken());
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