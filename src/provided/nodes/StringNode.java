package provided.nodes;
import java.util.ArrayList;
import provided.*;



public class StringNode implements JotlinTree{
    private JotlinToken token;

    public StringNode(JotlinToken token) {
        this.token = token;
    }
    public static StringNode parse(ArrayList<JotlinToken> tokens) {
        if(tokens.size() < 1) {
            throw new IllegalArgumentException("Not enough ");
        }
        JotlinToken first = tokens.getFirst();
        if(first.getTokenType() == TokenType.String) {
            tokens.removeFirst();
            return new StringNode(first);
        }
        throw new IllegalArgumentException("Expected string token");
    }

    public JotlinToken getToken() {return this.token;}



    public String convertToJotlin() {
        return '"' + token.getToken() + '"';
    }

    public String convertToJava() {
        return '"' + token.getToken() + '"';
    }
    @Override
    public boolean validate() throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validate'");
    }

}