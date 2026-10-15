package provided.nodes;

import java.util.ArrayList;

import provided.*;


public class WhileNode {
    
    private ExprNode expr;
    private JotlinToken body;

    private WhileNode(ExprNode e, JotlinToken b){
        expr = e;
        body = b;
    }
    
    public static WhileNode parse(ArrayList<JotlinToken> tokens){
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
        
        //BodyNode body = BodyNode.parse(tokens)
        return new WhileNode(expr, null);
    }
 
}
