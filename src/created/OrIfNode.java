package created;

import java.util.ArrayList;
import provided.*;

//< orif > -> <TBS > OrIf [ < expr >] < NLS > < body > < NLS >

public class OrIfNode implements JotlinTree {
    private TabNode tabs;
    private ExprNode expr;
    private NewLineNode newline1;
    private BodyNode body;
    private NewLineNode newline2;

    private OrIfNode(TabNode t,ExprNode e, NewLineNode nl1, BodyNode b, NewLineNode nl2){
        tabs = t;
        newline1 = nl1;
        expr = e;
        body = b;
        newline2 = nl2;

    }
    
    public static OrIfNode parse(ArrayList<JotlinToken> tokens){
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
        NewLineNode nl1 = NewLineNode.parse(tokens);
        BodyNode body = BodyNode.parse(tokens);
        NewLineNode nl2 = NewLineNode.parse(tokens);
        return new OrIfNode(tabs,expr, nl1,body,nl2);
    }

    
    @Override
    public boolean validate() throws Exception {
        return expr != null && expr.validate() && body != null && body.validate();
    }

    @Override
    public String convertToJava() {
        return "else if (" + expr.convertToJava() + ") {\n" + body.convertToJava() + "\n}";
    }

    @Override
    public String convertToJotlin() {
        return "OrIf [" + expr.convertToJotlin() + "]\n" + body.convertToJotlin();
    }

}
