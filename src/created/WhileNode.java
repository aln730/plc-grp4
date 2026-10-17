package created;

import java.util.ArrayList;
import provided.*;

//< while > -> <TBS > While [ < expr >] < NLS > < body >

public class WhileNode implements JotlinTree{
    
    private TabNode tabs;
    private ExprNode expr;
    private NewLineNode newLine;
    private BodyNode body;

    private WhileNode(TabNode t,ExprNode e, NewLineNode nl, BodyNode b){
        tabs = t;
        expr = e;
        body = b;
        newLine = nl;
    }
    
    public static WhileNode parse(ArrayList<JotlinToken> tokens){
        TabNode tabs = TabNode.parse(tokens);
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
        return new WhileNode(tabs,expr, newLine, body);
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
