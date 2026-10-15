package provided.nodes;

import java.util.ArrayList;

import provided.*;


public class IdentifierNode implements JotlinTree{
    public static JotlinToken token1;
    public static JotlinToken token2;

    public IdentifierNode(JotlinToken t1, JotlinToken t2){
        token1 = t1;
        token2 = t2;
    }

    public static IdentifierNode parse(ArrayList<JotlinToken> tokens){
        JotlinToken first = tokens.get(0);
        if(first.getTokenType() == TokenType.Keyword){
            tokens.remove(0);
            if(tokens.get(0).getTokenType() != TokenType.Dot){
                throw new IllegalArgumentException("Expected dot");
            }
            tokens.remove(0);
            JotlinToken second = tokens.remove(0);
            if(second.getTokenType() != TokenType.Id){
                throw new IllegalArgumentException("Expected Id");
            }
            return new IdentifierNode(first, second);
        } else if (first.getTokenType() == TokenType.Id){
            tokens.remove(0);
            if(tokens.get(0).getTokenType() != TokenType.Dot){
                return new IdentifierNode(null, first);
            }
            tokens.remove(0);
            JotlinToken second = tokens.remove(0);
            if(second.getTokenType() != TokenType.Id){
                throw new IllegalArgumentException("Expected Id");
            }
            return new IdentifierNode(first, second);
        } else{
            throw new IllegalArgumentException("Expected Keyword or Id");
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
		throw new UnsupportedOperationException("Unimplemented method 'convertToJava'");
	}

	@Override
	public String convertToJotlin() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'convertToJotlin'");
	}

}
