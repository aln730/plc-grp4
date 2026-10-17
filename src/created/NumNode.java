package created;

import java.util.ArrayList;

import provided.*;


public class NumNode implements JotlinTree{
    private JotlinTree number;

    public NumNode(JotlinTree number) {
        this.number = number;
    }

    public static NumNode parse(ArrayList<JotlinToken> tokens) {
        JotlinToken first = tokens.get(0);

        if (first.getTokenType() == TokenType.Integer) {
            return new NumNode(IntegerNode.parse(tokens));
        }
        else if (first.getTokenType() == TokenType.Double) {
            return new NumNode(DoubleNode.parse(tokens));
        }
        else {
            throw new IllegalArgumentException(
                "Error on " + tokens.get(0) + ": expected Integer or Double");
        }
    }

    @Override
    public boolean validate() throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validate'");
    }

    @Override
    public String convertToJava() {
        // TODO Auto-generated method stub
        return number.convertToJava();
    }

    @Override
    public String convertToJotlin() {
        // TODO Auto-generated method stub
        return number.convertToJotlin();
    }
}