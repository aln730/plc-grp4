package created;

import java.util.ArrayList;
import java.util.HashMap;


import provided.*;


public class IdentifierNode implements JotlinTree{
    private JotlinToken token1;
    private JotlinToken token2;

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
    public String evaluateValue() {
       if(token1 != null) {
            if(token1.getToken().equals("Self")) {
                HashMap<String, String[]> scope = SymbolTable.newestScope();
                if (scope.containsKey(token2.getToken())) {
                    return scope.get(token2.getToken())[1];
                }
                return null;
            }
       }
        return SymbolTable.valueOf(token2.getToken());    
    }

    public String evaluateType() {
       if(token1 != null && token1.getToken().equals("Self")) {
            HashMap<String, String[]> scope = SymbolTable.newestScope();
            if (scope.containsKey(token2.getToken())) {
                return scope.get(token2.getToken())[0];
            }
            return null;
       }
        return SymbolTable.typeOf(token2.getToken());
    }

    public String getName() {
        if(token2 != null) {
            return token2.getToken();
        }
        return null;
    }

	@Override
	public boolean validate() throws Exception {
        if(token1 != null && token1.getToken().equals("Printer") && token2 != null && token2.getToken().equals("display")) {
            return true;
        }
        if(token1 != null && token1.getToken().equals("Printer") && token2 != null && token2.getToken().equals("display")) {
            return true;
        }
        return token1 != null && token2 != null && !token1.getToken().isEmpty() && !token2.getToken().isEmpty();
	}

	@Override
    public String convertToJava() {
        if (token1 == null) {
            return token2.getToken();
        }
        return token1.getToken() + "." + token2.getToken();
    }

    @Override
    public String convertToJotlin() {
        if (token1 == null) {
            return (token2 != null? token2.getToken():"");
        }
        return (token1 != null? token1.getToken():"") + "." + (token2 != null? token2.getToken():"");
    }

}
