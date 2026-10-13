package provided;

import java.util.ArrayList;

public class FunccallNode implements JotlinTree{
        public static IdentifierNode name;
        public static JotlinToken[] params;

        public FunccallNode(IdentifierNode n, JotlinToken[] p){
            name = n;
            params = p;
        }

    public static FunccallNode parse(ArrayList<JotlinToken> tokens){
        if(tokens.get(0).getTokenType() != TokenType.FC_Header){
            throw new IllegalArgumentException("Expected Function Call Header");
        }
        tokens.remove(0);
        IdentifierNode name = IdentifierNode.parse(tokens);
        if(tokens.get(0).getTokenType() != TokenType.L_Bracket){
            throw new IllegalArgumentException("Expected left bracket");
        }
        tokens.remove(0);
        //expresion node arraylist called params
        Boolean first = true;
        while(tokens.get(0).getTokenType() != TokenType.R_Bracket){
            if(!first){
                if(tokens.get(0).getTokenType() != TokenType.Comma){
                    throw new IllegalArgumentException("Expected Comma");
                }
                tokens.remove(0);
            }
            first = false;
            //add to params
        }
        if(tokens.get(0).getTokenType() != TokenType.R_Bracket){
            throw new IllegalArgumentException("Expected right bracket");
        }
        tokens.remove(0);
        return new FunccallNode(name, null);
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
