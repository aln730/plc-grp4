package created;

import java.util.ArrayList;
import provided.*;

//< while > -> <TBS > While [ < expr >] < NLS > < body >

public class WhileNode implements JotlinTree{
    
    private ExprNode expr;
    private NewLineNode newLine;
    private BodyNode body;

    private WhileNode(ExprNode e, NewLineNode n, BodyNode b){
        expr = e;
        newLine = n;
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

        NewLineNode newLine = NewLineNode.parse(tokens);
        BodyNode body = BodyNode.parse(tokens);
        return new WhileNode(expr, newLine, body);
    }

    @Override
    public boolean validate() throws Exception {
        return expr.validate() && newLine.validate() && body.validate();
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
