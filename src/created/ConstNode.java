package created;

import java.util.ArrayList;

import provided.*;

public class ConstNode implements JotlinTree{
    private AmodNode node;
    private KeywordNode keyword;
    private FParamsNode params;
    private BodyNode body;
    private NewLineNode newLine;

    private ConstNode(AmodNode n, KeywordNode k, FParamsNode p, BodyNode b, NewLineNode nl){
        node = n;
        keyword = k;
        params = p;
        body = b;
        newLine = nl;
    }

    public static ConstNode parse(ArrayList<JotlinToken> tokens){
        if(tokens.get(0).getTokenType() != TokenType.Indent){
            throw new IllegalArgumentException("expected tab");
        }
        tokens.remove(0);
         //keywordNode.parse(tokens)
         AmodNode a_mod = AmodNode.parse(tokens);
        //assuming a_mod is not a node
        KeywordNode key = KeywordNode.parse(tokens);
         //keywordNode.parse(tokens)
        FParamsNode fparam = FParamsNode.parse(tokens);
        BodyNode bdy = BodyNode.parse(tokens);
        //body parse(tokens)
        if(tokens.get(0).getTokenType() != TokenType.LineEnd){
            throw new IllegalArgumentException("Expected new line");
        }
        NewLineNode nl = NewLineNode.parse(tokens);
        return new ConstNode(a_mod,key,fparam,bdy,nl);
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
