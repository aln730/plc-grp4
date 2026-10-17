package created;

import java.util.ArrayList;

import provided.*;


public class IdNode implements JotlinTree {

    private final String value;

    private IdNode(String value) {
        this.value = value;
    }

    public static IdNode parse(ArrayList<JotlinToken> tokens) {
        JotlinToken first = tokens.get(0);

        if (first.getTokenType() != TokenType.Id) {
            throw new IllegalArgumentException(
                "Error on " + first + ": expected an Id token");
        }

        tokens.remove(0);
        return new IdNode(first.getToken());
    }

    
    @Override
    public boolean validate() throws Exception {
        return value != null && !value.isEmpty();
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