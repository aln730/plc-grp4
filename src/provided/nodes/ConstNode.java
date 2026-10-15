package provided.nodes;

import java.util.ArrayList;

import provided.*;

public class ConstNode implements JotlinTree{
    private JotlinToken node;
    private JotlinToken keyword;
    private JotlinToken[] params;
    private JotlinToken body;

    private ConstNode(JotlinToken n, JotlinToken k, JotlinToken[] p, JotlinToken b){
        node = n;
        keyword = k;
        params = p;
        body = b;
    }

    public static ConstNode parse(ArrayList<JotlinToken> tokens){
        if(tokens.get(0).getTokenType() != TokenType.Indent){
            throw new IllegalArgumentException("expected tab");
        }
        tokens.remove(0);
        JotlinToken first = tokens.get(0);
        if(!first.getToken().equals("a_mod")){
            throw new IllegalArgumentException("expected a_mod");
        }
        //assuming a_mod is not a node
        tokens.remove(0);
        first = tokens.get(0);
        if(first.getTokenType() != TokenType.Keyword){
            throw new IllegalArgumentException("expected keyword");
        }
         //keywordNode.parse(tokens)
        first = tokens.get(0);
        if(first.getTokenType() != TokenType.L_Bracket){
            throw new IllegalArgumentException("expected left bracket");
        }
        //params parse(tokens)
        first = tokens.get(0);
        if(first.getTokenType() != TokenType.R_Bracket){
            throw new IllegalArgumentException("expected right bracket");
        }
        //body parse(tokens)
        
         while(!tokens.isEmpty() && tokens.get(0).getTokenType() == TokenType.LineEnd){
            tokens.remove(0);
        }
        return new ConstNode(null, null, null, null);
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
