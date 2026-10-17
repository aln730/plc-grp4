package created;
import java.util.ArrayList;
import provided.*;


public class FParamNode implements JotlinTree{
    private JotlinToken id;
    private JotlinToken keyword;
    
    public FParamNode(JotlinToken id, JotlinToken keyword) {
        this.id = id;
        this.keyword = keyword;
    }

    public static FParamNode parse(ArrayList<JotlinToken> tokens) {
        if(tokens.size() < 3) {
            throw new IllegalArgumentException("Not enough tokens for fparam");
        }
        if(tokens.get(0).getTokenType() != TokenType.Id) {
            throw new IllegalArgumentException("Fparam must start with id token");
        }
        JotlinToken first = tokens.removeFirst();
        if(!tokens.get(0).getToken().equals(':')) {
            throw new IllegalArgumentException("Fparam must have a ':' as the second character");
        }
        tokens.removeFirst();
        if(tokens.get(0).getTokenType() != TokenType.Keyword) {
            throw new IllegalArgumentException("Fparam must end with keyword token");
        }
        JotlinToken second = tokens.removeFirst();
        return new FParamNode(first,second);
    }

    @Override
    public boolean validate() throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validate'");
    }

    @Override
    public String convertToJava() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToJava'");
    }

    @Override
    public String convertToJotlin() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToJotlin'");
    }
    
}
