package provided.nodes;

import java.util.ArrayList;

import provided.*;


public class FuncDefNode {
    private JotlinToken node;
    private JotlinToken keyword;
    private IdNode id;
    private JotlinToken[] params;
    private JotlinToken rtrn;
    private JotlinToken body;

    private FuncDefNode(JotlinToken n, JotlinToken k, IdNode i, JotlinToken[] p, JotlinToken r, JotlinToken b){
        node = n;
        keyword = k;
        id = i;
        params = p;
        rtrn = r;
        body = b;
    }

    public static FuncDefNode parse(ArrayList<JotlinToken> tokens){
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
        if(!first.getToken().equals("static")){
            throw new IllegalArgumentException("expected static");
        }
        //assuming static is not a node
        tokens.remove(0);
        first = tokens.get(0);
        if(first.getTokenType() != TokenType.Id){
            throw new IllegalArgumentException("expected id");
        }
        tokens.remove(0);
        first = tokens.get(0);
        try{
            //param parse(tokens) for f params and f return
        } catch (IllegalArgumentException e) {
            throw e;
        }

        while(!tokens.isEmpty() && tokens.get(0).getTokenType() == TokenType.LineEnd){
            tokens.remove(0);
        }

         try{
            //param parse(tokens) for f body
        } catch (IllegalArgumentException e) {
            throw e;
        }

        return new FuncDefNode(null, null, null, null, null, null);
    }
}
