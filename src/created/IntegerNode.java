package created;

import java.util.ArrayList;
import provided.*;


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
        if (value == null || value.isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
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