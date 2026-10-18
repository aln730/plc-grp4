package created;

import java.util.ArrayList;

import provided.*;


public class NumNode implements JotlinTree{
    private JotlinTree number;

    public NumNode (JotlinTree numnber) {
        this.number = number;
    }

    public static JotlinTree parse(ArrayList<JotlinToken> tokens) {

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
        return number != null && number.validate();
    }
    @Override
    public String convertToJava() {
        return number == null ? "" : number.convertToJava();
    }

    @Override
    public String convertToJotlin() {
        return number == null ? "" : number.convertToJotlin();
    }
}