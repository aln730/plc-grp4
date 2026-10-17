package created;

import java.util.ArrayList;
import provided.*;

//< orif > -> <TBS > OrIf [ < expr >] < NLS > < body > < NLS >

public class OrIfNode implements JotlinTree {
    private ExprNode expr;
    private BodyNode body;

    private OrIfNode(ExprNode e, BodyNode b){
        expr = e;
        body = b;
    }
    
    public static OrIfNode parse(ArrayList<JotlinToken> tokens){
        while(!tokens.isEmpty() && tokens.get(0).getTokenType() == TokenType.Indent){
            tokens.remove(0);
        }
        JotlinToken first = tokens.get(0);
        if(first.getTokenType() != TokenType.Keyword || !first.getToken().equals("While")){
            throw new IllegalArgumentException("Expecting keyword or while to start while loop");
        }
        tokens.remove(0);

        first = tokens.get(0);
        if(first.getTokenType() != TokenType.L_Bracket){
            throw new IllegalArgumentException("Expecting left bracket");
        }
        tokens.remove(0);
        ExprNode expr = ExprNode.parse(tokens);
        first = tokens.get(0);
        if(first.getTokenType() != TokenType.R_Bracket){
            throw new IllegalArgumentException("Expecting right bracket");
        }
        while(!tokens.isEmpty() && tokens.get(0).getTokenType() == TokenType.LineEnd){
            tokens.remove(0);
        }
        BodyNode body = BodyNode.parse(tokens);
        while(!tokens.isEmpty() && tokens.get(0).getTokenType() == TokenType.LineEnd){
            tokens.remove(0);
        }
        
        return new OrIfNode(expr, body);
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
