package created;

import java.util.ArrayList;
import provided.*;


public class WhileNode implements JotlinTree{
    
    private ExprNode expr;
    private BodyNode body;
    private TabNode tabs;

    private WhileNode(TabNode t, ExprNode e, BodyNode b){
        expr = e;
        body = b;
        tabs = t;
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
        tokens.remove(0);
        while(!tokens.isEmpty() && tokens.get(0).getTokenType() == TokenType.LineEnd){
            tokens.remove(0);
        }
        
        BodyNode body = BodyNode.parse(tokens);
        return new WhileNode(tabs,expr, body);
    }

    @Override
    public boolean validate() throws Exception {
        return expr.validate() && body.validate();
    }

    @Override
    public String convertToJava() {
    return "while (" + expr.convertToJava() + ") " + body.convertToJava();
    }

    @Override
    public String convertToJotlin() {
        return tabs.convertToJotlin() + "While [" + expr.convertToJotlin() + "]\n" + body.convertToJotlin();
    }
 
}
